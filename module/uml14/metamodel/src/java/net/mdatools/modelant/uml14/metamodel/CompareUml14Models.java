/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml14.metamodel;

import java.util.ArrayList;
import java.util.List;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.match.ConsideredEqual;
import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.core.operation.model.CompareModels;

/**
 * Compare UML 1.4 models operation<pre>
 *
 *   compare = new CompareUml13Models(original model);
 *   result = compare.execute(changed model);
 *
 *   System.err.println(result);
 * </pre>
 * @author Rusi Popov
 */
public class CompareUml14Models extends CompareModels {

  /**
   * @param oldRootPackage the extent with the model, considered original (version)
   */
  public CompareUml14Models(RefPackage oldRootPackage) {
    this( new ArrayList<>(), oldRootPackage );
  }

  /**
   * @param bindings not null - any a priori known correspondence (equality) between original and target model elements
   * @param oldRootPackage the extent with the model, considered original (version)
   */
  public CompareUml14Models(List<ConsideredEqual> bindings,
                            RefPackage oldRootPackage) {
    super( MatchingCriteria.NAME_AND_NAMESPACE_MATCH,
           MatchingCriteria.NONE,
           bindings, oldRootPackage );
  }
}
