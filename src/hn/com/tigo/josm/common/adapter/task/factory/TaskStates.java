package hn.com.tigo.josm.common.adapter.task.factory;

/**
 * The enum TaskStates contains the <Usage of this class> for Common project.
 *
 * @author pgaldamez
 */
public enum TaskStates {

	/** The starting. */
	STARTING("STARTING"),

	/** The running. */
	RUNNING("RUNNING"),

	/** The paused. */
	PAUSED("PAUSED"),

	/** The shutdown. */
	SHUTDOWN("SHUTDOWN");

	/** The value. */
	private final String value;

	/**
	 * Instantiates a new task states.
	 *
	 * @param value
	 *            the value
	 */
	private TaskStates(String value) {
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
	 * @return the task states
	 */
	public static TaskStates parse(String s) {
		s = s.trim().toLowerCase();
		if (s.equals(STARTING.toString().toLowerCase()))
			return STARTING;
		else if (s.equals(RUNNING.toString().toLowerCase()))
			return RUNNING;
		else if (s.equals(PAUSED.toString().toLowerCase()))
			return PAUSED;
		else if (s.equals(SHUTDOWN.toString().toLowerCase()))
			return SHUTDOWN;
		else
			return STARTING;
	}
}
