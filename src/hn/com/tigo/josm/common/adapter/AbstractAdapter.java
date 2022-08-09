/**
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.adapter;

import java.io.Closeable;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import hn.com.tigo.josm.common.adapter.config.AdapterConfig;
import hn.com.tigo.josm.common.adapter.dto.ParameterType;
import hn.com.tigo.josm.common.adapter.dto.TaskRequestType;
import hn.com.tigo.josm.common.adapter.dto.TaskResponseType;
import hn.com.tigo.josm.common.cache.ObjectFactoryCache;
import hn.com.tigo.josm.common.exceptions.AdapterException;
import hn.com.tigo.josm.common.exceptions.ConfigurationException;
import hn.com.tigo.josm.common.exceptions.enumerators.AdapterErrorCode;
import hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote;
import hn.com.tigo.josm.common.interfaces.MonitoringManagerLocal;
import hn.com.tigo.josm.common.interfaces.producer.InterfaceFactory;
import hn.com.tigo.josm.common.jmx.event.ConnectionRefusedEvent;
import hn.com.tigo.josm.common.jmx.event.ConnectionRefusedEventType;
import hn.com.tigo.josm.common.jmx.event.CreateDriverEvent;
import hn.com.tigo.josm.common.jmx.event.UseDriverEvent;
import hn.com.tigo.josm.common.locator.ServiceLocator;
import hn.com.tigo.josm.common.locator.ServiceLocatorException;

/**
 * The AbstractAdapter represents the basic common behavior for all adapters.
 *
 * @author Camilo Gutierrez <mailto:cgutierrez@stefanini.com />
 * @author Harold Castillo
 * @version 2.0.0
 * @param <D>
 *            Generic type that represents the driver's type.
 * @since 07/10/2014 17:53:31
 */
public abstract class AbstractAdapter<D> implements AbstractAdapterMXBean {

	/**
	 * This attribute contains an instance of log4j logger for AbstractAdapter.
	 */
	private static final transient Logger LOGGER = Logger.getLogger(AbstractAdapter.class);

	/** Attribute that determine base name for JMX beans. */
	private static final String JMX_CONFIG_BASE = "hn.com.tigo.josm.orchestrator.adapter:type=%s";

	/** Attribute that determine base name for monitoring beans. */
	private static final String JMX_METRICS_BASE = "hn.tigo.com.josm.{0}:type={1}";

	/** Stores the collection of active drivers in a map. */
	private LinkedList<DriverReference<D>> driversPoolFreeList;

	/** Attribute that determine configurationType. */
	private AdapterConfig configurationType;	

	/** Attribute that determine platformMBeanServer. */
	private MBeanServer platformMBeanServer;

	/** Attribute that determine monitoring manager. */
	@EJB
	private MonitoringManagerLocal monitoringManager;

	/** The adapter name. */
	private String adapterSimpleName;

	/** The {@link Lock} for controlling access to the driver pool. */
	private Lock driverPoolLock;

	/** The driver version. */
	private long driverVersion;

	/** The the object name of an MBean for registering in JMX the adapter. */
	private ObjectName jmxObject;
	
	/** Calendar attribute that handles the cache expiration of an adapter. */
	private Calendar cacheExpiration;

	/**
	 * Instantiates a new abstract adapter.
	 */
	public AbstractAdapter() {
		this.adapterSimpleName = parseName(this.getClass().getSimpleName());
		this.driversPoolFreeList = new LinkedList<DriverReference<D>>();
		this.driverPoolLock = new ReentrantLock();

		try {
			final String name = String.format(JMX_CONFIG_BASE, adapterSimpleName);
			this.jmxObject = new ObjectName(name);
			this.configurationType = new AdapterConfig();
		} catch (MalformedObjectNameException e) {
			LOGGER.warn("The JMX ObjectName not has been created: ", e);
		}

	}

	/**
	 * Return the adapter name implementation .
	 *
	 * @param name
	 *            the {@code this.getClass().getSimpleName()}
	 * @return the specific adapter's name
	 */
	private String parseName(final String name) {
		final int index = name.indexOf("_");
		if (index != -1) {
			return name.substring(0, index);
		}

		return name;
	}

