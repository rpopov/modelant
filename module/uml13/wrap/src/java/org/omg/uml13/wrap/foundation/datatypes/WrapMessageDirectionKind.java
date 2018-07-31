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

import org.omg.uml13.foundation.datatypes.MessageDirectionKindEnum;
import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapMessageDirectionKind;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.MessageDirectionKind that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapMessageDirectionKind extends BaseWrapMessageDirectionKind {

  public WrapMessageDirectionKind(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapMessageDirectionKind(String value, Factory factory) {
    super( value, factory );
  }

  /**
   * @return true if and only if the message direction is ACTIVATION (forward arrow)
   */
  public  boolean isActivation() {
    return MessageDirectionKindEnum.MDK_ACTIVATION.equals( getWrapped() );
  }

  /**
   * @return true if and only if the message direction is RETURN (backward arrow)
   */
  public  boolean isReturn() {
    return MessageDirectionKindEnum.MDK_RETURN.equals( getWrapped() );
  }
}