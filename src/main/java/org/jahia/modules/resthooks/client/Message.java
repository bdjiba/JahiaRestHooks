/**
 * 
 */
package org.jahia.modules.resthooks.client;

/**
 * @author bdjiba
 *
 */
public class Message {
  private String id;
  private String topic;
  
  public Message(){
    
  }
  
  public Message(String msgTopic, String dataId){
    this.id = dataId;
    this.topic = msgTopic;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the topic
   */
  public String getTopic() {
    return topic;
  }

  /**
   * @param topic the topic to set
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }
  
  
}
