/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.name;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.model.ConstructProcedure;
import net.mdatools.modelant.core.api.model.NameMapping;

/**
 * A qualified name of a model element, with its own methods, ready to be searched for.
 *
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface Name<P extends Name<?>> {

  /**
   * These are the allowed separators in metamodel navigation paths
   */
  String METAMODEL_PATH_SEPARATOR = "::";

  /**
   * Used only to parse metamodel packages, allowing :,. and :: as names separator
   */
  String METAMODEL_PATH_SEPARATOR_PARSE = "[:.]+";


  /**
   * @return the owner.
   */
  P getOwner();


  /**
   * @return the name.
   */
  String getName();


  /**
   * @param qualifiedName not null
   * @return the separate identifiers in the qualified name provided
   */
  static String[] parseQualifiedName(String qualifiedName) {
    return qualifiedName.split( METAMODEL_PATH_SEPARATOR );
  }


  /**
   * @param parent could be null
   * @param name not null
   * @return not null instance of the same Name implementation class with the name and parent provided
   */
	Name<P> constructName(P parent, String name);

  /**
   * As of the requirements of JMI 1.0 {@link NameMapping#mapMetaAssociation(AssociationName, RefPackage, RefPackage, java.util.Map)} and
   * {@link NameMapping#mapMetaFieldName(FieldName, RefPackage, RefPackage, java.util.Map)}, the not changable target attribites
   * and derived associations should not be written into.
   *
   * @return non-null method to produce the transformation of a source model object into a target model object
   */
	ConstructProcedure<?> constructTransfromation();

  /**
   * @return non-null method to produce the transformation of a source model object into a target model object
   */
	ConstructProcedure<?> constructNoTransfromation();
}