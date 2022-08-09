package hn.com.tigo.josm.common.adapter.task.factory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import hn.com.tigo.josm.common.adapter.task.Task;

/**
 * A factory for creating Task objects.
 *
 * @author Peter Galdámez
 */
public final class TaskFactory {

	/** The Constant log. */
	private static final Logger log = Logger.getLogger(TaskFactory.class);

	/** The Constant TASKS. */
	private static final Map<String, TaskDescriptor> TASKS = new HashMap<>();

	/** The adapter name. */
	private static String adapterName;

	/** The task name. */
	private static String taskName;

	/**
	 * Instantiates a new task factory.
	 */
	private TaskFactory() {
	}

	/**
	 * New task instance.
	 *
	 * @param adaptername
	 *            the adaptername
	 * @param taskname
	 *            the taskname
	 * @return the task
	 * @throws TaskFactoryException
	 *             the task factory exception
	 */
	public static Task newTaskInstance(String adaptername, String taskname) throws TaskFactoryException {
		return newTaskDescriptor(adaptername, taskname).getTask();
	}

	/**
	 * New task instance.
	 *
	 * @param taskname
	 *            the taskname
	 * @return the task
	 * @throws TaskFactoryException
	 *             the task factory exception
	 */
	public static Task newTaskInstance(String taskname) throws TaskFactoryException {
		return newTaskInstance(null, taskname);
	}

	/**
	 * New task descriptor.
	 *
	 * @param adaptername
	 *            the adaptername
	 * @param taskname
	 *            the taskname
	 * @return the task descriptor
	 * @throws TaskFactoryException
	 *             the task factory exception
	 */
	public static TaskDescriptor newTaskDescriptor(String adaptername, String taskname) throws TaskFactoryException {
		initializeVariables(adaptername, taskname);
		String key = !TaskFactory.adapterName.isEmpty()
				? TaskFactory.adapterName.concat(".").concat(TaskFactory.taskName) : TaskFactory.taskName;

		if (TASKS.containsKey(key)) {
			return TASKS.get(key);
		}

		TaskDescriptor descriptor = createNewInstance();
		if (descriptor != null) {
			TASKS.put(key, descriptor);
			return TASKS.get(key);
		}

		return null;
	}

	/**
	 * New instance.
	 *
	 * @param jndi
	 *            the jndi
	 * @return the task
	 */
	private static Task newInstance(String jndi) {
		try {
			InitialContext ic = new InitialContext();
			Object obj = ic.lookup(jndi);
			return (Task) obj;
		} catch (NamingException ex) {
			log.error(ex.getLocalizedMessage());
			return null;
		}
	}

	/**
	 * Retieve JNDI.
	 *
	 * @param adaptername
	 *            the adaptername
	 * @param taskname
	 *            the taskname
	 * @return the string
	 * @throws TaskFactoryException
	 *             the task factory exception
	 */
	public static String retieveJNDI(String adaptername, String taskname) throws TaskFactoryException {
		return newTaskDescriptor(adaptername, taskname).getJndi();
	}

	/**
	 * Retieve WSDL.
	 *
	 * @param adaptername
	 *            the adaptername
	 * @param taskname
	 *            the taskname
	 * @return the string
	 * @throws TaskFactoryException
	 *             the task factory exception
	 */
	public static String retieveWSDL(String adaptername, String taskname) throws TaskFactoryException {
		return newTaskDescriptor(adaptername, taskname).getWsdl();
	}

	/**
	 * Retrieve task descriptor.
	 *
	 * @return the task descriptor
	 * @throws TaskFactoryException
	 *             the task factory exception
	 */
	private static TaskDescriptor retrieveTaskDescriptor() throws TaskFactoryException {
		return initializeTaskDescriptor();
	}

	/**
	 * Initialize variables.
	 *
	 * @param adaptername
	 *            the adaptername
	 * @param taskname
	 *            the taskname
	 */
	private static void initializeVariables(String adaptername, String taskname) {
		TaskFactory.adapterName = (adaptername != null ? adaptername : "");
		TaskFactory.taskName = (taskname != null ? taskname : "");
	}

	/**
	 * Initialize task descriptor.
	 *
	 * @return the task descriptor
	 */
	private static TaskDescriptor initializeTaskDescriptor() {
		try {
			TaskDescriptor descriptor = new TaskDescriptor();
			InetAddress server = InetAddress.getLocalHost();
			descriptor.setHost(server.getHostName());
			descriptor.setIpaddress(server.getHostAddress());
			descriptor.setServerid(server.toString());

			return descriptor;
		} catch (UnknownHostException ex) {
			log.error(ex.getLocalizedMessage());
		}
		return new TaskDescriptor();
	}

	/**
	 * Creates a new Task object.
	 *
	 * @return the task descriptor
	 * @throws TaskFactoryException
	 *             the task factory exception
	 */
	private static TaskDescriptor createNewInstance() throws TaskFactoryException {
		TaskDescriptor descriptor = retrieveTaskDescriptor();

		if (!descriptor.getJndi().isEmpty()) {
			Task resultTask = TaskFactory.newInstance(descriptor.getJndi());
			descriptor.setTask(resultTask);

			return descriptor;
		}

		return null;
	}

}
