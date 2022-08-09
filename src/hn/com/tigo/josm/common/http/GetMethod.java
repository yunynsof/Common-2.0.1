package hn.com.tigo.josm.common.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;

/**
 * GetMethod.
 *
 * @author harold.castillo
 * @version 1.0
 * @param <T>
 *            the generic type
 * @since Oct 4, 2016 10:30:51 AM
 */
public abstract class GetMethod<T> extends ServiceInvoker<T> {

	/**
	 * Instantiates a new gets the method.
	 *
	 * @param url
	 *            the url
	 * @param buildable
	 *            the buildable
	 * @param properties
	 *            the properties
	 */
	public GetMethod(final String staticUrl, String url, Buildable buildable, Map<String, String> properties) {

		super(staticUrl, url, buildable, properties);

		httpRequestBase = new HttpGet(url);
		httpRequestBase.setConfig(buildable.getRequestConfig());

		for (Map.Entry<String, String> property : properties.entrySet()) {
			httpRequestBase.addHeader(property.getKey(), property.getValue());
		}
	}

	/**
	 * Instantiates a new gets the method.
	 *
	 * @param url
	 *            the url
	 * @param buildable
	 *            the buildable
	 */
	public GetMethod(final String staticUrl, String url, Buildable buildable) {
		super(staticUrl, url, buildable);
	}

	@Override
	protected void loadEntity(final String request, final String charset) throws UnsupportedEncodingException {
		return;
	}

}
