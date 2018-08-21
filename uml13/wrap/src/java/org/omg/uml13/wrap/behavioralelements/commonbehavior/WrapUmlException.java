/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.behavioralelements.commonbehavior;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.modelmanagement.Model;
import org.omg.uml13.wrap.base.behavioralelements.commonbehavior.BaseWrapUmlException;
import org.omg.uml13.wrap.foundation.core.WrapNamespace;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.UmlException that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapUmlException extends BaseWrapUmlException {

  public WrapUmlException(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapUmlException(RefPackage extent) {
    super( extent );
  }

  /**
   * Instantiates in the model a public exception with the name provided
   * @param model
   * @param qualifiedName
   */
  public WrapUmlException(Model model, String qualifiedName) {
    this( model.refOutermostPackage() );

    setName( qualifiedName );
    setNamespace( model );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
  }

  /**
   * This method instantiates a public exception with the qualified name provided. <b>This method
   * instantiates an Exception, but the Exceptions might be messed with the regular classes so they
   * are registered as DataTypes at the model level instead of UnlExceprion-s when they are also
   * reverse engineered.
   * @param qualifiedName of the exception to be created
   * @param model TODO
   * @return the newly created public exception
   */
  public static Classifier instantiateException(String qualifiedName, Model model) {
    Classifier result = (Classifier) WrapNamespace.locateRelativeModelElement( model, qualifiedName );

    if ( result == null ) { // still not created, thus it is not included
      result = new WrapUmlException( model, qualifiedName ).getWrapped();
    }
    return result;
  }
}