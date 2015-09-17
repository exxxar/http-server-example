/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package annotatons;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Aleks
 */
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Command {
        public String name() default "";
  }
