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

import java.util.Iterator;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.util.FormatHelper;

import org.omg.uml13.foundation.core.AssociationEnd;
import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.foundation.core.Namespace;
import org.omg.uml13.foundation.core.UmlAssociation;
import org.omg.uml13.foundation.datatypes.AggregationKindEnum;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapUmlAssociation;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.UmlAssociation that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapUmlAssociation extends BaseWrapUmlAssociation {

  public WrapUmlAssociation(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapUmlAssociation(RefPackage extent) {
    super( extent );
  }

  /**
   * Creates a public association with the name provided
   * @param extent
   * @param namespace TODO
   * @param name
   */
  public WrapUmlAssociation(RefPackage extent, Namespace namespace, String name) {
    this( extent );
    
    setNamespace( namespace );
    setName( name );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
  }

  /**
   * This method establishes an association from <b>thisClass </b> class to <b>otherClass </b>
   * class. The association is in the namespace provided. The multiplicity of the other class is
   * provided in otherEndUpper parameter.
   *
   * @param thisClass is the client (this end) of the association
   * @param thisRole the name of the role thisClass participates in in the association
   * @param isComposition if the association is a composotion at thisClass' side
   * @param isThisNavigable the navigability at the side of thisClass
   * @param thisEndUpper the upper bound of the multiplicity of at side of this class. To indicate
   *          "*", provide MdrUml13Manager.UNLIMITED_UPPER_MULTIPLICITY
   * @param otherClass is the provider (the other end) of the association
   * @param otherRole is the role name of the other end class
   * @param otherEndUpper is the upper bound of the multiplicity of the other end. The association
   *          created is 0..otherEndUpper. To indicate "*", provide
   *          MdrUml13Manager.UNLIMITED_UPPER_MULTIPLICITY
   * @param namespace where to create the association
   * @param documentation of this association
   * @return the non-null association created
   */
  public static UmlAssociation instantiateAssociation(Classifier thisClass,
                                                       String thisRole,
                                                       boolean isComposition,
                                                       boolean isThisNavigable,
                                                       int thisEndUpper,
                                                       Classifier otherClass,
                                                       String otherRole,
                                                       int otherEndUpper,
                                                       Namespace namespace,
                                                       String documentation) {
    UmlAssociation result;
    AssociationEnd thisEnd;

    result = new WrapUmlAssociation( thisClass.refOutermostPackage(), namespace, "" ).getWrapped();

    // this end (the class with the associative attribute)
    thisEnd = new WrapAssociationEnd( result,
                                      thisClass,
                                      thisRole,
                                      isThisNavigable,
                                      thisEndUpper ).getWrapped();

    if ( isComposition ) {
      thisEnd.setAggregation( AggregationKindEnum.AK_COMPOSITE );
    }

    // other end - the referenced class
    new WrapAssociationEnd( result,
                            otherClass,
                            otherRole,
                            true,
                            otherEndUpper ).getWrapped();
    return result;
  }


  /**
   * This method formats the name of the attribute provided as a database column name.
   * If explicit overriding name is provided as a property/tagged value of the classifier,
   * then its contents is used to construct the table name
   * @param maxLenght maximum name length to construct
   * @return the name of the DB table that represents this assocaition as a cross table
   */
  public String formatCrossTableName(int maxLenght) {
    String result;
    WrapAssociationEnd thisEnd;
    WrapAssociationEnd otherEnd;
    Iterator assocEndIterator;

    WrapUmlClass thisRootClass;
    WrapUmlClass otherRootClass;

    assocEndIterator = getConnection().iterator();

    thisEnd = (WrapAssociationEnd) wrap( assocEndIterator.next());
    thisRootClass = (WrapUmlClass) wrap( thisEnd.getRootSuperclass() );

    otherEnd= (WrapAssociationEnd) wrap( assocEndIterator.next());
    otherRootClass = (WrapUmlClass) wrap( otherEnd.getRootSuperclass() );

    result = "X_"+FormatHelper.formatAbbreviatedConstantName( thisRootClass.getLogicalName(), maxLenght/2 - 3 )
             +"_"+FormatHelper.formatAbbreviatedConstantName( otherRootClass.getLogicalName(), maxLenght/2 - 3 ); // up to 2 digits for unique index

    // store the result table name and avoid its re-generation
    result = getUniqueName( result );

    return result.toUpperCase();
  }  

  /**
   * This method removes and association, together with its association ends, multiplicities, and tagged values
   */
  public void remove() {
    AssociationEnd thisEnd;
    Iterator associationEndIterator;

    // remove the association ends
    associationEndIterator = getConnection().iterator();
    while ( associationEndIterator.hasNext() ) {
      thisEnd = (AssociationEnd) associationEndIterator.next();
      associationEndIterator.remove();
      ((WrapAssociationEnd) Factories.wrap( thisEnd )).remove( );
    }
    removeTaggedValues();
    refDelete();
  }
}