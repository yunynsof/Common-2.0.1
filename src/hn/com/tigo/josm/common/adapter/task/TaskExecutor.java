/**
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.adapter.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import hn.com.tigo.josm.common.adapter.AbstractAdapter;
import hn.com.tigo.josm.common.adapter.DriverReference;
import hn.com.tigo.josm.common.adapter.dto.TaskResponseType;
import hn.com.tigo.josm.common.exceptions.AdapterException;
import hn.com.tigo.josm.common.interfaces.Closeable;
import hn.com.tigo.josm.common.interfaces.MonitoringManagerLocal;
import hn.com.tigo.josm.common.jmx.event.EndpointEvent;
import hn.com.tigo.josm.common.util.TaskMessage;

/**
 * TaskExecutor, this class works as a thread and it's execute from the
 * {@link ExecutorService} instance in the {@link AbstractTask}
 * 
 * @author Jose David Martinez Rico <mailto:jdmartinez@stefanini.com />
 * @author Harold Castillo
 * 
 * @version 1.1.0
 * @param <R>
 *            Generic type that represents the request driver type.
 * @param <D>
 *            Generic type that represents the driver's type.
 * @since 8/07/2015 03:52:05 PM
 */
public class TaskExecutor<R, D> implements Callable<TaskResponseType> {

	/** Attribute that determine {@link Logger} instance. */
	private final transient Logger LOGGER = Logger.getLogger(TaskExecutor.class);

	/** Attribute that determine a instance of {@link AbstractTask}. */
	private final AbstractTask<R, D> task;

	/** Attribute that determine a instance of {@link MonitoringManager}. */
	private final MonitoringManagerLocal monitoringManager;

	/**
	 * Instantiates a new instance of the class.
	 *
	 * @param task
	 *            the instance of {@link AbstractTask} for calling the method
	 *            {@link AbstractTask#executeDriverTask(Object)}.
	 * @param monitoringManager
	 *            the instance of {@link MonitoringManager} for writing in the JMX
	 *            all the events.
	 */
	public TaskExecutor(final AbstractTask<R, D> task, final MonitoringManagerLocal monitoringManager) {
		this.task = task;
		this.monitoringManager = monitoringManager;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public TaskResponseType call() throws AdapterException {

		TaskResponseType taskResponseType = null;
		AbstractAdapter<D> singletonAdapter = null;
		DriverReference<D> driverReference = null;
		D driver = null;
		EndpointEvent event = null;
		long initialTime = 0L;

		try {
			singletonAdapter = task.getSingletonAdapter();
			driverReference = singletonAdapter.getDriver();
			driver = driverReference.getDriver();
			event = new EndpointEvent();
			event.setComponent(singletonAdapter.getAdapterSimpleName());
			event.setObjectName(singletonAdapter.getAdapterSimpleName());
			initialTime = System.currentTimeMillis();
			taskResponseType = task.executeDriverTask(driver);
		} finally {
			LOGGER.info(String.format(TaskMessage.CLOSE_TASK_CAll.getValue(),
					this.task.parseName(this.task.getClass().getSimpleName())));
			if (driverReference != null) {

				if (event != null) {
					event.set_lastEndpointTransactionInMillis(System.currentTimeMillis() - initialTime);
					monitoringManager.receiveEvent(event);
				}

				if (driver != null && driver instanceof Closeable) {
					((Closeable) driver).closeConnection();
				}

				if (task.flagRemoveDriver) {
					driverReference.clearDriver();
				}
				singletonAdapter.freeDriver(driverReference);
			}
		}

		return taskResponseType;
	}

}
