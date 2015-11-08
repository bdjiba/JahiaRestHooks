/**
 * 
 */
package org.jahia.modules.resthooks.client;

import java.io.IOException;

import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.util.IndexedDocumentFactory;
import org.jahia.bin.Render;
import org.jahia.services.content.JCRNodeWrapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * This client acts as a client of the a subscription target callback URL. 
 * @author bdjiba
 *
 */
public class HookNotifier {
  private static final Logger log = LoggerFactory.getLogger(HookNotifier.class);
  
  //public HookNotifier(){}
  
  public static String notifySubscriber(String url, String topic, String dataId){
    log.info("$ NOTIF $: going to send request to " + url + ". The topic: " + topic + ", dataId: " + dataId);
    Message payloadMsg = new Message(topic, dataId);
    
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    try {
      String responseMsg = restTemplate.postForObject(url,payloadMsg , String.class);
      return responseMsg;
    }catch(HttpClientErrorException ex) {
      if(StringUtils.contains(ex.getMessage(), HttpStatus.GONE.getReasonPhrase()) || StringUtils.contains(ex.getMessage(), ""+ HttpStatus.GONE.value())){
        log.warn(ex.getMessage() + " when notifying subscriber.");
        return "401";
      }
      return null;
    }
  }
  
  public static String notifySubscriber(String url, String topic, JCRNodeWrapper targetNode){
    log.info("$ NOTIF $: going to send request to " + url + ". The topic: " + topic);
    JSONObject nodeJsonObject = null;
    try {
      nodeJsonObject = Render.serializeNodeToJSON(targetNode);
      log.info("the payload to send to the sub." + nodeJsonObject.toString());
    } catch (RepositoryException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if(nodeJsonObject == null) {
      return null;
    }
    JSONArray requestPayload = new JSONArray();
    requestPayload.put(nodeJsonObject);
    
    // Set the Content-Type header
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(new MediaType("application","json"));
    HttpEntity<String> requestEntity = new HttpEntity<String>(requestPayload.toString(), requestHeaders);

    try {
      log.info("Target node: " + requestPayload.toString(1));
    } catch (JSONException e) {
      log.error(e.getMessage(), e);
    }
    RestTemplate restTemplate = new RestTemplate();
    try {
      ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
      String responseMsg = responseEntity.getBody();
      log.info("Sending response message: " + responseMsg);
      return responseMsg;
    }catch(HttpClientErrorException ex) {
      if(StringUtils.contains(ex.getMessage(), HttpStatus.GONE.getReasonPhrase()) || StringUtils.contains(ex.getMessage(), ""+ HttpStatus.GONE.value())){
        log.warn(ex.getMessage() + " when notifying subscriber.");
        return "401";
      }
      return null;
    }
  }
  
}
