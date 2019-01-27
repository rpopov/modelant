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

 wrapperPackage  = (String) request.getAttribute("component");
 rootPackageName = (String) request.getAttribute("metamodel.root.package");

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
package <%=wrapped.calculateBaseWrapperPackageName( wrapperPackage ) %>;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import <%=wrapped.calculateQualifiedClassName() %>;
import <%=wrapped.calculateQualifiedClassName() %>Enum;
import net.mdatools.modelant.core.wrap.Factory;

/**
 * This is a wrapper of <%=wrapped.calculateQualifiedClassName() %> that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class <%=className %> extends <%=wrapped.calculateQualifiedSuperclassWrapperName( wrapperPackage ) %> {

  protected <%=className %>(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  protected <%=className %>(String value, Factory factory) {
    this( <%=wrapped.calculateQualifiedClassName() %>Enum.forName( value ), factory );
  }

  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public <%=modelInterfaceName %> getWrapped() {
    return (<%=modelInterfaceName %>) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected <%=modelInterfaceName %> getDelegate() {
    return (<%=modelInterfaceName %>) super.getDelegate();
  }

<% wrapped.renderDelegatedDeclaredMethods( wrapped.calculateQualifiedClassName(), request, response ); %>
}