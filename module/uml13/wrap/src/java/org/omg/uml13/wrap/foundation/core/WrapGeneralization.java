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

import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapGeneralization;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Generalization that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapGeneralization extends BaseWrapGeneralization {

  public WrapGeneralization(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapGeneralization(RefPackage extent) {
    super( extent );
  }

  /**
   * This method instantiates a generalization relationship between the subclass and superclass. The
   * relationship is stored in the subclass namespace.
   *
   * @param subClass represents the subclass/subinterface in the generalization relation
   * @param superClass represents the superclass/spuerinterface
   */
  public WrapGeneralization(Classifier subClass, Classifier superClass) {
    this( superClass.refOutermostPackage() );

    setNamespace( superClass.getNamespace() );
    setParent( superClass );
    setChild( subClass );
  }
}