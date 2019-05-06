/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 3, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.condition;

import javax.jmi.model.ModelElement;
import javax.jmi.model.MofPackage;
import javax.jmi.model.VisibilityKindEnum;

import net.mdatools.modelant.core.api.Condition;

/**
 * Choose only those packages that have no ignoreLifecycle tag set to "true"
 * @author Rusi Popov
 */
public class IsPublicPackage implements Condition<ModelElement> {

  public boolean eval(ModelElement element) throws RuntimeException, IllegalArgumentException {
    boolean result;
    MofPackage argument;

    result = element instanceof MofPackage;

    if ( result ) {
      argument = (MofPackage) element;
      result = VisibilityKindEnum.PUBLIC_VIS.equals( argument.getVisibility());
    }
    return result;
  }
}
