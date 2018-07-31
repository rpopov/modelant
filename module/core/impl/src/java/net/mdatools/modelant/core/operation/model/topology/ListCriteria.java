/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.topology;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.match.MatchingCriteria;

/**
 * A list of criteria (like class or other list criteria) to compare or print model elements.
 * Collect the results of all nested matching criteria, that are applicable.
 * @author Rusi Popov
 */
public class ListCriteria implements MatchingCriteria {

  /**
   * Holds the nested matching criteria
   */
  private final List<MatchingCriteria> nested = new ArrayList<MatchingCriteria>();

  /**
   * @see net.mdatools.modelant.core.api.match.MatchingCriteria#getAttributes(javax.jmi.reflect.RefObject)
   */
  public List<String> getAttributes(RefObject forObject) {
    List<String> result;
    Iterator<MatchingCriteria> nestedIterator;

    result = new ArrayList<String>(nested.size());

    nestedIterator = nested.iterator();
    while ( nestedIterator.hasNext() ) {
      result.addAll( nestedIterator.next().getAttributes( forObject ));
    }
    return result;
  }


  /**
   * @see net.mdatools.modelant.core.api.match.MatchingCriteria#getAssociations(javax.jmi.reflect.RefObject)
   */
  public List<String> getAssociations(RefObject forObject) {
    List<String> result;
    Iterator<MatchingCriteria> nestedIterator;

    result = new ArrayList<String>();

    nestedIterator = nested.iterator();
    while ( nestedIterator.hasNext() ) {
      result.addAll( nestedIterator.next().getAssociations( forObject ));
    }
    return result;
  }


  /**
   * Just registers criteria more general than the previously registered
   * @param criteria is not null criteria to add
   */
  public final void add(MatchingCriteria criteria) {
    assert criteria != null : "Expected non-null criteria";

    nested.add(criteria);
  }


  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder;

    builder = new StringBuilder();
    builder.append( "ListCriteria [" );
    builder.append( "nested=" ).append( nested );
    builder.append( "]" );

    return builder.toString();
  }
}