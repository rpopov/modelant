/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jmi.model.Attribute;
import javax.jmi.model.MofClass;
import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.Function;

/**
 * Retrieve all attributes of the model element, either defined in it or inherited
 * @author Rusi Popov
 */
public class RetrieveAttributes implements Function<RefObject, Collection<String>> {

  /**
   * This method retrieves the names of all attributes of the model element provided
   * @param element is non-null object from the first model
   * @return a non-null list of names of attributes
   */
  public Collection<String> execute(RefObject element) {
    List<String> result;
    List<MofClass> metaClasses;
    MofClass metaClass;

    Iterator featuresIterator;
    Iterator<MofClass> superclassIterator;
    Object contained;

    result = new ArrayList<String>();
    metaClass = (MofClass) element.refMetaObject();

    // check all inherited attributes
    metaClasses = new ArrayList<MofClass>();
    metaClasses.add( metaClass );
    metaClasses.addAll( metaClass.allSupertypes() );

    superclassIterator = metaClasses.iterator();
    while ( superclassIterator.hasNext() ) {
      metaClass = superclassIterator.next();

      featuresIterator = metaClass.getContents().iterator();
      while ( featuresIterator.hasNext() ) {
        contained = featuresIterator.next();

        if ( contained instanceof Attribute ) {
          result.add( ((Attribute) contained).getName() );
        }
      }
    }
    return result;
  }
}
