/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.core;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.foundation.core.DataType;
import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.modelmanagement.Model;
import org.omg.uml13.wrap.Uml13WrapFactory;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapDataType;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.DataType that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapDataType extends BaseWrapDataType {

  public WrapDataType(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapDataType(RefPackage extent) {
    super( extent );
  }

  /**
   * Instantiates a public data type with the name provided
   * @param model
   * @param name
   */
  public WrapDataType(Model model, String name) {
    this( model.refOutermostPackage() );

    setName( name );
    setNamespace( model );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
  }

  /**
   * @return type name of the wrapped DataType formatted and translated
   */
  public String getLogicalName() {
    String result;
    int index;

    result = getName();

    // Rose sometimes puts additional comments in the data type name. Stored in ()
    index = result.indexOf( "(" );
    if ( index >= 0 ) { // there are comments - strip them
      result = result.substring( 0, index ).trim();
    }
    return result;
  }

  /**
   * @return type name of the wrapped DataType formatted and translated
   */
  public String formatQualifiedName() {
    return getLogicalName();
  }

  /**
   * This method locates a data type as of the rules for data types creation established in
   * instantiateDataType() method.
   * @param model
   * @param dataTypeName is the name of the data type (or qualified name of a class represented as a data type)
   * @return the data type found or null otherwise.
   * @see #instantiateDataType(String, Model)
   */
  public static DataType locateDataType(Model model, String dataTypeName) {
    return (DataType) WrapNamespace.locateRelativeModelElement( model, dataTypeName );
  }

  /**
   * This method identifies the data type with the code, size and precision provided. If it does not
   * exist, a new datatype is created. Returns the data type model element found/created.
   *
   * @param dataTypeName is the non-null type name
   * @param model TODO
   * @return the data type identified or class identified
   */
  public static Classifier instantiateDataType(String dataTypeName, Model model) {
    Classifier result = (Classifier) WrapNamespace.locateRelativeModelElement( model, dataTypeName );

    if ( result == null ) { // data type not found
      result = new WrapDataType( model, dataTypeName ).getWrapped();
    }
    return result;
  }
  
  /**
   * @param qualifiedDataTypeName
   * @return a condition to recognize all classifiers that are subclasses of the data type provided 
   */
  public static ModelElementCondition<ModelElement> getSubclassesCondition(final String qualifiedDataTypeName) {
    ModelElementCondition<ModelElement> result;
    
    result = new ModelElementCondition<ModelElement>(Factories.getFactory( Uml13WrapFactory.class )) {
      public boolean eval(ModelElement modelElement) {
        boolean result;
        Classifier conditionModelClass;
        WrapClassifier wrapped;
        Model model;

        result = modelElement instanceof Classifier;
        if ( result ) {
          wrapped = (WrapClassifier) wrap(modelElement);
          model = wrapped.getModel();
          conditionModelClass = WrapDataType.locateDataType( model, qualifiedDataTypeName );
      
          result = wrapped.isSubclassOf( conditionModelClass );
        }        
        return result;
      }
    };
    return result; 
  }
  
  /**
   * @return a condition to recognize all classifiers that are subclasses of this data type 
   */
  public ModelElementCondition<ModelElement> getSubclassesCondition() {
    ModelElementCondition<ModelElement> result;
    
    result = new ModelElementCondition<ModelElement>(Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class )) {
      public boolean eval(ModelElement modelElement) {
        boolean result;
        WrapClassifier wrapped;
        
        result = modelElement instanceof Classifier;
        if ( result ) {
          wrapped = (WrapClassifier) wrap(modelElement);    
          result = wrapped.isSubclassOf( WrapDataType.this );
        }
        return result;
      }
    };
    return result; 
  }
}