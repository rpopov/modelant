/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.core.GeneralizableElement;
import org.omg.uml13.foundation.core.Generalization;
import org.omg.uml13.foundation.core.Interface;
import org.omg.uml13.foundation.core.Namespace;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapInterface;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Interface that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapInterface extends BaseWrapInterface {

  public WrapInterface(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapInterface(RefPackage extent) {
    super( extent );
  }

  public WrapInterface(Namespace owner, String name) {
    this( owner.refOutermostPackage() );

    setName( name );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
    setNamespace( owner );
  }

  /**
   * This method creates the public interface in the package provided
   *
   * @param umlPackage the containg package
   * @param interfaceName is the name of the interface to create
   * @return the interface built
   */
  public static Interface instantiateInterface(Namespace umlPackage, String interfaceName) {
    Interface result = (Interface) WrapNamespace.locateRelativeModelElement( umlPackage, interfaceName );

    if ( result == null ) { // none found - build it
      result = new WrapInterface( umlPackage, interfaceName ).getWrapped();
    }
    return result;
  }
  
  /**
   * @return non-null list of classes that immediately implement this interface
   */
  public Collection getImmediateImplementations() {
    Set result = new LinkedHashSet( 19 );
    GeneralizableElement subclass;
    Iterator generalizationIterator;

    // check subclasses
    generalizationIterator = getSpecialization().iterator();
    while ( generalizationIterator.hasNext() ) {
      subclass = ((Generalization) generalizationIterator.next()).getChild(); // it might be not only class, but
                                                                              // association, classifier, etc.
      result.addAll( ((WrapClassifier) wrap( subclass )).getImmediateImplementations() );
    }
    return result;
  }
  
}