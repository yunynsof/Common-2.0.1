/**
 * MonitoringManagerRemote.java
 * Common
 * Copyright (C) Tigo Honduras
*/
package hn.com.tigo.josm.common.interfaces;

import hn.com.tigo.josm.common.dto.JOSMEvent;

import javax.ejb.Remote;

/**
 * MonitoringManagerRemote. This interface allow remote access to the
 * MonitorManager implementation
 *
 * @author Jose David Martinez Rico <mailto:jdmartinez@stefanini.com />
 * @version
 * @see
 * @since 24/02/2015 02:54:20 PM 2015
 */
@Remote
public interface MonitoringManagerRemote {

	/**
	 * Receive event to monitoring with your result info.
	 *
	 * @param event
	 *            the event
	 */
	void receiveEvent(final JOSMEvent event);

	/**
	 * Method responsible to gets the TPS information (Transactions per second)
	 *
	 * @param component
	 *            the component
	 * @param objectName
	 *            the object name
	 * @return the tps
	 */
	long getTPS(final String component, final String objectName);

	/**
	 * Method responsible to unregister from jmx the MBean.
	 *
	 * @param component
	 *            the component
	 * @param objectName
	 *            the object name
	 */
	public void unregisterFromJMX(final String component, final String objectName);

}
