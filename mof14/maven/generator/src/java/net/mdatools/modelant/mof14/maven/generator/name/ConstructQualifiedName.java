/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 1, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.name;

import javax.jmi.model.ModelElement;

/**
 * Construct a qualified name considering local names overriding/decorating of the element and
 * its namespace and optional prefix.
 * @author Rusi Popov
 */
public class ConstructQualifiedName implements ConstructName {

  /**
   * not null
   */
  private final ConstructName constructName;

  /**
   * not null
   */
  private final ConstructName constructNamespace;

  /**
   * @param constructDecoratedName not null
   */
  public ConstructQualifiedName(ConstructName constructDecoratedName) {
    this( constructDecoratedName, "");
  }

  /**
   * @param constructDecoratedName not null
   * @param prefix optional namespace prefix,not ending at "."
   */
  public ConstructQualifiedName(ConstructName constructDecoratedName, String prefix) {
    assert constructDecoratedName != null : "Expected a non-null ConstructName to decorate";

    this.constructName = constructDecoratedName;
    this.constructNamespace = new ConstructNamespaceName( constructDecoratedName, prefix );
  }

  public String constructName(ModelElement element) {
    String result;
    String namespace;

    result = constructName.constructName( element );
    namespace = constructNamespace.constructName( element );

    if ( namespace != null && !namespace.isEmpty() ) {
      result = namespace + "." + result;
    }
    return result;
  }
}