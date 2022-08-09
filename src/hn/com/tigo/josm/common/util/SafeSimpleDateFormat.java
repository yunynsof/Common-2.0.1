package hn.com.tigo.josm.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The class SafeSimpleDateFormat contains the <Usage of this class> for Common
 * project.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Aug 11, 2017
 */
public final class SafeSimpleDateFormat {

	/**
	 * Instantiates a new safe simple date format.
	 */
	private SafeSimpleDateFormat() {
	}

	/** The Constant FORMATTER. */
	private static final ThreadLocal<SimpleDateFormat> FORMATTER = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		}
	};

	/**
	 * Parses the.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date parse(final String date) throws ParseException {
		return FORMATTER.get().parse(date);
	}

}
