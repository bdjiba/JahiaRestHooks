/**
 * 
 */
package org.jahia.modules.resthooks.api;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * @author bdjiba
 *
 */
public class JahiaHooksEventImpl implements JahiaHooksEvent {
  private String id;
  private String type;
  private DateTime createdDate;
  private EventStatus status;
  
  public JahiaHooksEventImpl(){
    /* EMPTY */
  }
  
  public JahiaHooksEventImpl(String id, String type, EventStatus sts){
      this(id, type, sts, null);
  }
  
  public JahiaHooksEventImpl(String id, String type, EventStatus sts, Date date){
    this.id = id;
    this.type = type;
    this.status = sts;
    this.createdDate = date == null ? new DateTime() : new DateTime(date);
  }
  

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksEvent#getId()
   */
  @Override
  public String getId() {
    return id;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksEvent#setId(java.lang.String)
   */
  @Override
  public void setId(String id) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksEvent#getType()
   */
  @Override
  public String getType() {
    return type;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksEvent#setType(java.lang.String)
   */
  @Override
  public void setType(String type) {
    this.type = type;

  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksEvent#getCreatedDate()
   */
  @Override
  public DateTime getCreatedDate() {
    return createdDate;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksEvent#setCreatedDate(org.joda.time.DateTime)
   */
  @Override
  public void setCreatedDate(DateTime v) {
    this.createdDate = v;

  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksEvent#getStatus()
   */
  @Override
  public EventStatus getStatus() {
    return status;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.JahiaHooksEvent#setStatus(org.jahia.modules.resthooks.api.EventStatus)
   */
  @Override
  public void setStatus(EventStatus status) {
    this.status = status;
  }

}
