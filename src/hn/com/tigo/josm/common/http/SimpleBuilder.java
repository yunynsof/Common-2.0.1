package hn.com.tigo.josm.common.http;

import hn.com.tigo.josm.common.cache.HttpClientConfigurationCache;
import hn.com.tigo.josm.common.configuration.dto.HttpClientConfiguration;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;

/**
 * SimpleBuilder.
 *
 * @author harold.castillo
 * @version 1.0
 * @since 09-27-2016 04:58:37 PM
 */
public class SimpleBuilder extends AbstractBuilder {

	/** The httpclient. */
	private CloseableHttpClient httpclient;

	/** The http client configuration. */
	private HttpClientConfiguration httpClientConfiguration;

	/** The http client configuration cache. */
	private HttpClientConfigurationCache httpClientConfigurationCache;

	/** The request config. */
	private RequestConfig requestConfig;

	/**
	 * Instantiates a new simple builder.
	 *
	 * @param httpConfigName
	 *            the http config name
	 */
	public SimpleBuilder(final String httpConfigName) {
		super(httpConfigName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hn.com.tigo.josm.common.http.AbstractBuilder#createHttpClient()
	 */
	public void createHttpClient() {

		httpClientConfiguration = httpClientConfigurationCache.retrieve();
		final HttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();
		final Builder builder = RequestConfig.custom();
		builder.setConnectTimeout(httpClientConfiguration.getConnectTimeout());
		builder.setConnectionRequestTimeout(httpClientConfiguration.getConnectionRequestTimeout());
		builder.setSocketTimeout(httpClientConfiguration.getSocketTimeout());
		requestConfig = builder.build();
		final HttpClientBuilder httpClientBuilder = HttpClients.custom();
		httpClientBuilder.setConnectionManager(connManager);
		httpclient = httpClientBuilder.build();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hn.com.tigo.josm.common.http.AbstractBuilder#getHttpclient()
	 */
	public CloseableHttpClient getHttpclient() {
		return httpclient;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hn.com.tigo.josm.common.http.AbstractBuilder#getRequestConfig()
	 */
	public RequestConfig getRequestConfig() {
		return requestConfig;
	}

}
