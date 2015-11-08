/**
 * 
 */
package org.jahia.modules.resthooks.front;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jcr.RepositoryException;
import javax.jcr.query.Query;

import org.apache.commons.lang.StringUtils;
import org.jahia.api.Constants;
import org.jahia.bin.Render;
import org.jahia.modules.resthooks.exception.InternalServerException;
import org.jahia.modules.resthooks.exception.JahiaRestHooksException;
import org.jahia.modules.resthooks.provider.DataSpi;
import org.jahia.services.content.JCRCallback;
import org.jahia.services.content.JCRNodeIteratorWrapper;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.content.JCRTemplate;
import org.jahia.services.content.QueryManagerWrapper;
import org.jahia.services.query.QueryResultWrapper;
import org.jahia.services.query.QueryWrapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.allen_sauer.gwt.log.client.Log;

/**
 * @author bdjiba
 *
 */
@Component
public class DataController implements DataSpi {
  private static final Logger logger = LoggerFactory.getLogger(DataController.class);

  // a quick dirty coding as we reach the end
  @Resource
  private JCRTemplate jcrTemplate;
  
  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.provider.DataSpi#getSubscription(java.lang.String)
   */
  @Override
  public ResponseEntity<String> findTask(final String dataId) throws JahiaRestHooksException {
    logger.info("Call to get data id: " + dataId);
    try {
      String result = jcrTemplate.doExecuteWithSystemSession(new JCRCallback<String>() {
        @Override
        public String doInJCR(JCRSessionWrapper session) throws RepositoryException {
          JCRNodeWrapper targetJCRNode = session.getNodeByIdentifier(dataId);
          
          JSONObject nodeJsonObject = new JSONObject();
          try {
            nodeJsonObject = Render.serializeNodeToJSON(targetJCRNode);
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          JSONArray requestPayload = new JSONArray();
          requestPayload.put(nodeJsonObject);
          return requestPayload.toString();
        }
        
      });
      // which status code ?
      return new ResponseEntity<String>(result, HttpStatus.FOUND);
    } catch (RepositoryException re) {
      logger.error("Error during subscription activation", re);
      throw new InternalServerException("Error during subscription activation: "  + re.getMessage(), re, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<String> findTasks(final String owner, final String limit)
      throws JahiaRestHooksException {
    try {
      String result = jcrTemplate.doExecuteWithSystemSession(new JCRCallback<String>() {

        @Override
        public String doInJCR(JCRSessionWrapper session) throws RepositoryException {
          //session.get
          QueryManagerWrapper qMgr = session.getWorkspace().getQueryManager();
          String statement = "select * from [" + Constants.JAHIANT_TASK +"] t where t.[jcr:createdBy] = '" + owner + "' order by t.[jcr:created] desc limit " + StringUtils.defaultIfBlank(limit, "5");
          QueryWrapper qw = qMgr.createQuery(statement , Query.JCR_SQL2);
          QueryResultWrapper qrw = qw.execute();
          JCRNodeIteratorWrapper nodeIt = qrw.getNodes();
          if(nodeIt.getSize() < 1) {
            JSONObject json = new JSONObject();
            return json.toString();
          }
          JSONArray results = new JSONArray();
          while (nodeIt.hasNext()) {
            JCRNodeWrapper node = (JCRNodeWrapper) nodeIt.next();
            try {
              results.put(Render.serializeNodeToJSON(node));
            } catch (IOException | JSONException e) {
              Log.error(e.getMessage(), e);
            } 
            
          }
          
          return results.toString();
        }
        
      });
      return new ResponseEntity<String>(result, HttpStatus.FOUND);
    } catch (RepositoryException e) {
      Log.error(e.getMessage(), e);
    }
    // to enhance for the response entity
    return null;
  }

  @Override
  public ResponseEntity<String> findLastTask(final String owner) throws JahiaRestHooksException {
    try {
      String result = jcrTemplate.doExecuteWithSystemSession(new JCRCallback<String>() {

        @Override
        public String doInJCR(JCRSessionWrapper session) throws RepositoryException {
          //
          QueryManagerWrapper qMgr = session.getWorkspace().getQueryManager();
          String statement = "select * from [" + Constants.JAHIANT_TASK +"] t where t.[jcr:createdBy] = '" + owner + "' order by t.[jcr:created] desc limit 1";
          QueryWrapper qw = qMgr.createQuery(statement , Query.JCR_SQL2);
          QueryResultWrapper qrw = qw.execute();
          JCRNodeIteratorWrapper nodeIt = qrw.getNodes();
          if(nodeIt.getSize() < 1) {
            JSONObject json = new JSONObject();
            return json.toString();
          }
          JSONArray results = new JSONArray();
          while (nodeIt.hasNext()) {
            JCRNodeWrapper node = (JCRNodeWrapper) nodeIt.next();
            try {
              results.put(Render.serializeNodeToJSON(node));
            } catch (IOException | JSONException e) {
              Log.error(e.getMessage(), e);
            } 
            
          }
          
          return results.toString();
        }
        
      });
      return new ResponseEntity<String>(result, HttpStatus.FOUND);
    } catch (RepositoryException e) {
      Log.error(e.getMessage(), e);
    }
    // to enhance for the response entity
    return null;
  }
  
  // other datas will come

}
