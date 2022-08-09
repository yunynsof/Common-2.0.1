package hn.com.tigo.josm.common.jmx.event;

import hn.com.tigo.josm.common.dto.JOSMEvent;

import java.io.Serializable;

/**
 * The class EndpointEvent contains the <Usage of this class> for Common
 * project.
 *
 * @author
 * @version 1.0.0
 * @since Sep 1, 2017
 */
public class EndpointEvent extends JOSMEvent implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Attribute that stores the _transactionInMillis. */
	private long _lastEndpointTransactionInMillis;

	/**
	 * Instantiates a new endpoint event.
	 */
	public EndpointEvent() {
		super(MonitoringEventType.ENDPOINT, MXBeanType.DRIVER);
	}

	/**
	 * Gets the last endpoint transaction in millis.
	 *
	 * @return the last endpoint transaction in millis
	 */
	public long get_lastEndpointTransactionInMillis() {
		return _lastEndpointTransactionInMillis;
	}

	/**
	 * Sets the last endpoint transaction in millis.
	 *
	 * @param _lastEndpointTransactionInMillis
	 *            the new last endpoint transaction in millis
	 */
	public void set_lastEndpointTransactionInMillis(long _lastEndpointTransactionInMillis) {
		this._lastEndpointTransactionInMillis = _lastEndpointTransactionInMillis;
	}

}
