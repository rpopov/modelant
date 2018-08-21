<%--
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 --%><%--
  * This remplate renders the list of all packages shown as the root page in the right frame
--%><%@page wraps="java.util.List"
%><%@page import="org.omg.uml13.wrap.foundation.core.*,
                  org.omg.uml13.foundation.core.*,
                  org.omg.uml13.wrap.modelmanagement.*,
                  org.omg.uml13.modelmanagement.*,
                  net.mdatools.modelant.core.wrap.Factories,
                  net.mdatools.modelant.uml13.reverse.Convention,
                  java.util.*"
%><%

  List allPackages;

  UmlPackage thisPackage;

  WrapUmlPackage wrapPackage;

  String className;
  String path;

  Iterator classIterator;
  Iterator packageIterator;

  String header;
  String footer;

  header = (String) request.getAttribute("header");
  footer = (String) request.getAttribute("footer"); // default: empty

%><html>

<head>
<meta http-equiv="Content-Language" content="en-us"/>
<meta name="robots" value="noindex" />
<link rel="stylesheet" type="text/css" href="stylesheet.css"/>
<base target="mainFrame"/>
</head>
<body>
<%=header %>
<table width="100%">
<tr>
<td align="right"><a target="top" href="index.html" align="right">TOP</a></td>
<tr>
</table>
<%
    WrapModelElement.sortByQualifiedName( wrapped );

    allPackages = WrapModelElement.filter( wrapped, WrapUmlPackage.getStereotypedElementConditionF(new String[]{"project"}) );
    if ( !allPackages.isEmpty() ) {
%>
<h2>Scripts</h2>

<table border="1" cellpadding="2" cellspacing="0" width="100%" class="visible">
  <thead>
    <th valign="top" width="25%"><b>Script</b></th>
    <th valign="top" width="75%"><b>Description</b></th>
  </thead>
  <tbody>
<%
      packageIterator = allPackages.iterator();
      while ( packageIterator.hasNext() ) {
        thisPackage = (UmlPackage) packageIterator.next();

        wrapPackage = (WrapUmlPackage) Factories.wrap( thisPackage );

        path = wrapPackage.formatQualifiedName().replace(".", "/");

%><tr>
  <td valign="top"><%=wrapPackage.getLogicalName() %><br/>
<a href="<%=path %>/package-summary.html"><%=wrapPackage.formatQualifiedNamespaceName() %></a></td>
  <td valign="top"><%=wrapPackage.getDocumentation() %></td>
</tr>
<%
      }
%>  </tbody>
</table><br/>
<%
    }

    allPackages = WrapModelElement.filter( wrapped, WrapUmlPackage.getStereotypedElementConditionF(new String[]{"antlib"}) );

    if ( !allPackages.isEmpty() ) {

%>
<h2>Libraries</h2>

Following the ModelAnt installation instructions, the .jar files of the ModelAnt
components are always in the ANT's classpath. Therefore using ModelAnt is quite
simple:<pre>

&lt;typedef resource="path_1/antlib.xml"/&gt;
&lt;typedef resource="path_2/antlib.xml"/&gt;
</pre>
<br/>
<table border="1" cellpadding="2" cellspacing="0" width="100%" class="visible">
  <thead>
    <th valign="top" width="25%"><b>Library</b></th>
    <th valign="top" width="75%"><b>Description</b></th>
  </thead>
  <tbody>
<%
      packageIterator = allPackages.iterator();
      while ( packageIterator.hasNext() ) {
        thisPackage = (UmlPackage) packageIterator.next();

        wrapPackage = (WrapUmlPackage) Factories.wrap( thisPackage );

        path = wrapPackage.formatQualifiedName().replace(".", "/");

%><tr>
  <td valign="top"><%=wrapPackage.getLogicalName() %><br/><a href="<%=path %>/package-summary.html"><%=wrapPackage.formatQualifiedNamespaceName() %></a></td>
  <td valign="top"><%=wrapPackage.getDocumentation() %></td>
</tr>
<%
      }
%>  </tbody>
</table><br/><br/>
<%
    }
%>
<%=footer%>
</body>
</html>