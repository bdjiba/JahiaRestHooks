/**
 * 
 */
package org.jahia.modules.resthooks.mvc.view;

/**
 * @author bdjiba
 *
 */
public interface PayloadDataView extends BaseView {
  Object getData();
  
  Class<? extends BaseView> getView();
}
