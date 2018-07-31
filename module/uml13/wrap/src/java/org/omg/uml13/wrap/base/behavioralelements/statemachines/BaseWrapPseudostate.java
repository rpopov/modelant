/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.statemachines;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.statemachines.Pseudostate;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.statemachines.Pseudostate that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapPseudostate extends org.omg.uml13.wrap.behavioralelements.statemachines.WrapStateVertex implements org.omg.uml13.behavioralelements.statemachines.Pseudostate {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapPseudostate(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapPseudostate(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.statemachines.Pseudostate (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getStateMachines().getPseudostate();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Pseudostate getWrapped() {
    return (Pseudostate) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Pseudostate getDelegate() {
    return (Pseudostate) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Pseudostate#getKind()
   */
  public org.omg.uml13.foundation.datatypes.PseudostateKind getKind() {
    return getDelegate().getKind();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Pseudostate#setKind(org.omg.uml13.foundation.datatypes.PseudostateKind)
   */
  public void setKind(org.omg.uml13.foundation.datatypes.PseudostateKind arg0) {
    getDelegate().setKind(arg0);
  }
}