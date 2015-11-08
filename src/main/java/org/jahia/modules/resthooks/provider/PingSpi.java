/**
 * 
 */
package org.jahia.modules.resthooks.provider;

import org.jahia.modules.resthooks.api.Ping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author bdjiba
 *
 */
@Controller
@RequestMapping(/*headers="content-type=application/json", */ produces="application/json",value ={"/ping","/hello", "/test"})
public interface PingSpi {
  //TODO: check if we can add reason that will used to build the response
  @RequestMapping(method=RequestMethod.GET)
  @ResponseBody
  ResponseEntity<String> getPing();
}
