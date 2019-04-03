/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 3, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.name;

import javax.jmi.model.ModelElement;

/**
 * Construct a Package Interface name, as of JMI 1.0 Specification, Section 4.8.1
 * @author Rusi Popov
 */
public class ConstructPackageName implements ConstructName {

  private final ConstructName decorated;

  /**
   * @param decorated not null mechanism to construct a name
   */
  public ConstructPackageName(ConstructName decorated) {
    this.decorated = decorated;
  }

  /**
   * @see net.mdatools.modelant.mof14.maven.generator.name.ConstructName#constructName(javax.jmi.model.ModelElement)
   */
  public String constructName(ModelElement element) {
    return decorated.constructName( element )+"Package";
  }

}
