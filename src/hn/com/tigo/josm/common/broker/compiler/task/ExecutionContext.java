/**
 * ExecutionContext.java
 * Orchestrator
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.broker.compiler.task;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

import org.apache.log4j.Logger;

/**
 * BPMGlobalContext.
 *
 * @author Andres Felipe Hinestroza <mailto:afhinestroza@stefanini.com />, Gary Gonzalez Zepeda
 * @version 
 * @since 20/01/2015 11:39:31 AM 2015
 */
public class ExecutionContext {

	/** This attribute contains an instance of log4j logger for GlobalContext. */
	private static final transient Logger LOGGER = Logger.getLogger(ExecutionContext.class);

	/** Attribute that determine the script engine field. */
	private static ScriptEngine _scriptEngine;

	/** Attribute that determine the script context field. */
	private ScriptContext _scriptContext;

	static {
		final ScriptEngineManager scriptManager = new ScriptEngineManager();
		ExecutionContext._scriptEngine = scriptManager.getEngineByName("JavaScript");
	}

	/**
	 * Instantiates a new global context.
	 */
	public ExecutionContext() {
		final Bindings b = ExecutionContext._scriptEngine.createBindings();
		_scriptContext = new SimpleScriptContext();
		_scriptContext.setBindings(b, ScriptContext.ENGINE_SCOPE);
		LOGGER.info("New BPMGlobalContext has created");
	}

	/**
	 * Method responsible to gets the context attribute.
	 *
	 * @param key
	 *            the key
	 * @return the attribute
	 */
	public Object getAttribute(final String key) {
		return _scriptContext.getAttribute(key);
	}

	/**
	 * Method responsible to set the attribute in the context.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void setAttribute(final String key, final Object value) {
		_scriptContext.setAttribute(key, value, ScriptContext.ENGINE_SCOPE);
	}

	/**
	 * Method responsible to get the script engine.
	 *
	 * @return the script engine
	 */
	public ScriptEngine getScriptEngine() {
		return ExecutionContext._scriptEngine;
	}

	/**
	 * Gets the script context.
	 *
	 * @return the script context
	 */
	public ScriptContext getScriptContext() {
		return _scriptContext;
	}

}
