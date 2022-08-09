/**
 * HttpBuilder.java
 * AbstractBuilder
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.http;

import hn.com.tigo.josm.common.cache.HttpClientConfigurationCache;
import hn.com.tigo.josm.common.configuration.dto.HttpClientConfiguration;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * AbstractBuilder.
 *
 * @author harold.castillo
 * @version 1.0
 * @since 09-27-2016 10:20:38 PM
 */
public abstract class AbstractBuilder implements Buildable {

	/** The httpclient. */
	protected CloseableHttpClient httpclient;

	/** The http client configuration. */
	protected HttpClientConfiguration httpClientConfiguration;

	/** The http client configuration cache. */
	protected HttpClientConfigurationCache httpClientConfigurationCache;

	/** The request config. */
	protected RequestConfig requestConfig;

	/**
	 * Instantiates a new abstract builder.
	 *
	 * @param httpConfigName
	 *            the http config name
	 */
	protected AbstractBuilder(final String httpConfigName) {
		httpClientConfigurationCache = new HttpClientConfigurationCache(httpConfigName);
		this.createHttpClient();
	}

	/**
	 * Creates the http client.
	 */
	protected abstract void createHttpClient();

	/*
	 * (non-Javadoc)
	 * 
	 * @see hn.com.tigo.josm.common.http.Buildable#getHttpclient()
	 */
	public CloseableHttpClient getHttpclient() {
		return httpclient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hn.com.tigo.josm.common.http.Buildable#getRequestConfig()
	 */
	public RequestConfig getRequestConfig() {
		return requestConfig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hn.com.tigo.josm.common.http.Buildable#getHttpClientConfigurationCache()
	 */
	public HttpClientConfigurationCache getHttpClientConfigurationCache() {
		return httpClientConfigurationCache;
	}

}
