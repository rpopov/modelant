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
                  org.omg.uml13.modelmanagement.*,net.mdatools.modelant.uml14.reverse.Convention,
                  net.mdatools.modelant.core.wrap.Factories"
%><%
  List allClasses;
  List allConditions;

  String header;
  String footer;
  String rootPath;

  header = (String) request.getAttribute("header");
  footer = (String) request.getAttribute("footer"); // default: empty

  rootPath = wrapped.formatQualifiedName().replace(".", "/").replaceAll("[a-zA-Z0-9]+","..");

%><html>

<head>
  <meta name="robots" value="noindex" />
  <meta http-equiv="Content-Language" content="en-us"/>
  <link rel="stylesheet" type="text/css" href="<%=rootPath %>/stylesheet.css"/>
</head>
<body>
<%=header %>
<table width="100%">
<tr>
<td  align="right"><a target="top" href="<%=rootPath %>/index.html" align="right">TOP</a></td>
<tr>
</table>
<H2><%
  if ( "project".equals(wrapped.getStereotype().getName() ) ) {
%>Script <%

   } else {
%>Library <%
   }
%><%=wrapped.getTaggedValueString(Convention.TAG_FILE_NAME).replace("\\","/") %></H2>
<%=wrapped.getDocumentation() %>
<%
    allClasses = wrapped.filter( wrapped.getOwnedElement(), wrapped.getStereotypedElementCondition(new String[]{"target"}) );
    if ( !allClasses.isEmpty() ) {
      request.setAttribute("title", "Targets");
      render( (Object) allClasses, "renderClassesInPackageSummaryList", request, response );
   }

    allClasses = wrapped.filter( wrapped.getOwnedElement(), wrapped.getStereotypedElementCondition(new String[]{"taskdef", "macrodef"}) );
    if ( !allClasses.isEmpty() ) {
      request.setAttribute("title", "Tasks and Macros");
      render( (Object) allClasses, "renderClassesInPackageSummaryList", request, response );
   }

   allConditions = wrapped.filter( (Collection<ModelElement>) wrapped.getOwnedElement(),
                                    WrapDataType.getSubclassesCondition( org.apache.tools.ant.taskdefs.condition.Condition.class.getName() ) );
   if ( !allConditions.isEmpty() ) {
     request.setAttribute("title", "Conditions");
     render( (Object) allConditions, "renderClassesInPackageSummaryList", request, response );
   }

   allClasses = wrapped.filter( wrapped.getOwnedElement(), wrapped.getStereotypedElementCondition(new String[]{"typedef"}) );
   allClasses.removeAll( allConditions );

    // filter only general purpose types, assuming the script-specific custom types would be described in their use pages
    allClasses = WrapModelElement.filter( allClasses, WrapClassifier.getAntStandardTypeCondition() );

   if ( !allClasses.isEmpty() ) {
     request.setAttribute("title", "Types");
     render( (Object) allClasses, "renderClassesInPackageSummaryList", request, response );
   }
%>
<%=footer %>
</body>
</html>