/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 6, 2019
 */
package net.mdatools.modelant.core.condition;

import javax.jmi.model.Attribute;
import javax.jmi.model.Feature;
import javax.jmi.model.ModelElement;

import net.mdatools.modelant.core.api.Condition;

/**
 * Is an attribute Derived
 * @author Rusi Popov
 */
public class IsDerivedAttribute implements Condition<ModelElement> {

  public boolean eval(ModelElement argument) throws RuntimeException, IllegalArgumentException {
    boolean result;

    result = (argument instanceof Attribute)
             && ((Attribute) argument).isDerived();
    return result;
  }
}
