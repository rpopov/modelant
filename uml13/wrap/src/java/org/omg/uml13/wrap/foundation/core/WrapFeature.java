/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.core;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.wrap.base.foundation.core.BaseWrapFeature;
import org.omg.uml13.wrap.foundation.datatypes.WrapScopeKind;
import org.omg.uml13.wrap.foundation.datatypes.WrapVisibilityKind;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Feature that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapFeature extends BaseWrapFeature {

  public WrapFeature(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * @return true if the scope (instance-level/class-level) of
   *         this attribute/method as is classifier (static).
   *         <b>Then we assume that it is a constant</b>
   * NOTE: Requires ServlectConfig, Used in JSPs
   * HACK: We assume all static fields are constants.
   * NOTE: Rose does not export changeability="frozen" for final fields
   */
  public final boolean isConstant() {
    boolean result;
    WrapScopeKind wrapKind;

    wrapKind = (WrapScopeKind) wrap( getOwnerScope());
    result = wrapKind != null && wrapKind.isClassifier();
    return result;
  }

  /**
   * @return true if the visibility of this is public
   */
  public final boolean isPublic() {
    boolean result;
    WrapVisibilityKind WrapVisibility;

    WrapVisibility = (WrapVisibilityKind) wrap( getVisibility());
    result = WrapVisibility != null && WrapVisibility.isPublic();
    return result;
  }

  /**
   * @return formats the scope (instance-level/class-level) of
   *         this attribute/method as a Java name (/static)
   */
  public final String formatScope() {
    String result;
    WrapScopeKind wrapKind;

    wrapKind = (WrapScopeKind) wrap( getOwnerScope());
    if ( wrapKind != null ) {
      result = wrapKind.format();
    } else {
      result = "";
    }
    return result;
  }

  /**
   * @return formats the visibility (public, private, protected, package) of
   *         this attribute/method as a Java name
   */
  public final String formatVisibility() {
    String result;
    WrapVisibilityKind wrapVisibility;

    wrapVisibility = (WrapVisibilityKind) wrap( getVisibility());
    if ( wrapVisibility != null ) {
      result = wrapVisibility.format();
    } else {
      result = "private";
    }
    return result;
  }


  /**
   * @return formats the public visibility as "public", whereas any other visibility is
   *         reduced to "protected"
   */
  public final String formatSimpleVisibility() {
    String result;

    if ( isPublic() ) {
      result = "public";
    } else {
      result = "protected";
    }
    return result;
  }

  /**
   * Thie method removes from the model the feature provided by invoking the corresponding removal method
   */
  public void remove() {
  }
}