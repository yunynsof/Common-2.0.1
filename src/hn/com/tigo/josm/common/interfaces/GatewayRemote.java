/**
 * GatewayRemote.java
 * Gateway
 * Copyright (C) Tigo Honduras
 */
package hn.com.tigo.josm.common.interfaces;

import javax.ejb.Remote;

import hn.com.tigo.josm.common.dto.MetaOrderRequest;
import hn.com.tigo.josm.common.exceptions.BrokerException;
import hn.com.tigo.josm.common.exceptions.GatewayException;
import hn.com.tigo.josm.common.order.OrderResponse;
import hn.com.tigo.josm.component.dto.JOSMResponse;

/**
 * GatewayRemote.
 * 
 * Remote interface that exposes the methods for session bean implementation
 * Gateway.
 * 
 * @author Jose David Martinez Rico
 * @version 1.0
 * @since 21/10/2014 11:04:10 AM 2014
 */
@Remote
public interface GatewayRemote {

	/**
	 * This method that execute any order of the Gateway and is it the
	 * responsible of validate the plugins associated.
	 * 
	 * @param gatewayRequest
	 *            The value of the order request
	 * @return the order response from the gateway
	 * @throws GatewayException
	 *             Throws broker exception when broker's logic validations fail
	 */
	OrderResponse executeOrder(MetaOrderRequest gatewayRequest) throws GatewayException;

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
	JOSMResponse compileBpmn(final Long productFamilyId, final String orderType) throws GatewayException;

}
