<%--
  This template produces the factory of wrapper classes for the classes in a metamodel
  Parameters:

    package.name - is the non-empty package name of the factory class to generate
    class.name - is the non-empty name of the factory class to generate
    all.collectiontype - a non-null, but possibly empty collection of model elements
    all.enumerationtype - a non-null, but possibly empty collection of model elements
    all.structuretype - a non-null, but possibly empty collection of model elements

--%><%@page wraps="java.util.ArrayList"
%><%@page import="net.mdatools.modelant.mof.*,
                  net.mdatools.modelant.core.wrap.Factories,
                  java.util.*"
%><%

  String packageName;
  String className;
  Iterator classIterator;
  Object modelClass;
  MofElementWrapper wrapper;
  Collection additional;
  String delegatorPackage;
  String wrapperPackage;
  String wrappedFactoryClassName;

  delegatorPackage = (String) request.getAttribute("component");
  wrapperPackage = (String) request.getAttribute("wrapped.component");

  wrappedFactoryClassName = (String) request.getAttribute("wrapped.factory");

  packageName = (String) request.getAttribute("package.name");
  className = (String) request.getAttribute("class.name");

  additional = (Collection) request.getAttribute("all.enumerationtype");
  wrapped.addAll( additional );

%>/**
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package <%=packageName %>;

import java.util.HashMap;
import java.util.Map;

import net.mdatools.modelant.core.wrap.DelegatorFactory;
import net.mdatools.modelant.core.wrap.Wrapper;

/**
 * This class maps Model Classes to corresponding wrapper classes
 * @author Rusi Popov
 */
public class <%=className %> extends DelegatorFactory {

  private static final Map<Class<?>, Class<? extends Wrapper>> mapModelToWrapper = new HashMap<Class<?>, Class<? extends Wrapper>>();
  static {
<%
    classIterator = wrapped.iterator();
    while ( classIterator.hasNext() ) {
      modelClass = classIterator.next();

      wrapper = (MofElementWrapper) Factories.wrap( modelClass );

%>    mapModelToWrapper.put(<%=wrapper.calculateQualifiedWrapperClassName( wrapperPackage ) %>.class,<%=wrapper.calculateQualifiedWrapperClassName( delegatorPackage ) %>.class);
<%
   }
%>  }

  protected <%=className %>() {
    super( <%=wrappedFactoryClassName %>.class );
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Factory#getMappedClass(java.lang.Class)
   */
  protected Class<? extends Wrapper> getMappedClass(Class<?> original) {
    Class result;
    result = (Class) mapModelToWrapper.get( original );
    return result;
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Factory#getProperty(java.lang.String)
   */
  protected String getProperty(String name) {
    return ((<%=wrappedFactoryClassName %>) getDelegateFactory()).getProperty( name );
  }
}