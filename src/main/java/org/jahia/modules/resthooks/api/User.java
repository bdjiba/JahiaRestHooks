/**
 * 
 */
package org.jahia.modules.resthooks.api;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * A simple reprentation of an authenticated user.<br />
 * @author bdjiba
 *
 */
public class User implements Serializable {
  
  private static final long serialVersionUID = 3114670841850718257L;
  
  private String username;
  private String emailAddress;
  private String firstname;
  private String lastname;
  
  public User(){
    /* EMPTY */
  }
  
  public User(String usrnam, String emailAddr, String firstName, String lastName) {
    this.username = usrnam;
    this.emailAddress = emailAddr;
    this.firstname = firstName;
    this.lastname = lastName;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @param emailAddress the emailAddress to set
   */
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  /**
   * @return the firstname
   */
  public String getFirstname() {
    return firstname;
  }

  /**
   * @param firstname the firstname to set
   */
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
   * @return the lastname
   */
  public String getLastname() {
    return lastname;
  }

  /**
   * @param lastname the lastname to set
   */
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
  
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
  
}
