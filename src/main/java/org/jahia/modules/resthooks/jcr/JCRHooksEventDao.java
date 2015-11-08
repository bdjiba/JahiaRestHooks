/**
 * 
 */
package org.jahia.modules.resthooks.jcr;

import java.util.List;

import org.jahia.modules.resthooks.api.JahiaHooksEvent;
import org.jahia.modules.resthooks.exception.JahiaRestHooksException;

/**
 * @author bdjiba
 *
 */
public interface JCRHooksEventDao {
  JahiaHooksEvent find(String id) throws JahiaRestHooksException;
  List<JahiaHooksEvent> findAll() throws JahiaRestHooksException;
  JahiaHooksEvent save(JahiaHooksEvent evt) throws JahiaRestHooksException;
  void delete(JahiaHooksEvent evt) throws JahiaRestHooksException;
}
