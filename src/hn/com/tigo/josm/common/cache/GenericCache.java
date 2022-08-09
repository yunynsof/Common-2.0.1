package hn.com.tigo.josm.common.cache;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * The class GenericCache contains the logic for generic cache Items.
 *
 * @author Igor Zelaya
 * @version 1.0.0
 * @param <K>
 *            the key type
 * @param <T>
 *            the generic type
 * @since Jul 11, 2017
 */
public class GenericCache<K, T> {

	/** The generic items. */
	private Map<K, CacheData<T>> genericItems;

	/** The ttl. */
	private final int _ttl;

	/** The time unit. */
	private final int _timeUnit;

	/**
	 * Instantiates a new generic cache.
	 *
	 * @param ttl
	 *            the ttl
	 * @param timeUnit
	 *            the time unit
	 */
	public GenericCache(final int ttl, final int timeUnit) {
		genericItems = new HashMap<K, CacheData<T>>();
		_ttl = ttl;
		_timeUnit = timeUnit;
	}

	/**
	 * Gets the generic items.
	 *
	 * @return the generic items
	 */
	public Map<K, CacheData<T>> getGenericItems() {
		return genericItems;
	}

	/**
	 * Sets the generic items.
	 *
	 * @param genericItems
	 *            the generic items
	 */
	public void setGenericItems(Map<K, CacheData<T>> genericItems) {
		this.genericItems = genericItems;
	}

	/**
	 * Put cache item.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void putCacheItem(K key, T value) {
		final CacheData<T> item = new CacheData<T>();
		final Calendar current = Calendar.getInstance();
		current.add(_timeUnit, _ttl);
		item.setExpirationDateTime(current);
		item.setData(value);
		this.genericItems.put(key, item);
	}

	/**
	 * Gets the cache item.
	 *
	 * @param key
	 *            the key
	 * @return the cache item
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	public T getCacheItem(K key) {
		T data = null;
		CacheData<T> item = genericItems.get(key);
		if (item != null) {
			final Calendar current = Calendar.getInstance();
			final Calendar expirationDateTime = item.getExpirationDateTime();
			if (!expirationDateTime.before(current)) {
				data = item.getData();
			}
		}
		return data;
	}

}
