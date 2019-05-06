<%--
  This template produces the wrapper class for a class as a model element

  Assumes that the wrapper and the wrapped classes are in the same package, thus the import is skipped

--%><%@
import javax.jmi.model.GeneralizableElement;
import javax.jmi.model.ModelElement;
import javax.jmi.model.MofPackage;
import javax.jmi.model.Import;

import net.mdatools.modelant.mof14.maven.generator.MofElementWrapper;
import net.mdatools.modelant.core.filter.Filter;
import net.mdatools.modelant.mof14.maven.generator.condition.IsPubliclyImportedPackage;
import net.mdatools.modelant.mof14.maven.generator.condition.IsPublicPackage;

import java.util.Collection;
import java.util.List;

%><%

 String className;
 MofElementWrapper<MofPackage> wrappedPackage;
 
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
<% wrapped.renderStatementPackageJmi(engine, context);%>

<%  // As of JMI 1.0, section 4.8.1
 
  for (Import element : (Collection<Import>) new Filter(new IsPubliclyImportedPackage()).execute((Collection<ModelElement>) ((MofPackage) wrapped.getWrapped()).getContents())) {
    wrappedPackage = new MofElementWrapper( element.getImportedNamespace() );
    
%>import <%=wrappedPackage.calculateQualifiedPackageProxyName()%>;
<%    
  }
  
  for (MofPackage element : (Collection<MofPackage>) new Filter(new IsPublicPackage()).execute((Collection<ModelElement>) ((MofPackage) wrapped.getWrapped()).getContents())) {
    wrappedPackage = new MofElementWrapper( element );
    
%>import <%=wrappedPackage.calculateQualifiedPackageProxyName()%>;
<%    
  }
  
%>
/**
 * The JMI standard <%=wrapped.calculateSimpleInterfaceName()%> interface
 */
public interface <%=wrapped.calculateSimplePackageProxyName() %> <% wrapped.renderPackageExtendsJmi(engine, context); %> {

<%  // As of JMI 1.0, section 4.8.1
 
  for (Import element : (Collection<Import>) new Filter(new IsPubliclyImportedPackage()).execute((Collection<ModelElement>) ((MofPackage) wrapped.getWrapped()).getContents())) {
    wrappedPackage = new MofElementWrapper( (MofPackage) element.getImportedNamespace() );
    
%>  <%=wrappedPackage.calculateSimplePackageProxyName()%> get<%=wrappedPackage.calculateSimplePackageProxyName()%>();
<%    
  }
  
  for (MofPackage element : (Collection<MofPackage>) new Filter(new IsPublicPackage()).execute((Collection<ModelElement>) ((MofPackage) wrapped.getWrapped()).getContents())) {
    wrappedPackage = new MofElementWrapper( (MofPackage) element );
    
%>  <%=wrappedPackage.calculateSimplePackageProxyName()%> get<%=wrappedPackage.calculateSimplePackageProxyName()%>();
<%    
  }

%>
<%-- wrapped.renderDelegatedDeclaredConstants( delegateClassName, context );
--%>
<%-- wrapped.renderDelegatedDeclaredMethods( delegateClassName, context );
--%>}