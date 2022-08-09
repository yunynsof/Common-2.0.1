package hn.com.tigo.josm.common.adapter.config;

import java.util.HashMap;
import java.util.Map;

/**
 * This class allows to expose the connection configuration for all the service
 * in an adapter.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version 1.0.0
 * @since 21/05/2018 03:44:59 PM
 */
public class ConnectionConfig {

	/** Attribute that determine parameters. */
	private Map<String, String> parameters;

	/**
	 * Creates a new instance of the class.
	 */
	public ConnectionConfig() {
		this.parameters = new HashMap<>();
	}

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

}
