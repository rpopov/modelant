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

import org.omg.uml13.wrap.base.foundation.core.BaseWrapComponent;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Component that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapComponent extends BaseWrapComponent {

  public WrapComponent(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapComponent(RefPackage extent) {
    super( extent );
  }
}