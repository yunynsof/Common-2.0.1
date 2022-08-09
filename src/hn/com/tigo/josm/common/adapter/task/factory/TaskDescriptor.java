package hn.com.tigo.josm.common.adapter.task.factory;

import hn.com.tigo.josm.common.adapter.task.Task;

/**
 * The class TaskDescriptor contains the <Usage of this class> for Common
 * project.
 *
 * @author Peter Galdámez
 */
public class TaskDescriptor {

	/** The task. */
	private Task task;

	/** The wsdl. */
	private String wsdl;

	/** The jndi. */
	private String jndi;

	/** The host. */
	private String host;

	/** The serverid. */
	private String serverid;

	/** The ipaddress. */
	private String ipaddress;

	/**
	 * Gets the task.
	 *
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Sets the task.
	 *
	 * @param task
	 *            the new task
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * Gets the wsdl.
	 *
	 * @return the wsdl
	 */
	public String getWsdl() {
		if (wsdl == null)
			wsdl = "";
		return wsdl;
	}

	/**
	 * Sets the wsdl.
	 *
	 * @param wsdl
	 *            the new wsdl
	 */
	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	/**
	 * Gets the jndi.
	 *
	 * @return the jndi
	 */
	public String getJndi() {
		if (jndi == null)
			jndi = "";
		return jndi;
	}

	/**
	 * Sets the jndi.
	 *
	 * @param jndi
	 *            the new jndi
	 */
	public void setJndi(String jndi) {
		this.jndi = jndi;
	}

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		if (host == null)
			host = "";
		return host;
	}

	/**
	 * Sets the host.
	 *
	 * @param host
	 *            the new host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the serverid.
	 *
	 * @return the serverid
	 */
	public String getServerid() {
		if (serverid == null)
			serverid = "";
		return serverid;
	}

	/**
	 * Sets the serverid.
	 *
	 * @param serverid
	 *            the new serverid
	 */
	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	/**
	 * Gets the ipaddress.
	 *
	 * @return the ipaddress
	 */
	public String getIpaddress() {
		if (ipaddress == null)
			ipaddress = "";
		return ipaddress;
	}

	/**
	 * Sets the ipaddress.
	 *
	 * @param ipaddress
	 *            the new ipaddress
	 */
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
}
