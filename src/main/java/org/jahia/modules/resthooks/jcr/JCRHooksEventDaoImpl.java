/**
 * 
 */
package org.jahia.modules.resthooks.jcr;

import java.util.List;

import javax.annotation.Resource;
import javax.jcr.ItemNotFoundException;
import javax.jcr.RepositoryException;

import org.apache.jackrabbit.util.ISO8601;
import org.jahia.api.Constants;
import org.jahia.modules.resthooks.JahiaRestHooksContants;
import org.jahia.modules.resthooks.JahiaRestHooksManager;
import org.jahia.modules.resthooks.api.EventStatus;
import org.jahia.modules.resthooks.api.JahiaHooksEvent;
import org.jahia.modules.resthooks.api.JahiaHooksEventImpl;
import org.jahia.modules.resthooks.exception.JahiaRestHooksException;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author bdjiba
 *
 */
@Component
public class JCRHooksEventDaoImpl implements JCRHooksEventDao, JahiaRestHooksContants {
  private static final Logger logger = LoggerFactory.getLogger(JCRHooksEventDaoImpl.class);
  
  @Resource
  private JCRTemplate jcrTemplate;
  
  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.jcr.JCRHooksEventDao#find(java.lang.String)
   */
  @Override
  public JahiaHooksEvent find(String id) throws JahiaRestHooksException {
    if(!JahiaRestHooksManager.isHooksSupported()) {
      return null;
    }
    
    try {
      JCRNodeWrapper hookEventNode = jcrTemplate.getSessionFactory().getCurrentUserSession().getNodeByIdentifier(id);
      JahiaHooksEvent hookEvent = new JahiaHooksEventImpl(hookEventNode.getIdentifier(), hookEventNode.getPropertyAsString(HOOKS_EVENT_TYPE_PROP_NAME), EventStatus.valueOf(hookEventNode.getPropertyAsString(HOOKS_EVENT_STATUS_PROP_NAME)), ISO8601.parse(hookEventNode.getPropertyAsString(Constants.JCR_CREATED)).getTime());
      return hookEvent;
    } catch (ItemNotFoundException infe) {
      logger.error("Item not found error for id: " + id, infe);
    } catch (RepositoryException re) {
      logger.error("Repo exception when getting hook event by id: " + id, re);
    }
    return null;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.jcr.JCRHooksEventDao#findAll()
   */
  @Override
  public List<JahiaHooksEvent> findAll() throws JahiaRestHooksException{
    // TODO:
    String queryString = null;
    
    return null;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.jcr.JCRHooksEventDao#save(org.jahia.modules.resthooks.api.Event)
   */
  @Override
  public JahiaHooksEvent save(JahiaHooksEvent evt) throws JahiaRestHooksException {
    // TODO save the event
    return null;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.jcr.JCRHooksEventDao#delete(org.jahia.modules.resthooks.api.Event)
   */
  @Override
  public void delete(JahiaHooksEvent evt) throws JahiaRestHooksException {
    // TODO delete restevent under /sites/<sitekey>/
    
  }

}
