package hn.com.tigo.josm.common.broker.compiler.task;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import hn.com.tigo.josm.common.cache.Cache;
import hn.com.tigo.josm.common.exceptions.ConfigurationException;
import hn.com.tigo.josm.common.exceptions.OrchestratorException;
import hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote;
import hn.com.tigo.josm.common.interfaces.producer.InterfaceFactory;
import hn.com.tigo.josm.common.locator.ServiceLocator;
import hn.com.tigo.josm.common.locator.ServiceLocatorException;

/**
 * ScriptTaskCache.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version
 * @see
 * @since 9/04/2018 04:54:34 PM 2018
 */
public class ScriptTaskCache extends Cache<ScriptTaskConfig, OrchestratorException> {

	/** Attribute that determine log. */
	private static final transient Logger LOGGER = Logger.getLogger(ScriptTaskCache.class);

	/** Attribute that determine a Constant of CONFIGURATION. */
	private static final String CONFIGURATION = "ScriptTaskConfig";

	/** Attribute that determine a Constant of CACHE_SCRIPT. */
	private static final ScriptTaskCache CACHE_SCRIPT = new ScriptTaskCache();

	/**
	 * Instantiates a new script task cache.
	 */
	public ScriptTaskCache() {
		super(84000000, CONFIGURATION);
	}

	/**
	 * Gets the single instance of ScriptTaskCache.
	 *
	 * @return single instance of ScriptTaskCache
	 */
	public static ScriptTaskCache getInstance() {
		return CACHE_SCRIPT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hn.com.tigo.josm.common.cache.Cache#loadData()
	 */
	@Override
	protected ScriptTaskConfig loadData() throws OrchestratorException {
		final ServiceLocator locator = ServiceLocator.getInstance();
		final ObjectMapper objectMapper = new ObjectMapper();
		ScriptTaskConfig config = null;

		try {
			final ConfigurationJosmRemote configurationJOSM = locator
					.getService(InterfaceFactory.COMMON_CONFIGURATION_REMOTE);
			final String result = configurationJOSM.getCurrentConfiguration(CONFIGURATION);
			config = objectMapper.readValue(result, ScriptTaskConfig.class);
		} catch (ServiceLocatorException | ConfigurationException | IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new OrchestratorException(e.getMessage(), e);
		}

		return config;
	}

}
