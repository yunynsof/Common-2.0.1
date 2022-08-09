package hn.com.tigo.josm.common.adapter.config;

/**
 * The MockTaskConfig class is for mock configuration.
 *
 * @author Andres Felipe Hinestroza <mailto:afhinestroza@stefanini.com />
 * @version 1.0.0
 * @since 18/04/2017 09:50:04 AM
 */
public class MockTaskConfig {

	/** Attribute that determine taskName. */
	private String taskName;
	
	/** Attribute that determine taskDelay. */
	private long taskDelay;
	
	/**
	 * Construct method that instantiates a new {@link MockTaskConfig}.
	 */
	public MockTaskConfig(){
		
	}

	/**
	 * Method responsible to gets the task name.
	 *
	 * @return the task name
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Method responsible to sets the task name.
	 *
	 * @param taskName the new task name
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * Method responsible to gets the task delay.
	 *
	 * @return the task delay
	 */
	public long getTaskDelay() {
		return taskDelay;
	}

	/**
	 * Method responsible to sets the task delay.
	 *
	 * @param taskDelay the new task delay
	 */
	public void setTaskDelay(final long taskDelay) {
		this.taskDelay = taskDelay;
	}
	
}
