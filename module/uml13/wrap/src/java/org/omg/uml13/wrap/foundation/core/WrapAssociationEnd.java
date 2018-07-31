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

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;

import javax.jmi.reflect.RefPackage;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Wrapper;
import net.mdatools.modelant.template.Render;
import net.mdatools.modelant.template.TemplateEngine;
import net.mdatools.modelant.util.FormatHelper;

import org.omg.uml13.foundation.core.AssociationEnd;
import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.foundation.core.UmlAssociation;
import org.omg.uml13.foundation.core.UmlClass;
import org.omg.uml13.foundation.datatypes.MultiplicityRange;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.foundation.extensionmechanisms.Stereotype;
import org.omg.uml13.wrap.Uml13WrapFactory;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapAssociationEnd;
import org.omg.uml13.wrap.foundation.datatypes.WrapAggregationKind;
import org.omg.uml13.wrap.foundation.datatypes.WrapMultiplicity;
import org.omg.uml13.wrap.foundation.datatypes.WrapOrderingKind;
import org.omg.uml13.wrap.foundation.datatypes.WrapVisibilityKind;
/**
 * This is a wrapper of org.omg.uml13.foundation.core.AssociationEnd that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapAssociationEnd extends BaseWrapAssociationEnd {

  /**
   * This renders the methods declarations to support associations
   */
  public static final Render RENDER_METHODS = new AssociationEndRenderer( "Methods" );

  /**
   * This renders the methods declarations to support associations of POJO
   */
  public static final Render RENDER_POJO_METHODS = new AssociationEndRenderer( "PojoMethods" );

  /**
   * This renders the methods declarations to support associations of COMMAND class
   * Note that these associations are unidirectional from the command class
   */
  public static final Render RENDER_COMMAND_METHODS = new AssociationEndRenderer( "CommandMethods" );

  /**
   * This renders the calls to initialize mandatory associations
   */
  public static final Render RENDER_INIT_METHODS = new AssociationEndRenderer( "InitMethods" );

  /**
   * Calculated values, lazily initialized
   */
  private String roleName;

  /**
   * The other end associated with this one
   */
  private AssociationEnd otherEnd;

  public WrapAssociationEnd(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapAssociationEnd(RefPackage extent) {
    super( extent );
  }


  /**
   * Creates an association end bound to that type and wth the role name provided
   * @param association TODO
   * @param type
   * @param name
   * @param isNavigable TODO
   * @param multiUpperBound TODO
   */
  public WrapAssociationEnd(UmlAssociation association, Classifier type, String name, boolean isNavigable, int multiUpperBound) {
    this( type.refOutermostPackage() );

    setName( name );
    setType( type );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
    setAssociation( association );
    setMultiplicity( new WrapMultiplicity( type.refOutermostPackage(),
                                           multiUpperBound ).getWrapped() );
  }

  /**
   * Creates an association end bound to that type and wth the role name provided
   * @param association 
   * @param type
   * @param multiUpperBound 
   */
  public WrapAssociationEnd(UmlAssociation association, Classifier type, int multiUpperBound) {
    this( type.refOutermostPackage() );

    setType( type );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
    setAssociation( association );
    setMultiplicity( new WrapMultiplicity( type.refOutermostPackage(),
                                           multiUpperBound ).getWrapped() );
  }

  /**
   * @return the properly formatted class name for this association end
   */
  public String getClassName() {
    return ((WrapModelElement) wrap( getType() )).getLogicalName();
  }

  /**
   * @return a database field name (Foreign Key) to support the association
   */
  public String formatForeignKeyFieldName(int maxLen) {
    String result;

    result = FormatHelper.formatSubstring( FormatHelper.formatConstantName( getAssociationRole() ),
                                                                            maxLen-3)
             +"_ID";

    return result;
  }

  /**
   * This method formats the name of a referential integrity constraint
   * the attribute provided as a database column name.
   * If explicit overriding name is provided as a property/tagged value of the classifier,
   * then its contents is used to construct the table name
   * @param maxLenght maximum name length to construct
   * @return a non-null name of a corresponding DB constraint
   */
  public String formatForeignKeyConstraintName(int maxLenght) {
    String result;
    WrapAssociationEnd otherEnd;
    WrapClassifier thisRootClass;
    WrapClassifier otherRootClass;

    otherEnd = (WrapAssociationEnd) wrap( getOtherAssociationEnd() );
    thisRootClass = (WrapClassifier) wrap( getRootSuperclass() );
    otherRootClass= (WrapClassifier) wrap( otherEnd.getRootSuperclass() );

    result = "FK_"+FormatHelper.formatAbbreviatedConstantName( thisRootClass.formatAllCapitalName(),
                                                               maxLenght/2 - 3 )
             +"_"+FormatHelper.formatAbbreviatedConstantName( otherRootClass.formatAllCapitalName(),
                                                              maxLenght/2 - 3 ); // up to 2 digits for unique index
    return result.toUpperCase();
  }


  /**
   * This method returns the string representation of the current end's multiplicity
   *
   * @return a string representing the multilicity ranges (like 0..n, *, 0..1, etc.) of this
   *         association. If more than one multiplicity provided, then they are separated with ','
   */
  public String getMultiplicityString() {
    StringBuilder result = new StringBuilder();
    MultiplicityRange multi;
    Iterator multiIt;

    if ( getAggregationKind().isAggregate() ) {
      result.append( "0..1" );

    } else if ( getAggregationKind().isComposite() ) {
      result.append( "1..1" );

    } else {
      multiIt = getMultiplicity().getRange().iterator();
      while ( multiIt.hasNext() ) {
        multi = (MultiplicityRange) multiIt.next();

        result.append( multi.getLower() ).append( ".." );
        if ( multi.getUpper() < 0 ) { // unlimited
          result.append( "*" );
        } else {
          result.append( multi.getUpper() );
        }
        if ( multiIt.hasNext() ) {
          result.append( ", " );
        }
      }
    }
    return result.toString();
  }

  /**
   * This method retrieves the other end of the association this end participates in. This method
   * @return non-null association end
   */
  public AssociationEnd getOtherAssociationEnd() {
    AssociationEnd instance;
    Collection allAssociationEnds;
    Iterator sourceIterator;

    if ( otherEnd == null ) {
      allAssociationEnds = getAssociation().getConnection();
      sourceIterator = allAssociationEnds.iterator();
      while ( otherEnd == null && sourceIterator.hasNext() ) {
        instance = (AssociationEnd) sourceIterator.next();

        if ( instance != getWrapped() ) {
          otherEnd = instance;
        }
      }
      if ( otherEnd == null ) { // association with more than 2 ends found
        throw new IllegalArgumentException( "Other association end not found for association end:" + getName());
      }
    }
    return otherEnd;
  }

  /**
   * getRoleName returns the role of this association end. Note that this is the role of this end
   * relatively to the other end.
   *
   * @return this role name
   */
  public String getRoleName() {
    if ( roleName == null ) {
      if ( isSingular() ) {
        roleName = getAssociationRole();

      } else { // multiple associated instances
        roleName = translatePlural( getAssociationRole() );
      }
    }
    return roleName;
  }

  /**
   * @return the role name of this association (its class is default)
   */
  public String getLogicalName() {
    return getRoleName();
  }

  /**
   * getAssociationRole finds the association role of a target end of its. If no role explicitly
   * specified, then the class name is used as a default role name.
   * @deprecated USE getRoleName() instead. Currently the BaseForm classes violate the naming convention
   *              to use plural forms for for multiple associated objects.
   * @return the role of this end in the association
   */
  public String getAssociationRole() {
    String result;
    AssociationEnd target = getWrapped();

    result = target.getName();
    if ( result == null || result.equals( "" ) ) {
      result = target.getType().getName();
    }
    return result;
  }

  /**
   * This method constructs a name of the association, starting from this end.
   * The name indicates the roles, the navigability and the multiplicity of both ends
   * of the association. Format:
   *
   * <this end multiplicity> <this root class name> ['(as <role name>')'] '-to-' <other end multiplicity> <other root class name> ['(as <role name>')']
   *
   * The role name is shown only if its end is navigable to (the other end provides
   * means to reach it) through the association
   * @return a non-null string
   */
  public String formatAssociationKey() {
    StringBuilder result = new StringBuilder(256);
    WrapAssociationEnd otherEnd;
    WrapUmlClass thisRootClass;
    WrapUmlClass otherRootClass;

    otherEnd = (WrapAssociationEnd) wrap(getOtherAssociationEnd());

    thisRootClass = (WrapUmlClass) wrap( getRootSuperclass() );
    otherRootClass= (WrapUmlClass) wrap( otherEnd.getRootSuperclass() );

    result.append( getMultiplicityString() )
          .append(" ");
    result.append( thisRootClass.getLogicalName() );

    if ( otherEnd.isAssociationSupported() ) {
      result.append(" (as ").append( getRoleName() ).append(")");
    }
    result.append( " -to- ");

    result.append( otherEnd.getMultiplicityString() )
          .append(" ");
    result.append( otherRootClass.getLogicalName() );

    if ( isAssociationSupported() ){
      result.append(" (as ").append( otherEnd.getRoleName()).append(")");
    }
    return result.toString();
  }

  /**
   * This method constructs a name of the association, starting from this end.
   * The name indicates the roles, the navigability and the multiplicity of both ends
   * of the association. Format:
   *
   * <this end multiplicity> <this class name> ['(as <role name>')'] '-to-' <other end multiplicity> <other class name> ['(as <role name>')']
   *
   * The role name is shown only if its end is navigable to (the other end provides
   * means to reach it) through the association
   * @return a non-null string
   */
  public String formatAssociation() {
    StringBuilder result = new StringBuilder(256);
    WrapAssociationEnd otherEnd;
    WrapUmlClass thisClass;
    WrapUmlClass otherClass;

    otherEnd = (WrapAssociationEnd) wrap(getOtherAssociationEnd());

    thisClass = (WrapUmlClass) wrap( getType() );
    otherClass= (WrapUmlClass) wrap( otherEnd.getType() );

    result.append( getMultiplicityString() )
          .append(" ");
    result.append( thisClass.getLogicalName() );

    if ( otherEnd.isAssociationSupported() ) {
      result.append(" (as ").append( getRoleName() ).append(")");
    }
    result.append( " -to- ");

    result.append( otherEnd.getMultiplicityString() )
          .append(" ");
    result.append( otherClass.getLogicalName() );

    if ( isAssociationSupported() ){
      result.append(" (as ").append( otherEnd.getRoleName()).append(")");
    }
    return result.toString();
  }

  /**
   * @return formats the public visibility as "public", whereas any other visibility is
   *         reduced to "protected"
   */
  public final String formatSimpleVisibility() {
    String result;

    if ( isPublic() ) {
      result = "public";
    } else {
      result = "protected";
    }
    return result;
  }

  /**
   * This method finds the root class name of the other end's class
   *
   * @return the root class of the class this association end wraps up.
   */
  public UmlClass getRootSuperclass() {
    UmlClass result;

    result = ((WrapClassifier)wrap( getType())).getRootSuperclass();

    return result;
  }


  /**
   * PRE-CONDITION:
   *  This end participates in ONE-to-ONE association
   *
   * @return true when this side table must provide the foreign key
   * @throws IllegalArgumentException when both ends are navigable, so no choice is possible
   * see associations.xls
   */
  public boolean hasForeignKey() throws IllegalArgumentException {
    boolean result;
    AssociationEnd otherEnd;
    WrapAggregationKind thisAggregation;
    WrapAggregationKind otherAggregation;

    otherEnd = getOtherAssociationEnd();

    thisAggregation = getAggregationKind();
    otherAggregation = ((WrapAssociationEnd) wrap(otherEnd)).getAggregationKind();

    result = thisAggregation.isAggregate()
             || thisAggregation.isComposite()
             || !isNavigable() && otherEnd.isNavigable();

    if ( !result
         && !thisAggregation.isAggregate()
         && !thisAggregation.isComposite()
         && !otherAggregation.isAggregate()
         && !otherAggregation.isComposite() ) {
      if ( isNavigable() && otherEnd.isNavigable() ) {
        throw new IllegalArgumentException( "Cannot select FK table for bi-directional association "
                                            + formatAssociationKey());
      }
      if ( !isNavigable() && !otherEnd.isNavigable() ) {
        LOGGER.log( Level.WARNING, "Cannot select FK table for non-navigable association "
                                            + formatAssociationKey());
      }
    }
    return result;
  }

  /**
   * Renders the this association by delegating it to the appropriate method of the
   * WrapAssociationRenderer provided. This selects the appropriate method depending on
   * the multiplicity of the associations at both sides. The renderer
   * method delegates to the the render actual method to call.
   * <pre>
   * For an association X-to-Y the templates/render methods used are:
   *    renderXToY&lt;suffix&gt;(this end)
   *    renderCommon&lt;suffix&gt;(this end)
   * </pre>
   * @param target is the non-null wrapper of an AssociationEnd to render. Note that the class of the target
   *        identifies the template's package, whereas any other conditions over it are handled in internal classes.
   * @param renderMethodSuffx
   * @param request
   * @param response
   * @throws ServletException
   */
  public static final void renderAssociationEnd(Wrapper target, String renderMethodSuffx, ServletRequest request, ServletResponse response) {
    WrapAssociationEnd thisEnd;
    WrapAssociationEnd otherEnd;
    Factory factory;

    factory = Factories.getFactory( Uml13WrapFactory.class );

    thisEnd = (WrapAssociationEnd) factory.wrap( target.getWrapped() );
    otherEnd= (WrapAssociationEnd) factory.wrap( thisEnd.getOtherAssociationEnd());

    if ( otherEnd.isSingular() ) { // Association X-TO-ONE
      if ( thisEnd.isSingular() ) { // ONE-TO-ONE
        TemplateEngine.render( target, "renderOneToOne"+renderMethodSuffx , request, response);

      } else { // MANY-TO-ONE
        TemplateEngine.render( target, "renderManyToOne"+renderMethodSuffx , request, response);
      }
    } else { // X-TO-MANY ASSOCITION
      if ( thisEnd.isSingular() ) { // ONE-TO-MANY
        TemplateEngine.render( target, "renderOneToMany"+renderMethodSuffx , request, response);

      } else { // MANY-TO-MANY
        TemplateEngine.render( target, "renderManyToMany"+renderMethodSuffx , request, response);
      }
    }
    TemplateEngine.render( target, "renderCommon"+renderMethodSuffx , request, response);
  }


  /**
   * @return view-mapped aggregation of the association end provided
   */
  public WrapAggregationKind getAggregationKind() {
    return (WrapAggregationKind) wrap( getWrapped().getAggregation());
  }

  /**
   * @return view-mapped multiplicity of the association end provided
   */
  private WrapMultiplicity getWrappedMultiplicity() {
    return (WrapMultiplicity) wrap( getWrapped().getMultiplicity());
  }

  /**
   * @param stereotypeName
   * @return true if the association end is navigable and has a stereotype with the name provided
   */
  public boolean isNavigableStereotyped(String stereotypeName) {
    boolean result = false;

    UmlAssociation assoc;
    Stereotype stereotype;

    if ( !isNavigable() ) {
      assoc = getAssociation();

      stereotype = ((WrapModelElement) wrap(assoc)).getStereotype();
      if ( stereotype != null ) {
        result = stereotypeName.equals( stereotype.getName() );
      }
    }
    return result;
  }

  /**
   * This method removes the association end together with its documentation and multiplicity
   */
  public void remove() {
    //  removeMultiplicity( getMultiplicity() ); // as an attribute type
    removeTaggedValues();
    refDelete();
  }

  /**
   * @return true if the this end is marked as a composite/ion
   */
  public boolean isComposition() {
    boolean result;

    result = getAggregationKind().isComposite();

    return result;
  }

  /**
   * @return true if the this end is marked as a aggregate/ion
   */
  public boolean isAggregation() {
    boolean result;

    result = getAggregationKind().isAggregate();

    return result;
  }

  /**
   * @return true if and only if this association end is for singular associated instance
   * or this end is marked as aggregation/composition (then by default it is assumed to be singular)
   */
  public boolean isSingular() {
    boolean result;

    result = getAggregationKind().isAggregate()
             || getAggregationKind().isComposite()
             || getWrappedMultiplicity().isSingular();
    return result;
  }

  /**
   * isMandatory returns true if and only if this association end is for at
   * least one associated object
   * @return true if the multiplicity of this end at least one
   */
  public boolean isMandatory() {
    boolean result;

    result = getAggregationKind().isComposite()
             || getWrappedMultiplicity().isMandatory();
    return result;
  }

  /**
   * Checks if the wrapped association end must provide a navigability to support the association at
   * this end.
   *<table Table border=1 cellspacing=0 cellpadding=0 align=left width=1540>
   * <tr>
   *  <td width=64 colspan=3><b>When</b></td>
   *  <td colspan=8 halign=center><b>Other End is:</b>(see bellow)</td>
   * </tr>
   * <tr>
   *  <td width=64 rowspan=6> and </td>
   *  <td width=156 colspan=2 rowspan=2><b>This end is:</b>(see bellow)</td>
   *  <td width=631 colspan=4>composition / association</td>
   *  <td width=302 colspan=2 rowspan=2>navigable</td>
   *  <td width=302 colspan=2 rowspan=2>not navigable</td>
   * </tr>
   * <tr>
   *  <td width=329 colspan=2>navigable</td>
   *  <td width=302 colspan=2>not navigable</td>
   * </tr>
   * <tr>
   *  <td width=156 rowspan=2>composition / association</td>
   *  <td width=84>navigable</td>
   *  <td width=631 colspan=4 rowspan=2></td>
   *  <td width=146><b>TRUE</b></td>
   *  <td width=156>TRUE</td>
   *  <td width=146>FALSE</td>
   *  <td width=156>TRUE</td>
   * </tr>
   * <tr>
   *  <td width=84>not navigable</td>
   *  <td width=146><b>TRUE</b></td>
   *  <td width=156>FALSE</td>
   *  <td width=146><b>TRUE</b></td>
   *  <td width=156>FALSE</td>
   * </tr>
   * <tr>
   *  <td width=240 colspan=2>navigable</td>
   *  <td width=160><b>TRUE</b></td>
   *  <td width=169>TRUE</td>
   *  <td width=146>FALSE</td>
   *  <td width=156>TRUE</td>
   *  <td width=146><b>TRUE</b></td>
   *  <td width=156>TRUE</td>
   *  <td width=146>FALSE</td>
   *  <td width=156>TRUE</td>
   * </tr>
   * <tr>
   *  <td width=240 colspan=2>not navigable</td>
   *  <td width=160><b>TRUE</b></td>
   *  <td width=169>FALSE</td>
   *  <td width=146>FALSE</td>
   *  <td width=156>TRUE</td>
   *  <td width=146><b>TRUE</b></td>
   *  <td width=156>FALSE</td>
   *  <td width=146><b>TRUE</b></td>
   *  <td width=156>TRUE</td>
   * </tr>
   * <tr>
   *  <td width=64 colspan=3><b>Then:</b></td>
   *  <td width=160><b>this.<br/>isAssocSupported</b></td>
   *  <td width=169><b>other.<br/>isAssocSupported</b></td>
   *  <td width=146>this.<br/>isAssocSupported</td>
   *  <td width=156>other.<br/>isAssocSupported</td>
   *  <td width=146>this.<br/>isAssocSupported</td>
   *  <td width=156>other.<br/>isAssocSupported</td>
   *  <td width=146>this.<br/>isAssocSupported</td>
   *  <td width=156>other.<br/>isAssocSupported</td>
   * </tr>
   *</table><br/>
   * This table is provided in docs\associations.xls
   * <b>Note that it must be "symmetric" so that whenever thisEnd and otherEnd exchange their
   * positions, the results must be the same</b>
   * @return true when this side must provide means to reach the other end
   */
  public boolean isAssociationSupported() {
    boolean result;
    WrapAggregationKind thisAggregation;
    WrapAggregationKind otherAggregation;

    AssociationEnd otherEnd;

    otherEnd = getOtherAssociationEnd();
    otherAggregation = (WrapAggregationKind) wrap( otherEnd.getAggregation() );

    thisAggregation = getAggregationKind();

    result = otherEnd.isNavigable()
             && !thisAggregation.isAggregate()
             && !thisAggregation.isComposite()
             ||( otherEnd.isNavigable() || !isNavigable() )
               && !otherAggregation.isAggregate()
               && !otherAggregation.isComposite();
    return result;
  }

  /**
   * @return true if the other association end is marked as ordered
   */
  public boolean isOrdered() {
    return ((WrapOrderingKind) wrap(getOrdering())).isOrdered();
  }

  /**
   * @return true if the other association end is marked as ordered
   */
  public boolean isExplicitRoleProvided() {
    boolean result;
    String explicitRole;

    explicitRole = getName();

    result = explicitRole != null && explicitRole.trim().length() > 0;
    return result;
  }

  /**
   * @return true if the other association end is marked as sorted
   */
  public boolean isSorted() {
    return ((WrapOrderingKind) wrap( getOrdering())).isSorted();
  }

  /**
   * @return true if the visibility of this is public
   */
  public final boolean isPublic() {
    boolean result;
    WrapVisibilityKind wrapVisibility;

    wrapVisibility = (WrapVisibilityKind) wrap( getVisibility());
    result = wrapVisibility != null && wrapVisibility.isPublic();

    return result;
  }


  /**
   * @return true if the association of this end binds at the other end a domain class
   * @throws IllegalArgumentException
   */
  public boolean isAssociationToDomain() throws IllegalArgumentException {
    boolean result;
    AssociationEnd otherEnd;

    otherEnd = getOtherAssociationEnd();

    result= otherEnd.getType() instanceof UmlClass
            && ((WrapClassifier) wrap( otherEnd.getType())).isDomainClass();
    return result;
  }


  /**
   * @return true if this end of the association is supported and the other end of is to a persistent class
   * @throws IllegalArgumentException
   */
  public boolean isAssocisationPersistenceSupported() throws IllegalArgumentException {
    boolean result;
    WrapAssociationEnd wrapOtherEnd;
    Classifier superClass;
    WrapClassifier wrapOtherSuperclass;

    result = isAssociationSupported();
    if ( result ) {
      wrapOtherEnd = (WrapAssociationEnd) wrap( getOtherAssociationEnd());
      superClass = wrapOtherEnd.getRootSuperclass();

      wrapOtherSuperclass = (WrapUmlClass) wrap( superClass );

      result = wrapOtherSuperclass.isPersistentClassesRoot();
    }
    return result;
  }

  /**
   * @return if the other end of this association [end] is bound to a persistent class
   * @throws IllegalArgumentException
   */
  public boolean isAssociationToDbTable() throws IllegalArgumentException {
    boolean result;
    WrapAssociationEnd wrapOtherEnd;
    Classifier superClass;
    WrapClassifier wrapOtherSuperclass;

    wrapOtherEnd = (WrapAssociationEnd) wrap( getOtherAssociationEnd());
    superClass = wrapOtherEnd.getRootSuperclass();
    wrapOtherSuperclass = (WrapUmlClass) wrap( superClass );

    result = wrapOtherSuperclass.isPersistentClassesRoot();
    return result;
  }
}