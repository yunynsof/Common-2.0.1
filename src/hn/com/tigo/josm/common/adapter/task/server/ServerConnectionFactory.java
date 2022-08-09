package hn.com.tigo.josm.common.adapter.task.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A factory for creating ServerConnection objects.
 *
 * @author Peter Galdámez
 */
public final class ServerConnectionFactory {

	/** The Constant CONNECTIONS. */
	private static final Map<String, ServerConnection> CONNECTIONS = new HashMap<>();

	/**
	 * Instantiates a new server connection factory.
	 */
	private ServerConnectionFactory() {
	}

	/**
	 * Gets the server connection.
	 *
	 * @param configuration
	 *            the configuration
	 * @return the server connection
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static ServerConnection getServerConnection(ServerConfig configuration) throws IOException {
		
		try {
			if (!configuration.getURL().isEmpty()) {
				if (CONNECTIONS.containsKey(configuration.getURL())) {
					return CONNECTIONS.get(configuration.getURL());
				}

				CONNECTIONS.put(configuration.getURL(), new ServerConnection(configuration));
				return CONNECTIONS.get(configuration.getURL());
			}
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		return null;
	}
}