	/**
	 * Method in charge of get the configuration properties from ConfigurationJosm.
	 *
	 * @param adapterConfig
	 *            the request JSON store in the database
	 * @return the c
	 * @throws AdapterException
	 *             the {@link AdapterException} it's throw if exist some mistakes
	 *             obtaining the configuration or accessing to the
	 *             common-configuration component
	 */
	protected AdapterConfig loadConfiguration(final String adapterConfig) throws AdapterException {
		try {
			final ServiceLocator serviceLocator = ServiceLocator.getInstance();
			final ConfigurationJosmRemote configurationJosm = serviceLocator
					.getService(InterfaceFactory.COMMON_CONFIGURATION_REMOTE);
			final String result = configurationJosm.getCurrentConfiguration(adapterConfig);
			final ObjectMapper objectMapper = ObjectFactoryCache.getInstance().getObjectMapper();
			final AdapterConfig response = objectMapper.readValue(result, AdapterConfig.class);

			LOGGER.info(result);
			return response;
		} catch (ServiceLocatorException e){
			LOGGER.error(AdapterErrorCode.INTERNAL_EXECUTOR_ERROR.getMessage(), e);
			throw new AdapterException(AdapterErrorCode.INTERNAL_EXECUTOR_ERROR, e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(AdapterErrorCode.JSON_CONFIGURATION_ERROR.getMessage(), e);
			throw new AdapterException(AdapterErrorCode.JSON_CONFIGURATION_ERROR, e.getMessage());
		} catch (ConfigurationException e) {
			LOGGER.error(AdapterErrorCode.COMMON_CONFIGURATION_ERROR.getMessage(), e);
			throw new AdapterException(AdapterErrorCode.COMMON_CONFIGURATION_ERROR, e.getMessage());
		}

	}

	/**
	 * Gets the configuration properties. Checks if configuration cache has expired
	 * to reload it otherwise retrieves the cached one.
	 *
	 * @return the properties object is an AdapterConfig instance that contains
	 *         parameters like configTimeOut or maxThroghput.
	 * @throws AdapterException
	 *             the {@link AdapterException} it's throw if exist a error in
	 *             {@link #loadConfiguration(String)}
	 */
	public AdapterConfig getConfigurationType() throws AdapterException {
		
		if (this.cacheExpiration == null || this.cacheExpiration.before(Calendar.getInstance())) {			
			this.configurationType = loadConfiguration(adapterSimpleName);
			this.validateConfigExpiration();
			this.cacheExpiration = Calendar.getInstance();
			this.cacheExpiration.add(Calendar.MILLISECOND, this.configurationType.getConfigExpiration());
			this.initDriverPool();
		}

		return configurationType;
	}
	
	/**
	 * Validate the expiration cache attribute from DB configuration.
	 *
	 * @throws AdapterException the adapter exception
	 */
	private void validateConfigExpiration() throws AdapterException {
		if(this.configurationType.getConfigExpiration() <= 0) {
			final String message = "The configuration expiration must be higher than 0.";
			LOGGER.error(message);
			throw new AdapterException(AdapterErrorCode.PARAMETERS_ERROR, message);
		}
	}

	/**
	 * Initializes the {@link LinkedList<DriverReference<D>>}.
	 */
	private void initDriverPool() {

		driverPoolLock.lock();
		try {

			this.closeDrivers();

			driversPoolFreeList.clear();
			driverVersion = System.currentTimeMillis();

			for (int i = 0; i < this.configurationType.getDriverPoolSize(); i++) {
				driversPoolFreeList.add(new DriverReference<D>(this, driverVersion));
			}

		} finally {
			driverPoolLock.unlock();
		}
		registerDriverEvent();
		registerUseDrivers();
	}

	/**
	 * Close all the drivers in the pool.
	 */
	public void closeDrivers() {

		try {
			for (DriverReference<D> ref : driversPoolFreeList) {
				if (ref.getDriver() != null && ref.getDriver() instanceof Closeable) {
					((Closeable) ref.getDriver()).close();
				}
			}
		} catch (AdapterException | IOException e) {
			LOGGER.error("Closing Drivers Failed: " + e.getMessage(), e);
		}
	}

	/**
	 * Method that generalizes the driver retrieval logic.
	 *
	 * @return Returns an adapter's driver
	 * @throws AdapterException
	 *             the {@link AdapterException} it's throw if there aren't drivers
	 *             in the pool
	 */
	public DriverReference<D> getDriver() throws AdapterException {

		DriverReference<D> sessionObject = null;
		driverPoolLock.lock();
		try {
			if (!driversPoolFreeList.isEmpty()) {
				sessionObject = driversPoolFreeList.pop();
			} else {
				registerConnectionRefused(ConnectionRefusedEventType.DRIVER);
				LOGGER.error("No available drivers on " + adapterSimpleName);
				throw new AdapterException(AdapterErrorCode.MAX_SESSION_ERROR.getError(),
						"No available drivers on " + adapterSimpleName);
			}
		} finally {
			driverPoolLock.unlock();
			registerUseDrivers();
		}

		return sessionObject;
	}

	/**
	 * Tells the adapter this driver is free now.
	 *
	 * @param driverObject
	 *            the driver reference
	 */
	public void freeDriver(final DriverReference<D> driverObject) {

		driverPoolLock.lock();
		try {
			final long freeVersion = driverObject.getVersion();
			if (driverVersion == freeVersion) {
				driversPoolFreeList.push(driverObject);
			} else {
				LOGGER.warn("Reset driver pool event detected, discarding driver " + adapterSimpleName);
			}
		} finally {
			driverPoolLock.unlock();
			registerUseDrivers();
		}

	}

	/**
	 * Creates a new adapter driver instance.
	 *
	 * @return the driver
	 * @throws AdapterException
	 *             the {@link AdapterException} it's throw if there a error in the
	 *             adapter implementation.
	 */
	protected abstract D createDriver() throws AdapterException;

	/**
	 * Gets the pool drivers free list size.
	 *
	 * @return the pool drivers free list size
	 */
	public int getPoolDriversFreeListSize() {
		return driversPoolFreeList.size();
	}

	/**
	 * Gets a parameter value from a parameter name.
	 * 
	 * @param parameterName
	 *            The name of a parameter
	 * @param taskReq
	 *            The request of the task
	 * @return the value from the parameter

	 */
	public static String getParameterValue(final String parameterName, final TaskRequestType taskReq) {
		String ret = null;
		for (ParameterType param : taskReq.getParameters().getParameter()) {
			if (param.getName() != null && param.getValue() != null && param.getName().equals(parameterName)) {
				ret = param.getValue();
				break;
			}
		}
		return ret;
	}

	/**
	 * Gets the parameter value.
	 *
	 * @param parameterName
	 *            the parameter name
	 * @param taskRes
	 *            the response of the task
	 * @return the parameter value
	 */
	public static String getParameterValue(final String parameterName, final TaskResponseType taskRes) {
		String ret = null;
		for (ParameterType param : taskRes.getParameters().getParameter()) {
			if (param.getName() != null && param.getValue() != null && param.getName().equals(parameterName)) {
				ret = param.getValue();
				break;
			}
		}
		return ret;
	}

	/**
	 * Gets the parameter value list from parameter name.
	 *
	 * @param parameterName            the parameter name
	 * @param taskReq            the request of the task
	 * @return the parameter value list
	 */
	public static List<String> getParameterValueList(final String parameterName, final TaskRequestType taskReq) {
		final List<String> values = new ArrayList<String>();
		for (ParameterType param : taskReq.getParameters().getParameter()) {
			if (param.getName() != null && param.getValue() != null && param.getName().equals(parameterName)) {
				values.add(param.getValue());
			}
		}
		return values;
	}

	/**
	 * Method responsible to gets the parameters in a HashMap.
	 *
	 * @param parameterName
	 *            the parameter name
	 * @param taskRequestType
	 *            the request of the task
	 * @return a {@link Map} of selected parameters
	 */
	public static Map<String, String> getParameterMap(final String parameterName, final TaskRequestType taskRequestType) {
		final Map<String, String> parametersMap = new HashMap<String, String>();
		for (ParameterType param : taskRequestType.getParameters().getParameter()) {
			if (param.getName() != null && param.getValue() != null && param.getName().startsWith(parameterName)) {
				parametersMap.put(param.getName(), param.getValue());
			}
		}
		return parametersMap;
	}

	/**
	 * Gets the {@link TaskRequestType} in a {@link HashMap}.
	 *
	 * @param taskRequestType
	 *            the request of the task
	 * @return a {@link Map} of the request parameters.
	 */
	public static Map<String, String> getParameterMap(final TaskRequestType taskRequestType) {
		final Map<String, String> parametersMap = new HashMap<String, String>();
		for (ParameterType param : taskRequestType.getParameters().getParameter()) {
			parametersMap.put(param.getName(), param.getValue());
		}
		return parametersMap;
	}

	/**
	 * Register in JMX. This method register a bean in the jmx server.
	 */
	@PostConstruct
	public void registerInJMX() {

		try {
			platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

			if (platformMBeanServer.isRegistered(jmxObject)) {
				platformMBeanServer.unregisterMBean(jmxObject);
			}
			platformMBeanServer.registerMBean(this, jmxObject);

			this.getConfigurationType();

		} catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException
				| InstanceNotFoundException | AdapterException e) {
			LOGGER.error("Problem during registration of " + adapterSimpleName + " into JMX:", e);
			throw new IllegalStateException(
					"Problem during registration of " + adapterSimpleName + " into JMX:" + e.getMessage(), e);
		}

	}

