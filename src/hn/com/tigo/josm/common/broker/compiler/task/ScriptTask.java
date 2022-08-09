/**
 * ScriptTask.java
 * Common
 * Copyright (C) Tigo Honduras
*/
package hn.com.tigo.josm.common.broker.compiler.task;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import hn.com.tigo.josm.common.exceptions.BPMNExecutionException;
import hn.com.tigo.josm.common.exceptions.OrchestratorException;
import hn.com.tigo.josm.common.exceptions.enumerators.OrchestratorErrorCode;

/**
 * ScriptTask.
 *
 * @author Jose David Martinez
 * @version
 * @since 17/02/2015 05:24:17 PM 2015
 */
public class ScriptTask extends AbstractElement {

	/** This attribute will store an instance of log4j for Broker class. */
	private static final Logger LOGGER = Logger.getLogger(ScriptTask.class);

	/** Attribute that determine a Constant of serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Attribute that determine cache. */
	private static ScriptTaskCache cache = ScriptTaskCache.getInstance();

	/** Attribute that determine the _script field. */
	private String _script;

	/** Attribute that determine config. */
	private ScriptTaskConfig config;

	/**
	 * Instantiates a new script task.
	 */
	private ScriptTask() {
		this.setType(TaskType.SCRIPT);
	}

	/**
	 * Instantiates a new script task.
	 *
	 * @param script
	 *            the script
	 */
	public ScriptTask(final String script) {
		this();
		this._script = script;
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.broker.compiler.task.AbstractElement#execute(hn.com.tigo.josm.common.broker.compiler.task.ExecutionContext)
	 */
	@Override
	public LinkedList<AbstractElement> execute(final ExecutionContext executionContext) throws BPMNExecutionException {
		final ExecutorService exService = Executors.newSingleThreadExecutor();
		final String scriptId = super.getId();
		try {
			this.config = ScriptTask.cache.retrieve();
			final Future<String> result = exService.submit(new ScriptEngineThread(executionContext, this._script));
			exService.shutdown();

			if (!exService.awaitTermination(this.config.getTimeout(), TimeUnit.MILLISECONDS)) {
				result.cancel(true);
				LOGGER.error(scriptId + " ::: " + _script);
				final String message = String.format(OrchestratorErrorCode.SCRIPT_TASK_INTERRUPTED.getMessage(),
						scriptId);
				throw new BPMNExecutionException(OrchestratorErrorCode.SCRIPT_TASK_INTERRUPTED.getError(), message);
			}

			final String message = result.get();
			if (message != null) {
				result.cancel(true);
				LOGGER.error(message);
				throw new BPMNExecutionException(OrchestratorErrorCode.SCRIPT_TASK_ERROR.getError(), message);
			}
			
		} catch (InterruptedException e) {
			LOGGER.error(scriptId + " ::: " + _script);
			LOGGER.error(e.getMessage(), e);
			final String message = String.format(OrchestratorErrorCode.SCRIPT_TASK_INTERRUPTED.getMessage(), scriptId);
			throw new BPMNExecutionException(OrchestratorErrorCode.SCRIPT_TASK_INTERRUPTED.getError(), message, e);
		} catch (ExecutionException e) {
			LOGGER.error(e.getMessage(), e);
			throw new BPMNExecutionException(OrchestratorErrorCode.SCRIPT_TASK_ERROR.getError(), e.getMessage(), e);
		} catch (OrchestratorException e) {
			LOGGER.error(e.getMessage(), e);
			throw new BPMNExecutionException(OrchestratorErrorCode.SCRIPT_TASK_ERROR.getError(), e.getMessage(), e);
		} finally {
			exService.shutdownNow();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Name: " + _name + ", Type: " + _type);
		return s.toString();
	}

}
