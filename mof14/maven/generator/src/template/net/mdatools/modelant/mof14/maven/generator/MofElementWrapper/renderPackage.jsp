<%--
  This template produces the wrapper class for a class as a model element

  Assumes that the wrapper and the wrapped classes are in the same package, thus the import is skipped

--%><%@
import net.mdatools.modelant.mof14.maven.generator.MofElementWrapper;
%><%

 String componentName;

%>/*
 * Copyright (c) 2001,2019 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package <%=wrapped.calculatePackageName() %>;

/**
 * Extend the JMI standard <%=wrapped.calculateSimpleInterfaceName()%> interface with
 * additional default methods, still keping its JMI compatibility.
 */
public interface <%=wrapped.calculateSimpleInterfaceName() %> extends <%=wrapped.calculateQualifiedJmiInterfaceName() %> {
}