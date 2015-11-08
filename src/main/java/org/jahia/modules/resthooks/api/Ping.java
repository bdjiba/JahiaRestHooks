/**
 * 
 */
package org.jahia.modules.resthooks.api;

import org.joda.time.DateTime;

/**
 * Representation of a Test message response
 * @author bdjiba
 *
 */
public interface Ping {
  /**
   * Get the response message
   * @return
   */
  String getMessage();
  
  /**
   * Set the response message
   * @param v
   */
  void setMessage(String v);
  
  /**
   * Get the date time
   * @return
   */
  DateTime getDateTime();
  
  /**
   * Sets the response message
   * @param v
   */
  void setDateTime(DateTime v);
  
}
