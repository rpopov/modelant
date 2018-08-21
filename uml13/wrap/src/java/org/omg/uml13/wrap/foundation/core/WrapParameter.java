/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.core;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.datatypes.Expression;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapParameter;
import org.omg.uml13.wrap.foundation.datatypes.WrapParameterDirectionKind;
/**
 * This is a wrapper of org.omg.uml13.foundation.core.Parameter that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapParameter extends BaseWrapParameter {

  public WrapParameter(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapParameter(RefPackage extent) {
    super( extent );
  }

  public WrapParameter(RefPackage extent, String name) {
    this( extent );
    setName( name );
  }

  /**
   * @return true if this parameter represents the result type of the method it
   *         pertains to
   */
  public boolean isReturn() {
    return ((WrapParameterDirectionKind) wrap( getKind() )).isReturn();
  }
  

  /**
   * This method removes the parameter together with its tagged values and expression registered
   */
  public void remove() {
    Expression initExpression = getDefaultValue();

    // remove the initialization expression
    if ( initExpression != null ) {
      initExpression.refDelete();
    }
    removeTaggedValues( );
    refDelete();
  }
}