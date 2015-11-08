/**
 * 
 */
package org.jahia.modules.resthooks;

/**
 * @author bdjiba
 *
 */
public interface JahiaRestHooksContants {
  // JCR HOOKS Event
  static final String HOOKS_EVENT_JCR_NODE_NAME = "resthookevent";
  static final String HOOKS_EVENT_JCR_NODE_TYPE_NAME = "jnt:resthookevent";
  static final String HOOKS_EVENT_TYPE_PROP_NAME = "eventType";
  static final String HOOKS_EVENT_STATUS_PROP_NAME = "eventStatus";
  
  // JCR HOOKS SUBS
  static final String HOOK_SUBS_JCR_NODE_NAME = "resthooksub";
  static final String HOOKS_SUBS_JCR_NODE_TYPE_NAME = "jnt:resthooksubscription";
  static final String HOOKS_SUBS_EVENT_PROP_NAME = "topics";
  static final String HOOKS_SUBS_STATUS_PROP_NAME = "subscriptionStatus";
  static final String HOOKS_SUBS_CALLBACK_URL_PROP_NAME = "callbackURL";
  static final String HOOKS_SUBS_USER_PROP_NAME = "owner";
  static final String HOOKS_SUBS_RETRY_PROP_NAME = "maxRetry";
  
  // JCR HOOKS
  static final String HOOKS_PARENT_NODE_TYPE = "jnt:hooks";
  static final String HOOKS_PARENT_NODE_NAME = "hooks";
  static final String HOOKS_PARENT_NODE_PATH = "/settings";
}
