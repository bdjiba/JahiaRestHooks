/**
 * 
 */
package org.jahia.modules.resthooks.exception;

import org.springframework.http.HttpStatus;

/**
 * @author bdjiba
 *
 */
public class InternalServerException extends JahiaRestHooksException {

  /**
   * 
   */
  private static final long serialVersionUID = -3438641753040579264L;

  /**
   * @param message
   * @param errorReason
   * @param stsCode
   */
  public InternalServerException(String message, Throwable errorReason, HttpStatus stsCode) {
    super(message, errorReason, stsCode);
  }

  /**
   * @param message
   * @param stsCode
   */
  public InternalServerException(String message, HttpStatus stsCode) {
    super(message, stsCode);
  }

}
