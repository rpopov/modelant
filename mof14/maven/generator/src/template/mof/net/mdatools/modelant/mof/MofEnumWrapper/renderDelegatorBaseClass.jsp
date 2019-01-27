<%--
  This template produces the wrapper class for a class as a model element

  Assumes that the wrapper and the wrapped classes are in the same package, thus the import is skipped

--%><%@page wraps="net.mdatools.modelant.mof.MofEnumWrapper"
%><%@page import="net.mdatools.modelant.mof.MofEnumWrapper, javax.jmi.model.Namespace, net.mdatools.modelant.mof.MofElementWrapper"
%><%

 String className = wrapped.calculateSimpleBaseWrapperClassName();
 String modelInterfaceName = wrapped.calculateSimpleClassName();

 String rootPackageName;
 String wrapperPackage;
 String delegatorPackage;
 String delegateClassName;

 rootPackageName = (String) request.getAttribute("metamodel.root.package");
 delegatorPackage = (String) request.getAttribute("component");
 wrapperPackage = (String) request.getAttribute("wrapped.component");

 delegateClassName = wrapped.calculateQualifiedWrapperClassName( wrapperPackage );

%>/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package <%=wrapped.calculateBaseWrapperPackageName( delegatorPackage ) %>;

import net.mdatools.modelant.core.wrap.Factory;

/**
 * This is a wrapper of <%=delegateClassName %> that allows adding specific
 * custom methods and using them as templates for [code] generation.
 * It inherits through delegation from <%=delegateClassName %>.
 */
public abstract class <%=className %> extends <%=wrapped.calculateQualifiedSuperclassWrapperName( delegatorPackage ) %> {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected <%=className %>(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  protected <%=className %>(String value, Factory factory) {
    this( <%=wrapped.calculateQualifiedClassName() %>Enum.forName( value ), factory );
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected <%=delegateClassName %> getDelegate() {
    return (<%=delegateClassName %>) super.getDelegate();
  }
<% wrapped.renderDelegatedDeclaredMethods( delegateClassName, request, response );
%>}