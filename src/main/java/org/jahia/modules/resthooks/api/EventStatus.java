/**
 * 
 */
package org.jahia.modules.resthooks.api;

/**
 * @author bdjiba
 *
 */
public enum EventStatus {
  PENDING, 
  RETRY, 
  ACKNOWLEDGED, 
  LOST;
  
  /*public static EventStatus fromStringToEventStatus(String v){
    for(EventStatus es : EventStatus.values())
  }*/
}
