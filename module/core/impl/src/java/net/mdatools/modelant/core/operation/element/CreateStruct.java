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
package net.mdatools.modelant.core.operation.element;

import java.util.ArrayList;
import java.util.List;

import javax.jmi.reflect.RefPackage;
import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Instantiate a model Structure.
 * @author Rusi Popov
 */
public class CreateStruct implements Function<RefPackage, RefStruct> {

  /**
   * Path in the metamodel to the metapackage whose instances (in the model/extent) are to be
   * processed. Format of metapackage attribute: { <package>::} <meta class>
   */
  private final String metapackage;

  /**
   * The Struct type name within the metapackage
   */
  private final String type;

  /**
   * Collected values of the structure's fields
   */
  private final List parameters = new ArrayList();

  public CreateStruct(String metapackage, String type, List<String> parameters){
    if ( metapackage == null ) {
      throw new IllegalArgumentException( "Epected a non-null metapackage");
    }
    if ( type == null || type.trim().length() == 0 ) { // no extent provided
      throw new IllegalArgumentException( "Eoected a non-empty struct type");
    }
    this.metapackage = metapackage;
    this.type = type;
    this.parameters.addAll( parameters );
  }

  /**
   * The task's execution method. This method filters the model classes associated with metapackage and
   * executes the nested &lt;template&gt; tasks for the filtered classes collection. This
   * methods invokes the internal formatting tasks with the metapackage itself.
   */
  public RefStruct execute(RefPackage sourceExtent) throws IllegalArgumentException {
    RefStruct result;
    RefPackage refPackage;

    if ( sourceExtent == null ) {
      throw new IllegalArgumentException("Expected non-null extent");
    }

    refPackage = Navigator.getMetaPackage( sourceExtent, metapackage ); // non-null

    result = refPackage.refCreateStruct( type, parameters );

    return result;
  }
}