/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.core;

import java.util.Iterator;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.core.Namespace;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapNamespace;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Namespace that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapNamespace extends BaseWrapNamespace {

  public WrapNamespace(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * This method locates the model element with name provided in <b>elementName </b> within the UML
   * namespace <b>outerPackage </b>. It keeps the relative cache up to date.
   *
   * @param namespace is the package where to search for. It must not be null.
   * @param elementName is a non-empty string package name
   * @return null if no package found, otherwise the package with the name specified
   */
  public static ModelElement locateRelativeModelElement(Namespace namespace, String elementName) {
    ModelElement result = null;
    ModelElement ownedElement;
    Iterator ownedElementsIterator;

    ownedElementsIterator = namespace.getOwnedElement().iterator();
    while ( result == null && ownedElementsIterator.hasNext() ) {
      ownedElement = (ModelElement) ownedElementsIterator.next();

      if ( elementName.equals( ownedElement.getName() ) ) {
        result = ownedElement;
      }
    }
    return result;
  }
}