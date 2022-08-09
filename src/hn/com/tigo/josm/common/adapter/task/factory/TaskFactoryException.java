package hn.com.tigo.josm.common.adapter.task.factory;

import hn.com.tigo.josm.common.exceptions.enumerators.TaskFactoryErrors;

/**
 * The class TaskFactoryException contains the <Usage of this class> for Common
 * project.
 *
 * @author Peter Galdámez
 */
public class TaskFactoryException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4447290310269629239L;

	/** The m details. */
	protected String mDetails;

	/**
	 * Instantiates a new task factory exception.
	 */
	public TaskFactoryException() {
		super();
	}

	/**
	 * Instantiates a new task factory exception.
	 *
	 * @param message
	 *            the message
	 * @param details
	 *            the details
	 * @param t
	 *            the t
	 */
	public TaskFactoryException(String message, String details, Throwable t) {
		super(message, t);
		setDetails(details);
	}

	/**
	 * Instantiates a new task factory exception.
	 *
	 * @param e
	 *            the e
	 * @param t
	 *            the t
	 */
	public TaskFactoryException(TaskFactoryErrors e, Throwable t) {
		this(e.toString(), t.getMessage(), t);
	}

	/**
	 * Instantiates a new task factory exception.
	 *
	 * @param e
	 *            the e
	 */
	public TaskFactoryException(TaskFactoryErrors e) {
		super(e.toString());
	}

	/**
	 * Instantiates a new task factory exception.
	 *
	 * @param e
	 *            the e
	 * @param details
	 *            the details
	 */
	public TaskFactoryException(TaskFactoryErrors e, String details) {
		super(e.toString());
		setDetails(details);
	}

	/**
	 * Instantiates a new task factory exception.
	 *
	 * @param message
	 *            the message
	 */
	public TaskFactoryException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new task factory exception.
	 *
	 * @param message
	 *            the message
	 * @param t
	 *            the t
	 */
	public TaskFactoryException(String message, Throwable t) {
		super(message, t);
		setDetails(t.getMessage());
	}

	/**
	 * Instantiates a new task factory exception.
	 *
	 * @param message
	 *            the message
	 * @param details
	 *            the details
	 */
	public TaskFactoryException(String message, String details) {
		super(message);
		setDetails(details);
	}

	/**
	 * Sets the details.
	 *
	 * @param details
	 *            the new details
	 */
	private void setDetails(String details) {
		mDetails = details;
		if (mDetails != null && mDetails.length() > 255) {
			mDetails = mDetails.substring(0, 255);
		}
	}

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public String getDetails() {
		return mDetails;
	}

}
