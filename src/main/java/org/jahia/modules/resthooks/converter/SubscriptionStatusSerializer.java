/**
 * 
 */
package org.jahia.modules.resthooks.converter;

import java.io.IOException;

import org.jahia.modules.resthooks.api.SubscriptionStatus;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author bdjiba
 *
 */
public class SubscriptionStatusSerializer extends JsonSerializer<SubscriptionStatus> {

  @Override
  public void serialize(SubscriptionStatus value, JsonGenerator jgen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
    jgen.writeString(value.name().toLowerCase());
    
  }

}
