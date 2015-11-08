/**
 * 
 */
package org.jahia.modules.resthooks.jcr;

import java.util.List;

import org.jahia.modules.resthooks.api.JahiaHooksSubscription;
import org.jahia.modules.resthooks.exception.JahiaRestHooksException;

/**
 * @author bdjiba
 *
 */
public interface JCRHooksSubscriptionDao {
  
  JahiaHooksSubscription find(String id) throws JahiaRestHooksException;
  
  List<JahiaHooksSubscription> findAll() throws JahiaRestHooksException;
  
  JahiaHooksSubscription create(String[] topics, String callbackURL, String username, int maxRetry) throws JahiaRestHooksException;
  
  // not secure !!
  JahiaHooksSubscription create(String[] topics, String subscribtionStatus, String callbackURL, String username, int maxRetry) throws JahiaRestHooksException;
  
  JahiaHooksSubscription update(JahiaHooksSubscription sub) throws JahiaRestHooksException;
  
  String activate(String subId) throws JahiaRestHooksException;
  
  String inactivate(String subId) throws JahiaRestHooksException;
  
  String delete(String id) throws JahiaRestHooksException;
}
