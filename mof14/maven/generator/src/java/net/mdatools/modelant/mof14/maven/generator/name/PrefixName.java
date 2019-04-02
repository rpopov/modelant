/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 2, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.name;

import javax.jmi.model.ModelElement;

/**
 * Add a non-null, not empty package prefix to the wrapped name
 * @author Rusi Popov
 */
public class PrefixName implements ConstructName {

  /**
   * not null, not empty
   */
  private final String prefix;

  /**
   * not null
   */
  private final ConstructName decorated;

  /**
   * @param prefix not null, not empty prefix, not ending at "."
   * @param decorated not null name decorator
   */
  public PrefixName(String prefix, ConstructName decorated) {
    assert prefix != null && !prefix.isEmpty() : "Expected a non-empty prefix";
    assert decorated != null : "Expected non-null decorator";

    this.prefix = prefix;
    this.decorated = decorated;
  }


  public String constructName(ModelElement element) {
    String result;
    String name;

    name = decorated.constructName( element );
    if ( name == null || name.isEmpty() ) {
      result = prefix;
    } else {
      result = prefix + "." + name;
    }
    return result;
  }
}
