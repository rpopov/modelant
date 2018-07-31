/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 30.09.2017
 */
package net.mdatools.modelant.mof14.metamodel;

import java.util.ArrayList;
import java.util.List;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.match.ConsideredEqual;
import net.mdatools.modelant.core.operation.model.CompareModels;
import net.mdatools.modelant.core.operation.model.topology.Equals;
import net.mdatools.modelant.core.operation.model.topology.ExceptionCriteria;
import net.mdatools.modelant.core.operation.model.topology.ListCriteria;

/**
 * Compare MOF 1.4 models (e.g. metamodels)
 * @author Rusi Popov
 */
public class CompareMof14Models extends CompareModels {

  /**
   * Positive criteria
   */
  private static final ListCriteria MOF14_CRITERIA = new ListCriteria();
  static {
    MOF14_CRITERIA.add( new Equals("ModelElement", "name", "container"));
    MOF14_CRITERIA.add( new Equals("Tag", "tagId", "elements"));

    // The references are considered part of the referenced association ends
    MOF14_CRITERIA.add( new Equals("Reference", "", "referencedEnd"));
  }

  /**
   * Suppress the comparison of References by container, still comparing them by referencedEnd
   */
  private static final ExceptionCriteria MOF14_EXACT_MATCH_CRITERIA = new ExceptionCriteria(MOF14_CRITERIA);
  static {
    MOF14_EXACT_MATCH_CRITERIA.add( new Equals("Reference", "", "container"));
  }

  /**
   * Skip these attributes and associations when identifying exact match
   */
  private static final ListCriteria MOF14_EXCLUDE_CHANGE_DETECTION = new ListCriteria();
  static {
    MOF14_EXCLUDE_CHANGE_DETECTION.add( new Equals("ModelElement", "qualifiedName", "requiredElements"));
  }

  /**
   * @param fromPackage not null package where the original/from/source metamodel (model in MOF 1.4) is loaded
   */
  public CompareMof14Models(RefPackage fromPackage) {
    this( new ArrayList<>(),
          fromPackage);
  }


  /**
   * @param bindings non null list defines explicitly listed objects as equals (even though they are not equal in the
   *   sense of exactMatchCriteria). These are model elements that should be treated as a-priori equal.
   * @param fromPackage not null extent holding the model, treated as "old"
   */
  public CompareMof14Models(List<ConsideredEqual> bindings,
                            RefPackage fromPackage) {
    super( MOF14_EXACT_MATCH_CRITERIA,
           MOF14_EXCLUDE_CHANGE_DETECTION,
           bindings,
           fromPackage );
  }
}
