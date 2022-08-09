/**
 * CrudServiceRemote.java
 * Common
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.interfaces;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

/**
 * The interface CrudServiceRemote contains the <Usage of this class> for Common
 * project.
 *
 * @author
 * @version 1.0.0
 * @since Sep 1, 2017
 */
@Remote
public interface CrudServiceRemote {

	/**
	 * Creates the.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param t
	 *            the t
	 * @return the t
	 */
	public <T> T create(final T t);

	/**
	 * Find.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <S>
	 *            the generic type
	 * @param id
	 *            the id
	 * @param type
	 *            the type
	 * @return the t
	 */
	public <T, S> T find(final S id, final Class<T> type);

	/**
	 * Delete.
	 *
	 * @param <T>
	 *            the generic type
	 * @param <S>
	 *            the generic type
	 * @param id
	 *            the id
	 * @param type
	 *            the type
	 */
	public <T, S> void delete(final S id, final Class<T> type);

	/**
	 * Update.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param t
	 *            the t
	 * @return the t
	 */
	public <T> T update(final T t);

	/**
	 * Find by named query.
	 * 
	 * @param namedQueryName
	 *            the named query name
	 * @return the list
	 */
	public List<?> findByNamedQuery(final String namedQueryName);

	/**
	 * Find by named query.
	 * 
	 * @param namedQueryName
	 *            the named query name
	 * @param parameters
	 *            the parameters
	 * @return the list
	 */
	public List<?> findByNamedQuery(final String namedQueryName, final Map<String, Object> parameters);

	/**
	 * Find by named query.
	 * 
	 * @param namedQueryName
	 *            the named query name
	 * @param resultLimit
	 *            the result limit
	 * @return the list
	 */
	public List<?> findByNamedQuery(final String namedQueryName, final int resultLimit);

	/**
	 * Find by named query.
	 * 
	 * @param namedQueryName
	 *            the named query name
	 * @param parameters
	 *            the parameters
	 * @param resultLimit
	 *            the result limit
	 * @return the list
	 */
	public List<?> findByNamedQuery(final String namedQueryName, final Map<String, Object> parameters,
			final int resultLimit);

	/**
	 * Count by named query.
	 * 
	 * @param namedQueryName
	 *            the named query name
	 * @return the long
	 */
	public long countByNamedQuery(final String namedQueryName);

	/**
	 * Count by named query.
	 * 
	 * @param namedQueryName
	 *            the named query name
	 * @param parameters
	 *            the parameters
	 * @return the long
	 */
	public long countByNamedQuery(final String namedQueryName, final Map<String, Object> parameters);

	/**
	 * Creates the native query.
	 *
	 * @param nativeQueryName
	 *            the native query name
	 */
	public void createNativeQuery(final String nativeQueryName);

}
