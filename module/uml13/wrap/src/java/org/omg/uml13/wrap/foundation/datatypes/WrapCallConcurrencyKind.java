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

import org.omg.uml13.foundation.datatypes.CallConcurrencyKindEnum;
import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapCallConcurrencyKind;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.CallConcurrencyKind that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapCallConcurrencyKind extends BaseWrapCallConcurrencyKind {

  public WrapCallConcurrencyKind(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapCallConcurrencyKind(String value, Factory factory) {
    super( value, factory );
  }

  /**
   * @return true if and only if the concurrency kind is CONCURRENT
   */
  public final boolean isConcurrent() {
    return CallConcurrencyKindEnum.CCK_CONCURRENT.equals( getWrapped() );
  }

  /**
   * @return true if and only if the concurrency kind is GUARDED
   */
  public final boolean isGuarded() {
    return CallConcurrencyKindEnum.CCK_GUARDED.equals( getWrapped() );
  }

  /**
   * @return true if and only if the concurrency kind is synchronized
   */
  public final boolean isSequential() {
    return CallConcurrencyKindEnum.CCK_SEQUENTIAL.equals( getWrapped() );
  }

  /**
   * Formatting method/operation call concurrency
   * @return "synchronized" if indicated, "" otherwise
   */
  public String format() {
    String result;

    if ( CallConcurrencyKindEnum.CCK_CONCURRENT.equals( getWrapped() ) ) {
      result = "";
    } else if ( CallConcurrencyKindEnum.CCK_GUARDED.equals( getWrapped() ) ) {
      result = ""; // no analog in Java
    } else if ( CallConcurrencyKindEnum.CCK_SEQUENTIAL.equals( getWrapped() ) ) {
      result = "synchronized";
    } else {
      result = "";
    }
    return result;
  }
}