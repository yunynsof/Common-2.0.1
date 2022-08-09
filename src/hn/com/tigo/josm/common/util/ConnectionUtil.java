package hn.com.tigo.josm.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * ConnectionUtil.
 *
 * @author Jhon Fredy Cortes Gaspar <mailto:jfgaspar@stefanini.com />
 * @version 1.0
 * @since 05-14-2015 06:19:09 PM 2015
 */
public class ConnectionUtil {

	/** The logger. */
	private Logger logger = Logger.getLogger(ConnectionUtil.class);

	/** The conn. */
	private Connection conn = null;

	/**
	 * Open.
	 *
	 * @param user
	 *            the user
	 * @param password
	 *            the password
	 * @throws SQLException
	 *             the SQL exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	public void open(final String user, final String password)
			throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

		String driver_class = "oracle.jdbc.driver.OracleDriver";
		String connectionURL = null;

		try {
			Class.forName(driver_class).newInstance();
			connectionURL = "jdbc:oracle:thin:@192.168.159.52:1521:josmdb";
			conn = DriverManager.getConnection(connectionURL, user, password);
			conn.setAutoCommit(false);
			logger.info("Connected.\n");

		} catch (IllegalAccessException e) {
			logger.error("Illegal Access Exception: (Open Connection).", e);
			throw e;
		} catch (InstantiationException e) {
			logger.error("Instantiation Exception: (Open Connection).", e);
			throw e;
		} catch (ClassNotFoundException e) {
			logger.error("Class Not Found Exception: (Open Connection).", e);
			throw e;
		} catch (SQLException e) {
			logger.error("Caught SQL Exception: (Open Connection).", e);
			throw e;
		}

	}

	/**
	 * Write script.
	 *
	 * @param script
	 *            the script
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void writeScript(String script) throws SQLException {

		Statement stmt = null;
		try {

			logger.info(script);
			System.out.println(script);
			stmt = conn.createStatement();
			stmt.execute("BEGIN " + script + " END;");
			conn.commit();

		} catch (SQLException e) {
			logger.error("Caught SQL Exception: (Write script value - Stream Method).", e);
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

	}

	/**
	 * Close.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void close() throws SQLException {

		try {
			conn.close();
			logger.info("Disconnected.\n");
		} catch (SQLException e) {
			logger.error("Caught SQL Exception: (Closing Connection).", e);
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e2) {
					logger.error("Caught SQL (Rollback Failed) Exception.", e);
				}
			}
			throw e;
		}

	}

	/**
	 * Gets the file source.
	 *
	 * @param file
	 *            the file
	 * @param path
	 *            the path
	 * @return the file source
	 */
	public static String getFileSource(final String file, final String path) {
		FileInputStream fileReader = null;
		final StringBuilder enviromentScript = new StringBuilder();
		try {
			fileReader = new FileInputStream(new File(".", path + file));

			byte[] buffer = new byte[1024];

			int i = -1;
			while ((i = fileReader.read(buffer)) != -1) {
				enviromentScript.append(new String(buffer, 0, i));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return enviromentScript.toString();

	}

}
