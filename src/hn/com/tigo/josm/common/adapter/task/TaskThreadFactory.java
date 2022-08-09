/**
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.adapter.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * A factory for creating TaskThread objects.
 * 
 * @author Harold Castillo
 * @version 1.0.0
 * @since 22/05/2018 12:50:24 PM
 */
public class TaskThreadFactory implements ThreadFactory {

	private static final String NAME = "ThreadExecutor_%sv%s";

	/** Attribute that determine the adapter name. */
	private final String adapterName;

	/**
	 * Instantiates a new task thread factory.
	 *
	 * @param adapterName
	 *            the adapter name
	 */
	public TaskThreadFactory(final String adapterName) {
		this.adapterName = adapterName;
	}

	/**
	 * Method that allows create a new thread.
	 *
	 * @param runnable
	 *            the runnable instance for executing the thread
	 * @return the thread to run in the {@link ExecutorService} in the
	 *         {@link AbstractTask}
	 */
	@Override
	public Thread newThread(final Runnable runnable) {
		final Thread thread = new Thread(runnable, String.format(NAME, adapterName, System.currentTimeMillis()));
		return thread;
	}

}
