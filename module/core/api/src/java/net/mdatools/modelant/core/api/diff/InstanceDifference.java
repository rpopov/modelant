/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 19.11.2017
 */
package net.mdatools.modelant.core.api.diff;

import java.util.Iterator;
import java.util.List;

import javax.jmi.reflect.RefObject;

/**
 * The differences identified on a single object X compared with another object Y
 * @author Rusi Popov
 */
public interface InstanceDifference {

  /**
   * @return true when the comparison of the new and old objects reveals a difference / change
   */
  boolean isExactMatch();

  /**
   * @return not null list of attributes having different values (if any) in X and Y objects
   */
  List<String> getAttributesWithDifferences();


  /**
   * @return not null list of associations binding different values objects in X and Y objects
   */
  List<AssociationDifference> getAssociationDiffs();

  /**
   * @return non-null X object matched as correspondent to Y
   */
  RefObject getXObject();

  /**
   * @return non-null Y object matched as correspondent to X
   */
  RefObject getYObject();

  /**
   * @param diff non-null && !diff.thereAreChanges() i.e. best match
   * @return true if this is a match of at least one of the objects used in diff, this way representing
   *         a sub-optimal match
   */
  default boolean covers(InstanceDifference diff) {
    boolean result;

    result = getXObject() == diff.getXObject()
             || getYObject() == diff.getYObject();

    return result;
  }



  /**
   * Remove from the provided localDiffs collection all Diffs that are covered by this,
   * i.e. that involve either the new or the old model instance
   * @param localDiffs
   */
  default void removeCoveredDiffs(List<InstanceDifference> localDiffs) {
    Iterator<InstanceDifference> localDiffsIterator;
    InstanceDifference singleComparison;

    localDiffsIterator = localDiffs.iterator();
    while ( localDiffsIterator.hasNext() ) {
      singleComparison = localDiffsIterator.next();

      if ( covers(singleComparison) ) {
        localDiffsIterator.remove();
      }
    }
  }
}