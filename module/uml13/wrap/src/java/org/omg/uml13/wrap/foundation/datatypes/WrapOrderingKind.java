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

import org.omg.uml13.foundation.datatypes.OrderingKindEnum;
import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapOrderingKind;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.OrderingKind that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapOrderingKind extends BaseWrapOrderingKind {

  public WrapOrderingKind(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapOrderingKind(String value, Factory factory) {
    super( value, factory );
  }

  /**
   * @return true if and only if the ordering specification is ORDERED
   */
  public final boolean isOrdered() {
    return OrderingKindEnum.OK_ORDERED.equals( getWrapped() );
  }

  /**
   * @return true if and only if the ordering specification is SORTED
   */
  public final boolean isSorted() {
    return OrderingKindEnum.OK_SORTED.equals( getWrapped() );
  }

  /**
   * @return true if and only if the ordering specification is UNORDERED
   */
  public final boolean isUnordered() {
    return OrderingKindEnum.OK_UNORDERED.equals( getWrapped() );
  }

  /**
   * This method formats the ordering indication for associations, etc.
   * @return ORDERED, SORTED, UNORDERED
   */
  public String format() {
    String result;

    if ( OrderingKindEnum.OK_ORDERED.equals( getWrapped() ) ) {
      result = "ORDERED";
    } else if ( OrderingKindEnum.OK_SORTED.equals( getWrapped() ) ) {
      result = "SORTED";
    } else if ( OrderingKindEnum.OK_UNORDERED.equals( getWrapped() ) ) {
      result = "UNORDERED";
    } else { // any other
      result = "";
    }
    return result;
  }
}