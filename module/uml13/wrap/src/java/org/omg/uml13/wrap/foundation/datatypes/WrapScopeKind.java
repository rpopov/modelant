/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.datatypes;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.datatypes.ScopeKindEnum;
import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapScopeKind;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.ScopeKind that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapScopeKind extends BaseWrapScopeKind {

  public WrapScopeKind(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapScopeKind(String value, Factory factory) {
    super( value, factory );
  }

  /**
   * @return true if the scope is CLASSIFIER (field/method)
   */
  public final boolean isClassifier() {
    return ScopeKindEnum.SK_CLASSIFIER.equals( getWrapped() );
  }

  /**
   * @return true if the scope is INSTANCE (instance field/method)
   */
  public final boolean isInstance() {
    return ScopeKindEnum.SK_INSTANCE.equals( getWrapped() );
  }

  /**
   * @return the java formatting/representation of this type
   */
  public String format() {
    String result;

    if ( isClassifier() ) {
      result = "static";
    } else {
      result = "";
    }
    return result;
  }
}