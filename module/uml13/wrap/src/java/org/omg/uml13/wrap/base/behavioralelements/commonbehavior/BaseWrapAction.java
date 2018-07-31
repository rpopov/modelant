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
package org.omg.uml13.wrap.base.behavioralelements.commonbehavior;

import org.omg.uml13.behavioralelements.commonbehavior.Action;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.Action that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapAction extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.commonbehavior.Action {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapAction(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Action getWrapped() {
    return (Action) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Action getDelegate() {
    return (Action) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#isAsynchronous()
   */
  public boolean isAsynchronous() {
    return getDelegate().isAsynchronous();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#getStimulus()
   */
  public java.util.Collection getStimulus() {
    return getDelegate().getStimulus();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#getActualArgument()
   */
  public java.util.List getActualArgument() {
    return getDelegate().getActualArgument();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#getActionSequence()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.ActionSequence getActionSequence() {
    return getDelegate().getActionSequence();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#getScript()
   */
  public org.omg.uml13.foundation.datatypes.ActionExpression getScript() {
    return getDelegate().getScript();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#getRecurrence()
   */
  public org.omg.uml13.foundation.datatypes.IterationExpression getRecurrence() {
    return getDelegate().getRecurrence();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#getTarget()
   */
  public org.omg.uml13.foundation.datatypes.ObjectSetExpression getTarget() {
    return getDelegate().getTarget();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#setActionSequence(org.omg.uml13.behavioralelements.commonbehavior.ActionSequence)
   */
  public void setActionSequence(org.omg.uml13.behavioralelements.commonbehavior.ActionSequence arg0) {
    getDelegate().setActionSequence(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#setAsynchronous(boolean)
   */
  public void setAsynchronous(boolean arg0) {
    getDelegate().setAsynchronous(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#setRecurrence(org.omg.uml13.foundation.datatypes.IterationExpression)
   */
  public void setRecurrence(org.omg.uml13.foundation.datatypes.IterationExpression arg0) {
    getDelegate().setRecurrence(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#setScript(org.omg.uml13.foundation.datatypes.ActionExpression)
   */
  public void setScript(org.omg.uml13.foundation.datatypes.ActionExpression arg0) {
    getDelegate().setScript(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Action#setTarget(org.omg.uml13.foundation.datatypes.ObjectSetExpression)
   */
  public void setTarget(org.omg.uml13.foundation.datatypes.ObjectSetExpression arg0) {
    getDelegate().setTarget(arg0);
  }
}