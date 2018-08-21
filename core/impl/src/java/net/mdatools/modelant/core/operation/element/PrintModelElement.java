/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jmi.model.ModelElement;
import javax.jmi.model.MofClass;
import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefAssociation;
import javax.jmi.reflect.RefAssociationLink;
import javax.jmi.reflect.RefBaseObject;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefEnum;
import javax.jmi.reflect.RefFeatured;
import javax.jmi.reflect.RefPackage;
import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.Function;

/**
 * Print the actual attributes and values of model elements using only the
 * reflection interfaces of JMI - it is independent of the actual metamodel being processed. Its
 * toString() method handles the proper result formatting.
 * <pre>
 * Usage:
 *
 * System.err.println( "model element ="+ new ModelElementPrinter().execute( modelElement ) );
 * </pre>
 * @author Rusi Popov
 */
public class PrintModelElement implements Function<Object, String>{
  /**
   * The wrapped model element
   */
  private final String prefix;

  public PrintModelElement() {
    this("");
  }

  /**
   * The only constructor of ModelElementPrinter is to wrap up a non-null model element to print.
   * @param prefix to all lines
   */
  public PrintModelElement(String prefix) {
    if ( prefix != null ) {
      this.prefix = prefix;
    } else {
      this.prefix = "";
    }
  }

  /**
   * Prints the elements nested according to the prefix
   * @param object the model element to print
   * @return the string representation of this, with the prefix added to each line
   */
  public String execute(Object object) throws RuntimeException, IllegalArgumentException {
    StringBuilder result = new StringBuilder( 2048 );

    toString( object, result, prefix, new HashSet() );

    return result.toString();
  }

  /**
   * @param prefix
   * @see java.lang.Object#toString()
   */
  private void toString(Object wrapped, StringBuilder result, String prefix, Set visited) {
    if ( !visited.contains( wrapped ) ) {
      // Note that only the model elements are checked if they were visited

      if ( wrapped instanceof JmiException ) {
        append( result, prefix, (JmiException) wrapped, visited );

      } else if ( wrapped instanceof RefClass ) {
        visited.add( wrapped );
        append( result, (RefClass) wrapped, visited );

      } else if ( wrapped instanceof RefPackage ) {
        visited.add( wrapped );
        append( result, (RefPackage) wrapped, visited );

      } else if ( wrapped instanceof RefAssociation ) {
        visited.add( wrapped );
        append( result, prefix, (RefAssociation) wrapped, visited );

      } else if ( wrapped instanceof RefFeatured ) {
        visited.add( wrapped );
        append( result, prefix, (RefFeatured) wrapped, visited );

      } else if ( wrapped instanceof RefStruct ) {
        visited.add( wrapped );
        append( result, prefix, (RefStruct) wrapped, visited );

      } else if ( wrapped instanceof RefAssociationLink ) {
        visited.add( wrapped );
        append( result, prefix, (RefAssociationLink) wrapped, visited );

      } else if ( wrapped instanceof RefEnum ) {
        visited.add( wrapped );
        append( result, (RefEnum) wrapped, visited );

      } else if ( wrapped instanceof Collection ) {
        append( result, prefix, (Collection) wrapped, visited );

//      } else if ( wrapped != null ) {
//        result.append( wrapped.getClass() ).append( "  " );
//        result.append( wrapped );
//
      } else {
        result.append( wrapped );
      }
    } else {
      result.append("<visited>");
    }
  }

  /**
   * @param result
   * @param exception
   */
  private void append(StringBuilder result, String prefix, JmiException exception, Set visited) {
    result.append( exception.getClass() );
    result.append( " {\n" );
    result.append( prefix );
    result.append( "  message = " );
    result.append( exception.getMessage() );
    result.append( "\n" );

    result.append( prefix );
    result.append( "  elementInError = " );
    toString(exception.getElementInError(), result, prefix+"  ", visited);
    result.append( "\n" );

    result.append( prefix );
    result.append( "  objectInError = " );
    toString( exception.getObjectInError(), result, prefix+"  ", visited );
    result.append( "\n" );

    result.append( prefix );
    result.append( "}" );
  }


  /**
   * This method requires an M1 object object and a StringBuilder where to describe the object.
   *
   * @param result is the buffer where to add the obect's description
   * @param object is the M1 object to investigate
   */
  private void append(StringBuilder result, String prefix, RefFeatured object, Set visited) {
    MofClass metaObject;
    List allMetaObjects;
    ModelElement contents;
    Object value;
    Iterator contentsIterator;
    Iterator metaObjectsIterator;
    String prefixNested;

    metaObject = (MofClass) object.refMetaObject();
    prefixNested = prefix+"  ";

    result.append( metaObject.getName() )
          .append( " {\n" );

    // find the description of the class of this object and their superclasses
    // Each element of allMetaObjects is an M2 object of MOF classes that describes
    // the object's class or its superclass

    allMetaObjects = new ArrayList();

    allMetaObjects.add( metaObject );
    allMetaObjects.addAll( metaObject.allSupertypes() );

    // explore all features (attributes) of all (super)classes print their values
    metaObjectsIterator = allMetaObjects.iterator();
    while ( metaObjectsIterator.hasNext() ) {
      metaObject = (MofClass) metaObjectsIterator.next();

      contentsIterator = metaObject.getContents().iterator();
      while ( contentsIterator.hasNext() ) {
        contents = (ModelElement) contentsIterator.next();

        if ( contents instanceof javax.jmi.model.Attribute ) { // Handling Structs causes infinite loop, so StructuralFeatures are not considered
          // print the attribute's name
          result.append(prefixNested).append( contents.getName() );
          try {
            value = object.refGetValue( contents );

            // print the defaultAttribute value
            result.append( "=" );
            toString( value, result, prefixNested, visited );

          } catch (Exception e) { // this should not happen
            result.append( " exception caused: " + e.getClass().getName() +" : "+ e.getMessage() );
          }
          result.append( ",\n" );
        }
      }
    }
    result.append( prefix )
          .append( "}" );
  }


