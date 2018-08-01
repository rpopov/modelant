/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.topology;

/**
 * Criteria how to compare instances of a the same metaclass and find them equal.
 * @author Rusi Popov
 */
public class Equals extends ClassCriteria {
  /**
   * @param className non-null, non-empty meta-model class name instances of which are compared for equality
   * @param attributes a comma-separated list of attribute paths common for all objects/model elements
   * of that class and used to distinguish non-equal elements. It might be empty.
   * Each attribute path complies with the syntax:<p>
   * <b>{asssociationName.}attributeName</b></p>
   * @param associations a non-null comma-separated list of association paths common for all objects/model elements
   * of that class and used to distinguish non-equal elements. It might be empty.
   * Each association path complies with the syntax:<p>
   * <b>{asssociationName.}asssociationName</b></p>
   */
  public Equals(String className, String attributes, String associations) {
    super(className, new SimpleCriteria( attributes, associations ) );
  }
}