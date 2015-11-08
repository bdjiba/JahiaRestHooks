/**
 * 
 */
package org.jahia.modules.resthooks.mvc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author bdjiba
 *
 */
public class JsonViewSupportFactoryBean implements InitializingBean {
  private static final Logger log = LoggerFactory.getLogger(JsonViewSupportFactoryBean.class);

  /**
   * 
   */
  public JsonViewSupportFactoryBean() {
    // TODO Auto-generated constructor stub
  }

  @Autowired
  private RequestMappingHandlerAdapter adapter;

  @Override
  public void afterPropertiesSet() throws Exception {
      List<HandlerMethodReturnValueHandler> handlers= adapter.getReturnValueHandlers().getHandlers();
      List<HandlerMethodReturnValueHandler> customHandlers=new ArrayList<HandlerMethodReturnValueHandler>();
      for(HandlerMethodReturnValueHandler handler : handlers) {
          if(handler instanceof HttpEntityMethodProcessor) {
            ReturnValueViewInjectHandler decorator=new ReturnValueViewInjectHandler(handler);
              customHandlers.add(decorator);
              log.debug("JsonView decorator support wired up");
              break;
          }
      }
      adapter.setCustomReturnValueHandlers(customHandlers);
  }

  private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
      List<HandlerMethodReturnValueHandler> customHandlers=new ArrayList<HandlerMethodReturnValueHandler>();
      for(HandlerMethodReturnValueHandler handler : handlers) {
          if(handler instanceof HttpEntityMethodProcessor) {
            ReturnValueViewInjectHandler decorator=new ReturnValueViewInjectHandler(handler);
              int index=handlers.indexOf(handler);
              handlers.set(index, decorator);

              log.debug("JsonView decorator support wired up");
              break;
          }
      }
  }


}
