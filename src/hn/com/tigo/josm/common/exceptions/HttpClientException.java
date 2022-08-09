package hn.com.tigo.josm.common.exceptions;

/**
 * HttpClientException.
 *
 * @author harold.castillo
 * @version 1.0
 * @since 09-27-2016 04:57:46 PM
 */
public class HttpClientException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new http client exception.
	 *
	 * @param message
	 *            the message
	 */
	public HttpClientException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new http client exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public HttpClientException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new http client exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public HttpClientException(final Throwable cause) {
		super(cause);
	}

}
