/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jmi.reflect.RefObject;

/**
 * Once the access to the attributes and associations of the model elements is slow,
 * especially when the detecting changes,
 * this class represents a cache of the model element's attributes and associations.
 * @author Rusi Popov (popovr@mdatools.net)
 */
class CachedModelElement {

  private final RefObject wrapped;

  private final Map<String, Object> attributeValues = new HashMap<>();
  private final Map<String, Object> associationValues = new HashMap<>();

  /**
   * @param wrapped not null
   * @param attributeNames
   * @param associationNames
   */
  private CachedModelElement(RefObject wrapped,
                             Collection<String> attributeNames,
                             Collection<String> associationNames) {
    Object value;

    if ( wrapped == null ) {
      throw new IllegalArgumentException("Expected a non-null model element to wrap");
    }
    this.wrapped = wrapped;

    for (String attributeName : attributeNames) {
      value = wrapped.refGetValue( attributeName );
      attributeValues.put( attributeName, value);
    }

    for (String associationName : associationNames) {
      value = wrapped.refGetValue( associationName );

      if ( value instanceof Collection ) {
        associationValues.put( associationName, new ArrayList<>((Collection) value));
      } else {
        associationValues.put( associationName, value);
      }
    }
  }

  /**
   * @param elements not null set of model elements
   * @param attributeNames not null attribute names
   * @param associationNames not null association names
   * @return a cached model element (with its attributes and associations) for each element of elements
   */
  public static Collection<CachedModelElement> cacheModel(Collection<RefObject> elements,
                                                          Collection<String> attributeNames,
                                                          Collection<String> associationNames) {
    Collection<CachedModelElement> result;

    result = new ArrayList<>();
    for (RefObject element : elements) {
      result.add( new CachedModelElement( element,
                                          attributeNames,
                                          associationNames ) );
    }
    return result;
  }

  /**
   * @return the wrapped.
   */
  public final RefObject getWrapped() {
    return wrapped;
  }

  /**
   * @return the names of the attributes
   */
  public Set<String> getAttributeNames() {
    return attributeValues.keySet();
  }

  /**
   * @return the names of the associations
   */
  public Set<String> getAssociationNames() {
    return associationValues.keySet();
  }

  /**
   * @param name not null attribute name
   * @return the value of the attribute on the wrapped object
   */
  public Object getAttributeValue(String name) {
    return attributeValues.get( name );
  }

  /**
   * @param name not null association name
   * @return the value of the attribute on the wrapped object
   */
  public Object getAssociationValue(String name) {
    return associationValues.get( name );
  }
}