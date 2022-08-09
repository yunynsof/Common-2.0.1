package hn.com.tigo.josm.common.interfaces;

import hn.com.tigo.josm.common.exceptions.ResponseFaultMsg;
import hn.com.tigo.josm.subscription.dto.ActivateSubscriptionType;
import hn.com.tigo.josm.subscription.dto.ChangeNumberType;
import hn.com.tigo.josm.subscription.dto.DeactivateSubscriptionType;
import hn.com.tigo.josm.subscription.dto.DeactivateSubscriptionsAllType;
import hn.com.tigo.josm.subscription.dto.MessageACKType;
import hn.com.tigo.josm.subscription.dto.VerifySubscriptionType;

import javax.ejb.Remote;

/**
 * The interface SubscriptionRemote contains the <Usage of this class> for
 * Common project.
 *
 * @author 
 * @version 1.0.0
 * @since Sep 1, 2017
 */
@Remote
public interface SubscriptionRemote {

	/**
	 * Activate subscription.
	 *
	 * @param body
	 *            the body
	 * @return the message ACK type
	 * @throws ResponseFaultMsg
	 *             the response fault msg
	 */
	public MessageACKType activateSubscription(ActivateSubscriptionType body) throws ResponseFaultMsg;

	/**
	 * Deactivate subscription.
	 *
	 * @param body
	 *            the body
	 * @return the message ACK type
	 * @throws ResponseFaultMsg
	 *             the response fault msg
	 */
	public MessageACKType deactivateSubscription(DeactivateSubscriptionType body) throws ResponseFaultMsg;

	/**
	 * Deactivate subscriptions all.
	 *
	 * @param body
	 *            the body
	 * @return the message ACK type
	 * @throws ResponseFaultMsg
	 *             the response fault msg
	 */
	public MessageACKType deactivateSubscriptionsAll(DeactivateSubscriptionsAllType body) throws ResponseFaultMsg;

	/**
	 * Verify subscription.
	 *
	 * @param body
	 *            the body
	 * @return the message ACK type
	 * @throws ResponseFaultMsg
	 *             the response fault msg
	 */
	public MessageACKType verifySubscription(VerifySubscriptionType body) throws ResponseFaultMsg;

	/**
	 * Change number.
	 *
	 * @param body
	 *            the body
	 * @return the message ACK type
	 * @throws ResponseFaultMsg
	 *             the response fault msg
	 */
	public MessageACKType changeNumber(ChangeNumberType body) throws ResponseFaultMsg;

}
