<%--
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 --%><%@page wraps="org.omg.uml13.wrap.foundation.core.WrapUmlClass"
%><%@page import="org.omg.uml13.wrap.foundation.core.*,net.mdatools.modelant.uml14.reverse.Convention"
%><%

  String   className = wrapped.getName();

  String header;
  String footer;
  String path;
  String rootPath;

  header = (String) request.getAttribute("header");
  footer = (String) request.getAttribute("footer"); // default: empty

  path = wrapped.formatQualifiedNamespaceName();
  rootPath = path.replace(".", "/").replaceAll("[a-zA-Z0-9]+","..");

%><html>
<head>
  <!--meta name="robots" value="noindex"/-->
  <meta http-equiv="Content-Language" content="en-us">
  <link rel="stylesheet" type="text/css" href="<%=rootPath %>/stylesheet.css"/>
  <title><%=className %></title>
</head>
<body>
<%=header%>
<table width="100%">
<tr>
<td  align="right"><a target="top" href="<%=rootPath %>/index.html" align="right">TOP</a></td>
<tr>
</table>
  <h2><a name="taskname"><%=className %></a></h2>
  <h3>Description</h3>
<%

  render(wrapped, "renderDescribeAntClass", request, response );

  if ( wrapped.isOfStereotype("macrodef")
       || wrapped.isOfStereotype("typedef")
       || wrapped.isOfStereotype("taskdef") ){

%><br/><b>Use</b> &lt;typedef resource="<%=((WrapModelElement) wrapped.wrap( wrapped.getNamespace())).getTaggedValueString(Convention.TAG_FILE_NAME).replace("\\","/") %>"/&gt;
<%
   }
%>
<%=footer%>
</body>
</html>