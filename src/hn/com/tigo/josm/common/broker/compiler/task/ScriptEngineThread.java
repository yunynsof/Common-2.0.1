package hn.com.tigo.josm.common.broker.compiler.task;

import java.util.concurrent.Callable;

import javax.script.ScriptException;

import org.apache.log4j.Logger;

/**
 * ScriptEngineThread.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version
 * @see
 * @since 6/04/2018 10:07:41 AM 2018
 */
public class ScriptEngineThread implements Callable<String> {

	/** This attribute will store an instance of log4j for Broker class. */
	private static final Logger LOGGER = Logger.getLogger(ScriptEngineThread.class);

	/** Attribute that determine the _script field. */
	private String _script;
	
	/** Attribute that determine executionContext. */
	private ExecutionContext executionContext;

	/**
	 * Instantiates a new script engine thread.
	 *
	 * @param executionContext the execution context
	 * @param script            the script
	 */
	public ScriptEngineThread(final ExecutionContext executionContext, final String script) {
		this._script = script;
		this.executionContext = executionContext;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public String call() {
		String result = null;
		try {
			this.executionContext.getScriptEngine().eval(this._script, this.executionContext.getScriptContext());			
		} catch (ScriptException e) {
			result = e.getMessage();
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.executionContext = null;
			this._script = null;
		}
		
		return result;
	}

}
