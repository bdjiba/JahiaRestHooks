/**
 * 
 */
package org.jahia.modules.resthooks.converter;

import java.io.IOException;

import org.jahia.modules.resthooks.api.User;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author bdjiba
 *
 */
public class UserSerializer extends JsonSerializer<User> {

  @Override
  public void serialize(User value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
    jgen.writeStartObject();
    jgen.writeFieldName("username");
    jgen.writeString(value.getUsername());
    jgen.writeFieldName("emailAdress");
    jgen.writeString(value.getEmailAddress());
    jgen.writeFieldName("firstname");
    jgen.writeString(value.getFirstname());
    jgen.writeFieldName("lastname");
    jgen.writeString(value.getLastname());
    jgen.writeEndObject();
    
    
  }

}
