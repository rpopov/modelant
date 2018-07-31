/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 11.02.2018 г.
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
