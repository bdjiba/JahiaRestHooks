/**
 * 
 */
package org.jahia.modules.resthooks.exception;

import org.springframework.http.HttpStatus;

/**
 * @author bdjiba
 *
 */
public class IncompatibleTypeException extends JahiaRestHooksException {

  /**
   * 
   */
  private static final long serialVersionUID = 2518932181856647198L;

  /**
   * @param message
   * @param errorReason
   * @param stsCode
   */
  public IncompatibleTypeException(String message, Throwable errorReason, HttpStatus stsCode) {
    super(message, errorReason, stsCode);
  }

  /**
   * @param message
   * @param stsCode
   */
  public IncompatibleTypeException(String message, HttpStatus stsCode) {
    super(message, stsCode);
  }

}
