/**
 * 
 */
package org.jahia.modules.resthooks.mvc.view;

/**
 * @author bdjiba
 *
 */
public class DataView implements PayloadDataView {

  private final Object data;
  private final Class<? extends BaseView> view;
  
  public DataView(Object d, Class<? extends BaseView> v) {
    this.data = d;
    this.view = v;
  }

  @Override
  public Object getData() {
    return this.data;
  }

  @Override
  public Class<? extends BaseView> getView() {
    return this.view;
  }

}