	/**
	 * Unregister from JMX server.
	 */
	@PreDestroy
	public void unregisterFromJMX() {
		try {
			final Set<ObjectName> objectNames = platformMBeanServer.queryNames(jmxObject, null);

			for (ObjectName obname : objectNames) {
				if (obname.equals(jmxObject)) {
					platformMBeanServer.unregisterMBean(jmxObject);
					final String name = MessageFormat.format(JMX_METRICS_BASE, adapterSimpleName, adapterSimpleName);
					final ObjectName jmxMetricsObjet = new ObjectName(name);
					platformMBeanServer.unregisterMBean(jmxMetricsObjet);
				}
			}
		} catch (MBeanRegistrationException | InstanceNotFoundException | MalformedObjectNameException e) {
			throw new IllegalStateException("Problem during unregistration of " + adapterSimpleName + " into JMX:" + e,
					e);
		}
	}

	/**
	 * Gets the adapter simple name.
	 *
	 * @return the adapter simple name
	 */
	public String getAdapterSimpleName() {
		return adapterSimpleName;
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.adapter.AbstractAdapterMXBean#resetConfiguration()
	 */
	@Override
	public void resetConfiguration() {
		this.cacheExpiration = Calendar.getInstance();
	}

	/**
	 * Method responsible to register total drivers on JMX event monitor.
	 */
	public void registerDriverEvent() {
		final CreateDriverEvent event = new CreateDriverEvent();
		event.setComponent(adapterSimpleName);
		event.setObjectName(adapterSimpleName);
		event.setTotalDriver(this.configurationType.getDriverPoolSize());
		monitoringManager.receiveEvent(event);
	}

	/**
	 * Method responsible to register use drivers in JMX monitoring.
	 */
	public void registerUseDrivers() {
		final UseDriverEvent event = new UseDriverEvent();
		event.setComponent(adapterSimpleName);
		event.setObjectName(adapterSimpleName);
		event.setIdlesDrivers(getPoolDriversFreeListSize());
		monitoringManager.receiveEvent(event);
	}

	/**
	 * Register connection refused.
	 *
	 * @param type
	 *            the {@link ConnectionRefusedEventType} instance
	 */
	public void registerConnectionRefused(final ConnectionRefusedEventType type) {
		final ConnectionRefusedEvent event = new ConnectionRefusedEvent(type);
		event.setComponent(adapterSimpleName);
		event.setObjectName(adapterSimpleName);
		monitoringManager.receiveEvent(event);
	}

}