  /**
   * This method requires an M1 object object and a StringBuilder where to describe the object.
   *
   * @param result is the buffer where to add the obect's description
   * @param object is the M1 object to investigate
   */
  private void append(StringBuilder result, String prefix, RefStruct object, Set visited) {
    String name;
    Object value;
    Iterator namesIterator;
    List typeNames;
    String prefixNested;

    prefixNested = prefix+"  ";

    typeNames = object.refTypeName(); // size > 0
    result.append( typeNames.get( typeNames.size()-1 ) )
          .append( " {\n" );

    namesIterator = object.refFieldNames().iterator();
    while ( namesIterator.hasNext() ) {
      name = (String) namesIterator.next();

      // print the attribute's name
      result.append(prefixNested).append( name );
      try {
        // get the value of the defaultAttribute
        value = object.refGetValue( name );

        // print the defaultAttribute value
        result.append( "=" );
        toString( value, result, prefixNested, visited );

      } catch (Exception e) { // this should not happen
        result.append( " exception caused: " + e.getClass().getName() + " : " + e.getMessage() );
      }
      result.append( ",\n" );
    }
    result.append( prefix )
          .append( "}" );
  }


  /**
   * This method requires an M1 object object and a StringBuilder where to describe the object.
   *
   * @param result is the buffer where to add the obect's description
   * @param object is the M1 object to investigate
   */
  private void append(StringBuilder result, RefPackage object, Set visited) {
    result.append( "RefPackage ")
          .append( object.refMetaObject().refGetValue( "name" ) );
  }

  /**
   * @param result is the buffer where to add the obect's description
   * @param object non-null enum to print
   */
  private void append(StringBuilder result, RefEnum object, Set visited) {
    result.append( "RefEnum ")
          .append( object.refTypeName() )
          .append( " " )
          .append( object.toString() );
  }


  /**
   * This method requires an M1 object object and a StringBuilder where to describe the object.
   *
   * @param result is the buffer where to add the obect's description
   * @param object is the M1 object to investigate
   */
  private void append(StringBuilder result, RefClass object, Set visited) {
    result.append( "RefClass ")
          .append( object.refMetaObject().refGetValue( "name" ) );
  }


  /**
   * This method requires an M1 object object and a StringBuilder where to describe the object.
   *
   * @param result is the buffer where to add the obect's description
   * @param object is the M1 object/association to investigate
   */
  private void append(StringBuilder result, String prefix, RefAssociation object, Set visited) {
    Iterator<RefAssociationLink> linksIterator;
    RefAssociationLink link;

    result.append( "RefAssociation ")
          .append( object.refMetaObject().refGetValue( "name" ) );
    result.append( " {\n");

//    linksIterator = object.refAllLinks().iterator();
//    while ( linksIterator.hasNext() ) {
//      link = linksIterator.next();
//
//      result.append( prefix );
//      toString( link, result, prefix+"  ", visited );
//      result.append( "\n");
//    }

    result.append( prefix )
          .append( "}");
  }


  /**
   * This method requires an M1 object object and a StringBuilder where to describe the object.
   *
   * @param result is the buffer where to add the obect's description
   * @param object is the M1 object to investigate
   */
  private void append(StringBuilder result, String prefix, RefAssociationLink object, Set visited) {
    result.append( "RefAssociationLink ")
          .append( "{\n")
          .append( prefix )
          .append( "  firstEnd=");
           toString( object.refFirstEnd(), result, prefix+"  ", visited);
           result.append("\n")
          .append( prefix )
          .append( "  secondEnd=");
           toString( object.refSecondEnd(), result, prefix+"  ", visited);
           result.append("\n")
          .append( prefix )
          .append("}");
  }

  /**
   * This method requires an M1 object object and a StringBuilder where to describe the object.
   *
   * @param result is the buffer where to add the obect's description
   * @param collection is the M1 object to investigate
   */
  private void append(StringBuilder result, String prefix, Collection collection, Set visited) {
    Object value;
    Iterator valuesIterator;
    boolean onSeparateLine;

    result.append( "{" );

    onSeparateLine = false;

    valuesIterator = collection.iterator();
    while ( valuesIterator.hasNext() ) {
      value = valuesIterator.next();

      onSeparateLine |= value instanceof RefBaseObject
                        || value instanceof RefStruct
                        || value instanceof RefEnum
                        || value instanceof RefAssociation
                        || value instanceof RefAssociationLink;

      if ( onSeparateLine ) { // print the model elements with the prefix
        result.append( "\n" );
        result.append( prefix+"  " );
      }
      toString( value, result, prefix+"  ", visited );
      if ( valuesIterator.hasNext() ) {
        result.append(",");
      }
    }
    if ( onSeparateLine ) { // model elements were printed
      result.append("\n")
            .append( prefix )
            .append( "}" );

    } else { // a regular list
      result.append( "}" );
    }
  }

  /**
   * @param wrapped
   * @return a non-null object with its toString() printing the wrapped element
   */
  public Object toPrint(Object wrapped ) {
    return new ToString(wrapped);
  }

  /**
   * Use the parent printer to form the toString() contents
   */
  private class ToString {
    private final Object wrapped;

    public ToString(Object wrapped) {
      this.wrapped = wrapped;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
      return execute(wrapped);
    }
  }
}