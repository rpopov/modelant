/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.behavioralelements.activitygraphs;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factory;
import org.omg.uml13.wrap.base.behavioralelements.activitygraphs.BaseWrapActionState;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.activitygraphs.ActionState that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapActionState extends BaseWrapActionState {

  public WrapActionState(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapActionState(RefPackage extent) {
    super( extent );
  }
}