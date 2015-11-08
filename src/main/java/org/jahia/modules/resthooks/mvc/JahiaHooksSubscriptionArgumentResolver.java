/**
 * 
 */
package org.jahia.modules.resthooks.mvc;

import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.jahia.modules.resthooks.api.JahiaHooksSubscription;
import org.jahia.modules.resthooks.api.JahiaHooksSubscriptionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author bdjiba
 *
 */
//@Component
public class JahiaHooksSubscriptionArgumentResolver implements HandlerMethodArgumentResolver {
  private static final Logger log = LoggerFactory.getLogger(JahiaHooksSubscriptionArgumentResolver.class);
  
  /**
   * 
   */
  public JahiaHooksSubscriptionArgumentResolver() {
  }
  
  //@PostConstruct
  public void hello(){
    log.info("&&&&& HELLO &&&");
  }

  /* (non-Javadoc)
   * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
   */
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    log.info("$$ support paramter " + parameter.getParameterName());
    return JahiaHooksSubscription.class.isAssignableFrom(parameter.getParameterType());
  }

  /* (non-Javadoc)
   * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
   */
  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    log.info("resolving argument " + parameter.getParameterName());
    JahiaHooksSubscription subscription = new JahiaHooksSubscriptionImpl();
    HttpServletRequest request = (HttpServletRequest)webRequest;
    
    InputStream body = request.getInputStream();
    log.info("message body : " + IOUtils.toString(body));;
    
    return subscription;
  }
  
  
}
