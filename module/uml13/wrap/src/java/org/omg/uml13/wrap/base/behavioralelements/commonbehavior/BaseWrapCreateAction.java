/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.commonbehavior;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.commonbehavior.CreateAction;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.CreateAction that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapCreateAction extends org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapAction implements org.omg.uml13.behavioralelements.commonbehavior.CreateAction {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapCreateAction(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapCreateAction(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.CreateAction (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getCreateAction();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public CreateAction getWrapped() {
    return (CreateAction) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected CreateAction getDelegate() {
    return (CreateAction) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.CreateAction#getInstantiation()
   */
  public org.omg.uml13.foundation.core.Classifier getInstantiation() {
    return getDelegate().getInstantiation();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.CreateAction#setInstantiation(org.omg.uml13.foundation.core.Classifier)
   */
  public void setInstantiation(org.omg.uml13.foundation.core.Classifier arg0) {
    getDelegate().setInstantiation(arg0);
  }
}