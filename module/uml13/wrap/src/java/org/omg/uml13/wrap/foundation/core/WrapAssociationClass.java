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
package org.omg.uml13.wrap.foundation.core;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.wrap.base.foundation.core.BaseWrapAssociationClass;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.AssociationClass that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapAssociationClass extends BaseWrapAssociationClass {

  public WrapAssociationClass(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapAssociationClass(RefPackage extent) {
    super( extent );
  }
}