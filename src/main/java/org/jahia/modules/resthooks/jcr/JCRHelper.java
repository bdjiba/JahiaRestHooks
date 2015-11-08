/**
 * 
 */
package org.jahia.modules.resthooks.jcr;

import java.util.Arrays;

import javax.jcr.RepositoryException;

import org.jahia.modules.resthooks.JahiaRestHooksContants;
import org.jahia.modules.resthooks.api.User;
import org.jahia.modules.resthooks.api.JahiaHooksSubscription;
import org.jahia.modules.resthooks.api.JahiaHooksSubscriptionImpl;
import org.jahia.modules.resthooks.api.SubscriptionStatus;
import org.jahia.modules.resthooks.exception.IncompatibleTypeException;
import org.jahia.modules.resthooks.exception.JahiaRestHooksException;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRPropertyWrapper;
import org.jahia.services.content.JCRValueWrapper;
import org.jahia.services.content.decorator.JCRUserNode;
import org.jahia.services.preferences.user.UserPreferencesHelper;
import org.jahia.services.usermanager.JahiaUser;

/**
 * This class is used to translate data in memory to JCR or JCR to memory.
 * 
 * @author bdjiba
 *
 */
public class JCRHelper implements JahiaRestHooksContants {

  /**
   * Transform a REST hook subscription JCR node to its memory representation
   * 
   * @param hookSubscriptionNode the JCR node
   * @return the memory representation of a JCR subscription node
   * @throws JahiaRestHooksException
   * @throws RepositoryException
   */
  public static JahiaHooksSubscription jcrRestHooksSubcriptionToJahiaHooksSubscription(
      JCRNodeWrapper hookSubscriptionNode) throws RepositoryException {
    if (hookSubscriptionNode == null) {
          return null;
    }
  
    if (!hookSubscriptionNode.getPrimaryNodeTypeName().equals(HOOKS_SUBS_JCR_NODE_TYPE_NAME)) {
          throw new RepositoryException("Given node type is not a Jahia hook subscription.", null);
    }
  
    JahiaHooksSubscription hookSubs = new JahiaHooksSubscriptionImpl();
    hookSubs.setId(hookSubscriptionNode.getIdentifier());
    hookSubs.setCallbackURL(
            hookSubscriptionNode.getPropertyAsString(HOOKS_SUBS_CALLBACK_URL_PROP_NAME));
  
    String[] topics = getMultivaluedStringProp(hookSubscriptionNode.getProperty(HOOKS_SUBS_EVENT_PROP_NAME));
    hookSubs.setEvents(topics);
    JCRUserNode user = (JCRUserNode) hookSubscriptionNode.getSession()
            .getNodeByUUID(hookSubscriptionNode.getPropertyAsString(HOOKS_SUBS_USER_PROP_NAME));
    JahiaUser jusr = user.getJahiaUser();
    User owner =
            new User(jusr.getUsername(), UserPreferencesHelper.getEmailAddress(user),
                UserPreferencesHelper.getFirstName(user), UserPreferencesHelper.getLastName(user));
    hookSubs.setUser(owner);
    hookSubs.setMaxRetry(
            Integer.parseInt(hookSubscriptionNode.getPropertyAsString(HOOKS_SUBS_RETRY_PROP_NAME)));
    // by default
    SubscriptionStatus status = SubscriptionStatus.INACTIVE;
    try {
          status = SubscriptionStatus
              .valueOf(hookSubscriptionNode.getPropertyAsString(HOOKS_SUBS_STATUS_PROP_NAME));
    } catch (IllegalArgumentException iaex) {
          // TODO: log a warn
    }
    hookSubs.setStatus(status);
    return hookSubs;
 }
  
  /**
   * EXPERIMENTAL: A multi valued prop item array representation.
   * Not tested yet !!!
   * @param targetProperty
   * @param v
   * @return
   * @throws RepositoryException
   */
  public static <T> T[] getMultivaluedProp(JCRPropertyWrapper targetProperty, T[] v) throws RepositoryException {
    JCRValueWrapper[] jcrPropValues = targetProperty.getValues();
    return Arrays.copyOf(jcrPropValues, jcrPropValues.length, (Class<? extends T[]>) v.getClass());
  }
  
  /**
   * Given a multi valued prop with String as type return its String array representation.  
   * @param targetProperty the node property
   * @return an array of string
   * @throws RepositoryException
   */
  public static String[] getMultivaluedStringProp(JCRPropertyWrapper targetProperty) throws RepositoryException {
    JCRValueWrapper[] jcrPropValues = targetProperty.getValues();
    String[] multivaluedProp = new String[jcrPropValues.length];
    for(int i = 0; i < multivaluedProp.length; i++) {
      multivaluedProp[i] = jcrPropValues[i].getString();
    }
    return multivaluedProp;
  }
}
