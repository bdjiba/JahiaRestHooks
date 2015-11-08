/**
 * 
 */
package org.jahia.modules.resthooks.front;

import org.jahia.modules.resthooks.api.Ping;
import org.jahia.modules.resthooks.provider.PingSpi;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author bdjiba
 *
 */
@Component
public class PingController implements PingSpi {

  /**
   * 
   */
  public PingController() {
    // TODO Auto-generated constructor stub
  }

  /* (non-Javadoc)
   * @see org.jahia.modules.resthooks.provider.PingSpi#getPing()
   */
  @Override
  public ResponseEntity<String> getPing() {
    Ping pingResponse = new Ping() {
      String message = HttpStatus.OK.name();
      DateTime dateTime = new DateTime();
      @Override
      public void setMessage(String v) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void setDateTime(DateTime v) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public String getMessage() {
        return message;
      }
      
      @Override
      public DateTime getDateTime() {
        if(dateTime == null){
          dateTime = new DateTime();
        }
        return dateTime;
      }
    };
    
    JSONArray jsons = new JSONArray();
    try {
      JSONObject json = new JSONObject();
      json.put("message", pingResponse.getMessage());
      json.put("dateTime", pingResponse.getDateTime());
      jsons.put(json);
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return new ResponseEntity<String>(jsons.toString(), HttpStatus.OK);
  }

}
