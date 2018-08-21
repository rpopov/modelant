/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.core.GeneralizableElement;
import org.omg.uml13.foundation.core.Generalization;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapGeneralizableElement;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.GeneralizableElement that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapGeneralizableElement extends BaseWrapGeneralizableElement {

  public WrapGeneralizableElement(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * formatMethodDeclaration formats as a string a single method declaration. Note that no comments
   * or documentation are included. The declaration includes the message body as well if one is
   * provided, so that this method can be compiled (if the message body is correct).
   *
   * @return a string representing the method declaration in Java
   */
  public String formatInheritance() {
    String result;

    // Add inheritance specification
    if ( isAbstract() ) {
      result = "abstract";
    } else if ( isLeaf() ) {
      result = "final";
    } else {
      result = "";
    }
    return result;
  }

  /**
   * Builds a list of all superclasses and superinterfaces of the class provided, including itself
   * @return a non-null list of superclasses extended directly or indirectly
   */
  public Collection listSuperclassesOf() {
    ArrayList result = new ArrayList( 20 );
    GeneralizableElement classToCheck;
    GeneralizableElement superclass;
    Iterator generalizationIterator;

    int i = 0;

    // breadth-first search of all subclasses
    result.add( getWrapped() );
    while ( i < result.size() ) { // INVARIANT: result contains only unique UmlClass instances
                                  // processed up to i-1
      classToCheck = (GeneralizableElement) result.get( i++ );

      // check superclasses
      generalizationIterator = classToCheck.getGeneralization().iterator();
      while ( generalizationIterator.hasNext() ) {
        superclass = ((Generalization) generalizationIterator.next()).getParent(); // it might be not only
                                                                                   // class, but association,
                                                                                   // classifier, etc.
        if ( !result.contains( superclass ) ) { // not checked yet
          result.add( superclass ); // subclass added for investigation
        }
      }
    }
    return result;
  }
}