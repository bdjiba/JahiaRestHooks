/**
 * 
 */
package org.jahia.modules.resthooks.front;

import javax.annotation.Resource;

import org.apache.commons.lang.NumberUtils;
import org.apache.felix.framework.resolver.ResourceNotFoundException;
import org.jahia.modules.resthooks.api.JahiaHooksSubscription;
import org.jahia.modules.resthooks.api.JahiaHooksSubscriptionImpl;
import org.jahia.modules.resthooks.api.SubscriptionStatus;
import org.jahia.modules.resthooks.exception.JahiaRestHooksException;
import org.jahia.modules.resthooks.jcr.JCRHooksSubscriptionDao;
import org.jahia.modules.resthooks.provider.SubscriptionSpi;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bdjiba
 *
 */
@Component
public class JahiaHookSubscriptionController implements SubscriptionSpi {
  private static final Logger log = LoggerFactory.getLogger(JahiaHookSubscriptionController.class);
  
  @Resource
  private JCRHooksSubscriptionDao  jCRHooksSubscriptionDao;
  
  


  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.provider.SubscriptionSpi#updateSubscription(java.lang.String, org.jahia.modules.resthooks.api.JahiaHooksSubscription)
   */
  @Override
  public ResponseEntity<JahiaHooksSubscription> updateSubscription(String subsId,
      JahiaHooksSubscription subs) throws ResourceNotFoundException {
    log.info("@@ update subscription: " + subsId);
    // TODO: update
    return null;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.provider.SubscriptionSpi#deleteSubscription(java.lang.String)
   */
  @Override
  public ResponseEntity<JahiaHooksSubscription> deleteSubscription(String subId) {
    log.info("@@ delete subscription: " + subId);
    // TODO: remove from JCR
    return null;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.provider.SubscriptionSpi#getSubscription(java.lang.String)
   */
  @Override
  public ResponseEntity<JahiaHooksSubscription> getSubscription(String subId) {
    log.info("@@ getting subscription: " + subId);
    // TODO get the sub
    return null;
  }

  @Override
  public ResponseEntity<JahiaHooksSubscription> createSubscription(@RequestParam(value = "event", required = true)String[] topics, @RequestParam(value = "url", required = true)String callbackURL, @RequestParam(value = "owner", required= true) String username, @RequestParam("max_retry") String maxRetry) {
    log.info("@@ creating subscription for topics " + topics + " by " + username + " for url " + callbackURL);
    try {
      JahiaHooksSubscription jahiaHooksSubscription = jCRHooksSubscriptionDao.create(topics, callbackURL, username, NumberUtils.stringToInt(maxRetry, 3));
      HttpHeaders headers = new HttpHeaders();
      headers.set("Warning", "Your subscription is created but you should request it activation before it works.");
      return new ResponseEntity<JahiaHooksSubscription>(jahiaHooksSubscription, headers, HttpStatus.CREATED);
    } catch (JahiaRestHooksException e) {
      log.error(e.getMessage(), e);
      HttpHeaders headers = new HttpHeaders();
      headers.set("Warning", "Your subscription is not created. Cause " + e.toString());
      new ResponseEntity<JahiaHooksSubscription>(new JahiaHooksSubscriptionImpl(), headers, HttpStatus.BAD_REQUEST);
    }
    
    return null;
  }
  
  @Override
  public ResponseEntity<JahiaHooksSubscription> createSubscription(HttpEntity<String> request) {
    log.info("@@ create subscription: ");
    // TODO Create the subscription as it is sent from external app ?? or ignore the status to update it later for security
    try {
      JSONObject json = new JSONObject(request.getBody());
      log.info("Receive subscription: " + json.toString());
      String[] events = json.optString("event", "").split(",");
      log.info("TOPICS: " + events[0]);
      // FIXME be stored ?? (to know when the request come from)
      String subURL = json.optString("subscription_url");
      String subCallbackURL = json.optString("target_url");
      String username = json.optString("owner", null);
      int maxRetry = json.optInt("max_retry", 1);
      JahiaHooksSubscription jahiaHooksSubscription = jCRHooksSubscriptionDao.create(events, SubscriptionStatus.ACTIVE.name(),subCallbackURL, username, maxRetry);
      HttpHeaders headers = new HttpHeaders();
      //headers.set("Warning", "Your subscription is created but you should request it activation before it works.");
      return new ResponseEntity<JahiaHooksSubscription>(jahiaHooksSubscription, headers, HttpStatus.CREATED);
    } catch (JahiaRestHooksException e) {
      log.error(e.getMessage(), e);
      HttpHeaders headers = new HttpHeaders();
      headers.set("Warning", "Your subscription is not created. Cause " + e.toString());
      new ResponseEntity<JahiaHooksSubscription>(new JahiaHooksSubscriptionImpl(), headers, HttpStatus.BAD_REQUEST);
    } catch (JSONException e) {
      log.info(e.getMessage(), e);
    }
    return null;
  }

  @Override
  public ResponseEntity<JahiaHooksSubscription> activateSubscription(String subId) {
    log.info("@@ activate subscription: " + subId);
    // TODO call activate status
    return null;
  }

  @Override
  public ResponseEntity<JahiaHooksSubscription> inactivateSubscription(String subId) {
    log.info("@@ inactivate subscription: " + subId);
    // TODO call desactivation
    return null;
  }

  

}
