/**
 * 
 */
package org.jahia.modules.resthooks.jcr;

import java.util.List;

import javax.annotation.Resource;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jahia.modules.resthooks.JahiaRestHooksContants;
import org.jahia.modules.resthooks.JahiaRestHooksManager;
import org.jahia.modules.resthooks.api.JahiaHooksSubscription;
import org.jahia.modules.resthooks.api.SubscriptionStatus;
import org.jahia.modules.resthooks.exception.InternalServerException;
import org.jahia.modules.resthooks.exception.InvalidResourceException;
import org.jahia.modules.resthooks.exception.JahiaRestHooksException;
import org.jahia.services.content.JCRCallback;
import org.jahia.services.content.JCRContentUtils;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.content.JCRTemplate;
import org.jahia.services.content.decorator.JCRUserNode;
import org.jahia.services.usermanager.JahiaUserManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author bdjiba
 *
 */
@Component
public class JCRHooksSubscriptionDaoImpl implements JCRHooksSubscriptionDao, JahiaRestHooksContants {
  private static final Logger logger = LoggerFactory.getLogger(JCRHooksSubscriptionDaoImpl.class);
  
  @Resource
  private JCRTemplate jcrTemplate;
  @Resource
  private JahiaUserManagerService userManagerService;

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.jcr.JCRHooksSubscriptionDao#find(java.lang.String)
   */
  @Override
  public JahiaHooksSubscription find(String id) throws JahiaRestHooksException {
    if(!JahiaRestHooksManager.isHooksSupported()) {
      return null;
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.jcr.JCRHooksSubscriptionDao#findAll()
   */
  @Override
  public List<JahiaHooksSubscription> findAll() throws JahiaRestHooksException {
    if(!JahiaRestHooksManager.isHooksSupported()) {
      return null;
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.jcr.JCRHooksSubscriptionDao#delete(java.lang.String)
   */
  @Override
  public String delete(final String id) throws JahiaRestHooksException {
    if(!JahiaRestHooksManager.isHooksSupported()) {
      return null;
    }
    try {
      String deletionStatus = jcrTemplate.doExecuteWithSystemSession(new JCRCallback<String>() {
        @Override
        public String doInJCR(JCRSessionWrapper session) throws RepositoryException {
          session.getNodeByIdentifier(id).remove();
          session.save();
          return "removed";
        }
        
      });
      return deletionStatus;
    } catch (RepositoryException re) {
      logger.error("Error during subscription deletion", re);
      throw new InternalServerException("Error during subscription deletion: "  + re.getMessage(), re, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public JahiaHooksSubscription create(final String[] topics, final String callbackURL, final String username, final int maxRetry) throws JahiaRestHooksException {
    return create(topics, SubscriptionStatus.INACTIVE.name().toLowerCase(),callbackURL, username, maxRetry);
  }
  
  @Override
  public JahiaHooksSubscription create(final String[] topics, final String subscribtionStatus,
      final String callbackURL, final String username, final int maxRetry) throws JahiaRestHooksException {
    if(!JahiaRestHooksManager.isHooksSupported()) {
      return null;
    }
    JahiaHooksSubscription subscription = null;
    try {
      subscription = jcrTemplate.doExecuteWithSystemSession(new JCRCallback<JahiaHooksSubscription>() {
        
        @Override
        public JahiaHooksSubscription doInJCR(JCRSessionWrapper session) throws RepositoryException {
          JCRNodeWrapper jahiaHooks = session.getNode(JahiaRestHooksManager.getJahiaHooksPath());
          // TODO: check the node existance (duplication)
          // create a subs and its event
          
          String nodeName = JCRContentUtils.findAvailableNodeName(jahiaHooks, HOOK_SUBS_JCR_NODE_NAME);
          JCRNodeWrapper subsNode = jahiaHooks.addNode(nodeName,HOOKS_SUBS_JCR_NODE_TYPE_NAME);
          subsNode.setProperty(HOOKS_SUBS_CALLBACK_URL_PROP_NAME, callbackURL);
          subsNode.setProperty(HOOKS_SUBS_EVENT_PROP_NAME, topics);
          String status = SubscriptionStatus.INACTIVE.name().toLowerCase();
          if(StringUtils.isNotBlank(subscribtionStatus) && SubscriptionStatus.valueOf(subscribtionStatus.toUpperCase()) == SubscriptionStatus.ACTIVE) {
            status =  SubscriptionStatus.ACTIVE.name().toLowerCase();
          }
          // By defaut: a subscrption is inactive when it is created
          subsNode.setProperty(HOOKS_SUBS_STATUS_PROP_NAME, status);
          subsNode.setProperty(HOOKS_SUBS_RETRY_PROP_NAME, maxRetry);
          String ownerId = "unknown"; // by default?? default user
          if(StringUtils.isNotBlank(username)) {
            // authenticated jahia user as the subscriber
            JCRUserNode owner = userManagerService.lookupUser(username);
            if(owner != null) {
              ownerId = owner.getIdentifier();
            }
          }
          subsNode.setProperty(HOOKS_SUBS_USER_PROP_NAME, ownerId, PropertyType.WEAKREFERENCE);
          // TODO
          session.save();
          
          return JCRHelper.jcrRestHooksSubcriptionToJahiaHooksSubscription(subsNode);
        }});
    } catch (RepositoryException re) {
      logger.error("Repo error when creating subscription", re);
      throw new InternalServerException(re.getMessage(),re, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return subscription;
  }

  @Override
  public JahiaHooksSubscription update(final JahiaHooksSubscription sub) throws JahiaRestHooksException {
    if(!isValidSubscription(sub)) {
      throw new InvalidResourceException("Could not update invalid subscription. These fields are mandatory: callback, topics (event names), username", HttpStatus.NOT_ACCEPTABLE);
    }
    if(!JahiaRestHooksManager.isHooksSupported()) {
      return sub;
    }
    JahiaHooksSubscription subscription = null;
    try {
      subscription = jcrTemplate.doExecuteWithSystemSession(new JCRCallback<JahiaHooksSubscription>() {

        @Override
        public JahiaHooksSubscription doInJCR(JCRSessionWrapper session) throws RepositoryException {
          
          // TODO: check the node existance (duplication)
          // create a subs and its event
          JCRNodeWrapper subsNode = session.getNodeByUUID(sub.getId());
          // check which props have changed
          if(!StringUtils.equals(subsNode.getPropertyAsString(HOOKS_SUBS_CALLBACK_URL_PROP_NAME), sub.getCallbackURL())){
            subsNode.setProperty(HOOKS_SUBS_CALLBACK_URL_PROP_NAME, sub.getCallbackURL());
          }
          // override the existing with the new one. Will check for merge
          if(!ArrayUtils.isEquals(sub.getEvents(),JCRHelper.getMultivaluedStringProp(subsNode.getProperty(HOOKS_SUBS_EVENT_PROP_NAME)))){
            subsNode.setProperty(HOOKS_SUBS_EVENT_PROP_NAME, sub.getEvents());
          }
          
          if(!StringUtils.equals(subsNode.getPropertyAsString(HOOKS_SUBS_STATUS_PROP_NAME), sub.getStatus().name())){
            subsNode.setProperty(HOOKS_SUBS_STATUS_PROP_NAME, sub.getStatus().name());
          }
          
          if(Integer.parseInt(subsNode.getPropertyAsString(HOOKS_SUBS_RETRY_PROP_NAME)) != sub.getMaxRetiry()){
            subsNode.setProperty(HOOKS_SUBS_RETRY_PROP_NAME, sub.getMaxRetiry());
          }
          //FIXME: We could not update user
          session.save();
          return JCRHelper.jcrRestHooksSubcriptionToJahiaHooksSubscription(subsNode);
        }});
    } catch (RepositoryException re) {
      logger.error("Repo error when updating subscription", re);
      throw new InternalServerException(re.getMessage(),re, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return subscription;
  }

  @Override
  public String activate(final String subId)
      throws JahiaRestHooksException {
    try {
      String newSubscriptionStatus = jcrTemplate.doExecuteWithSystemSession(new JCRCallback<String>() {
        @Override
        public String doInJCR(JCRSessionWrapper session) throws RepositoryException {
          JCRNodeWrapper targetSub = session.getNodeByIdentifier(subId);
          targetSub.setProperty(HOOKS_SUBS_STATUS_PROP_NAME, SubscriptionStatus.ACTIVE.name().toLowerCase());
          session.save();
          return targetSub.getPropertyAsString(HOOKS_SUBS_STATUS_PROP_NAME);
        }
        
      });
      return newSubscriptionStatus;
    } catch (RepositoryException re) {
      logger.error("Error during subscription activation", re);
      throw new InternalServerException("Error during subscription activation: "  + re.getMessage(), re, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public String inactivate(final String subId)
      throws JahiaRestHooksException {
    try {
      String newSubscriptionStatus = jcrTemplate.doExecuteWithSystemSession(new JCRCallback<String>() {
        @Override
        public String doInJCR(JCRSessionWrapper session) throws RepositoryException {
          JCRNodeWrapper targetSub = session.getNodeByIdentifier(subId);
          targetSub.setProperty(HOOKS_SUBS_STATUS_PROP_NAME, SubscriptionStatus.INACTIVE.name().toLowerCase());
          session.save();
          return targetSub.getPropertyAsString(HOOKS_SUBS_STATUS_PROP_NAME);
        }
        
      });
      return newSubscriptionStatus;
    } catch (RepositoryException re) {
      logger.error("Error during subscription inactivation", re);
      throw new InternalServerException("Error during subscription inactivation: " + re.getMessage(), re, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  private boolean isValidSubscription(JahiaHooksSubscription subs) {
    boolean isValidSub = true;
    if(StringUtils.isBlank(subs.getCallbackURL()) || ArrayUtils.isEmpty(subs.getEvents()) || subs.getUser() == null || StringUtils.isBlank(subs.getUser().getUsername())) {
      isValidSub = false;
    }
    return isValidSub;
  }

}
