/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.datatypes;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.datatypes.ChangeableKindEnum;
import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapProcedureExpression;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.ProcedureExpression that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapProcedureExpression extends BaseWrapProcedureExpression {

  public WrapProcedureExpression(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapProcedureExpression(RefPackage extent) {
    super( extent );
  }

  /**
   * @return true if and only if the changeability is READ_ONLY
   */
  public final boolean isAddOnly() {
    return ChangeableKindEnum.CK_ADD_ONLY.equals( getWrapped() );
  }

  /**
   * @return true if and only if the changeability is CHANGEABLE
   */
  public final boolean isChangeable() {
    return ChangeableKindEnum.CK_CHANGEABLE.equals( getWrapped() );
  }

  /**
   * @return true if and only if the changeability is FROZEN (constant)
   */
  public final boolean isFrozen() {
    return ChangeableKindEnum.CK_FROZEN.equals( getWrapped() );
  }
}