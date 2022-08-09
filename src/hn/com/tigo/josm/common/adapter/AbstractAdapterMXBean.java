/**
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.adapter;

/**
 * The interface AbstractAdapterMXBean contains the reset configuration method
 * for exposing in the JMX.
 *
 * @author Harold Castillo
 * @version 1.0.0
 * @since 22/05/2018 12:36:01 PM
 */
public interface AbstractAdapterMXBean {

	/**
	 * Reset configuration in JMX.
	 */
	public void resetConfiguration();

}
