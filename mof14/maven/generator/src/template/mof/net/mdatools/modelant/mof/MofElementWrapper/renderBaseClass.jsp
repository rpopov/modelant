<%--
  This template produces the wrapper class for a class as a model element

  Assumes that the wrapper and the wrapped classes are in the same package, thus the import is skipped

--%><%@page wraps="net.mdatools.modelant.mof.MofElementWrapper"
%><%@page import="net.mdatools.modelant.mof.MofElementWrapper, javax.jmi.model.Namespace"
%><%

 String className = wrapped.calculateSimpleBaseWrapperClassName();
 String modelInterfaceName = wrapped.calculateSimpleClassName();

 String rootPackageName;
 String factoryClassName;
 String wrapperPackage;
 String delegateClassName;

 wrapperPackage  = (String) request.getAttribute("component");
 rootPackageName = (String) request.getAttribute("metamodel.root.package");
 factoryClassName= (String) request.getAttribute("factory.class.name");

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
package <%=wrapped.calculateBaseWrapperPackageName( wrapperPackage ) %>;

<%
  if ( !wrapped.isAbstract() ) {

%>import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
<%
  }
%>import <%=wrapped.calculateQualifiedClassName() %>;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of <%=wrapped.calculateQualifiedClassName() %> that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class <%=className %> extends <%=wrapped.calculateQualifiedSuperclassWrapperName( wrapperPackage ) %> <% wrapped.renderImplementedInterfaces( delegateClassName, request, response); %>{

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
   * Constructs a new wrapped object
   */
  public <%=className %>(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( <%= factoryClassName %>.class ));
    Factories.getFactory( <%= factoryClassName %>.class ).bind( this );
  }

  /**
   * This method retrieves the factory for <%=wrapped.calculateQualifiedClassName() %> (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((<%=rootPackageName %>) extent)<%

    for (String name : wrapped.calculateQualifiedMofName()) {
%>.get<%=net.mdatools.modelant.util.FormatHelper.formatCapitalizeFirst(name) %>()<%
    }
    %>;
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

<% wrapped.renderDelegatedDeclaredMethods( delegateClassName, request, response );
%>}