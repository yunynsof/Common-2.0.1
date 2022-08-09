package hn.com.tigo.josm.common.dto;

import hn.com.tigo.josm.common.jmx.NotificationConstant;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Configuration.
 *
 * @author harold.castillo
 * @version 1.0
 * @since Oct 18, 2016 09:50:21 AM
 */
public class ConfigurationListener {

	/** The counter. */
	private AtomicLong counter;

	/** The last execution. */
	private Date lastExecution;

	/** The threshold. */
	private long threshold;

	/** The delta time millis. */
	private int deltaTimeMillis;

	/** The product id. */
	private long productId;

	/** The order type. */
	private String orderType;

	/**
	 * Instantiates a new configuration listener.
	 *
	 * @param threshold
	 *            the threshold
	 * @param deltaTimeMillis
	 *            the delta time millis
	 * @param productId
	 *            the product id
	 * @param orderType
	 *            the order type
	 */
	public ConfigurationListener(final long threshold, final int deltaTimeMillis, final long productId,
			final String orderType) {
		this.threshold = threshold;
		this.deltaTimeMillis = deltaTimeMillis;
		this.productId = productId;
		this.counter = new AtomicLong(NotificationConstant.INITIAL_VALUE);
		this.lastExecution = Calendar.getInstance().getTime();
		this.orderType = orderType;
	}

	/**
	 * Gets the threshold.
	 *
	 * @return the threshold
	 */
	public long getThreshold() {
		return this.threshold;
	}

	/**
	 * Gets the delta time millis.
	 *
	 * @return the delta time millis
	 */
	public int getDeltaTimeMillis() {
		return this.deltaTimeMillis;
	}

	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public long getProductId() {
		return this.productId;
	}

	/**
	 * Gets the counter.
	 *
	 * @return the counter
	 */
	public AtomicLong getCounter() {
		return this.counter;
	}

	/**
	 * Gets the last execution.
	 *
	 * @return the last execution
	 */
	public Date getLastExecution() {
		return this.lastExecution;
	}

	/**
	 * Reset last execution.
	 */
	public void resetLastExecution() {
		this.lastExecution = Calendar.getInstance().getTime();
	}

	/**
	 * Gets the order type.
	 *
	 * @return the order type
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * Reset.
	 */
	public void reset() {
		this.counter.set(NotificationConstant.INITIAL_VALUE);
		this.lastExecution = Calendar.getInstance().getTime();
	}
}
