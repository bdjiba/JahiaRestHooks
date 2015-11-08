/**
 * 
 */
package org.jahia.modules.resthooks.exception;

import org.springframework.http.HttpStatus;

/**
 * @author bdjiba
 *
 */
public class InvalidResourceException extends JahiaRestHooksException {

  private static final long serialVersionUID = -8706200006113089586L;

  /**
   * @param message
   * @param errorReason
   * @param stsCode
   */
  public InvalidResourceException(String message, Throwable errorReason, HttpStatus stsCode) {
    super(message, errorReason, stsCode);
  }

  /**
   * @param message
   * @param stsCode
   */
  public InvalidResourceException(String message, HttpStatus stsCode) {
    super(message, stsCode);
  }

}
