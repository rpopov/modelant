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

import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapVisibilityKind;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.VisibilityKind that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapVisibilityKind extends BaseWrapVisibilityKind {

  public WrapVisibilityKind(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapVisibilityKind(String value, Factory factory) {
    super( value, factory );
  }

  /**
   * @return true if the visibility is PRIVATE
   */
  public final boolean isPrivate() {
    return VisibilityKindEnum.VK_PRIVATE.equals( getWrapped() );
  }

  /**
   * @return true if the visibility is PROTECTED
   */
  public final boolean isProtected() {
    return VisibilityKindEnum.VK_PROTECTED.equals( getWrapped() );
  }

  /**
   * @return true if the visibility is PUBLIC
   */
  public final boolean isPublic() {
    return VisibilityKindEnum.VK_PUBLIC.equals( getWrapped() );
  }

  /**
   * @return the java representation of this visibility value
   */
  public String format() {
    String result;

    if ( VisibilityKindEnum.VK_PUBLIC.equals( getWrapped() ) ) {
      result = "public";
    } else if ( VisibilityKindEnum.VK_PROTECTED.equals( getWrapped() ) ) {
      result = "protected";
    } else if ( VisibilityKindEnum.VK_PRIVATE.equals( getWrapped() ) ) {
      result = "private";
    } else { // package level
      result = "";
    }
    return result;
  }
}