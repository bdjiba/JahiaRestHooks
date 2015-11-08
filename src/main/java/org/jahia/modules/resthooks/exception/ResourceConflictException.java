/**
 * 
 */
package org.jahia.modules.resthooks.exception;

import org.springframework.http.HttpStatus;

/**
 * @author bdjiba
 *
 */
public class ResourceConflictException extends JahiaRestHooksException {

  /**
   * 
   */
  private static final long serialVersionUID = 7996376216849268697L;

  public ResourceConflictException(String message, Exception ex) {
    super(message, ex, HttpStatus.CONFLICT);
  }
  
  /**
   * 
   */
  public ResourceConflictException(String message) {
    super(message, HttpStatus.CONFLICT);
  }

}
