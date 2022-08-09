package hn.com.tigo.josm.common.adapter.task.server;

import java.util.HashMap;
import java.io.IOException;
import javax.naming.Context;
import java.net.MalformedURLException;
import java.util.Properties;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.JMXConnectorFactory;

/**
 * The class ServerConnection contains the <Usage of this class> for Common
 * project.
 *
 * @author Peter Galdámez
 */
public class ServerConnection {

	/** The service URL. */
	private JMXServiceURL serviceURL;

	/** The connection. */
	private MBeanServerConnection connection;

	/** The connector. */
	private JMXConnector connector;

	/** The configuration. */
	private final ServerConfig configuration;

	/** The initialize URL. */
	private final boolean initializeURL = true;

	/** The user. */
	private final String USER = "USER";

	/** The password. */
	private final String PASSWORD = "PASSWORD";

	/** The properties. */
	private Properties properties = null;

	/**
	 * Instantiates a new server connection.
	 *
	 * @param properties
	 *            the properties
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ServerConnection(Properties properties) throws IOException {

		configuration = null;
		this.properties = properties;
	}

	/**
	 * Instantiates a new server connection.
	 *
	 * @param url
	 *            the url
	 * @throws MalformedURLException
	 *             the malformed URL exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ServerConnection(String url) throws MalformedURLException, IOException {
		serviceURL = new JMXServiceURL(url);
		configuration = new ServerConfig();
		configuration.setHost(serviceURL.getHost());
		configuration.setPort(serviceURL.getPort());
		configuration.setProtocol(serviceURL.getProtocol());
		properties = new Properties();
		initializeConnections(!initializeURL);
	}

	/**
	 * Instantiates a new server connection.
	 *
	 * @param config
	 *            the config
	 * @throws MalformedURLException
	 *             the malformed URL exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ServerConnection(ServerConfig config) throws MalformedURLException, IOException {
		configuration = config;
		properties = new Properties();
		initializeConnections(initializeURL);
	}

	/**
	 * Initialize connections.
	 *
	 * @param initializeURL
	 *            the initialize URL
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void initializeConnections(boolean initializeURL) throws IOException {
		if (initializeURL)
			serviceURL = new JMXServiceURL(configuration.getURL());

		connector = JMXConnectorFactory.connect(serviceURL, initializeEnviroment());
		connection = connector.getMBeanServerConnection();
	}

	/**
	 * Initialize enviroment.
	 *
	 * @return the hash map
	 */
	private HashMap<String, String> initializeEnviroment() {
		HashMap<String, String> h = new HashMap<>();
		if (configuration.getUser().isEmpty())
			configuration.setUser(properties.getProperty(USER));

		if (configuration.getPassword().isEmpty())
			configuration.setPassword(properties.getProperty(PASSWORD));

		h.put(Context.SECURITY_PRINCIPAL, configuration.getUser());
		h.put(Context.SECURITY_CREDENTIALS, configuration.getPassword());
		h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES, configuration.getProtocolProviderPackages());
		return h;
	}

	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	public ServerConfig getConfiguration() {
		return configuration;
	}

	/**
	 * Gets the connector.
	 *
	 * @return the connector
	 */
	public JMXConnector getConnector() {
		return connector;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public MBeanServerConnection getConnection() {
		return connection;
	}

	/**
	 * Close.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void close() throws IOException {
		this.getConnector().close();
	}

	/**
	 * Connect.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void connect() throws IOException {
		this.getConnector().connect();
	}
}
