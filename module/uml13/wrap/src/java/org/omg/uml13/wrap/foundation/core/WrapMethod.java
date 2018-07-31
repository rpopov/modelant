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

import java.util.Iterator;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.core.Parameter;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapMethod;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Method that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapMethod extends BaseWrapMethod {

  public WrapMethod(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapMethod(RefPackage extent) {
    super( extent );
  }

  /**
   * This method removes from the mdoel the model method provided. The method is removed together with its
   * marameters, tagged values and dependencies.
   */
  public void remove() {
    Iterator parameterIterator;
    Parameter parameter;

    // remove parameters
    parameterIterator = getParameter().iterator();
    while ( parameterIterator.hasNext() ) {
      parameter = (Parameter) parameterIterator.next();
      parameterIterator.remove();

      ((WrapParameter) Factories.wrap( parameter )).remove();
    }
    // remove dependencies from/to this method
    removeDepenencies();
    removeTaggedValues();
    refDelete();
  }
}