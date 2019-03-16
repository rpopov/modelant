<%--
  This template produces the wrapper class for a class as a model element

  Assumes that the wrapper and the wrapped classes are in the same package, thus the import is skipped

--%><%@page import="net.mdatools.modelant.mof.MofElementWrapper"
%><%

 String className = wrapped.calculateSimpleBaseWrapperClassName();
 String componentName;
 String delegateClassName;

 componentName  = (String) request.getAttribute("component");

 delegateClassName = wrapped.calculateQualifiedClassName();

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
package <%=wrapped.calculateBasecomponentNameName( componentName ) %>;

import <%=wrapped.calculateQualifiedClassName() %>;

/**
 * The JMI standard <%=wrapped.calculateQualifiedClassName()%> interface
 */
public interface <%=className %> extends <%=wrapped.calculateQualifiedSuperclassWrapperName( componentName ) %> <% wrapped.renderImplementedInterfaces( delegateClassName, context); %>{

<% wrapped.renderDelegatedDeclaredConstants( delegateClassName, context );
%>
<% wrapped.renderDelegatedDeclaredMethods( delegateClassName, context );
%>}