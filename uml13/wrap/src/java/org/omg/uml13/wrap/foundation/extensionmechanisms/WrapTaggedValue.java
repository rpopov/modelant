/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.extensionmechanisms;

import java.util.Collection;
import java.util.Iterator;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.uml13.metamodel.Convention;

import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.extensionmechanisms.TaggedValue;
import org.omg.uml13.wrap.Uml13WrapFactory;
import org.omg.uml13.wrap.base.foundation.extensionmechanisms.BaseWrapTaggedValue;

/**
 * This is a wrapper of org.omg.uml13.foundation.extensionmechanisms.TaggedValue that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapTaggedValue extends BaseWrapTaggedValue {

  public WrapTaggedValue(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapTaggedValue(RefPackage extent) {
    super( extent );
  }

  public WrapTaggedValue(ModelElement modelElement, String name, String value) {
    this( modelElement.refOutermostPackage() );

    setTag( name );
    setValue( value );
    setModelElement( modelElement );
    
    ((Uml13WrapFactory) getFactory()).createdTaggedValue( getWrapped() );
  }

  /**
   * This method instantiates a tagged value indicating the attribute as a primary key
   * @param assignTo
   * @return the tag created.
   * @see Convention#TAG_VALUE_PRIMARY_KEY
   */
  public static TaggedValue instantiateTagPrimaryKey(ModelElement assignTo) {
    return new WrapTaggedValue( assignTo, Convention.TAG_VALUE_PRIMARY_KEY, Convention.TAG_VALUE_PRIMARY_KEY_VALUE ).getWrapped();
  }

  /**
   * This method instantiates the tagged value with the documentation
   * @param assignTo
   * @param text documentation
   * @return the tagged value found or null if none
   */
  public static TaggedValue instantiateTagDocumentation(ModelElement assignTo, String text) {
    return new WrapTaggedValue( assignTo, Convention.TAG_VALUE_DOCUMENTATION, text ).getWrapped();
  }

  
  /**
   * This method instantiates the tagged value with the documentation
   * @param assignTo
   * @return the tagged value found or null if none
   */
  public static TaggedValue getTagDocumentation(ModelElement assignTo) {
    TaggedValue result;
    Collection<TaggedValue> boundValues;
    Factory factory;
    
    factory = Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class );
    boundValues = ((Uml13WrapFactory) factory).locateTaggedValues( assignTo );
    
    result = findTaggedValueByTag( boundValues, Convention.TAG_VALUE_DOCUMENTATION );
    
    return result;
  }
  
  /**
   * @param values
   * @param tag not null
   * @return possibly null TaggedValue that has the tag assigned
   */
  private static TaggedValue findTaggedValueByTag(Collection<TaggedValue> values, String tag) {
    TaggedValue result;
    Iterator<TaggedValue> valuesIterator;
    TaggedValue value;
    
    assert tag != null : "Expected a non-null name provided";
    
    result = null;
    valuesIterator = values.iterator();
    while ( result == null && valuesIterator.hasNext() ) {
      value = valuesIterator.next();
      
      if ( tag.equals( value.getTag() ) ) {
        result = value;
      }
    }
    
    return result;
  }
  
  /**
   * This method binds the model element provided with a tag that indicates it is persistent
   * @param assignTo
   */
  public static TaggedValue instantiateTagTransient(ModelElement assignTo) {
    return new WrapTaggedValue( assignTo,
                                Convention.TAG_VALUE_PERSISTENCE,
                                Convention.TAG_VALUE_TRANSIENT_VALUE ).getWrapped();
  }

  /**
   * This method locates the tagged value that indicates a model element is persistent.
   * @param assignTo
   * @return the tagged value found or null if none
   */
  public static TaggedValue instantiateTagHistory(ModelElement assignTo) {
    return new WrapTaggedValue( assignTo, Convention.TAG_VALUE_HISTORY, "" ).getWrapped();
  }

  /**
   * This method binds the model element provided with a tag that inidcates it is persistent
   * @param assignTo
   */
  public static TaggedValue instantiateTagPersistent(ModelElement assignTo) {
    return new WrapTaggedValue( assignTo,
                                Convention.TAG_VALUE_PERSISTENCE,
                                Convention.TAG_VALUE_PERSISTENCE_VALUE ).getWrapped();
  }


  /**
   * This method instantiates a tagged value indicating the number of decimal places in the values
   * of this attribute.
   * @param assignTo
   * @param decimalDigits
   * @return the tag created
   * @see Convention#TAG_VALUE_DATA_TYPE_PRECISION
   */
  public static TaggedValue instantiateTagFieldPrecision(ModelElement assignTo, int decimalDigits) {
    return new WrapTaggedValue( assignTo,
                                Convention.TAG_VALUE_DATA_TYPE_PRECISION,
                                "" + decimalDigits ).getWrapped();
  }


  /**
   * This method binds the attribute and a newly created comment for (field) size. For String
   * attributes this is the maximum size of their possible values.
   * @param assignTo
   * @param size
   */
  public static TaggedValue instantiateTagFieldSize(ModelElement assignTo, int size) {
    return new WrapTaggedValue( assignTo,
                                Convention.TAG_VALUE_DATA_LENGTH,
                                "" + size ).getWrapped();
  }
}