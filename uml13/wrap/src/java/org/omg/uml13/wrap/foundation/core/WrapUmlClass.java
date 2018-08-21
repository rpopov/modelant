/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.util.FormatHelper;

import org.apache.tools.ant.BuildException;
import org.omg.uml13.foundation.core.AssociationEnd;
import org.omg.uml13.foundation.core.Namespace;
import org.omg.uml13.foundation.core.UmlClass;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.modelmanagement.Model;
import org.omg.uml13.modelmanagement.UmlPackage;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapUmlClass;
import org.omg.uml13.wrap.modelmanagement.WrapUmlPackage;
/**
 * This is a wrapper of org.omg.uml13.foundation.core.UmlClass that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapUmlClass extends BaseWrapUmlClass {

  /**
   * Internal cache of this' logical name
   */
  private String logicalName;

  public WrapUmlClass(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapUmlClass(RefPackage extent) {
    super( extent );
  }

  /**
   * INstantiates a public class with the simple name provided in the namespace
   * @param namespace
   * @param className
   */
  public WrapUmlClass(Namespace namespace, String className) {
    this( namespace.refOutermostPackage() );

    setName( className );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
    setNamespace( namespace );
  }


  /**
   * This method checks if thisClass has associations with other classes
   * @return true if thisClass has at least one supported association to a domain class
   */
  public boolean hasAssociations() {
    boolean result = false;
    AssociationEnd thisAssocEnd;
    Iterator assocEndIterator;

    assocEndIterator = getAssociationEnds( getAssocisationToDomainCondition() ).iterator();
    while ( assocEndIterator.hasNext() && !result ) {
      thisAssocEnd = (AssociationEnd) assocEndIterator.next();
      result = ((WrapAssociationEnd) wrap( thisAssocEnd)).isAssociationSupported();
    }
    return result;
  }

  /**
   * This method checks if thisClass has associated collections at the other end of its associations ends.
   * @return true if thisClass has at least one association, which other end is collection (X-TO-MANY association)
   */
  public boolean hasAssociatedCollections() {
    boolean result = false;
    AssociationEnd end;

    WrapAssociationEnd otherEnd;
    WrapAssociationEnd thisEnd;

    Iterator assocEndIterator;

    assocEndIterator = getAssociationEnds( getAssocisationToDomainCondition() ).iterator();
    while ( assocEndIterator.hasNext() && !result ) {
      end = (AssociationEnd) assocEndIterator.next();

      thisEnd = (WrapAssociationEnd) wrap( end);
      otherEnd = (WrapAssociationEnd) wrap( thisEnd.getOtherAssociationEnd());

      // Check for association X-TO-MANY instance at the other end
      result = thisEnd.isAssociationSupported()
               && ! otherEnd.isSingular();
    }
    return result;
  }

  /**
   * @return the translated simple class name of the wrapped object
   */
  public String getLogicalName() {
    if ( logicalName == null ) {
      logicalName = FormatHelper.formatClassName( getName() );
    }
    return logicalName;
  }

  /**
   * This method removes the class together with the documentation,
   * fields, operations, associations
   */
  public void remove() {
    AssociationEnd thisEnd;
    Object featureToRemove;
    Iterator associationIterator;
    Iterator associationEndIterator;
    Iterator featureIterator;

    Set associations = new HashSet();

    // remove the associations of this class - first collect them in a set to avoid duplicates
    associationEndIterator = getFactory().locateAssociationEndsOf(getWrapped()).iterator();
    while ( associationEndIterator.hasNext() ) {
      thisEnd = (AssociationEnd) associationEndIterator.next();
      associations.add( thisEnd.getAssociation() );
    }
    associationIterator = associations.iterator();
    while ( associationIterator.hasNext() ) {
      ((WrapUmlAssociation) Factories.wrap( associationIterator.next() )).remove();
    }


    // remove the features, avoiding hanging references
    featureIterator = getOwnedElement().iterator();
    while ( featureIterator.hasNext() ) {
      featureToRemove = featureIterator.next();
      featureIterator.remove();

      ((WrapAttribute) Factories.wrap(featureToRemove)).remove( );
    }

    // remove the methods, that have not been removed together with operations
    featureIterator = getOwnedElement().iterator();
    while ( featureIterator.hasNext() ) {
      featureToRemove = featureIterator.next();
      featureIterator.remove();
      ((WrapMethod) Factories.wrap( featureToRemove )).remove();
    }
    removeTaggedValues();

    // remove this association itself
    refDelete();
  }

  /**
   * This method instantiates a new model class using its qualified name
   * @param qualifiedName where the package names are separated with '.'
   * @param model TODO
   * @return the mdoel class created
   */
  public static UmlClass instantiateClass(String qualifiedName, Model model) {
    UmlClass newClass;
    UmlPackage newPackage;
    int lastDotPosition;
    String packageName = "";

    // parse the qualified class name and skip the last dot
    lastDotPosition = qualifiedName.lastIndexOf( "." );
    if ( lastDotPosition > -1 ) { // there is a package name
      packageName = qualifiedName.substring( 0, lastDotPosition );
      qualifiedName   = qualifiedName.substring( lastDotPosition + 1 );
    }

    // create the class
    newPackage = WrapUmlPackage.instantiatePackage( packageName, model );
    newClass = instantiateClass( newPackage, qualifiedName );
    return newClass;
  }

  /**
   * This method creates the model class in the package provided
   *
   * @param namespace is the containing package (or Classifier)
   * @param className the class to create
   * @return the class built
   */
  public static UmlClass instantiateClass(Namespace namespace, String className) {
    UmlClass result;
    Object lookedUp;
    
    lookedUp = WrapNamespace.locateRelativeModelElement( namespace, className );
    if ( lookedUp == null ) { // none found - build it
      result = new WrapUmlClass( namespace, className ).getWrapped();
      
    } else if ( lookedUp instanceof UmlClass ) {
      result = (UmlClass) lookedUp;
      
    } else {
      throw new BuildException("Expected to lookup a UmlClass instance for the name: "+namespace.getName()+"."+className+" isntead of "+lookedUp);
    }
    return result;
  }
}