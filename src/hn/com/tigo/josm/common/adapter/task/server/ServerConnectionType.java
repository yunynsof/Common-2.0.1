package hn.com.tigo.josm.common.adapter.task.server;

/**
 * The enum ServerConnectionType contains the <Usage of this class> for Common
 * project.
 *
 * @author pgaldamez
 */
public enum ServerConnectionType {

	/** The domain. */
	DOMAIN("domain"),

	/** The runtime. */
	RUNTIME("runtime");

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new server connection type.
	 *
	 * @param value
	 *            the value
	 */
	private ServerConnectionType(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return value;
	}

	/**
	 * Parses the.
	 *
	 * @param s
	 *            the s
	 * @return the server connection type
	 */
	public static ServerConnectionType parse(String s) {
		s = s.trim().toLowerCase();
		if (s.equals(DOMAIN.toString()))
			return DOMAIN;
		else if (s.equals(RUNTIME.toString()))
			return RUNTIME;
		else
			return RUNTIME;
	}

}
