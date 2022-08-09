package hn.com.tigo.josm.common.adapter.task.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The class ServerConfig contains the <Usage of this class> for Common project.
 *
 * @author Peter Galdámez
 */
public class ServerConfig {

	/** The host. */
	private String host;

	/** The port. */
	private int port;

	/** The user. */
	private String user;

	/** The password. */
	private String password;

	/** The protocol. */
	private String protocol;

	/** The jndiroot. */
	private String jndiroot;

	/** The protocol provider packages. */
	private String protocolProviderPackages;

	/** The mserver. */
	private String mserver;

	/** The domain. */
	private String domain;

	/** The jndiprefix. */
	private String jndiprefix;

	/** The query. */
	private String query;

	/** The jndiformat. */
	private String jndiformat;

	/** The jndidefinition. */
	private String jndidefinition;

	/** The wsdlformat. */
	private String wsdlformat;

	/** The wsdldefinition. */
	private String wsdldefinition;

	/**
	 * Gets the wsdldefinition.
	 *
	 * @return the wsdldefinition
	 */
	public String getWsdldefinition() {
		if (wsdldefinition == null)
			wsdldefinition = "";
		return wsdldefinition;
	}

	/**
	 * Sets the wsdldefinition.
	 *
	 * @param wsdldefinition
	 *            the new wsdldefinition
	 */
	public void setWsdldefinition(String wsdldefinition) {
		this.wsdldefinition = wsdldefinition;
	}

	/**
	 * Gets the wsdlformat.
	 *
	 * @return the wsdlformat
	 */
	public String getWsdlformat() {
		if (wsdlformat == null)
			wsdlformat = "";

		return wsdlformat;
	}

	/**
	 * Sets the wsdlformat.
	 *
	 * @param wsdlformat
	 *            the new wsdlformat
	 */
	public void setWsdlformat(String wsdlformat) {
		this.wsdlformat = wsdlformat;
	}

	/**
	 * Gets the jndiprefix.
	 *
	 * @return the jndiprefix
	 */
	public String getJndiprefix() {
		if (jndiprefix == null) {
			jndiprefix = "";
		}
		return jndiprefix;
	}

	/**
	 * Sets the jndiprefix.
	 *
	 * @param jndiprefix
	 *            the new jndiprefix
	 */
	public void setJndiprefix(String jndiprefix) {
		this.jndiprefix = jndiprefix;
	}

	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public String getQuery() {
		if (query == null) {
			query = "";
		}
		return query;
	}

	/**
	 * Gets the formatted query.
	 *
	 * @param values
	 *            the values
	 * @return the formatted query
	 */
	public String getFormattedQuery(String... values) {
		String s = String.format(this.getQuery(), (Object[]) values);
		return this.getDomain() + s;
	}

	/**
	 * Sets the query.
	 *
	 * @param query
	 *            the new query
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Gets the domain.
	 *
	 * @return the domain
	 */
	public String getDomain() {
		if (domain == null) {
			domain = "";
		}
		return domain;
	}

	/**
	 * Sets the domain.
	 *
	 * @param domain
	 *            the new domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		if (host == null) {
			host = "";
		}
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
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port
	 *            the new port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public String getUser() {
		if (user == null) {
			user = "";
		}
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		if (password == null) {
			password = "";
		}
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the protocol.
	 *
	 * @return the protocol
	 */
	public String getProtocol() {
		if (protocol == null) {
			protocol = "";
		}
		return protocol;
	}

	/**
	 * Sets the protocol.
	 *
	 * @param protocol
	 *            the new protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * Gets the jndiroot.
	 *
	 * @return the jndiroot
	 */
	public String getJndiroot() {
		if (jndiroot == null) {
			jndiroot = "";
		}
		return jndiroot;
	}

	/**
	 * Sets the jndiroot.
	 *
	 * @param jndiroot
	 *            the new jndiroot
	 */
	public void setJndiroot(String jndiroot) {
		this.jndiroot = jndiroot;
	}

	/**
	 * Gets the protocol provider packages.
	 *
	 * @return the protocol provider packages
	 */
	public String getProtocolProviderPackages() {
		if (protocolProviderPackages == null) {
			protocolProviderPackages = "";
		}
		return protocolProviderPackages;
	}

	/**
	 * Sets the protocol provider packages.
	 *
	 * @param protocolProviderPackages
	 *            the new protocol provider packages
	 */
	public void setProtocolProviderPackages(String protocolProviderPackages) {
		this.protocolProviderPackages = protocolProviderPackages;
	}

	/**
	 * Gets the mserver.
	 *
	 * @return the mserver
	 */
	public String getMserver() {
		if (mserver == null) {
			mserver = "";
		}
		return mserver;
	}

	/**
	 * Sets the mserver.
	 *
	 * @param mserver
	 *            the new mserver
	 */
	public void setMserver(String mserver) {
		this.mserver = mserver;
	}

	/**
	 * Gets the jndiformat.
	 *
	 * @return the jndiformat
	 */
	public String getJndiformat() {
		if (jndiformat == null)
			jndiformat = "";
		return jndiformat;
	}

	/**
	 * Sets the jndiformat.
	 *
	 * @param jndiformat
	 *            the new jndiformat
	 */
	public void setJndiformat(String jndiformat) {
		this.jndiformat = jndiformat;
	}

	/**
	 * Gets the jndidefinition.
	 *
	 * @return the jndidefinition
	 */
	public String getJndidefinition() {
		if (jndidefinition == null)
			jndidefinition = "";
		return jndidefinition;
	}

	/**
	 * Sets the jndidefinition.
	 *
	 * @param jndidefinition
	 *            the new jndidefinition
	 */
	public void setJndidefinition(String jndidefinition) {
		this.jndidefinition = jndidefinition;
	}

	/**
	 * Builds the definition.
	 *
	 * @param isJNDI
	 *            the is JNDI
	 * @param properties
	 *            the properties
	 * @return the string
	 */
	private String buildDefinition(boolean isJNDI, Map<String, String> properties) {
		Map<String, String> nodeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		nodeMap.putAll(this.toMap());
		nodeMap.putAll(properties);

		String def = isJNDI ? this.getJndidefinition() : this.getWsdldefinition();
		String[] definition = def.split("\\,");

		List<String> list = new LinkedList<>();
		for (String d : definition) {
			list.add(nodeMap.get(d.trim()));
		}

		return String.format(isJNDI ? this.getJndiformat() : this.getWsdlformat(), list.toArray());
	}

	/**
	 * Builds the JNDI.
	 *
	 * @param properties
	 *            the properties
	 * @return the string
	 */
	public String buildJNDI(Map<String, String> properties) {
		return buildDefinition(true, properties);
	}

	/**
	 * Builds the WSDL.
	 *
	 * @param properties
	 *            the properties
	 * @return the string
	 */
	public String buildWSDL(Map<String, String> properties) {
		return buildDefinition(false, properties);
	}

	/**
	 * To map.
	 *
	 * @return the map
	 */
	public Map<String, String> toMap() {
		// Builder<ServerConfig> builder =
		// BuilderFactory.getBuilder(this.getClass());
		// return builder.build(this).getValues();
		return null;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getURL() {
		StringBuilder url = new StringBuilder();

		if (!this.getProtocol().isEmpty()) {
			url.append(this.getProtocol());
			url.append("//");
		}

		if (!this.getHost().isEmpty()) {
			url.append(this.getHost());
			if (this.getPort() > 0) {
				url.append(":");
				url.append(String.valueOf(this.getPort()));
			}
		}

		url.append(this.getJndiroot());
		url.append(this.getMserver());
		return url.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getURL();
	}
}
