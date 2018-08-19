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
  *
  * Render a list of classes, assumed to pertain to the same package
  *
--%><%@page wraps="org.omg.uml13.wrap.modelmanagement.WrapUmlPackage"
%><%@page import="org.omg.uml13.wrap.modelmanagement.WrapUmlPackage,
                  java.util.*,
                  org.omg.uml13.wrap.foundation.core.*,
                  org.omg.uml13.foundation.core.*,
                  org.omg.uml13.wrap.foundation.extensionmechanisms.*,
                  org.omg.uml13.foundation.extensionmechanisms.*,
                  org.omg.uml13.wrap.modelmanagement.*,
                  org.omg.uml13.modelmanagement.*,
                  net.mdatools.modelant.core.wrap.Factories,
                  net.mdatools.modelant.uml13.reverse.Convention,
                  javax.jmi.reflect.*"
%><%
  List allClasses;
  List allConditions;

  String path;
  String cssPath;

  path = wrapped.formatQualifiedName();
  cssPath = path.replace(".", "/").replaceAll("[a-zA-Z0-9]+","..");

%><html>

<head>
  <meta name="robots" value="noindex" />
  <meta http-equiv="Content-Language" content="en-us"/>
  <link rel="stylesheet" type="text/css" href="<%=cssPath %>/stylesheet.css"/>
  <title>Contents of package <%=wrapped.formatQualifiedName() %></title>
</head>
<body>
<table width="100%">
<tr><td nowrap><a href="package-summary.html" target="mainFrame"><%=wrapped.formatQualifiedNamespaceName() %></a>
</td></tr></table>
<br/>
<%
    allClasses = wrapped.filter( wrapped.getOwnedElement(), wrapped.getStereotypedElementCondition(new String[]{"target"}) );
    if ( !allClasses.isEmpty() ) {
      request.setAttribute("title", "Targets");
      render( (Object) allClasses, "renderClassesInPackageIndexList", request, response );
    }

    allClasses = wrapped.filter( wrapped.getOwnedElement(), wrapped.getStereotypedElementCondition(new String[]{"taskdef", "macrodef"}) );
    if ( !allClasses.isEmpty() ) {
      request.setAttribute("title", "Tasks and Macros");
      render( (Object) allClasses, "renderClassesInPackageIndexList", request, response );
    }

    allConditions = wrapped.filter( (Collection<ModelElement>) wrapped.getOwnedElement(),
                                    WrapDataType.getSubclassesCondition( org.apache.tools.ant.taskdefs.condition.Condition.class.getName() ) );
    if ( !allConditions.isEmpty() ) {
      request.setAttribute("title", "Conditions");
      render( (Object) allConditions, "renderClassesInPackageIndexList", request, response );
    }

    allClasses = wrapped.filter( wrapped.getOwnedElement(), wrapped.getStereotypedElementCondition(new String[]{"typedef"}) );
    allClasses.removeAll( allConditions );

    // filter only general purpose types, assuming the scrip-specific custom types would be described in their use pages
    allClasses = WrapModelElement.filter( allClasses, WrapClassifier.getAntStandardTypeCondition() );

    if ( !allClasses.isEmpty() ) {
      request.setAttribute("title", "Types");
      render( (Object) allClasses, "renderClassesInPackageIndexList", request, response );
    }

%>
</body>
</html>