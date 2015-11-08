/**
 * 
 */
package org.jahia.modules.resthooks.config;

import org.jahia.services.content.JCRTemplate;
import org.jahia.services.usermanager.JahiaUserManagerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bdjiba
 *
 */
@Configuration
public class SpringBeanConfig {
  @Bean
  public static JCRTemplate jcrTemplate() {
    JCRTemplate jcrTemplate = JCRTemplate.getInstance();
    // FIXME: set the session factory ??
    return jcrTemplate;
  }
  
  @Bean
  public static JahiaUserManagerService userManagerService() {
    JahiaUserManagerService userManagerService = JahiaUserManagerService.getInstance();
    return userManagerService;
  }
}
