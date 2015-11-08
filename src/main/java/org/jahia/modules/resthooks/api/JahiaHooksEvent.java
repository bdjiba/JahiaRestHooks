/**
 * 
 */
package org.jahia.modules.resthooks.api;

import org.joda.time.DateTime;

/**
 * @author bdjiba
 *
 */
public interface JahiaHooksEvent {
  /** 
   * Returns the event identifier
   * @return
   */
  String getId();
  
  /**
   * Sets the event identifier
   * @param id the id to set
   */
  void setId(String id);
  
  /**
   * Gets the event type
   * @return
   */
  String getType();
  
  /**
   * Sets the event type
   * @param type
   */
  void setType(String type);
  
  /**
   * Gets the event creation date
   * @return the creation date time
   */
  DateTime getCreatedDate();
  
  /**
   * Sets the event creation date time
   * @param v the creation date time
   */
  void setCreatedDate(DateTime v);
  
  /**
   * Gets the event status.
   * @return the event status
   */
  EventStatus getStatus();
  
  /**
   * Sets the event status
   * @param status the event status.
   */
  void setStatus(EventStatus status);
}
