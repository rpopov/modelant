/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.match;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.jmi.reflect.RefObject;

/**
 * This class represents the criteria to use when matching model elements.
 * So far it allows matching by attribute(s) and association(s)
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface MatchingCriteria {

  /**
   * @param forObject is a non-null model element to find the comparison attributes for
   * @return a non-null list of attribute names (paths) to compare
   */
  List<String> getAttributes(RefObject forObject);

  /**
   * @param forObject is a non-null model element to find the comparison associations for
   * @return a non-null list of associations names (paths) to compare
   */
  List<String> getAssociations(RefObject forObject);

  /**
   * No matching criteria
   */
  MatchingCriteria NONE = new MatchingCriteria() {
    public List<String> getAttributes(RefObject forObject) {
      return Collections.EMPTY_LIST;
    }

    public List<String> getAssociations(RefObject forObject) {
      return Collections.EMPTY_LIST;
    }
  };

  /**
   * Name-only matching criteria
   */
  MatchingCriteria NAME_MATCH = new MatchingCriteria() {
    public List<String> getAttributes(RefObject forObject) {
      return Arrays.asList( "name" );
    }

    public List<String> getAssociations(RefObject forObject) {
      return Collections.EMPTY_LIST;
    }
  };

  /**
   * Name and namespace matching criteria.
   * Compare the tagged values (the most elements in the model) by tag and referred model element
   */
  MatchingCriteria NAME_AND_NAMESPACE_MATCH = new MatchingCriteria() {
    public List<String> getAttributes(RefObject forObject) {
      return Arrays.asList( "name", "tag" );
    }

    public List<String> getAssociations(RefObject forObject) {
      return Arrays.asList( "namespace", "modelElement" );
    }
  };
}
