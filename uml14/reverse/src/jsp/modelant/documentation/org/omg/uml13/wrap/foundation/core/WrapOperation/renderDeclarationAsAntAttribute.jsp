<%--
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 --%><%@page wraps="org.omg.uml13.wrap.foundation.core.WrapOperation"
%><%@page import="net.mdatools.modelant.util.FormatHelper,
                  org.omg.uml13.wrap.foundation.core.*,
                  org.omg.uml13.foundation.core.*"
%><%--
  Describes this method as an Ant attribite.
  Expected the name of this method is set*() and it has exactly 1 parameter.

--%><%
  String initialValue;
  boolean isConstant = wrapped.isConstant();
  String name;
  Parameter parameter;
  WrapParameter viewParameter;

  name = wrapped.getName().substring(3);
  name = net.mdatools.modelant.util.FormatHelper.formatFirstLower( name );

  parameter = wrapped.getParameters().get(0); // still to check if there are

  viewParameter = (WrapParameter) wrapped.wrap( parameter );

%>  <tr>
    <td valign="top"><%=name %></td>
    <td valign="top"><%=wrapped.getDocumentation() %> <%=viewParameter.getDocumentation() %></td>
    <td valign="top"><% render( wrapped.wrap(parameter.getType()), "renderAsAntAttributeType", request, response); %></td>
  </tr>