package hn.com.tigo.josm.common.exceptions;

/**
 * The class ValidatorException contains the <Usage of this class> for Common
 * project.
 *
 * @author 
 * @version 1.0.0
 * @since Sep 1, 2017
 */
public class ValidatorException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8673620214931120559L;

	/** Attribute that stores the error code. **/
	private int _errorCode;

	/** Attribute that stores the message. */
	private String _message;

	/**
	 * Instantiates a new validator exception.
	 *
	 * @param message
	 *            the message
	 */
	public ValidatorException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new validator exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ValidatorException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Method responsible to gets the error code.
	 * 
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return _errorCode;
	}

	/**
	 * Method responsible to sets the error code.
	 * 
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(final int errorCode) {
		this._errorCode = errorCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		return _message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this._message = message;
	}

}
