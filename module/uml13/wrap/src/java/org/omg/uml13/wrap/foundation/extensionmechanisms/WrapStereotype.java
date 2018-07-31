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
package org.omg.uml13.wrap.foundation.extensionmechanisms;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.foundation.extensionmechanisms.Stereotype;
import org.omg.uml13.modelmanagement.Model;
import org.omg.uml13.wrap.base.foundation.extensionmechanisms.BaseWrapStereotype;
import org.omg.uml13.wrap.foundation.core.WrapModelElement;
import org.omg.uml13.wrap.foundation.core.WrapNamespace;

/**
 * This is a wrapper of org.omg.uml13.foundation.extensionmechanisms.Stereotype that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapStereotype extends BaseWrapStereotype {

  public WrapStereotype(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapStereotype(RefPackage extent) {
    super( extent );
  }

  /**
   * Instantiates within the model a new stereotype with the name provided
   * @param model
   * @param qualifiedName
   */
  public WrapStereotype(Model model, String qualifiedName) {
    this( model.refOutermostPackage() );

    setName( qualifiedName );
    setNamespace( model );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
  }

  /**
   * This method instantiates a the public stereotype with the qualified name provided. No
   * duplicates are allowed.
   * @param qualifiedName of the exception to be created
   * @param extendedElement is the model element this stereotype extends
   * @return the newly created public exception
   */
  public static Stereotype instantiateStereotype(String qualifiedName,
                                                 ModelElement extendedElement) {
    Stereotype result;
    Model model;

    model = ((WrapModelElement) Factories.wrap( extendedElement )).getModel();

    result = (Stereotype) WrapNamespace.locateRelativeModelElement( model, qualifiedName );
    if ( result == null ) { // still not created, thus it is not included
      result = new WrapStereotype( model, qualifiedName ).getWrapped();
    }
    result.getExtendedElement().add( extendedElement );

    return result;
  }
}