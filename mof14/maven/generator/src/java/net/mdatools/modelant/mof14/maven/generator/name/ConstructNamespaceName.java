/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 1, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.name;

import javax.jmi.model.AliasType;
import javax.jmi.model.ModelElement;

/**
 * Construct the qualified name of the namespace of the provided element,
 * considering explicit names overrides and namespace overrides.
 * @author Rusi Popov
 */
public class ConstructNamespaceName implements ConstructName {

  /**
   * The <code>ORG_OMG_MOF_IDL_PREFIX</code> is the MOF tag ID for package name prefix
   */
  private static final String ORG_OMG_MOF_IDL_PREFIX = "org.omg.mof.idl_prefix";

  /**
   * The <code>JAVAX_JMI_PACKAGE_PREFIX</code> is the JMI standard tag ID for package name prefix
   * See section 4.6.1 of JMI 1.0 specification
   */
  private static final String JAVAX_JMI_PACKAGE_PREFIX = "javax.jmi.packagePrefix";


  private final ConstructName constructDecoratedName;

  /**
   * Retrieve any
   */
  private final ConstructName constructDecoratedNamespace;

  /**
   * Optional namespace prefix
   */
  private final String prefix;

  /**
   * @param constructDecoratedName not null
   * @param prefix non-null, possibly empty namespace prefix (like jms, base, ...). Does not end at "."
   */
  public ConstructNamespaceName(ConstructName constructDecoratedName) {
    this( constructDecoratedName, "" );
  }

  /**
   * @param constructDecoratedName not null
   * @param prefix non-null, possibly empty namespace prefix (like jms, base, ...). Does not end at "."
   */
  public ConstructNamespaceName(ConstructName constructDecoratedName, String prefix) {
    assert constructDecoratedName != null : "Expected a non-null ConstructName to decorate";
    assert prefix != null : "Expected a non-null prefix";

    this.constructDecoratedName = constructDecoratedName;
    this.prefix = prefix;

    this.constructDecoratedNamespace =
        new DecorateNameWithTag( JAVAX_JMI_PACKAGE_PREFIX,
                                 new DecorateNameWithTag( ORG_OMG_MOF_IDL_PREFIX,
                                                          new EmptyName() ) );
  }

  public String constructName(ModelElement element) {
    String result;
    String containerName;
    String ownName;

    if ( element instanceof AliasType ) {
      result = constructName(((AliasType) element).getType());

    } else {

      // check for explicit namespace override
      result = constructDecoratedNamespace.constructName( element );

      if ( result.isEmpty() ) { // no explicit override

        if ( element != null && element.getContainer() != null ) {
          containerName = constructName( element.getContainer() );

          ownName = constructDecoratedName.constructName( element.getContainer())
                                          .replaceAll( "[^a-zA-Z0-9.$]", "" )
                                          .toLowerCase();

          if ( containerName != null && !containerName.isEmpty() ) {
            result = containerName
                     + "."
                     + ownName;
          } else {
            result = ownName;
          }
        } else { // there is no container - the namespace is the provided namespace prefix (if any)
          result = prefix;
        }
      } else if ( !prefix.isEmpty() ) {  // there is an explicit override + prefix
        result = prefix + "." + result;
      }
    }
    return result;
  }
}