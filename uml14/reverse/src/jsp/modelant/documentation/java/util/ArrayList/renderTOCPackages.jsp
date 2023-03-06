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
  * Render a root list of packages shown in the left upper frame
--%><%@page wraps="java.util.List"
%><%@page import="org.omg.uml13.wrap.foundation.core.*,
                  org.omg.uml13.foundation.core.*,
                  org.omg.uml13.wrap.modelmanagement.*,
                  org.omg.uml13.modelmanagement.*,
                  net.mdatools.modelant.core.wrap.Factories,net.mdatools.modelant.uml14.reverse.Convention,
                  java.util.*"
%><%

  List allPackages;
  List wrappedPackages;

  UmlPackage thisPackage;

  WrapUmlPackage wrapPackage;

  String className;
  String path;
  String printName;

  Iterator classIterator;
  Iterator packageIterator;

  final Set<String> libs = new TreeSet<String>();

%><html>
<head>
<meta name="robots" value="noindex" />
<meta http-equiv="Content-Language" content="en-us"/>
<link rel="stylesheet" type="text/css" href="stylesheet.css"/>
<base target="mainFrame"/>
</head>
<body>
<%
    WrapModelElement.sortByQualifiedName( wrapped );

    allPackages = WrapModelElement.filter( wrapped, WrapUmlPackage.getStereotypedElementConditionF(new String[]{"project"}) );

    if ( !allPackages.isEmpty() ) {
%>
<table width="100%">
<thead>
  <th align="left"><b>Scripts</b></th>
</thead>
<tbody>
<%
      packageIterator = allPackages.iterator();
      while ( packageIterator.hasNext() ) {
        thisPackage = (UmlPackage) packageIterator.next();
        wrapPackage = (WrapUmlPackage) Factories.wrap( thisPackage );

        path = wrapPackage.formatQualifiedName().replace(".", "/");

%><tr><td nowrap><a href="<%=path %>/package-frame.html" target="bottomNavFrame"><%=wrapPackage.formatQualifiedName() %></a></td></tr>
<%    }
%></tbody></table><br/>
<%
    }

    allPackages = WrapModelElement.filter( wrapped, WrapUmlPackage.getStereotypedElementConditionF(new String[]{"antlib"}) );

    // extract the unique simple package names (i.e. file names)
    for (UmlPackage p : (List<UmlPackage>) allPackages) {
      wrapPackage = (WrapUmlPackage) Factories.wrap( p );
      libs.add( wrapPackage.getLogicalName() );
    }

    for ( String libName : libs ) {
      printName = net.mdatools.modelant.util.FormatHelper.formatFirstUpper(libName);

%>
<table width="100%">
<thead>
  <th align="left"><b><a href="toc-<%=libName %>-classes.html" target="bottomNavFrame"><%=printName %></a></b></th>
</thead>
<tbody>
<%
      packageIterator = allPackages.iterator();
      while ( packageIterator.hasNext() ) {
        thisPackage = (UmlPackage) packageIterator.next();
        wrapPackage = (WrapUmlPackage) Factories.wrap( thisPackage );

        if ( wrapPackage.getLogicalName().equals(libName) ) {
          path = wrapPackage.formatQualifiedName().replace(".", "/");

%><tr><td nowrap><a href="<%=path %>/package-frame.html" target="bottomNavFrame"><%=wrapPackage.formatQualifiedNamespaceName() %></a></td></tr>
<%      }
      }
%></tbody></table><br/>
<%
    }
%>
<a href="toc-classes.html" target="bottomNavFrame">All tasks</a><br/><br/>
</body>
</html>