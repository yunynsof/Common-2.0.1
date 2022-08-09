/**
 * BrokerLocal.java
 * Broker
 * Copyright (c) Tigo Honduras.
 */
package hn.com.tigo.josm.common.interfaces;

import javax.ejb.Local;

import hn.com.tigo.josm.common.dto.MetaOrderRequest;
import hn.com.tigo.josm.common.exceptions.BrokerException;
import hn.com.tigo.josm.common.order.EnumOrderType;
import hn.com.tigo.josm.common.order.OrderResponse;
import hn.com.tigo.josm.component.dto.JOSMResponse;

/**
 * BrokerLocal. Broker interface for local access
 * 
 * @author Juan Pablo Gomez<mailto:jgomezg@stefaninicolombia.com />
 * @version 1.0
 * @since 21/10/2014 04:52:16 PM 2014
 */
@Local
public interface BrokerLocal {

	/**
	 * Executes a broker order. This method will process a orderRequest applying
	 * broker's logic
	 * 
	 * @param orderRequest
	 *            The value of the order request
	 * @return the product The product response value
	 * @throws BrokerException
	 *             Throws broker exception when broker's logic validations fail
	 */
	OrderResponse executeOrder(MetaOrderRequest orderRequest) throws BrokerException;

	/**
	 * Compile bpmn.
	 *
	 * @param productFamilyId
	 *            the product family id
	 * @param orderType
	 *            the order type
	 * @return the JOSM response
	 * @throws BrokerException
	 *             the broker exception
	 */
	JOSMResponse compileBpmn(final Long productFamilyId, final String orderType) throws BrokerException;

	/**
	 * Method responsible to change the status product.
	 *
	 * @param productId
	 *            the product id
	 * @param orderType
	 *            the order type
	 * @param enable
	 *            the enable
	 * @return the JOSM response
	 * @throws BrokerException
	 *             the broker exception
	 */
	JOSMResponse changeStatusProduct(final Long productId, final EnumOrderType orderType, final Boolean enable) throws BrokerException;

	/**
	 * Compile xml.
	 *
	 * @param xml
	 *            the xml
	 * @return the byte[]
	 * @throws BrokerException
	 *             the broker exception
	 */
	byte[] compileXml(final String xml) throws BrokerException;
	
}
