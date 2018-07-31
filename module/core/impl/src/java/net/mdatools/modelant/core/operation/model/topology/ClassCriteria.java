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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jmi.model.GeneralizableElement;
import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.match.MatchingCriteria;

/**
 * This class represents criteria based on the target object's class, i.e. if the target object/model element
 * is not of the class this criteria represents, then it just returns no associations.
 * This is a composite criteria
 * @author Rusi Popov
 */
public class ClassCriteria implements MatchingCriteria {

  private final MatchingCriteria nested;
  private final Set<String> knownNotCovered = new HashSet<String>(11);
  private final Set<String> knownSubclasses = new HashSet<String>(11);

  /**
   * @param className
   * @param nested
   */
  public ClassCriteria(String className, MatchingCriteria nested) {
    this.nested = nested;

    knownSubclasses.add( className );
  }

  /**
   * Returns the wrapped list of attributes only if the model element is of the metaclass with class name provided
   * @see net.mdatools.modelant.core.operation.model.topology.SimpleCriteria#getAttributes(javax.jmi.reflect.RefObject)
   */
  public List<String> getAttributes(RefObject forObject) {
    List<String> result;

    if ( isOfClass(forObject) ) {
      result = nested.getAttributes( forObject );
    } else {
      result = new ArrayList<String>();
    }
    return result;
  }

  /**
   * Returns the wrapped list of associations only if the model element is of the metaclass with class name provided
   * @see net.mdatools.modelant.core.operation.model.topology.SimpleCriteria#getAssociations(RefObject)
   */
  public List<String> getAssociations(RefObject forObject) {
    List<String> result;

    if ( isOfClass(forObject) ) {
      result = nested.getAssociations( forObject );
    } else {
      result = new ArrayList<String>();
    }
    return result;
  }

  /**
   * @param forObject
   * @return true if forObject is of class with the simple name provided.
   */
  private boolean isOfClass(RefObject forObject) {
    boolean result;
    List<GeneralizableElement> superclasses;
    int i;
    boolean decided;
    GeneralizableElement superClass;
    String forClassName;
    String thisClassName;

    superClass = (GeneralizableElement) forObject.refMetaObject();
    thisClassName = superClass.getName();

    // examine all superclasses
    superclasses = new ArrayList<GeneralizableElement>();
    superclasses.add( superClass );

    i = 0;
    do {
      superClass = superclasses.get( i++ );

      forClassName = superClass.getName();

      result = knownSubclasses.contains( forClassName );
      decided = result
                || knownNotCovered.contains( forClassName );
      if ( !decided ) {
        superclasses.addAll( superClass.getSupertypes() );
      }
    } while ( !decided && i < superclasses.size() );

    if ( result ) {
      knownSubclasses.add( thisClassName );
    } else if ( !decided ) { // decision not found in the cache
      knownNotCovered.add( thisClassName );
    }
    return result;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder;

    builder = new StringBuilder();
    builder.append( "ClassCriteria [" );
    builder.append( "nested=" ).append( nested ).append( ", " );

    builder.append( "knownNotCovered=" ).append( knownNotCovered ).append( ", " );
    builder.append( "knownSubclasses=" ).append( knownSubclasses );
    builder.append( "]" );

    return builder.toString();
  }
}