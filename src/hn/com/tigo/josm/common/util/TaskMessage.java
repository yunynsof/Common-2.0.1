/**
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.util;

import java.net.InetAddress;

import hn.com.tigo.josm.common.adapter.dto.TaskRequestType;
import hn.com.tigo.josm.common.adapter.task.TaskExecutor;
import hn.com.tigo.josm.common.exceptions.AdapterException;

/**
 * TaskMessage store the message used 
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @author Harold Castillo
 * 
 * @version 1.0.0
 * @since 21/05/2018 04:39:07 PM
 */
public enum TaskMessage {

	/** Define message when the task it's started. */
	INIT_TASK("Init task %s TransactionId: %s Client IP: %s"),

	/** Define message when the task it's finished. */
	END_TASK("Execution of the %s task completed"),
	
	/** Define message error when an {@link AdapterException} it's throw */
	ADAPTER_ERROR("Error in task %s: %s"),
	
	/** Define message error when there are some exception with the thread execution. */
	THREAD_ERROR("Thread execution exception in task %s: %s"),
	
	/** Define message when the request timeout parameter it's assigned. */
	REQUEST_TIMEOUT("Request timeout set: %s"),
	
	/** Define message for printing the value of a specific {@link TaskRequestType} parameter. */
	PARAMETER("Parameter <%s, %s>"),
	
	/** Define message error when fail the call to the {@link InetAddress#getAddress()} */
	UNKNOWN_HOST_ERROR("The IP address of a host could not be determined: %s"),
	
	/** Define message when a call to the {@link TaskExecutor} ends. */
	CLOSE_TASK_CAll("Closing connection thread from %s call");
	
	/** Attribute that determine the message value . */
	private final String value;

	/**
	 * Enumerator constructor.
	 *
	 * @param value
	 *            the message value
	 */
	private TaskMessage(final String value) {
		this.value = value;
	}

	/**
	 * Gets the message value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
