package hn.com.tigo.josm.common.broker.compiler.task;

import java.io.Serializable;

/**
 * ScriptTaskConfig.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version
 * @see
 * @since 9/04/2018 11:51:02 AM 2018
 */
public class ScriptTaskConfig implements Serializable {

	/** Attribute that determine a Constant of serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Attribute that determine timeout. */
	private int timeout;

	/**
	 * Instantiates a new script task config.
	 */
	public ScriptTaskConfig() {
	}

	/**
	 * Gets the timeout.
	 *
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * Sets the timeout.
	 *
	 * @param timeout
	 *            the new timeout
	 */
	public void setTimeout(final int timeout) {
		this.timeout = timeout;
	}

}
