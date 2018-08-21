/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.datatypes;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.datatypes.ChangeableKindEnum;
import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapChangeableKind;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.ChangeableKind that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapChangeableKind extends BaseWrapChangeableKind {

  public WrapChangeableKind(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapChangeableKind(String value, Factory factory) {
    super( value, factory );
  }

  /**
   * Formatting how an AttributeLink or LinkEnd may be modified.
   * @return no analog in Java
   */
  public String format() {
    String result;

    if ( ChangeableKindEnum.CK_ADD_ONLY.equals( getWrapped() ) ) {
      result = "addOnly";
    } else if ( ChangeableKindEnum.CK_CHANGEABLE.equals( getWrapped() ) ) {
      result = "changeable";
    } else if ( ChangeableKindEnum.CK_FROZEN.equals( getWrapped() ) ) {
      result = "frozen";
    } else {
      result = "";
    }
    return result;
  }
}