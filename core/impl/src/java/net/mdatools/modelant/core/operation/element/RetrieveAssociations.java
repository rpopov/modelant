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

import javax.jmi.model.MofClass;
import javax.jmi.model.Reference;
import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.Function;

/**
 * Retrieve all associations of the model element, either defined in it or inherited
 * @author Rusi Popov
 */
public class RetrieveAssociations implements Function<RefObject, Collection<String>> {


  /**
   * Retrieve the names of all associations in the METAMODEL that
   * the model element <b>element</b> has, i.e. these are the names of the
   * MOF References of all associations in MOF the class of the <b> element</b>
   * is described with.
   * NOTE: These are NOT model associations!
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

        if ( contained instanceof Reference ) {
          result.add( ((Reference) contained).getName() );
        }
      }
    }
    return result;
  }
}
