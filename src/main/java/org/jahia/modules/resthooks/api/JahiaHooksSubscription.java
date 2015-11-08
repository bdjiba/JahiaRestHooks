package org.jahia.modules.resthooks.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

/**
 * The subscription mecanism regarding the documentation at 
 * http://resthooks.org/docs/
 * @author bdjiba
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NONE)
@JsonSubTypes.Type(value = JahiaHooksSubscriptionImpl.class)
public interface JahiaHooksSubscription extends Serializable{
  /**
   * Returns the subscription identifier
   * @return the subscription id
   */
  String getId();
  
  /**
   * Sets the subscription identifier
   * @param subId
   */
  void setId(String subId);
  
  /**
   * Returns event names the subscription includes.
   * As subscription topics
   * @return the event names
   */
  String[] getEvents();
  
  /**
   * Set the subscription event names.
   * The sub topics
   * @param v
   */
  void setEvents(String[] v);
  
  
  /**
   * The subscription owner.
   * @return
   */
  User getUser();
  
  /**
   * Sets the authenticated user.<br />
   * It is subscription parent user (owner)
   * @param v
   */
  void setUser(User v);
  
  /**
   * Gets the target URL to send the payloads
   * @return
   */
  String getCallbackURL();
  
  /**
   * Set the target URL that will be used to send the payloads.
   * @param v
   */
  void setCallbackURL(String v);
  
  /**
   * Gets the subscription status
   * @return
   */
  SubscriptionStatus getStatus();
  
  /**
   * Sets the subscription status
   * @param v
   */
  void setStatus(SubscriptionStatus v);
  
  
  /**
   * Get the max retry
   * @return
   */
  int getMaxRetiry();
  
  /**
   * Set the max retry count
   * @param v
   */
  void setMaxRetry(int v);
}
