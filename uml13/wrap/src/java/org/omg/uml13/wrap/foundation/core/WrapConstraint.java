/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.core;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.core.AssociationEnd;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapConstraint;
import org.omg.uml13.wrap.foundation.datatypes.WrapBooleanExpression;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Constraint that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapConstraint extends BaseWrapConstraint {

  public WrapConstraint(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapConstraint(RefPackage extent) {
    super( extent );
  }

  public WrapConstraint(AssociationEnd end, String constraint) {
    this( end.refOutermostPackage() );

    setVisibility(VisibilityKindEnum.VK_PUBLIC);
    setBody( new WrapBooleanExpression( end.refOutermostPackage(),
                                        "",
                                        constraint).getWrapped() );
    getConstrainedElement().add( end );
  }
}