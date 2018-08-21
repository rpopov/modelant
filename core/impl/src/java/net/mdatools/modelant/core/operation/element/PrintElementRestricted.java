/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.element;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.model.GeneralizableElement;
import javax.jmi.reflect.RefAssociation;
import javax.jmi.reflect.RefAssociationLink;
import javax.jmi.reflect.RefBaseObject;
import javax.jmi.reflect.RefEnum;
import javax.jmi.reflect.RefFeatured;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Print model elements showing only their "important" attributes
 * and associations closer to the human language
 * @author Rusi Popov
 */
public class PrintElementRestricted implements Function<Object, String> {

  /**
   * This is a common logger
   */
  private static Logger LOGGER = Logger.getLogger( PrintElementRestricted.class.getPackage().getName() );

  /**
   * What is important to show in order to identify the object being printed
   */
  private final MatchingCriteria criteria;

  /**
   * A common prefix of every line of the string presentation of the printed object
   */
  private final String prefix;

  /**
   * @param prefix
   * @param criteria not null
   */
  public PrintElementRestricted(String prefix, MatchingCriteria criteria) {
    this.criteria = criteria;

    if ( prefix == null ) {
      this.prefix = "";
    } else {
      this.prefix = prefix;
    }
  }

  /**
   * @param argument The object to start printing from
   * @return non-null string representation of the
   */
  public String execute(Object argument) throws RuntimeException, IllegalArgumentException {
    StringBuilder result = new StringBuilder(2048);

    append( result, argument, prefix );
    return result.toString();
  }

  private void append(StringBuilder result, Object object, String prefix) {
    if ( object instanceof RefFeatured ) { // note: RefStruct is not checked
      append( result, (RefBaseObject) object, prefix);

    } else if ( object instanceof Collection ) {
      append( result, (Collection) object, prefix);
    } else {
      result.append( object );
    }
  }

  /**
   * This method dumps the object into the output string buffer indenting the nested elements
   * with the prefix provided.
   * @param result
   * @param forObject
   * @param prefix
   */
  private void append(StringBuilder result, RefBaseObject forObject, String prefix) {
    Iterator<String> namesIterator;
    String name;
    List values;
    List associated;
    String nestedPrefix;
    List<String> asociationNames;
    List<String> attributeNames;
    String nested2Prefix;

    result.append( ((GeneralizableElement) forObject.refMetaObject()).getName() );

    if ( forObject instanceof RefObject ) {
      attributeNames = criteria.getAttributes( (RefObject) forObject );
      asociationNames = criteria.getAssociations( (RefObject) forObject );

      if ( attributeNames.size() + asociationNames.size() == 0) {
        // print nothing
      } else {
        nestedPrefix = prefix+"  ";
        nested2Prefix = nestedPrefix+"  ";

        if ( attributeNames.size() + asociationNames.size() == 1
             || attributeNames.size() == 1
                && asociationNames.size() == 1) { // there is chance for compact printing

          if ( attributeNames.size() == 1 ) { // a single attribute - print its value only
            values = Navigator.collectValues( (RefObject) forObject,
                                              attributeNames.get( 0 ),
                                              LOGGER.isLoggable( Level.FINE ));

            if ( values.size() > 0 ) { // a non-empty list of non-empty values
              append( result, values, nestedPrefix );
            }
          }

          if ( asociationNames.size() == 1 ) { // a single association
            // use the value to select the printing format

            name = asociationNames.get( 0 );

            associated = Navigator.collectValues( (RefObject) forObject, name, false );

            if ( associated.size() == 1 ) { // a single value - use compact print
              result.append( "/" );
              append(result, (RefObject) associated.get( 0 ), prefix );

            } else if ( associated.size() > 1 ) { // multiple values, use regular print
              appendAssociated( result, name, associated, nestedPrefix, nested2Prefix );
            }
          }
        } else { // there are many attributes and/or associations, use regular (wide) printing

          // print the non-empty attributes
          namesIterator = attributeNames.iterator();
          while ( namesIterator.hasNext() ) {
            name = namesIterator.next();

            try {
              values = Navigator.collectValues( (RefObject) forObject, name, false );

              if ( values.size() > 0 ) { // a non-empty list of non-empty values
                result.append("\n")
                      .append( nestedPrefix )
                      .append( name )
                      .append( "=");
                append( result, values, prefix );
              }
            } catch (Exception ex) {
              LOGGER.log( Level.FINE,
                          "Model element: {0} does not support attribute: {1}",
                          new Object[]{new PrintModelElement(nestedPrefix).execute( forObject ), name});
            }
          }

          // print the non-empty associations
          namesIterator = asociationNames.iterator();
          while ( namesIterator.hasNext() ) {
            name = namesIterator.next();

            try {
              associated = Navigator.collectValues( (RefObject) forObject, name, false );

              appendAssociated( result, name, associated, nestedPrefix, nested2Prefix );
            } catch (Exception ex) {
              LOGGER.log( Level.INFO,
                          "Accessing associated {1} on model element {0} caused {2}",
                          new Object[]{new PrintModelElement(nestedPrefix).execute( forObject ), name, ex.getMessage()});
            }
          }
        }
      }
    }
  }

