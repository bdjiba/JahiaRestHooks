/**
 * 
 */
package org.jahia.modules.resthooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jahia.api.Constants;
import org.jahia.modules.resthooks.client.HookNotifier;
import org.jahia.modules.resthooks.jcr.JCRHelper;
import org.jahia.services.content.DefaultEventListener;
import org.jahia.services.content.JCRCallback;
import org.jahia.services.content.JCREventIterator;
import org.jahia.services.content.JCRNodeIteratorWrapper;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRObservationManager;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.content.JCRTemplate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bdjiba
 *
 */
//@Component
public class JahiaHooksSubscriptionManager extends DefaultEventListener implements JahiaRestHooksContants {
  private static final Logger log = LoggerFactory.getLogger(JahiaHooksSubscriptionManager.class);
  
  //private static final Map<Integer, Map<String, List<String>>> hooksCallbackRegist
  
  // let give a quick dirty quick implementation
  //private static final Map<Integer, List<String>> JCR_EVENT_MAPPING = new HashMap<Integer, List<String>>();
  
  // topic vs jcr event mapping
  private static final Map<String, Integer> TOPIC_JCR_EVENT_MAPPIG = new HashMap<String, Integer>();
  
  private static final Map<String, String> EVENT_TYPE_MAPPING = new HashMap<String, String>();
  static {
    TOPIC_JCR_EVENT_MAPPIG.put("task_created", Event.NODE_ADDED);
    TOPIC_JCR_EVENT_MAPPIG.put("task_update", Event.PROPERTY_CHANGED); // ??
    TOPIC_JCR_EVENT_MAPPIG.put("task_removed", Event.NODE_REMOVED);
    
    //TOPIC_JCR_EVENT_MAPPIG.put("contact_created", Event.NODE_ADDED);
    //TOPIC_JCR_EVENT_MAPPIG.put("contact_updated", Event.NODE_ADDED);
    //TOPIC_JCR_EVENT_MAPPIG.put("contact_removed", Event.NODE_ADDED);
    // etc.
    // which jcr type the event is listening
    EVENT_TYPE_MAPPING.put("task_created", Constants.JAHIANT_TASK);
    EVENT_TYPE_MAPPING.put("task_updated", Constants.JAHIANT_TASK);
    EVENT_TYPE_MAPPING.put("task_removed", Constants.JAHIANT_TASK);
  }
  
  /* */
   private static class Holder {
    static final JahiaHooksSubscriptionManager INSTANCE = new JahiaHooksSubscriptionManager();
  }
  
  
  private JahiaHooksSubscriptionManager(){
    super();
    log.info("Subscription manager intit");
  }
  
  public static JahiaHooksSubscriptionManager getInstance(){
    return Holder.INSTANCE;
  }
  
  
  //@Resource
  private JCRTemplate jcrTemplate = JCRTemplate.getInstance();
  
  //@PostConstruct
  public void registerAsListener() {
    registerForJCREvents();

  }

  @Override
  public void onEvent(final EventIterator events) {
    final int operationType = ((JCREventIterator) events).getOperationType();
    log.info("Event occured with operation type " + operationType);
    // Fetch the corresponding subsrib
    try {
      
        jcrTemplate.doExecuteWithSystemSession(new JCRCallback<Void>() {
        
        @Override
        public Void doInJCR(JCRSessionWrapper session) throws RepositoryException {
          processJCREvent(session, events);
          return null;
        }
        
      });
    } catch (RepositoryException e) {
      log.error(e.getMessage(), e);
    }
    
  }

  @Override
  public int getEventTypes() {
    return Event.NODE_ADDED + Event.NODE_REMOVED + Event.PROPERTY_ADDED + Event.PROPERTY_CHANGED +
        Event.PROPERTY_REMOVED;
  }
  
  /**
   * Returns the node supported node types.
   * TODO: let make it configurable later
   * @return
   */
  public String[] getPrimaryNodeTypeNames(){
    return EVENT_TYPE_MAPPING.values().toArray(new String[0]);
  }
  
