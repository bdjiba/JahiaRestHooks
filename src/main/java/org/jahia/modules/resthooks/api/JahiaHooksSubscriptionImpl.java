/**
 * 
 */
package org.jahia.modules.resthooks.api;

import org.jahia.modules.resthooks.converter.UserDeserializer;
import org.jahia.modules.resthooks.converter.UserSerializer;
import org.jahia.modules.resthooks.converter.SubscriptionStatusDeserializer;
import org.jahia.modules.resthooks.converter.SubscriptionStatusSerializer;
import org.jahia.modules.resthooks.mvc.view.BaseView;
import org.jahia.modules.resthooks.mvc.view.PayloadDataView;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author bdjiba
 *
 */
public class JahiaHooksSubscriptionImpl implements JahiaHooksSubscription {
  /**
   * 
   */
  private static final long serialVersionUID = -6511531948866663807L;
  
  //@JsonProperty("id")
  private String id;
  
  //@JsonProperty("events")
  private String[] events;
  
  //@JsonProperty("user")
  @JsonSerialize(using=UserSerializer.class)
  @JsonDeserialize(using=UserDeserializer.class)
  private User user;
  
  //@JsonProperty("callbackURL")
  private String callBackURL;
  
  //@JsonProperty("status")
  @JsonSerialize(using=SubscriptionStatusSerializer.class)
  @JsonDeserialize(using=SubscriptionStatusDeserializer.class)
  private SubscriptionStatus status;
  
  //@JsonProperty("maxRetry")
  private int maxRetry;

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksSubscription#getId()
   */
  @Override
  public String getId() {
    return id;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksSubscription#setId(java.lang.String)
   */
  @Override
  public void setId(String subId) {
    this.id = subId;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksSubscription#getUser()
   */
  @Override
  public User getUser() {
    return user;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksSubscription#setUser(org.jahia.modules.resthooks.api.AuthenticatedUser)
   */
  @Override
  public void setUser(User v) {
    this.user = v;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksSubscription#getCallbackURL()
   */
  @Override
  public String getCallbackURL() {
    return callBackURL;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksSubscription#setCallbackURL(java.lang.String)
   */
  @Override
  public void setCallbackURL(String v) {
    this.callBackURL = v;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksSubscription#getStatus()
   */
  @Override
  public SubscriptionStatus getStatus() {
    return status;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksSubscription#setStatus(org.jahia.modules.resthooks.api.SubscriptionStatus)
   */
  @Override
  public void setStatus(SubscriptionStatus v) {
    this.status = v;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksSubscription#getMaxRetiry()
   */
  @Override
  public int getMaxRetiry() {
    return maxRetry;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksSubscription#setMaxRetry(int)
   */
  @Override
  public void setMaxRetry(int v) {
    this.maxRetry = v;
  }

  @Override
  public String[] getEvents() {
    
    return events;
  }

  @Override
  public void setEvents(String[] v) {
    this.events = v;
  }

}
