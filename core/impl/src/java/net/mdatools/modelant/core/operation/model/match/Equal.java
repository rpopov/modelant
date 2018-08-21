/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.match;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Selector;
import net.mdatools.modelant.core.api.match.ConsideredEqual;
import net.mdatools.modelant.core.selector.SelectByQualifiedName;

/**
 * A pair of correspondent classes in the metamodels to compare, that should be considered equal
 * Used as &lt;metaclass&gt; element in the plugin's configuration.
 * @author Rusi Popov
 */
public class Equal implements ConsideredEqual {

  /**
   * Qualified name of the source class in the metamodel
   * @parameter
   * @required
   */
  private String source;

  /**
   * Qualified name of the target class in the metamodel
   * @parameter
   * @required
   */
  private String target;

  /**
   * Instantiate the Equal class initialized. Useful for internal purposes, like testing.
   * @param source
   * @param target
   */
  public Equal(String source, String target) {
    this.source = source;
    this.target = target;
  }


  /**
   * Used by MAVEN - it will take care to initialize the fields before instance's use.
   */
  public Equal() {
  }


  /**
   * @see net.mdatools.modelant.core.api.match.ConsideredEqual#selectNew()
   */
  public Selector<RefPackage, RefObject> selectNew() {
    return new SelectByQualifiedName(target);
  }


  /**
   * @see net.mdatools.modelant.core.api.match.ConsideredEqual#selectOld()
   */
  public Selector<RefPackage, RefObject> selectOld() {
    return new SelectByQualifiedName(source);
  }
}
