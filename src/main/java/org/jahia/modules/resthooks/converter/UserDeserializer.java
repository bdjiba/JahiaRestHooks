/**
 * 
 */
package org.jahia.modules.resthooks.converter;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.jahia.modules.resthooks.api.User;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author bdjiba
 *
 */
public class UserDeserializer extends JsonDeserializer<User> {

  @Override
  public User deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    User user = new User();
    
    /*while(jp.nextToken() != null){
      if(jp.get)
    }*/
    JsonNode userNode = jp.readValueAsTree();
    if(userNode.isObject()) {      
      if(userNode.get("username") != null){
        user.setUsername(StringUtils.defaultIfBlank(userNode.get("username").textValue(), null));
      }
      if(userNode.get("emailAddress") != null){
        user.setUsername(StringUtils.defaultIfBlank(userNode.get("emailAddress").textValue(), null));
      }
      if(userNode.get("firstname") != null){
        user.setUsername(StringUtils.defaultIfBlank(userNode.get("firstname").textValue(), null));
      }
      if(userNode.get("lastname") != null){
        user.setUsername(StringUtils.defaultIfBlank(userNode.get("lastname").textValue(), null));
      }
      
    }
    
    return user;
  }

}
