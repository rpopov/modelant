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
package org.omg.uml13.wrap.modelmanagement;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.Convention;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.core.Namespace;
import org.omg.uml13.modelmanagement.Model;
import org.omg.uml13.wrap.base.modelmanagement.BaseWrapModel;
import org.omg.uml13.wrap.foundation.core.WrapNamespace;

/**
 * This is a wrapper of org.omg.uml13.modelmanagement.Model that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapModel extends BaseWrapModel {

  public WrapModel(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapModel(RefPackage extent) {
    super( extent );
  }

  /**
   * @param extent is non-null
   * @return the non-null Model in the extent provided
   * @throws NoSuchElementException when there is no any Model instance
   */
  public static Model getModel(RefPackage extent) throws NoSuchElementException {
    Model result;
    Iterator modelIterator;

    modelIterator = WrapModel.getClassProxy( extent ).refAllOfClass().iterator();
    result = (Model) modelIterator.next();
    return result;
  }

  /**
   * This method locates the model element using its qualified name in this model object. The empty
   * (string) name is recognized as the name of the default package - the model itself.
   * This method allows locating java, CORBA, MOF, etc. packages, Classes within packages and
   * attributes within classes. <b>'.'</b> is the separator of the names within the qualified name.
   *
   * @param qualifiedName is a non-empty string with the qualified name of a model element
   * @return the model element with the name specified. null is returned when the element cannot be
   *         found.
   */
  public ModelElement locateQualifiedModelElement(String qualifiedName) {
    ModelElement result;
    StringTokenizer packageTokenizer;
    String relativeName;

    result = getWrapped();

    // parse the package name and locate the nested packages
    packageTokenizer = new StringTokenizer( qualifiedName,
                                            Convention.JAVA_PACKAGE_SEPARATOR );
    while ( packageTokenizer.hasMoreTokens() && (result instanceof Namespace) ) {
      relativeName = packageTokenizer.nextToken();
      result = WrapNamespace.locateRelativeModelElement( (Namespace) result, relativeName );
    }

    if ( packageTokenizer.hasMoreTokens() ) { // the element is not reached
      result = null;
    }
    return result;
  }
}