/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.gwtproject.core.shared;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A simple of a GwtIncompatible annotation.
 *
 * <p>Any class, method or field with an annotation @GwtIncompatible (with any package prefix) is
 * ignored by the GWT compiler.
 *
 * <p>Since only the name of the annotation matters, Java libraries may use their own copy of this
 * annotation class to avoid adding a compile-time dependency on GWT.
 *
 * <p>For example:
 *
 * <p>{@code class A {
 *
 * <p><p><p><p><p><p><p><p><p><p><p>int field; @GwtIncompatible("incompatible class") class Inner {
 * .... } @GwtIncompatible("incompatible field") int field2 = methodThatisNotSupportedbyGwt();
 *
 * <p><p><p><p><p><p><p><p><p><p><p>void method1() { } @GwtIncompatible("incompatbile method") void
 * method2() {} } }
 *
 * <p>is seen by the Gwt compiler as
 *
 * <p>{@code class A {
 *
 * <p><p><p><p><p><p><p><p><p><p><p>int field;
 *
 * <p><p><p><p><p><p><p><p><p><p><p>void method1() { }
 *
 * <p><p><p><p><p><p><p><p><p><p><p>} }
 *
 * <p>Warning: this may have surprising effects when combined with method overloading or
 * inheritance.
 */
@Retention(RetentionPolicy.CLASS)
@Target({
  ElementType.TYPE, ElementType.METHOD,
  ElementType.CONSTRUCTOR, ElementType.FIELD
})
@Documented
public @interface GwtIncompatible {
  /**
   * An attribute that can be used to explain why the code is incompatible. A GwtIncompatible
   * annotation can have any number of attributes; attributes are for documentation purposes and are
   * ignored by the GWT compiler.
   */
  String value() default "";
}