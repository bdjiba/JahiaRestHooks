/**
 * 
 */
package org.jahia.modules.resthooks.provider;

import org.jahia.modules.resthooks.exception.JahiaRestHooksException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author bdjiba
 *
 */
@Controller
@RequestMapping(headers="content-type=application/json", produces="application/json",value ={"/data","/content","/node"})
public interface DataSpi {
  /**
   * Find the task by it id
   * @param dataId the task id
   * @return the available task
   * @throws JahiaRestHooksException
   */
  @RequestMapping(method=RequestMethod.GET, value="/task", params = {"id"})
  @ResponseBody
  ResponseEntity<String> findTask(@RequestParam("id") String dataId) throws JahiaRestHooksException;
  
  /**
   * Find all the task of the given user (is it owner or assignee ??) at most the max given in parameter
   * @param owner the task owner (or may be assignee)
   * @param limit the maximum number of records to retrieve
   * @return the list of available tasks
   * @throws JahiaRestHooksException
   */
  @RequestMapping(method=RequestMethod.GET, value="/tasks", params = {"user","max"})
  @ResponseBody
  ResponseEntity<String> findTasks(@RequestParam("user") String owner, @RequestParam("max") String limit) throws JahiaRestHooksException;
  
  /**
   * Find the last task from the user's tasks
   * @param owner the task owner
   * @return the last task
   * @throws JahiaRestHooksException
   */
  @RequestMapping(method=RequestMethod.GET, value="/user/task/last.json", params = {"user"})
  @ResponseBody
  ResponseEntity<String> findLastTask(@PathVariable("user") String owner) throws JahiaRestHooksException;
}
