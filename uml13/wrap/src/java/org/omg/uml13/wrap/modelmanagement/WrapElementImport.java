/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.modelmanagement;

import javax.jmi.reflect.RefPackage;

import org.omg.uml13.wrap.base.modelmanagement.BaseWrapElementImport;

import net.mdatools.modelant.core.api.wrap.WrapperFactory;

/**
 * This is a wrapper of org.omg.uml13.modelmanagement.ElementImport that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapElementImport extends BaseWrapElementImport {

  public WrapElementImport(java.lang.Object wrapped, WrapperFactory factory) {
    super( wrapped, factory );
  }

  public WrapElementImport(RefPackage extent) {
    super( extent );
  }
}