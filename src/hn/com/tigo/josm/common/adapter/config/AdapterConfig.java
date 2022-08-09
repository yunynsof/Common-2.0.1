package hn.com.tigo.josm.common.adapter.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The AdapterConfig is a class that contains the general configuration for
 * adapters.
 *
 * @author Andres Felipe Hinestroza <mailto:afhinestroza@stefanini.com />
 * @version 1.0.0
 * @since 18/04/2017 09:43:27 AM
 */
public class AdapterConfig implements Serializable {

	/** Attribute that determine a Constant of serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Attribute that determine configExpiration. */
	private int configExpiration;

	/** Attribute that determine maxThroughput. */
	private int maxThroughput;

	/** Attribute that determine driverPoolSize. */
	private int driverPoolSize;

	/** Attribute that determine driverConfig. */
	private DriverConfig driverConfig;

	/** Attribute that determine the configuration mock tasks. */
	private List<MockTaskConfig> mockTaskDelay;

	/**
	 * Construct method that instantiates a new adapter configuration DTO.
	 */
	public AdapterConfig() {
		this.mockTaskDelay = new ArrayList<>();
	}

	/**
	 * Method responsible to gets the configuration expiration time.
	 *
	 * @return the config expiration
	 */
	public int getConfigExpiration() {
		return configExpiration;
	}

	/**
	 * Method responsible to sets the configuration expiration time.
	 *
	 * @param configExpiration
	 *            the new config expiration
	 */
	public void setConfigExpiration(int configExpiration) {
		this.configExpiration = configExpiration;
	}

	/**
	 * Method responsible to gets the max throughput.
	 *
	 * @return the max throughput
	 */
	public int getMaxThroughput() {
		return maxThroughput;
	}

	/**
	 * Method responsible to sets the max throughput.
	 *
	 * @param maxThroughput
	 *            the new max throughput
	 */
	public void setMaxThroughput(int maxThroughput) {
		this.maxThroughput = maxThroughput;
	}

	/**
	 * Method responsible to gets the driver pool size.
	 *
	 * @return the driver pool size
	 */
	public int getDriverPoolSize() {
		return driverPoolSize;
	}

	/**
	 * Method responsible to sets the driver pool size.
	 *
	 * @param driverPoolSize
	 *            the new driver pool size
	 */
	public void setDriverPoolSize(int driverPoolSize) {
		this.driverPoolSize = driverPoolSize;
	}

	/**
	 * Gets the driver config.
	 *
	 * @return the driver config
	 */
	public DriverConfig getDriverConfig() {
		return driverConfig;
	}

	/**
	 * Sets the driver config.
	 *
	 * @param driverConfig
	 *            the new driver config
	 */
	public void setDriverConfig(DriverConfig driverConfig) {
		this.driverConfig = driverConfig;
	}

	/**
	 * Method responsible to gets the mock task delay list.
	 *
	 * @return the mock task delay list
	 */
	public List<MockTaskConfig> getMockTaskDelay() {
		return mockTaskDelay;
	}

}
