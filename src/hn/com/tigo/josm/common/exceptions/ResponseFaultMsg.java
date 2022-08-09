
package hn.com.tigo.josm.common.exceptions;

import hn.com.tigo.josm.subscription.dto.MessageFaultType;

import javax.ejb.ApplicationException;
import javax.xml.ws.WebFault;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.2.8 Generated source
 * version: 2.2
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Sep 1, 2017
 */
@WebFault(name = "MessageFault", targetNamespace = "http://tigo.com/josm/subscription/services/subscription/v1/schema")
@ApplicationException(rollback = true)
public class ResponseFaultMsg extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Java type that goes as soapenv:Fault detail element.
	 * 
	 */
	private MessageFaultType faultInfo;

	/**
	 * Instantiates a new response fault msg.
	 *
	 * @param message
	 *            the message
	 * @param faultInfo
	 *            the fault info
	 */
	public ResponseFaultMsg(String message, MessageFaultType faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	/**
	 * Instantiates a new response fault msg.
	 *
	 * @param message
	 *            the message
	 * @param faultInfo
	 *            the fault info
	 * @param cause
	 *            the cause
	 */
	public ResponseFaultMsg(String message, MessageFaultType faultInfo, Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	/**
	 * Gets the fault info.
	 *
	 * @return returns fault bean:
	 *         hn.com.tigo.josm.subscription.schedule.client.MessageFaultType
	 */
	public MessageFaultType getFaultInfo() {
		return faultInfo;
	}

	/**
	 * Creates the fault.
	 *
	 * @param error
	 *            the error
	 * @param message
	 *            the message
	 * @return the response fault msg
	 */
	public static ResponseFaultMsg createFault(final int error, final String message) {

		final MessageFaultType faultType = new MessageFaultType();
		faultType.setError(error);
		faultType.setDescription(message);
		final ResponseFaultMsg fault = new ResponseFaultMsg(message, faultType);

		return fault;

	}

	/**
	 * Creates the fault.
	 *
	 * @param error
	 *            the error
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 * @return the response fault msg
	 */
	public static ResponseFaultMsg createFault(final int error, final String message, final Throwable cause) {

		final MessageFaultType faultType = new MessageFaultType();
		faultType.setError(error);
		faultType.setDescription(message);
		final ResponseFaultMsg fault = new ResponseFaultMsg(message, faultType, cause);

		return fault;

	}

}
