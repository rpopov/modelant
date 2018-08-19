<%--
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 --%><%@page wraps="org.omg.uml13.wrap.foundation.core.WrapAttribute"
%><%@page import="net.mdatools.modelant.util.FormatHelper"
%><%--
  Generate the field definitions representing the class
  attributes. All attributes are defined as private.

--%><%
  String initialValue;
  boolean isConstant = wrapped.isConstant();

%>  <tr>
    <td valign="top"><%=wrapped.getName() %></td>
    <td valign="top"><%=wrapped.getDocumentation() %></td>
  </tr>