/**
 * 
 */
package org.jahia.modules.resthooks.converter;

import java.io.IOException;
import java.lang.reflect.Type;

import org.jahia.modules.resthooks.api.JahiaHooksSubscription;
import org.jahia.modules.resthooks.api.JahiaHooksSubscriptionImpl;
import org.jahia.modules.resthooks.mvc.view.PayloadDataView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * @author bdjiba
 *
 */
public class ViewAwareJsonMessageConverter extends MappingJackson2HttpMessageConverter {

  private static final Logger log = LoggerFactory.getLogger(ViewAwareJsonMessageConverter.class);
  /**
   * 
   */
  public ViewAwareJsonMessageConverter() {
    super();
    ObjectMapper customMapper = new ObjectMapper();
    customMapper.setSerializationInclusion(Include.NON_NULL);
    customMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
    
    //customMapper.reader(baseSubType);
    customMapper.registerSubtypes(JahiaHooksSubscriptionImpl.class);
    setObjectMapper(customMapper);
  }

  /* (non-Javadoc)
   * @see org.springframework.http.converter.AbstractHttpMessageConverter#readInternal(java.lang.Class, org.springframework.http.HttpInputMessage)
   */
  @Override
  protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    log.info("RR - Read internal http message: " + inputMessage.getBody().toString());
    return super.readInternal(clazz, inputMessage);
  }
  
  @Override
  protected void writeInternal(Object object, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {
    log.info("WW - write internal: " + outputMessage.getBody().toString());
    if(object instanceof PayloadDataView) {
      writeView((PayloadDataView)object, outputMessage);
    }else{
      super.writeInternal(object, outputMessage);
    }
  }
  
  private void writeView(PayloadDataView view, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    log.info("WW - write view: " + outputMessage.getBody().toString());
    ObjectMapper mapper=new ObjectMapper();
    ObjectWriter writer=mapper.writerWithView(view.getView());
    writer.writeValue(outputMessage.getBody(), view.getData());
  }
  
  @Override
  protected boolean supports(Class<?> clazz) {
    log.info("checking support of class");
    return JahiaHooksSubscription.class.isAssignableFrom(clazz);
  }

  @Override
  protected JavaType getJavaType(Type type, Class<?> contextClass) {
    log.info("TT - get type " + type.getClass() + " for context " + contextClass.getCanonicalName());
    if (type instanceof Class && JahiaHooksSubscription.class.isAssignableFrom((Class)type)) {
      log.info(" ^^^ binding type");
      final JavaType baseInterface = TypeFactory.defaultInstance().constructType(JahiaHooksSubscription.class);
      final JavaType baseSubType = TypeFactory.defaultInstance().constructSpecializedType(baseInterface, JahiaHooksSubscriptionImpl.class);
      return baseSubType;
    } else {
      return super.getJavaType(type, contextClass);
    }
  }
}
