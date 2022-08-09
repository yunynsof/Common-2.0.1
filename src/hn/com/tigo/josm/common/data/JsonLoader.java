package hn.com.tigo.josm.common.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.log4j.Logger;

import hn.com.tigo.josm.common.cache.Cache;
import hn.com.tigo.josm.common.exceptions.ConfigurationException;
import hn.com.tigo.josm.common.interfaces.ConfigurationJosmRemote;
import hn.com.tigo.josm.common.interfaces.producer.InterfaceFactory;
import hn.com.tigo.josm.common.locator.ServiceLocator;
import hn.com.tigo.josm.common.locator.ServiceLocatorException;

/**
 * The class JsonLoader contains the <Usage of this class> for Common project.
 *
 * @author
 * @version 1.0.0
 * @since Sep 1, 2017
 */
public class JsonLoader extends Cache<JsonObject, ConfigurationException> {

	/** This attribute will store an instance of log4j for Scheduler class. */
	private static final Logger LOGGER = Logger.getLogger(JsonLoader.class);

	/**
	 * Instantiates a new json loader.
	 */
	public JsonLoader() {
		super();
	}

	/**
	 * Instantiates a new json loader.
	 *
	 * @param path
	 *            the path
	 */
	public JsonLoader(String path) {
		super(path);
	}

	/**
	 * Instantiates a new json loader.
	 *
	 * @param expiration
	 *            the expiration
	 * @param path
	 *            the path
	 */
	public JsonLoader(int expiration, String path) {
		super(expiration, path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hn.com.tigo.josm.common.cache.Cache#loadData()
	 */
	public JsonObject loadData() throws ConfigurationException {

		JsonObject parameter = null;

		try {
			final ServiceLocator serviceLocator = ServiceLocator.getInstance();
			final ConfigurationJosmRemote config = serviceLocator
					.getService(InterfaceFactory.COMMON_CONFIGURATION_REMOTE);
			final byte[] a = config.getFileFromSystem(this.path);
			parameter = deserialize(a);
			LOGGER.info("JsonObject loadData :::::::: " + parameter);
		} catch (ServiceLocatorException | IOException e1) {
			throw new ConfigurationException("Load configuration error",
					"Failed to load the configuration for the path ".concat(this.path));
		}

		return parameter;
	}

	/**
	 * Load data.
	 *
	 * @param path
	 *            the path
	 * @return the json object
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public JsonObject loadData(final String path) throws ConfigurationException {
		this.path = path;
		return loadData();
	}

	/**
	 * Deserialize.
	 *
	 * @param content
	 *            the content
	 * @return the json object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private JsonObject deserialize(final byte[] content) throws IOException {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(content);
				JsonReader reader = Json.createReader(bais)) {
			return reader.readObject();
		}
	}

}
