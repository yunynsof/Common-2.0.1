/**
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.adapter;

import java.io.Serializable;

import hn.com.tigo.josm.common.exceptions.AdapterException;

/**
 * This class encapsulates the drivers reference of the adapters in the JVM
 * where it is running.
 *
 * @author Harold Castillo
 * @version 1.0.0
 * @param <D>
 *            Generic type that represents the driver's type.
 * @since 22/05/2018 12:45:22 PM
 */
public class DriverReference<D> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3489774581940645580L;

	/** The driver instance. */
	private D driver;

	/** The {@link AbstractAdapter} instance. */
	private AbstractAdapter<D> adapter;

	/** The reference version. */
	private long version;

	/**
	 * Instantiates a new driver reference.
	 *
	 * @param adapter
	 *            the adapter that allow crate a driver
	 * @param version
	 *            the version of the reference
	 */
	public DriverReference(final AbstractAdapter<D> adapter, final long version) {
		this.adapter = adapter;
		this.version = version;
	}

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 * @throws AdapterException
	 *             the adapter exception
	 */
	public D getDriver() throws AdapterException {
		if (driver == null) {
			driver = adapter.createDriver();
		}

		return driver;
	}

	/**
	 * Clear driver reference.
	 */
	public void clearDriver() {
		driver = null;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

}
