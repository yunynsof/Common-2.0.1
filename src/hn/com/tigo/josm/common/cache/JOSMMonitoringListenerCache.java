package hn.com.tigo.josm.common.cache;

import hn.com.tigo.josm.common.data.JsonLoader;
import hn.com.tigo.josm.common.dto.ConfigurationListener;
import hn.com.tigo.josm.common.exceptions.ConfigurationException;

import java.util.Hashtable;
import java.util.Map;

import javax.json.JsonObject;

/**
 * ListenerCache.
 *
 * @author Harold Castillo
 * @version 1.0
 * @since Oct 17, 2016 5:46:49 PM
 */
public final class JOSMMonitoringListenerCache {

	/** The listener cache. */
	private static JOSMMonitoringListenerCache listenerCache = new JOSMMonitoringListenerCache();

	/** The json loader. */
	private final JsonLoader jsonLoader;

	/** The configuration map. */
	private Map<String, ConfigurationListener> configurationMap;

	/**
	 * Instantiates a new listener cache.
	 */
	private JOSMMonitoringListenerCache() {
		jsonLoader = new JsonLoader(84000000, "./properties/monitoring/listener.json");
		configurationMap = new Hashtable<String, ConfigurationListener>();
	}

	/**
	 * Gets the single instance of ListenerCache.
	 *
	 * @return single instance of ListenerCache
	 */
	public static JOSMMonitoringListenerCache getInstance() {
		return listenerCache;
	}

	/**
	 * Gets the configuration.
	 *
	 * @param objectName
	 *            the object name
	 * @return the configuration
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public ConfigurationListener getConfiguration(final String objectName) throws ConfigurationException {

		if (!configurationMap.containsKey(objectName)) {
			final JsonObject config = jsonLoader.retrieve().getJsonObject("config");
			final JsonObject jsonConfig = config.getJsonObject(objectName);
			final ConfigurationListener configuration = new ConfigurationListener(
					jsonConfig.getJsonNumber("threshold").longValue(),
					jsonConfig.getJsonNumber("deltaTimeMillis").intValue(),
					jsonConfig.getJsonNumber("productId").longValue(),
					jsonConfig.getJsonString("orderType").getString());
			configurationMap.put(objectName, configuration);
		}

		return configurationMap.get(objectName);
	}

	/**
	 * Reset.
	 */
	public void reset() {
		jsonLoader.reset();
		configurationMap = new Hashtable<String, ConfigurationListener>();
	}

}
