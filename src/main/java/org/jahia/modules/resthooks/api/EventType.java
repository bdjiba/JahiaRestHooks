/**
 * 
 */
package org.jahia.modules.resthooks.api;

/**
 * Default supported event types
 * @author bdjiba
 *
 */
public enum EventType {
  // for user
  USER_CREATED,
  USER_UPDATED,
  USER_DELETED,
  // TODO ....
  // File
  FILE_CREATED,
  FILE_UPDATED,
  FILE_REMOVED,
  // TODO ...
  // Publication
  PAGE_PUBLISHED,
  PAGE_UNPUBLISHED;

}
