/**
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.adapter.config;

import java.util.HashMap;
import java.util.Map;

/**
 * The DriverConfig is a DTO that contains the information for general driver
 * configuration.
 *
 * @author Andres Felipe Hinestroza <mailto:afhinestroza@stefanini.com />
 * @version 1.0.0
 * @since 18/04/2017 09:47:06 AM
 */
public class DriverConfig {

	/** Attribute that determine the driver timeout. */
	private int timeout;

	/** Attribute that determine connections. */
	private Map<String, ConnectionConfig> connections;

	/**
	 * Construct method that instantiates a new driver configuration.
	 */
	public DriverConfig() {
		this.connections = new HashMap<>();
	}

	/**
	 * Gets the driver timeout.
	 *
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * Sets the new timeout.
	 *
	 * @param timeout the new timeout
	 */
	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}

	/**
	 * Gets the connections.
	 *
	 * @return the connections
	 */
	public Map<String, ConnectionConfig> getConnections() {
		return connections;
	}

}
