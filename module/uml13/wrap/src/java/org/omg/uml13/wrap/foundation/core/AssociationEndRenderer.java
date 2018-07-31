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
package org.omg.uml13.wrap.foundation.core;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.mdatools.modelant.core.wrap.Wrapper;
import net.mdatools.modelant.template.Render;

/**
 * This class invokes the renderXtoY&lt;suffix&gt;() method for each association end,
 * where the suffix is a parameter of the class, this way serving all renderings with
 * a single class.
 * @author Rusi Popov
 */
public class AssociationEndRenderer implements Render {

  private final String suffix;

  /**
   * @param suffix
   */
  public AssociationEndRenderer(String suffix) {
    super();
    this.suffix = suffix;
  }

  /**
   * @see net.mdatools.modelant.template.Render#render(Object, ServletRequest, ServletResponse)
   */
  public void render(Object instance, ServletRequest request, ServletResponse response) {
    WrapAssociationEnd.renderAssociationEnd( (Wrapper) instance, suffix, request, response );
  }
}