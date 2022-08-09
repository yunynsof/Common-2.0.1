/**
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.adapter.task;

import hn.com.tigo.josm.common.adapter.dto.TaskRequestType;
import hn.com.tigo.josm.common.adapter.dto.TaskResponseType;
import hn.com.tigo.josm.common.exceptions.AdapterException;

import javax.ejb.Remote;

/**
 * Remote interface that defines signatures for the execution of
 * {@link AbstractTask} implementation.
 *
 * @author Jimmy Muchachasoy <mailto:jamuchavisoy@stefanini.com />
 * @author Harold Castillo
 * @version 1.0.1
 * @see {@link AbstractTask}
 * @since 06/10/2014 03:47:00 PM
 */
@Remote
public interface Task {

	/**
	 * Method responsible for execute the implementation task.
	 * 
	 * @param taskRequestType
	 *            The {@link TaskRequestType} instance contains all the parameters
	 *            for a task's implementation.
	 * @return TaskResponseType the output parameters of a specific task.
	 * @throws AdapterException
	 *             thrown when task's logic validations doesn't pass.
	 */
	TaskResponseType executeTask(TaskRequestType taskRequestType) throws AdapterException;

}