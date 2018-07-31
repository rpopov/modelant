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
package org.omg.uml13.wrap.base.behavioralelements.collaborations;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.collaborations.Interaction;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.collaborations.Interaction that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapInteraction extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.collaborations.Interaction {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapInteraction(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapInteraction(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.collaborations.Interaction (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCollaborations().getInteraction();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Interaction getWrapped() {
    return (Interaction) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Interaction getDelegate() {
    return (Interaction) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Interaction#getMessage()
   */
  public java.util.Collection getMessage() {
    return getDelegate().getMessage();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Interaction#getContext()
   */
  public org.omg.uml13.behavioralelements.collaborations.Collaboration getContext() {
    return getDelegate().getContext();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Interaction#setContext(org.omg.uml13.behavioralelements.collaborations.Collaboration)
   */
  public void setContext(org.omg.uml13.behavioralelements.collaborations.Collaboration arg0) {
    getDelegate().setContext(arg0);
  }
}