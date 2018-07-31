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
package org.omg.uml13.wrap.foundation.core;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.uml13.metamodel.Convention;
import net.mdatools.modelant.util.FormatHelper;

import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.foundation.datatypes.Expression;
import org.omg.uml13.foundation.datatypes.ScopeKindEnum;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapAttribute;
import org.omg.uml13.wrap.foundation.datatypes.WrapMultiplicity;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Attribute that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapAttribute extends BaseWrapAttribute {
  public WrapAttribute(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapAttribute(RefPackage extent) {
    super( extent );
  }

  public WrapAttribute(RefPackage extent, String name) {
    this( extent );
    setName( name );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
    setOwnerScope( ScopeKindEnum.SK_INSTANCE );
  }

  
  /**
   * formatExpression formats an expression as of exprClass class/type. If no expression body
   * specified this method returns an empty string. Note that it is possible to have an Expression
   * instance with empty value, even the expression type is not String. If constant initializer is
   * detected, then this method prefixes the constant with its class cname.
   *
   * @return expression formatted as of Java syntax
   */
  public final String formatExpression() {
    String result = "";
    Expression initialValueExpr;
    String expressionClassName;
    String expressionText;

    initialValueExpr = getInitialValue();
    if (initialValueExpr != null) {
      expressionText = initialValueExpr.getBody();

      if ( expressionText != null && !expressionText.equals( "" ) ) { // there is an explicit initializer
        // formatting it
        expressionText = expressionText.trim();

        if ( expressionText.equals( FormatHelper.formatConstantName( expressionText ) ) ) { // the initializer is a constant
          // prefix it with the class name
          expressionClassName = ((WrapClassifier) wrap( getOwner())).getLogicalName();

          result = expressionClassName + "." + expressionText;

        } else { // this is not a constant initializer - provide it as it is
          result = expressionText;
        }
      }
    }
    return result;
  }

  /**
   * Identifies the SQL type to represent the type from the model by its name, using the key to type 
   * mappings defined in the .properties file.
   * <pre>
   * Format:
   *
   * SQL.&lt;model type name&gt; = &lt;SQL column type&gt;
   * </pre>
   * @param modelTypeName is the actual non-null name of a model type
   * @return the mapped SQL type for it
   * @throws IllegalArgumentException when mapped type not found
   */
  public String getSqlType(String modelTypeName) throws IllegalArgumentException{
    String result;
    String key;

    key = "SQL."+modelTypeName;

    result = getProperty( key );

    if ( result == key ) { // no translation found
      throw new IllegalArgumentException("Cound not find SQL type name maped to "+key
                                          +". Provide this mapping in the context");
    }
    return result;
  }

  

  /**
   * This method retrieves the size (as a string) expression defining the DB column size for this
   * model type representing the type of this attribute (as a column). Convention:
   * <pre>
   * Format:
   *
   * LENGTH.&lt;java class name&gt; = &lt;SQL column size&gt;
   *
   * or the general form stating that field name always is translated to:
   *
   * LENGTH.&lt;model type name&gt; = &lt;SQL column size&gt;
   * </pre>
   * @param typeName
   * @return the string representation of the field size or null if not defined
   */
  public String getSqlColumnSize(String typeName) {
    String result;
    String key;
    
    key = "LENGTH." + typeName;

    result = getProperty( key );
    return result;
  } 
    
  /**
   * @return the actual class/type name of this attribute, as it is provided in the model.
   *         It can be null.
   */
  public final String getClassName() {
    String result;
    Classifier type;

    type = getType();
    if ( type != null ) {
      result = ((WrapModelElement) wrap(type)).getLogicalName();
    } else {
      result = null;
    }
    return result;
  }

  /**
   * This method maps the original [primitive] type name provided, used for
   * persistence generation.
   * @return null, when the original type is NOT primitive, otherwise - the
   *         non-null wrapper class name
   */
  public final String getWrapperClass() {
    String result;

    result = getFactory().getProperty( Convention.PREFIX_MAP_PRIMITIVE_TYPE_TO_WRAPPER_CLASS+getClassName() );
    
    return result;
  }


  /**
   * This method formats the name of the attribute provided as a database column name. If explicit
   * overriding name is provided as a translation in the properties fules, then its contents is
   * used to construct the table name
   *
   * @param maxLenght maximum name length to construct
   * @return the name of this attribute formatted as a DB column name, not longer than maxLenght
   *         chars
   */
  public final String formatSqlColumnName(int maxLenght) {
    String result;
    String nameToFormat;

    nameToFormat = getName();
    
    result = FormatHelper.formatSubstring( FormatHelper.formatConstantName( translate( nameToFormat ) ), maxLenght );
    return result;
  }
  
  /**
   * This method retrieves the size (as a string) expression defining the DB column size for this
   * attribute/DB column. Convention:
   * <ul>
   * <li>if the SQL TYPE is provided in the <b>vegas.db.column.type</b> tagged value, then the size
   * is taken from <b>vegas.db.column.size</b> tagged value
   * <li>by default, using the key to size mappings defined in the context.
   *
   * <pre>
   * Format:
   *
   * LENGTH.&lt;java class&gt; = &lt;SQL column size&gt;
   *
   * or the general form stating that field name always is translated to:
   *
   * LENGTH.&lt;model type name&gt; = &lt;SQL column size&gt;
   * </pre>
   *
   * </ul>
   *
   * @return the size of the database column for this attribute. When null returned, then no
   *         specific size should be used.
   */
  public final String formatSqlColumnSize() {
    String result;
    WrapClassifier wrapType;

    wrapType = (WrapClassifier) wrap( getType() );
    result = getSqlColumnSize( wrapType.getLogicalName() );
    return result;
  }

  /**
   * This method retrieves the SQL type (as a string) of the DB column for this attribute/DB column.
   * Convention:
   * <ul>
   * <li>the ENCRYPTED columns are always stored in STRINGs
   * <li>the type might be provided in <b>vegas.db.column.type</b> tagged value
   * <li>by default, using the key to type mappings defined in the context.
   *
   * <pre>
   * Format:
   *
   * SQL.&lt;model type name&gt; = &lt;SQL column type&gt;
   * </pre>
   *
   * </ul>
   *
   * @return the non-null SQL type that corresponds to the type/class of this attribute when
   *         creating a corresponding database column.
   * @throws IllegalArgumentException when no type identified
   */
  public final String formatSqlColumnType() {
    String result;
    WrapClassifier wrapType;

    wrapType = (WrapClassifier) wrap( getType() );
    result = getSqlType( wrapType.getLogicalName() );
    
    return result;
  }  
  
  
  /**
   * This method removes the attribute from the model together with the documentation associated
   */
  public void remove() {
    Expression initExpression = getInitialValue();

    // remove the initialization expression
    if ( initExpression != null ) {
      initExpression.refDelete();
    }
    removeDepenencies( );
    removeTaggedValues( );
    ((WrapMultiplicity) Factories.wrap( getMultiplicity() ) ).remove();

    getOwner().getFeature().remove( getWrapped() );
    refDelete();
  }
}