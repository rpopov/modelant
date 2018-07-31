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
package net.mdatools.modelant.core.operation.model.topology;

import java.util.ArrayList;
import java.util.List;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.match.MatchingCriteria;

/**
 * This class represents a list of criteria (like class or other list criteria)
 * @author Rusi Popov
 */
public class ExceptionCriteria implements MatchingCriteria {

  /**
   * Holds the nested matching criteria
   */
  private final MatchingCriteria nested;

  /**
   * Holds the exceptions of the rules nested imposes
   */
  private final ListCriteria exceptions = new ListCriteria();


  /**
   * @param nested
   */
  public ExceptionCriteria(MatchingCriteria nested) {
    assert nested != null : "Expected a non-null criteria to wrap";
    this.nested = nested;
  }



  /**
   * @see net.mdatools.modelant.core.api.match.MatchingCriteria#getAttributes(javax.jmi.reflect.RefObject)
   */
  public List<String> getAttributes(RefObject forObject) {
    List<String> result;

    result = new ArrayList<String>( nested.getAttributes( forObject ));
    result.removeAll( exceptions.getAttributes( forObject ) );

    return result;
  }


  /**
   * @see net.mdatools.modelant.core.api.match.MatchingCriteria#getAssociations(javax.jmi.reflect.RefObject)
   */
  public List<String> getAssociations(RefObject forObject) {
    List<String> result;

    result = new ArrayList<String>( nested.getAssociations( forObject ));
    result.removeAll( exceptions.getAssociations( forObject ) );

    return result;
  }

  /**
   * @param criteria
   */
  public void add(MatchingCriteria criteria) {
    assert criteria != null : "Expected non-null criteria";

    exceptions.add(criteria);
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder;

    builder = new StringBuilder();
    builder.append( "ExceptionCriteria [" );
    builder.append( "nested=" ).append( nested ).append( ", " );
    builder.append( "exceptions=" ).append( exceptions );
    builder.append( "]" );
    return builder.toString();
  }
}