  @Override
  public String[] getNodeTypes() {
    return getPrimaryNodeTypeNames();
  }
  
  protected void registerForJCREvents(){
    log.info("MM - Register for JCR store changes.");
    try {
      jcrTemplate.doExecuteWithSystemSession(new JCRCallback<Void>() {

        @Override
        public Void doInJCR(JCRSessionWrapper session) throws RepositoryException {
          try {
            JCRObservationManager jcrObservationManager = new JCRObservationManager(session.getWorkspace());
            jcrObservationManager.addEventListener(JahiaHooksSubscriptionManager.getInstance(), getEventTypes(), null, false, null, getNodeTypes(), true);
          } catch (RepositoryException e) {
            log.info("erreur msg "+ e.getMessage(), e);
          }
          return null;
        }
        
      });
    } catch (RepositoryException e) {
      // TODO Auto-generated catch block
      log.error(e.getMessage(), e);
    }
    
   /* try {
      JCRObservationManager jcrObservationManager = new JCRObservationManager(jcrTemplate.getSessionFactory().getCurrentUserSession().getWorkspace());
      jcrObservationManager.addEventListener(this, getEventTypes(), null, false, null, getNodeTypes(), true);
    } catch (RepositoryException e) {
      log.info(e.getMessage(), e);
    }*/
  }
  
  protected void processJCREvent(final JCRSessionWrapper session, final EventIterator events) throws ItemNotFoundException, RepositoryException{
    while(events.hasNext()) {
      Event evt = events.nextEvent();
      if(TOPIC_JCR_EVENT_MAPPIG.containsValue(evt.getType())){
        // supported
        JCRNodeWrapper targetNode = session.getNodeByIdentifier(evt.getIdentifier());
        String nodeType = targetNode.getPrimaryNodeTypeName();
        List<String> topicList = new ArrayList<String>();
        for(Map.Entry<String, Integer> entry : TOPIC_JCR_EVENT_MAPPIG.entrySet()) {
          if(entry.getValue() == evt.getType() && StringUtils.equals(nodeType, EVENT_TYPE_MAPPING.get(entry.getKey()))) {
            topicList.add(entry.getKey());
          }
        }
        // corresponding subs
        JCRNodeWrapper subsParent = session.getNode(JahiaRestHooksManager.getJahiaHooksPath());
        JCRNodeIteratorWrapper nodesWappper = subsParent.getNodes();
        // this list collect found dummy subs
        List<String> dummySubcriptionIdList = new ArrayList<String>();
        while(nodesWappper.hasNext()) {
          Node node = nodesWappper.nextNode();
          if(StringUtils.equalsIgnoreCase(HOOKS_SUBS_JCR_NODE_TYPE_NAME, node.getPrimaryNodeType().getName())) {
            // found a subscription
            for(String topic : topicList){
              if(ArrayUtils.contains(JCRHelper.getMultivaluedStringProp(((JCRNodeWrapper)node).getProperty(HOOKS_SUBS_EVENT_PROP_NAME)),topic)) {
                // found subscriber to notify
                // we get the callback URL
                String callbackURL = node.getProperty(HOOKS_SUBS_CALLBACK_URL_PROP_NAME).getString();
                // TODO notify: call the external service with the node id as payload
                //
                String notifResponse = HookNotifier.notifySubscriber(callbackURL, topic, targetNode);
                // todo change to debug
                if("401".equalsIgnoreCase(notifResponse)) {
                  // Resect convention for moved subscriptions: to be REMOVE from jcr
                  // FIXME: delete directly may leads to issue
                  dummySubcriptionIdList.add(node.getIdentifier());
                }
                log.info("Notify subscriber response: " + notifResponse);
              }
            }
          }
        }
        // clean: remove dummy subs
        for(String uid : dummySubcriptionIdList) {
          session.getNodeByIdentifier(uid).remove();
        }
        // save session to apply deletion
        session.save();
      }
      
    }
  }

}
