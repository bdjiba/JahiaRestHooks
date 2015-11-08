/**
 * 
 */
package org.jahia.modules.resthooks.converter;

import java.io.IOException;

import org.jahia.modules.resthooks.api.SubscriptionStatus;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author bdjiba
 *
 */
public class SubscriptionStatusDeserializer extends JsonDeserializer<SubscriptionStatus> {

  @Override
  public SubscriptionStatus deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    SubscriptionStatus status = SubscriptionStatus.valueOf(jp.getValueAsString());
    return status;
  }

}
