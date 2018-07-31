/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package org.omg.uml13.wrap.foundation.datatypes;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.datatypes.ParameterDirectionKind;
import org.omg.uml13.foundation.datatypes.ParameterDirectionKindEnum;
import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapParameterDirectionKind;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.ParameterDirectionKind that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapParameterDirectionKind extends BaseWrapParameterDirectionKind {

  public WrapParameterDirectionKind(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapParameterDirectionKind(String value, Factory factory) {
    super( value, factory );
  }

  /**
   * @return true if parameter's direction is IN
   */
  public final boolean isIn() {
    return ParameterDirectionKindEnum.PDK_IN.equals( getWrapped() );
  }

  /**
   * @return true if parameter's direction is INOUT
   */
  public final boolean isInOut() {
    return ParameterDirectionKindEnum.PDK_INOUT.equals( getWrapped() );
  }

  /**
   * @return true if parameter's direction is OUT
   */
  public final boolean isOut() {
    return ParameterDirectionKindEnum.PDK_OUT.equals( getWrapped() );
  }

  /**
   * @return true if parameter's direction is RETURN
   */
  public final boolean isReturn() {
    return ParameterDirectionKindEnum.PDK_RETURN.equals( getWrapped() );
  }

  /**
   * This method formats the parameter direction indication
   *
   * @param value
   * @return "PDK_IN", "PDK_INOUT", "PDK_OUT", PDK_RETURN"
   */
  public String formatParameterDirection(ParameterDirectionKind value) {
    String result;

    if ( isIn() ) {
      result = "PDK_IN";
    } else if ( isInOut() ) {
      result = "PDK_INOUT";
    } else if ( isOut() ) {
      result = "PDK_OUT";
    } else if ( isReturn() ) {
      result = "PDK_RETURN";
    } else {
      result = "";
    }
    return result;
  }
}