<%--
  This template produces the wrapper class for a class as a model element

--%><%@page wraps="net.mdatools.modelant.mof.MofElementWrapper"
%><%@page import="net.mdatools.modelant.mof.MofElementWrapper, javax.jmi.model.Namespace"
%><%

 String className = wrapped.calculateSimpleBaseWrapperClassName();
 String subClassName = wrapped.calculateSimpleWrapperClassName();

 String modelInterfaceName = wrapped.calculateSimpleClassName();

 String rootPackageName;
 String wrapperPackage;
 String delegatorPackage;
 String factoryClassName;
 String delegateClassName;

 rootPackageName = (String) request.getAttribute("metamodel.root.package");
 factoryClassName= (String) request.getAttribute("factory.class.name");
 delegatorPackage= (String) request.getAttribute("component");
 wrapperPackage  = (String) request.getAttribute("wrapped.component");

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

<%
  if ( !wrapped.isAbstract() ) {

%>import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
<%
  }
%>import <%=wrapped.calculateQualifiedClassName() %>;
import <%=wrapped.calculateQualifiedWrapperClassName( delegatorPackage ) %>;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of <%=delegateClassName %> that allows adding specific
 * custom methods and using them as templates for [code] generation.
 * It inherits through delegation from <%=delegateClassName %>.
 */
public abstract class <%=className %> extends <%=wrapped.calculateQualifiedSuperclassWrapperName( delegatorPackage ) %> <% wrapped.renderImplementedInterfaces(delegateClassName, request, response); %>{

<% wrapped.renderDelegatedDeclaredConstants( delegateClassName, request, response );
%>  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected <%=className %>(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

<%
  if ( !wrapped.isAbstract() ) {


%>  /**
   * Constructs a new object to wrap
   */
  public <%=className %>(RefPackage extent) {
    this( new <%=delegateClassName %>(extent),
          Factories.getFactory( <%=factoryClassName %>.class ) );
    Factories.getFactory( <%=factoryClassName %>.class ).bind( this );
  }
<%
   }

   if ( wrapped.isRoot() ) {
%>
   /**
    * The factory instance of this wrapper
    */
   protected final <%=factoryClassName %> getFactory() {
     return (<%=factoryClassName %>) super.getFactory();
   }
<%
   }
%>

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected <%=delegateClassName %> getDelegate() {
    return (<%=delegateClassName %>) super.getDelegate();
  }

<% wrapped.renderDelegatedDeclaredMethods( delegateClassName, request, response );
%>}