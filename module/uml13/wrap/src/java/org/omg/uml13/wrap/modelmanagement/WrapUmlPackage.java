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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.uml13.metamodel.Convention;
import net.mdatools.modelant.util.FormatHelper;

import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.foundation.extensionmechanisms.Stereotype;
import org.omg.uml13.modelmanagement.Model;
import org.omg.uml13.modelmanagement.UmlPackage;
import org.omg.uml13.wrap.Uml13WrapFactory;
import org.omg.uml13.wrap.base.modelmanagement.BaseWrapUmlPackage;
import org.omg.uml13.wrap.foundation.core.ModelElementCondition;
import org.omg.uml13.wrap.foundation.core.WrapModelElement;
import org.omg.uml13.wrap.foundation.core.WrapNamespace;
/**
 * This is a wrapper of org.omg.uml13.modelmanagement.UmlPackage that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapUmlPackage extends BaseWrapUmlPackage {

  /**
   * @param wrapped
   * @param factory
   */
  public WrapUmlPackage(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * @param extent
   */
  public WrapUmlPackage(RefPackage extent) {
    super( extent );
  }

  /**
   * Instantiates a public package with that name directly nested in the outerPackage provided
   * @param outerPackage
   * @param relativeName
   */
  public WrapUmlPackage(UmlPackage outerPackage, String relativeName) {
    this( outerPackage.refOutermostPackage() );
    setName( relativeName );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
    setNamespace( outerPackage );
  }

  /**
   * @return the name of this UML package formatted as a java package name.
   */
  public String getLogicalName() {
    String result;

    result =  FormatHelper.formatPackageName( translate( getName() ));
    
    return result;
  }

  /**
   * @return a non-null list with the simple names forming the qualified name of this model element as they are located
   *         in the actual model
   */
  public List<String> getQualifiedNameList() {
    List<String> result;
    
    if ( getFactory().getProperty( Convention.PREFIX_EXCLUDED_PACKAGE_NAME+getName()) == null // the explicitly excluded packages stop the process
        && !(getWrapped() instanceof Model)) {
      result =  super.getQualifiedNameList();
      
    } else { // this is a stop name 
      result = new ArrayList<String>();
    }
    return result;
  }
  
  
  
  /**
   * This method constructs in the model an UML package using its qualified name.
   *
   * @param qualifiedName is the qualified package name needed for its further lookup
   * @param model 
   * @return the package created. If an empty name is provided, no package is cretaed and null is
   *         returned
   */
  public static UmlPackage instantiatePackage(String qualifiedName, UmlPackage model) {
    return instantiatePackage(qualifiedName, model, net.mdatools.modelant.core.Convention.JAVA_PACKAGE_SEPARATOR);
  }

  /**
   * This method constructs in the model an UML package using its qualified name.
   *
   * @param qualifiedName is the qualified package name needed for its further lookup
   * @param namespace 
   * @param separator 
   * @return the package created. If an empty name is provided, no package is created and null is
   *         returned
   */
  public static UmlPackage instantiatePackage(String qualifiedName, UmlPackage namespace, String separator) {
    UmlPackage result = namespace;

    StringTokenizer packageTokenizer;
    String relativeName;

    packageTokenizer = new StringTokenizer( qualifiedName,
                                            separator );
    while ( packageTokenizer.hasMoreTokens() ) {
      relativeName = packageTokenizer.nextToken();

      result = instantiatePackage( result, relativeName );
    } // result is the last package created - one with the qualified name
    return result;
  }

  
  /**
   * This method constructs an UML package with the name provided within the namespace / package
   * provided. There must always be a package provided (it might be the model itself)
   *
   * @param outerPackage is a non-null package where to cteare the new one
   * @param relativeName is the relative nale of the package within the outerPackage
   * @return the package created.
   */
  public static UmlPackage instantiatePackage(UmlPackage outerPackage, String relativeName) {
    UmlPackage result = (UmlPackage) WrapNamespace.locateRelativeModelElement( outerPackage, relativeName );

    if ( result == null ) { // no package found
      result = new WrapUmlPackage( outerPackage, relativeName ).getWrapped();
    }
    return result;
  }
  
  /**
   * @param stereotypeNamesArray a non-null array of stereotypes to filter classes in
   * @return a condition to retrieve only classes among all objects contained in the package, that 
   *         have a stereotype among the listed ones 
   */
  public static ModelElementCondition<ModelElement> getStereotypedElementConditionF(String[] stereotypeNamesArray) {
    return getStereotypedElementCondition( Factories.getFactory( Uml13WrapFactory.class ), stereotypeNamesArray);
  }  
  
  /**
   * @param stereotypeNamesArray a non-null array of stereotypes to filter classes in
   * @return a condition to retrieve only classes among all objects contained in the package, that 
   *         have a stereotype among the listed ones 
   */
  public ModelElementCondition<ModelElement> getStereotypedElementCondition(String[] stereotypeNamesArray) {
    return getStereotypedElementCondition( getFactory(), stereotypeNamesArray);
  }
  
  /**
   * @param stereotypeNamesArray a non-null array of stereotypes to filter classes in
   * @return a condition to retrieve only classes among all objects contained in the package, that 
   *         have a stereotype among the listed ones 
   */
  private static ModelElementCondition<ModelElement> getStereotypedElementCondition(Factory factory, String[] stereotypeNamesArray) {
    final List<String> stereotypeNames;
    
    stereotypeNames = Arrays.asList( stereotypeNamesArray );
        
    return new ModelElementCondition<ModelElement>( factory ) {
      public boolean eval(ModelElement modelElement) {
        boolean result;
        WrapModelElement wrapped;
        Stereotype stereotype;
        
        wrapped = (WrapModelElement) wrap( modelElement );
        stereotype = wrapped.getStereotype();
        
        result = stereotype != null
                 && stereotypeNames.contains( stereotype.getName() ); 
        return result;
      }
    };
  }
}