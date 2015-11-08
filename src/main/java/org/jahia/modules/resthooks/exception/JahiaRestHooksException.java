/**
 * 
 */
package org.jahia.modules.resthooks.exception;

import org.springframework.http.HttpStatus;

/**
 * @author bdjiba
 *
 */
public class JahiaRestHooksException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -7367680339645937708L;
  
  private final String message;
  private final Throwable reason;
  private final HttpStatus statusCode;
 
  
  JahiaRestHooksException(String message, Throwable errorReason, HttpStatus stsCode) {
    this.message = message;
    this.reason = errorReason;
    this.statusCode = stsCode;
  }
  
  JahiaRestHooksException(String message, HttpStatus stsCode) {
    this(message, null, stsCode);
  }
  
  public String getMessage() {
    return message;
  }
  
  public int getCode(){
    return statusCode.value();
  }
  
  public HttpStatus getStatus() {
    return statusCode;
  }
  
  public Throwable getReason() {
    return reason;
  }
  
  @Override
  public String toString() {
    return java.text.MessageFormat.format("RestException'{'status:{0},message:''{1}'',reason:{2}'}'", statusCode, message, reason) ;
  }

}
