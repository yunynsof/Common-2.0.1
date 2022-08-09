/**
 * FactoryTestDatabaseProperties.java
 * Common
 * Copyright (C) Tigo Honduras
*/
package hn.com.tigo.josm.common.util;

import java.util.Properties;

/**
 * A factory for creating TestDatabaseProperties objects.
 */
public final class TestDatabasePropertiesFactory {

	/** The Constant CONTEXT_FACTORY. */
	private static final String CONTEXT_FACTORY = "org.apache.openejb.client.LocalInitialContextFactory";

	/**
	 * Instantiates a new test database properties factory.
	 */
	private TestDatabasePropertiesFactory() {
	}

	/**
	 * New database properties.
	 *
	 * @return the properties
	 */
	public static Properties newDatabaseProperties() {
		final Properties prop = new Properties();
		prop.put(EnumTest.P_JOSM_JPA.getKey(), EnumTest.P_JOSM_JPA.getValue());
		prop.put(EnumTest.P_JOSM_JPA_JDBC_DRIVER.getKey(), EnumTest.P_JOSM_JPA_JDBC_DRIVER.getValue());
		prop.put(EnumTest.P_JOSM_JPA_JDBC_URL.getKey(), EnumTest.P_JOSM_JPA_JDBC_URL.getValue());
		prop.put(EnumTest.P_JOSM_JPA_USER_NAME.getKey(), EnumTest.P_JOSM_JPA_USER_NAME.getValue());
		prop.put(EnumTest.P_JOSM_JPA_PASSWORD.getKey(), EnumTest.P_JOSM_JPA_PASSWORD.getValue());
		prop.put(EnumTest.P_JOSM_JPA_JTA_MANAGED.getKey(), EnumTest.P_JOSM_JPA_JTA_MANAGED.getValue());
		prop.put(EnumTest.P_OPENEJB_EMBEDDED_REMOTABLE.getKey(), EnumTest.P_OPENEJB_EMBEDDED_REMOTABLE.getValue());

		prop.put(EnumTest.P_MS_JPA.getKey(), EnumTest.P_MS_JPA.getValue());
		prop.put(EnumTest.P_MS_JPA_JDBC_DRIVER.getKey(), EnumTest.P_MS_JPA_JDBC_DRIVER.getValue());
		prop.put(EnumTest.P_MS_JPA_JDBC_URL.getKey(), EnumTest.P_MS_JPA_JDBC_URL.getValue());
		prop.put(EnumTest.P_MS_JPA_USER_NAME.getKey(), EnumTest.P_MS_JPA_USER_NAME.getValue());
		prop.put(EnumTest.P_MS_JPA_PASSWORD.getKey(), EnumTest.P_MS_JPA_PASSWORD.getValue());
		prop.put(EnumTest.P_JOSM_JPA_JTA_MANAGED.getKey(), EnumTest.P_JOSM_JPA_JTA_MANAGED.getValue());

		prop.put("openejb.localcopy", "false");
		prop.put("openejb.embedded.remotable", "true");
		prop.put("java.naming.factory.initial", CONTEXT_FACTORY);
		prop.put("ProtectedDatasource.JtaManaged", "true");

		return prop;
	}

}
