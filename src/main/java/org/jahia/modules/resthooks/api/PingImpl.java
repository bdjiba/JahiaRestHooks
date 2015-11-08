/**
 * 
 */
package org.jahia.modules.resthooks.api;

import org.joda.time.DateTime;

/**
 * @author bdjiba
 *
 */
public class PingImpl implements Ping {
  private String message;
  
  public PingImpl() {
    this(null);
  }
  
  public PingImpl(String message) {
    this.message = message;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.Ping#getMessage()
   */
  @Override
  public String getMessage() {
    // TODO Auto-generated method stub
    return message;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.Ping#setMessage(java.lang.String)
   */
  @Override
  public void setMessage(String v) {
    this.message = v;
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.Ping#getDateTime()
   */
  @Override
  public DateTime getDateTime() {
    return new DateTime();
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.api.Ping#setDateTime(org.joda.time.DateTime)
   */
  @Override
  public void setDateTime(DateTime v) {
    // EMPTY: nothing to do
  }

}
