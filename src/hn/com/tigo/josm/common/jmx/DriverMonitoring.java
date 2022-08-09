package hn.com.tigo.josm.common.jmx;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The class DriverMonitoring contains the <Usage of this class> for Common project.
 *
 * @author 
 * @version 1.0.0
 * @since Aug 11, 2017
 */
public class DriverMonitoring extends JOSMMonitoring implements DriverMonitoringMXBean {

	/** The total drivers. */
	private final AtomicLong _totalDrivers;
	
	/** The idle drivers. */
	private final AtomicLong _idleDrivers;
	
	/** The insuficient drivers. */
	private final AtomicLong _insuficientDrivers;
	
	/** The connection refused. */
	private final AtomicLong _connectionRefused;
	
	/** The last endpoint time millis. */
	private final AtomicLong _lastEndpointTimeMillis;		
	
	/** The max used drivers. */
	private final AtomicLong _maxUsedDrivers;
	
	
	/**
	 * Instantiates a new driver monitoring.
	 */
	public DriverMonitoring(){
		_totalDrivers = new AtomicLong(INIT_VALUE);
		_idleDrivers = new AtomicLong(INIT_VALUE);
		_insuficientDrivers = new AtomicLong(INIT_VALUE);
		_connectionRefused =  new AtomicLong(INIT_VALUE);
		_lastEndpointTimeMillis = new AtomicLong(INIT_VALUE);
		_maxUsedDrivers = new AtomicLong(INIT_VALUE);	
	}

	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#getTotalDrivers()
	 */
	@Override
	public long getTotalDrivers() {
		return _totalDrivers.longValue();
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#getIdleDrivers()
	 */
	@Override
	public long getIdleDrivers() {
		return _idleDrivers.longValue();
	}


	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#setTotalDrivers(long)
	 */
	@Override
	public void setTotalDrivers(final long total) {
		_totalDrivers.set(total);		
	}


	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#setIdleDrivers(long)
	 */
	@Override
	public void setIdleDrivers(long idles) {
		_idleDrivers.set(idles);
		if(this._totalDrivers.longValue() - this._idleDrivers.longValue() > this._maxUsedDrivers.longValue()){
			this._maxUsedDrivers.set(this._totalDrivers.longValue() - this._idleDrivers.longValue());
		}
	}
	
	

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#incrementInsuficientDrivers()
	 */
	@Override
	public void incrementInsuficientDrivers() {
		this._insuficientDrivers.getAndIncrement();
		
	}
	
	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#getInsuficientDrivers()
	 */
	@Override
	public long getInsuficientDrivers() {
		return _insuficientDrivers.longValue();
	}


	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#incrementConnectionRefused()
	 */
	@Override
	public void incrementConnectionRefused() {
		this._connectionRefused.getAndIncrement();		
	}


	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#getConnectionRefused()
	 */
	@Override
	public long getConnectionRefused() {
		return _connectionRefused.longValue();
	}

	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.JOSMMonitoringMXBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		_insuficientDrivers.set(INIT_VALUE);
		_connectionRefused.set(INIT_VALUE);
		_maxUsedDrivers.set(INIT_VALUE);
		_lastEndpointTimeMillis.set(INIT_VALUE);
	}


	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#getMaxUsedDrivers()
	 */
	@Override
	public long getMaxUsedDrivers() {
		return _maxUsedDrivers.longValue();
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#getLastEndpointTimeMillis()
	 */
	@Override
	public long getLastEndpointTimeMillis() {
		return _lastEndpointTimeMillis.longValue();
	}


	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.jmx.DriverMonitoringMXBean#setLastEndpointTimeMillis(long)
	 */
	@Override
	public void setLastEndpointTimeMillis(long lastEndpoint) {
		_lastEndpointTimeMillis.set(lastEndpoint);
	}	

}
