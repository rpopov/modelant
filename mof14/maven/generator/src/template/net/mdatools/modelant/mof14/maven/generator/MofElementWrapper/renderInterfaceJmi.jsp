<%--
  This template produces the wrapper class for a class as a model element

  Assumes that the wrapper and the wrapped classes are in the same package, thus the import is skipped

--%><%@
import javax.jmi.model.GeneralizableElement;
import javax.jmi.model.ModelElement;

import net.mdatools.modelant.mof14.maven.generator.MofElementWrapper;

import java.util.List;

%><%

 String className;

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

<% wrapped.renderStatementImportsJmi(engine, context); %>

/**
 * The JMI standard <%=wrapped.calculateSimpleInterfaceName()%> interface
 */
public interface <%=wrapped.calculateSimpleInterfaceName() %> <% wrapped.renderStatementExtends(engine, context); %> {

<%-- wrapped.renderDelegatedDeclaredConstants( delegateClassName, context );
--%>
<%-- wrapped.renderDelegatedDeclaredMethods( delegateClassName, context );
--%>}