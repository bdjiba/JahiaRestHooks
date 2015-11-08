/**
 * 
 */
package org.jahia.modules.resthooks.provider;

import org.apache.felix.framework.resolver.ResourceNotFoundException;
import org.jahia.modules.resthooks.api.JahiaHooksSubscription;
import org.jahia.modules.resthooks.mvc.JahiaSubscription;
import org.jahia.modules.resthooks.mvc.ResponseView;
import org.jahia.modules.resthooks.mvc.view.PayloadDataView;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author bdjiba
 *
 */
@Controller
@RequestMapping(headers="content-type=application/json", produces="application/json",value ={"/subscription","/resthookssub","/sub", "/hooks"})
public interface SubscriptionSpi {
  // create a subscription
  @RequestMapping(method=RequestMethod.POST, params = {"event","url","owner","max_retry"})
  @ResponseBody
  ResponseEntity<JahiaHooksSubscription> createSubscription(@RequestParam(value = "event", required = true)String[] topics, @RequestParam(value = "url", required = true)String callbackURL, @RequestParam(value = "owner", required= true) String username, @RequestParam("max_retry") String maxRetry);
  
  @RequestMapping(method=RequestMethod.POST, consumes="application/json")
  @ResponseBody
  //ResponseEntity<JahiaHooksSubscription> createSubscription(JahiaHooksSubscription subs);
  ResponseEntity<JahiaHooksSubscription> createSubscription(HttpEntity<String> request);
  
  // update a subscription
  @RequestMapping(method=RequestMethod.PUT, value="/{id}")
  @ResponseBody
  ResponseEntity<JahiaHooksSubscription> updateSubscription(@PathVariable("id") String subsId, JahiaHooksSubscription subs) throws ResourceNotFoundException;
  
  // delete a subscription
  @RequestMapping(method=RequestMethod.DELETE, value="/{id}")
  @ResponseBody
  ResponseEntity<JahiaHooksSubscription> deleteSubscription(@PathVariable("id") String subId);
  
  //Get a subscription
  @RequestMapping(method=RequestMethod.GET, value="/{id}")
  @ResponseBody
  ResponseEntity<JahiaHooksSubscription> getSubscription(@PathVariable("id") String subId);
  
  @RequestMapping(method={RequestMethod.PUT, RequestMethod.POST}, value="/activate/{id}")
  @ResponseBody
  ResponseEntity<JahiaHooksSubscription> activateSubscription(@PathVariable("id") String subId);
  
  @RequestMapping(method={RequestMethod.PUT, RequestMethod.POST}, value="/inactivate/{id}")
  @ResponseBody
  ResponseEntity<JahiaHooksSubscription> inactivateSubscription(@PathVariable("id") String subId);
  
  
}
