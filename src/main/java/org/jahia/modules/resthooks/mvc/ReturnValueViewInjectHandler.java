/**
 * 
 */
package org.jahia.modules.resthooks.mvc;

import java.util.Arrays;

import org.jahia.modules.resthooks.mvc.view.BaseView;
import org.jahia.modules.resthooks.mvc.view.DataView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.MethodParameter;
import org.springframework.expression.BeanResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author bdjiba
 *
 */
public class ReturnValueViewInjectHandler implements HandlerMethodReturnValueHandler , ApplicationContextAware {
  private static final Logger log = LoggerFactory.getLogger(ReturnValueViewInjectHandler.class);

  private final HandlerMethodReturnValueHandler delegate;

  /**
   * 
   */
  public ReturnValueViewInjectHandler(HandlerMethodReturnValueHandler retValueHandler) {
    this.delegate = retValueHandler;
    log.info("jahiahooksSubcription resolver init with "
        + retValueHandler.getClass().getCanonicalName());

  }

  @Override
  public boolean supportsReturnType(MethodParameter returnType) {
    log.info("$$ return param " + returnType.getParameterName());
    // boolean supportedType =
    // JahiaHooksSubscription.class.isAssignableFrom(returnType.getParameterType());

    return this.delegate.supportsReturnType(returnType);
  }

  @Override
  public void handleReturnValue(Object returnValue, MethodParameter returnType,
      ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
    log.info("HH - handle return for " + returnValue + " type " + returnType);
    Class<? extends BaseView> viewClass = getDeclaredViewClass(mavContainer, returnType);
    if (viewClass != null)
    {
        returnValue = wrapResult(returnValue,viewClass);    
    }

    delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);

    delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
  }

  private Object wrapResult(Object returnValue, Class<? extends BaseView> viewClass) {
    
    if(returnValue instanceof ResponseEntity) {
      return new DataView(((ResponseEntity<Object>)returnValue).getBody(), viewClass);
    }
    return new DataView(returnValue, viewClass);
  }

  /**
   * Returns the view class declared on the method, if it exists. Otherwise, returns null.
   * 
   * @param returnType the returned type
   * @return the view or null
   */
  private Class<? extends BaseView> getDeclaredViewClass(ModelAndViewContainer model,
      MethodParameter returnType) {
    log.info(" DDDD - declared view: " + returnType.getParameterName());
    if (returnType.getMethodAnnotation(ResponseView.class) == null) {
      return null;
    }
    Class<? extends BaseView> view = null;
    ResponseView annotation = returnType.getMethodAnnotation(ResponseView.class);
    // normally we need payload but cover other views
    if (annotation.value().equals(BaseView.class)) {
      String viewName = ((ModelAndView) model.getModel().get("modelAndView")).getViewName();
      int index = 0;
      if (viewName != null) {
        if (annotation.names().length != annotation.views().length) {
          log.warn("Annotation ResponseView on method {} has different number of names and views",
              returnType.getMethod().getName());
        }
        index = Arrays.binarySearch(annotation.names(), viewName);
      } else {
        log.debug("No view name on model, using default view {}", annotation.views()[0]);
      }
      if (0 <= index && index < annotation.views().length) {
        view = annotation.views()[index];
        viewName = annotation.names()[index];
      } else {
        log.error("The view name: {}; has no corresponding view.", viewName);
      }
      log.debug("Using view: {}; with name: {};", annotation.views()[index], viewName);

    } else {
      view = annotation.value();
      log.debug("Using default view {}. Ignoring model.", annotation.value());
    }
    return view;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    BeanResolver resolver=new BeanFactoryResolver(applicationContext);
    
  }

}