  /**
   * @param result
   * @param name
   * @param associated
   * @param nestedPrefix
   * @param nested2Prefix
   */
  private void appendAssociated(StringBuilder result, String name, List associated, String nestedPrefix,
                                String nested2Prefix) {
    Object node;
    Iterator associatedIterator;
    if ( associated.size() > 0 ) {
      result.append( "\n" )
            .append( nestedPrefix )
            .append( name )
            .append( "=");

      // result.append("{");
      if ( associated.size() == 1 ) {
        append(result, (RefObject) associated.get( 0 ), nestedPrefix );
      } else {
        associatedIterator = associated.iterator();
        while (associatedIterator.hasNext()) {
          node = associatedIterator.next();

          result.append("\n").append( nested2Prefix );
          append(result, (RefObject) node, nested2Prefix );
        }
      }
    }
  }

  /**
   * This method requires an M1 object object and a StringBuilder where to describe the object.
   *
   * @param result is the buffer where to add the obect's description
   * @param collection is the M1 object to investigate
   */
  private void append(StringBuilder result, Collection collection, String prefix) {
    Object value;
    Iterator valuesIterator;
    boolean onSeparateLine;
    String nested;

    nested = prefix+"  ";
    result.append( " " );

    valuesIterator = collection.iterator();
    if ( !valuesIterator.hasNext() ) { // an empty list
      result.append( "{}" );

    } else {
      value = valuesIterator.next();

      onSeparateLine = shouldPrintOnSeparateLine( value );

      if ( !valuesIterator.hasNext() ) { // a single value list - print it without {}
        appendSingleValue( result, value, onSeparateLine, nested );

      } else { // a list of size > 1
        result.append( "{" );

        appendSingleValue( result, value, onSeparateLine, nested );
        while ( valuesIterator.hasNext() ) {
          value = valuesIterator.next();

          result.append(", ");
          onSeparateLine |= shouldPrintOnSeparateLine( value );

          appendSingleValue( result, value, onSeparateLine, nested );
        }
        if ( onSeparateLine ) { // model elements were printed
          result.append("\n")
                .append( prefix )
                .append( " ");
        }
        result.append( "}" );
      }
    }
  }

  /**
   * @return true when the value is a model element, so it should be printed on separate line(s)
   */
  private boolean shouldPrintOnSeparateLine(Object value) {
    return value instanceof RefBaseObject
           || value instanceof RefStruct
           || value instanceof RefEnum
           || value instanceof RefAssociation
           || value instanceof RefAssociationLink;
  }

  /**
   * @param result
   * @param value
   * @param isModelElement is true when value != null && value is a model element
   * @param nested
   */
  private void appendSingleValue(StringBuilder result, Object value, boolean isModelElement, String nested) {
    if ( isModelElement ) { // print the model elements with the prefix
      result.append( "\n" )
            .append( nested );
      append( result, value, nested);

    } else { // a "primitive" value
//      result.append( "\"" );
      append( result, value, nested);
//      result.append( "\"" );
    }
  }
}