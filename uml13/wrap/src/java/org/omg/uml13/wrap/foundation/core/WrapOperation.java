/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.behavioralelements.commonbehavior.Signal;
import org.omg.uml13.behavioralelements.commonbehavior.UmlException;
import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.foundation.core.Dependency;
import org.omg.uml13.foundation.core.Method;
import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.core.Operation;
import org.omg.uml13.foundation.core.Parameter;
import org.omg.uml13.foundation.datatypes.ScopeKindEnum;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapOperation;
import org.omg.uml13.wrap.foundation.datatypes.WrapParameterDirectionKind;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Operation that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapOperation extends BaseWrapOperation {

  public WrapOperation(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapOperation(RefPackage extent) {
    super( extent );
  }

  public WrapOperation(RefPackage extent, String name) {
    this( extent );

    setName( name );
    setVisibility( VisibilityKindEnum.VK_PUBLIC );
    setOwnerScope( ScopeKindEnum.SK_INSTANCE );
  }

  /**
   * Builds a list of all exceptions (signals) that are marked as raised signals by this method
   * @return all method's exceptions
   */
  public List getExceptions() {
    List result = new ArrayList();
    ModelElement supplier;
    Dependency dependency;
    Iterator clientDependenciesIterator;
    Iterator suppliersIterator;
    Operation operation;

    operation = getWrapped();
    result.addAll( listSignalsOf() );

    // Rose describes the exceptions as dependencies - collect them
    clientDependenciesIterator = operation.getClientDependency().iterator();
    while ( clientDependenciesIterator.hasNext() ) {
      dependency = (Dependency) clientDependenciesIterator.next();
      suppliersIterator = dependency.getSupplier().iterator();

      while ( suppliersIterator.hasNext() ) {
        supplier = (ModelElement) suppliersIterator.next();
        if ( supplier instanceof UmlException ) {
          result.add( supplier );
        }
      }
    }
    return result;
  }

  /**
   * <p>This method returns a JavaDoc of the method parameters for given operation. The method returns one or more rows
   * like this one: "&amp;param template - a [ClassName] instance"</p>
   * <p>If the method has return type the following row is also added :<br>
   * "&amp;return [ClassName] instance"</p>
   *
   * @return a JavaDoc of the method parameters for given operation
   * TODO: IMPLEMENT USING TEMPLATES INSTEAD OF STRIGS
   */
  public String getMethodAttributesJavaDoc() {
    StringBuilder result = new StringBuilder(512);
    Iterator parametersIterator;
    Iterator signalsIterator;
    WrapClassifier resultType;
    Parameter parameter;
    Signal signal;
    WrapParameter WrapParameter;
    WrapClassifier WrapParameterClass;

    parametersIterator = getParameter().iterator();
    resultType = null;

    // To add the result type it should be searched for among the parameters.
    // It will be known after params are processed.
    //  Add @param tags
    while (parametersIterator.hasNext()) {
      parameter = (Parameter) parametersIterator.next();

      WrapParameter = (WrapParameter) wrap( parameter);
      WrapParameterClass = (WrapClassifier) wrap( parameter.getType());

      if (!WrapParameter.isReturn()) { // a regular parameter
        result.append("@param ");
        result.append( WrapParameter.formatFirstLowerName() );
        result.append(" - a ");
        result.append( WrapParameterClass.getLogicalName() );
        result.append(" instance");
        result.append( "\r\n" );
      } else { // method return type
        resultType = WrapParameterClass;
      }
    }

    //  Add @return tag at the end
    if (resultType != null) { // There is return type
      if (!resultType.isVoid() ) {
        result.append("@return ");
        result.append( resultType.getLogicalName() );
        result.append(" instance");
        result.append( "\r\n" );
      }
    }

    // Add @throws tags
    signalsIterator = getExceptions().iterator();
    while (signalsIterator.hasNext()) {
      signal = (Signal) signalsIterator.next();
      result.append("@throws ");
      result.append( ((WrapClassifier) wrap( signal)).getLogicalName());
      result.append(" when error occur");
      result.append( "\r\n" );
    }  // parameters contains the list of the method's formal parameters together with the following throws clause

    return result.toString();
  }

  /**
   * Due to the specification of UML 1.3, the method specification is provided in an operation
   * defined, so it may be design tool specific decifion where to provide the method name - in the
   * method object or in the operation. This method retrieves ths method name.
   * @param method
   * @return method name
   */
  public String getMethodName(Method method) {
    String result = method.getName();

    if ( result == null ) {
      result = method.getSpecification().getName();
    }
    return result;
  }


  /**
   * This is a general method to select the Signals/Exceprions associated with a method.
   *
   * @return a collection of all TaggedValues that are associated with this model element
   */
  private Collection listSignalsOf() {
    ArrayList result = new ArrayList();
    Collection allSignals;
    Iterator sourceIterator;
    Signal instance;
    Operation target;

    target = getWrapped();
    allSignals = ((org.omg.uml13.Uml13Package) ((RefObject) target).refOutermostPackage()).getBehavioralElements()
                  .getCommonBehavior().getSignal()
                  .refAllOfClass();
    sourceIterator = allSignals.iterator();
    while ( sourceIterator.hasNext() ) {
      instance = (Signal) sourceIterator.next();
      if ( instance.getContext().contains( target ) ) { // the Signal is issued by the
                                                        // method/operation
        result.add( instance );
      }
    }
    return result;
  }

  /**
   * This method returns a JavaDoc of the method parameters. The method returns one or more rows
   * like this one: " * &amp;param template "
   *
   * @param parameterNamesList - the parameterNamesList, returned from
   *          getPostMethodAttributesNamesList method
   * @return TODO REMOVE
   */
  public String getMethodAttributesJavaDoc(String parameterNamesList) {
    StringBuilder result = new StringBuilder(256);
    String[] parameterNames = parameterNamesList.split( "," );

    for (int i = 0; i < parameterNames.length; i++) {
      if ( result.length() > 0 ) {
        result.append( "\r\n" );
      }
      result.append( "   * @param " );
      result.append( parameterNames[ i ].trim() );
      result.append( " - a " );
      result.append( parameterNames[ i ].trim() );
      result.append( " instance." );
    }

    return result.toString();
  }

  /**
   * This is the name of the section in a page description that describes the static page contents.
   */
  public static final String STATIC_CONTENTS_DOC_SECTION = "Static contents";

  /**
   * This is the name of the section in a page description that describes the dynamic data to be
   * shown on the page. It serves as a terminator of the static contents section as well.
   */
  public static final String DYNAMIC_CONTENTS_DOC_SECTION = "Dynamic contents";

  /**
   * This is the name of the section in a page description that describes what the used can do on
   * the page. It serves as a terminator of the the dynamic contents section as well.
   */
  public static final String POSSIBLE_ACTIONS_DOC_SECTION = "Possible actions";

  /**
   * This is the name of the section in a page description that describes what the used can change
   * on the page. It serves as a terminator of the the possible actions section as well.
   */
  public static final String USER_INPUT_DOC_SECTION = "User input";


  /**
   * @return a non-null collection of all parameters of this operation, EXCLUDING the
   *         return parameter
   */
  public List<Parameter> getParameters() {
    List<Parameter> result = new ArrayList<Parameter>();
    Iterator parametersIterator;
    Parameter parameter;

    parametersIterator = getParameter().iterator();
    while (parametersIterator.hasNext()) {
      parameter = (Parameter) parametersIterator.next();

      if ( !((WrapParameterDirectionKind) wrap( parameter.getKind())).isReturn()  ) { // a regular parameter
        result.add( parameter );
      }
    }
    return result;
  }

  /**
   * @return a possibly null class this operation returns.
   *         Null returned when this operation is void
   */
  public Classifier getReturnType() {
    Classifier result = null;
    Iterator parametersIterator;
    Parameter parameter;

    parametersIterator = getParameter().iterator();
    while ( result==null && parametersIterator.hasNext()) {
      parameter = (Parameter) parametersIterator.next();

      if ( ((WrapParameterDirectionKind) wrap( parameter.getKind())).isReturn()  ) {
        result = parameter.getType();
      }
    }
    return result;
  }

  /**
   * This method/operation is a Constructor iff its name is same as the owner class
   * @return if this is a constructor method/operation
   */
  public boolean isConstructor() {
    boolean result;
    result = getName() != null
             && getName().equalsIgnoreCase( getOwner().getName());
    return result;
  }

  /**
   * This method removes the method together with it parameters and tagged values
   */
  public void remove() {
    Iterator methodIterator;
    Iterator parameterIterator;
    Parameter parameter;
    Method method;

    // remove parameters
    parameterIterator = getParameter().iterator();
    while ( parameterIterator.hasNext() ) {
      parameter = (Parameter) parameterIterator.next();
      parameterIterator.remove();
      ((WrapParameter) Factories.wrap( parameter )).remove( );
    }
    // remove dependencies from/to this method
    removeDepenencies( );
    removeTaggedValues( );

    // remove the specified methods
    methodIterator = getMethod().iterator();
    while ( methodIterator.hasNext() ) {
      method = (Method) methodIterator.next();
      methodIterator.remove();
      ((WrapMethod) Factories.wrap( method )).remove();
    }
    refDelete();
  }
}