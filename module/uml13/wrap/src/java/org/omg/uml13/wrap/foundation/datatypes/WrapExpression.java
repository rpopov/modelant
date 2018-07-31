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

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapExpression;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.Expression that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapExpression extends BaseWrapExpression {

  public WrapExpression(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapExpression(RefPackage extent) {
    super( extent );
  }

  public WrapExpression(RefPackage extent, String body) {
    this( extent );
    setBody( body );
  }
}