/**
 * 
 */
package org.jahia.modules.resthooks;

import javax.jcr.ItemExistsException;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.jahia.services.content.JCRCallback;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRObservationManager;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.content.JCRTemplate;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author bdjiba
 *
 */
public class JahiaRestHooksManager implements BundleActivator, JahiaRestHooksContants {
  private static final Logger logger = LoggerFactory.getLogger(JahiaRestHooksManager.class);
  
  private static JCRNodeWrapper jahiaHooksNode = null;
  
  /* (non-Javadoc)
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    // TODO TEST
    logger.info("> > > Starting Jahia REST Hooks bundle.");
    JCRTemplate.getInstance().doExecuteWithSystemSession(new JCRCallback<Void>() {

      @Override
      public Void doInJCR(JCRSessionWrapper session) throws RepositoryException {
        addHooksSupport(session);
        JahiaHooksSubscriptionManager.getInstance().registerAsListener();
        return null;
      }});
  }

  /* (non-Javadoc)
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    // TODO TEST
    logger.info("< < < Stopping Jahia REST Hooks bundle.");
  }
  
  protected void addHooksSupport(JCRSessionWrapper session) throws RepositoryException{
    try {
      jahiaHooksNode = session.getNode(HOOKS_PARENT_NODE_PATH + "/" + HOOKS_PARENT_NODE_NAME);
    } catch (PathNotFoundException e) {
      if(session.nodeExists(HOOKS_PARENT_NODE_PATH)){
        jahiaHooksNode = session.getNode(HOOKS_PARENT_NODE_PATH).addNode(HOOKS_PARENT_NODE_NAME, HOOKS_PARENT_NODE_TYPE);
      }else {
        // FIXME: Then create hooks at the root
        jahiaHooksNode.getNode("/").addNode(HOOKS_PARENT_NODE_NAME, HOOKS_PARENT_NODE_TYPE);
      }
      session.save();
    }
  }
  
  /**
   * Get the system hooks node.
   * 
   * @return hooks node of null if not exists
   */
  public static JCRNodeWrapper getJahiaHooksNode() {
    return jahiaHooksNode;
  }
  
  /**
   * Check if the hooks is supported
   * @return true if the hooks node is not null. otherwise return false
   */
  public static boolean isHooksSupported(){
    return jahiaHooksNode != null;
  }
  
 /**
  * Get the JAHIA hooks path in the JCR (.../hooks)
  * @return the hooks path if the hooks node exists. otherwise return null
  */
  public static String getJahiaHooksPath(){
    if(isHooksSupported()) {
      return jahiaHooksNode.getPath();
    }
    return null;
  }

}
