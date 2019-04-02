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
 * Construct the qualified name of the namespace of the provided element,
 * considering explicit names overrrides and namespace overrides.
 * @author Rusi Popov
 */
public class ConstructNamespaceName implements ConstructName {

  private static final String ORG_OMG_XMI_NAMESPACE = "org.omg.xmi.namespace";

  /**
   * The <code>ORG_OMG_MOF_IDL_PREFIX</code> is the MOF tag ID for package name prefix
   */
  private static final String ORG_OMG_MOF_IDL_PREFIX = "org.omg.mof.idl_prefix";

  /**
   * The <code>JAVAX_JMI_PACKAGE_PREFIX</code> is the JMI standard tag ID for package name prefix
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
   * @param prefix optional namespace prefix (like jms, base, ...). Does not end at "."
   */
  public ConstructNamespaceName(ConstructName constructDecoratedName, String prefix) {
    this.constructDecoratedName = constructDecoratedName;
    this.prefix = prefix;

    this.constructDecoratedNamespace =
        new DecorateNameWithTag( JAVAX_JMI_PACKAGE_PREFIX,
                                 new DecorateNameWithTag( ORG_OMG_MOF_IDL_PREFIX,
                                                          new DecorateNameWithTag( ORG_OMG_XMI_NAMESPACE,
                                                                                   new NullName() ) ) );
  }

  public String constructName(ModelElement element) {
    String result;
    String containerName;

    // check for explicit namespace override
    result = constructDecoratedNamespace.constructName( element );

    if ( result == null || result.isEmpty() ) {

      if ( element.getContainer() != null ) {
        containerName = constructName( element.getContainer() );

        if ( containerName != null && !containerName.isEmpty() ) {
          result = containerName
                   + "."
                   + constructDecoratedName.constructName( element.getContainer());
        } else {
          result = constructDecoratedName.constructName( element.getContainer());
        }
      } else { // there is no container - the namespace is the provided namespace prefix (if any)
        result = prefix;
      }
    }
    return result;
  }
}