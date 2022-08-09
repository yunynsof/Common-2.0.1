/**
 * AdapterConstants.java
 * LTE-EJB
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.adapter;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * AdapterConstants. This class defines the constants used by the adapters
 * 
 * @author Juan Pablo Gomez <mailto:jgomezg@stefaninicolombia.com />
 * @version 1.0.0
 * @since 06/10/2014 03:44:56 PM
 */
public final class AdapterConstants {

	/**
	 * EMPTY constant of type String.
	 */
	public static final String EMPTY = "";

	/** ZERO constant of type {@link Integer#intValue()}. */
	public static final int ZERO = 0;

	/** NUMERIC constant of type string. */
	public static final String NUMERIC = "^[0-9]+$";

	/** DECIMAL constant of type string. */
	public static final String DECIMAL = "^\\-{0,1}\\d+\\.{0,1}[0-9]+$";

	/** ALFANUMERIC constant of type string. */
	public static final String ALFANUMERIC = "^[a-zA-Z0-9\\_]+$";

	/** ALFANUMERIC constant of type string. */
	public static final String ALFANUMERIC_EXTENDED = "^[a-zñA-ZÑ0-9\\_\\/\\-\\s\\=\\>\\<\\!\\¡\\:\\.\\&\\,\\;\\*\\¿\\?\\#\\+]+$";

	/** ALFANUMERIC ALL constant of type string. */
	public static final String ALFANUMERIC_ALL = "(.*?)|(.*?(\\n))+.*?";

	/** Attribute that determine a Constant of BOOLEAN. */
	public static final String BOOLEAN = "([T,t][R,r][U,u][E,e])|([F,f][A,a][L,l][S,s][E,e])";

	/** Attribute that determine a Constant of IP_V4. */
	public static final String IP_V4 = "^(?=\\d+\\.\\d+\\.\\d+\\.\\d+$)(?:(?:25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\\.?){4}$";

	/** Attribute that determine a Constant of ALL. */
	public static final String ALL = null;

	/** Attribute that determine a constant for new line character. */
	public static final String NEW_LINE = "\n";

	/** Attribute that determine the constant missing parameter message. */
	public static final String MSG_MISSING_PARAMETER = "Missing parameter [%s]";

	/** Attribute that determine the constant invalid parameter message. */
	public static final String MSG_INVALID_PARAMETER = "Parameter [%s] value is invalid";

	/** Attribute that determine the constant invalid parameter message. */
	public static final String MSG_INVALID_PARAMETER_COMPARE_DATE = "Parameter [%s] value cannot be greater than Parameter [%s]";

	/** Attribute that determine the Constant MSG_INVALID_PARAMETER. */
	public static final String MSG_INVALID_PARAMETER_LENGTH = "Maximum length is [%s] characters for [%s] parameter";
	
	/**
	 * Attribute that determine the Constant
	 * MSG_INVALID_PARAMETER_LENGTH_BETWEEN.
	 */
	public static final String MSG_INVALID_PARAMETER_LENGTH_BETWEEN = "Length must be between  %s and %s characters for %s parameter";

	/** Constant pattern for {@link XMLGregorianCalendar}. */
	public static final String XML_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	/** Constant for invalid IP address. */
	public static final String UNKNOWN_HOST = "Unknown ip address";

	/**
	 * Instantiates a new adapter constants.
	 */
	private AdapterConstants() {

	}

}
