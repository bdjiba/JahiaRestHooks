/**
 * 
 */
package org.jahia.modules.resthooks.mvc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jahia.modules.resthooks.mvc.view.BaseView;

/**
 * @author bdjiba
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseView {
  public Class<? extends BaseView> value();
  public Class<? extends BaseView>[] views() default {};
  public String[] names() default {};
}
