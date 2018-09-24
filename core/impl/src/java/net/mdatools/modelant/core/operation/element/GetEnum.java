/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.element;

import javax.jmi.reflect.RefEnum;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Retrieve an enumerated literal of a (meta)type provided
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class GetEnum implements Function<RefPackage, RefEnum> {

  /**
   * Path in the metamodel to the metaPackage whose instances (in the model/extent) are to be
   * processed. Format of metaPackage attribute: {<package>::} <meta class>
   */
  private String metaPackage;

  /**
   * The name of the enumerated type
   */
  private String type;

  /**
   * The name of the enumerated literal to retrieve
   */
  private String literal;

  /**
   * The task's execution method. This method filters the model classes associated with metaPackage and
   * executes the nested &lt;template&gt; tasks for the filtered classes collection. This
   * methods invokes the internal formatting tasks with the metaPackage itself.
   */
  public RefEnum execute(RefPackage sourceExtent) throws IllegalArgumentException {
    RefEnum result;
    RefPackage refPackage;

    if ( sourceExtent == null ) {
      throw new IllegalArgumentException("Expected a non-null extent");
    }

    refPackage = Navigator.getMetaPackage( sourceExtent, metaPackage ); // non-null

    result = refPackage.refGetEnum( type, literal );

    return result;
  }
}