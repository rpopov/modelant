/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml13.wrap;

import java.util.Iterator;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.repository.Cache;

import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.extensionmechanisms.Stereotype;
import org.omg.uml13.foundation.extensionmechanisms.TaggedValue;

/**
 * This class is a cache of steretotypes bound to a classifier
 */
public class StereotypeCache extends Cache<ModelElement, Stereotype> {

  /**
   * @see net.mdatools.modelant.repository.Cache#register(javax.jmi.reflect.RefObject)
   */
  public void register(RefObject refObject) {
    Iterator modelElementIterator;
    ModelElement specified;
    Stereotype stereotype;
    
    if ( refObject instanceof Stereotype ) {
      // cache the model elements to this stereotype
      stereotype = (Stereotype) refObject;
      
      modelElementIterator = stereotype.getExtendedElement().iterator();
      while ( modelElementIterator.hasNext() ) {
        specified = (ModelElement) modelElementIterator.next();
        put( specified, stereotype );
      }
    }
  }
  
  /**
   * @see net.mdatools.modelant.repository.Cache#unregister(javax.jmi.reflect.RefObject)
   */
  public void unregister(RefObject refObject) {
    ModelElement extendedElement;
    Iterator elementsIterator;

    if ( refObject instanceof Stereotype ) {
      // remove the tagged value from the cached backward link
      elementsIterator = ((Stereotype) refObject).getExtendedElement().iterator();
      while ( elementsIterator.hasNext() ) {
        extendedElement = (ModelElement) elementsIterator.next();
        remove( extendedElement );
      }
    }
  }
}