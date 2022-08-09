/**
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.http;

import java.util.Map;

import org.apache.http.client.methods.HttpDelete;

/**
 * HTTP DELETE method implementation.
 *
 * @author Harold Castillo
 * @version 1.0.0
 * @param <T> Generic type that represents the response's type.
 * @since 29/05/2018 09:32:05 AM
 */
public abstract class DeleteMethod<T> extends ServiceInvoker<T> {

	/**
	 * Instantiates a new HTTP DELETE method implementation.
	 *
	 * @param url the url
	 * @param buildable the buildable
	 * @param properties the properties
	 */
	public DeleteMethod(final String staticUrl, final String url, final Buildable buildable, final Map<String, String> properties) {
		super(staticUrl, url, buildable, properties);

		httpRequestBase = new HttpDelete(url);
		httpRequestBase.setConfig(buildable.getRequestConfig());

		for (Map.Entry<String, String> property : properties.entrySet()) {
			httpRequestBase.addHeader(property.getKey(), property.getValue());
		}
	}

}
