/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.diff;

import java.util.Iterator;
import java.util.List;

import javax.jmi.reflect.RefBaseObject;
import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.match.MatchingCriteria;

/**
 * The data structure of the result of model comparison.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface ModelComparisonResult {

  /**
   * @return non-null criteria to match models, this result is based on
   */
  MatchingCriteria getMatchingCriteria();

  /**
   * @return the non-null correspondence between the objects from the source to the target model
   */
  List<InstanceDifference> getExactlyMatched();

  /**
   * @return non-null list of the elements of the old model that are detected as deleted in the new model
   */
  List<ModelDifference> getDeleted();

  /**
   * @return non-null list of the elements of the new model, that are detected as added in the new model
   */
  List<ModelDifference> getAdded();

  /**
   * @return non-null list of individual object changes
   */
  List<InstanceDifference> getChanged();

  /**
   * Two models are exactly matched if and only if there are no added, deleted and changed elements
   * found in their comparison
   * @return true if this is a result of comparing exactly matched models
   */
  default boolean isExactMatch() {
    boolean result;

    result = getDeleted().isEmpty() && getAdded().isEmpty() && getChanged().isEmpty();

    return result;
  }


  /**
   * @param x
   * @param y
   * @return the difference between x and y among the changes in the Match model comparison result.
   *         null when nothing found
   */
  default InstanceDifference findDiff(RefBaseObject x, RefBaseObject y) {
    InstanceDifference result;
    InstanceDifference diff;
    Iterator<InstanceDifference> diffIterator;

    result = null;
    diffIterator = getChanged().iterator();
    while (result==null && diffIterator.hasNext()) {
      diff = diffIterator.next();

      if ( diff.getXObject() == x
           && diff.getYObject() == y ) {
        result = diff;
      }
    }
    return result;
  }

  /**
   * @param x
   * @return the change having its X = provided x or null when not found
   */
  default InstanceDifference findDiffX(RefObject x) {
    InstanceDifference result;

    result = null;
    for (InstanceDifference diff : getChanged()) {
      if ( diff.getXObject() == x ) {
        result = diff;
      }
    }
    return result;
  }

  /**
   * @param y
   * @return the change having its Y = provided y or null when not found
   */
  default InstanceDifference findDiffY(RefObject y) {
    InstanceDifference result;

    result = null;
    for (InstanceDifference diff : getChanged()) {
      if ( diff.getYObject() == y ) {
        result = diff;
      }
    }
    return result;
  }
}