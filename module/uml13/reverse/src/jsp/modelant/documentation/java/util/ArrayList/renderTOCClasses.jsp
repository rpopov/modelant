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

  * Render a root list of classes shown in the left lower frame

--%><%@page wraps="java.util.List"
%><%@page import="java.util.*,
                  org.omg.uml13.wrap.foundation.core.*,
                  org.omg.uml13.foundation.core.*,
                  org.omg.uml13.wrap.foundation.extensionmechanisms.*,
                  org.omg.uml13.foundation.extensionmechanisms.*,
                  org.omg.uml13.wrap.modelmanagement.*,
                  org.omg.uml13.modelmanagement.*,
                  net.mdatools.modelant.core.wrap.Factories,
                  javax.jmi.reflect.*"
%><%

  List allClasses;
  List allConditions;

%><html>

<head>
<meta name="robots" value="noindex" />
<meta http-equiv="Content-Language" content="en-us"/>
<link rel="stylesheet" type="text/css" href="stylesheet.css"/>
</head>
<body>
<%
    // NOTE: the targets are not listed intensionally, becasue they mean nothng without the script they are defined in
    WrapModelElement.sortByName( wrapped );

    allClasses = WrapModelElement.filter( wrapped, WrapUmlPackage.getStereotypedElementConditionF(new String[]{"taskdef", "macrodef"}) );
    if ( !allClasses.isEmpty() ) {
      request.setAttribute("title", "Tasks and Macros");
      render( (Object) allClasses, "renderTOCClassesList", request, response );
    }

    allConditions = WrapModelElement.filter( (Collection<ModelElement>) wrapped,
                                             WrapDataType.getSubclassesCondition( org.apache.tools.ant.taskdefs.condition.Condition.class.getName() ) );
    if ( !allConditions.isEmpty() ) {
      request.setAttribute("title", "Conditions");
      render( (Object) allConditions, "renderTOCClassesList", request, response );
    }

    allClasses = WrapModelElement.filter( wrapped, WrapUmlPackage.getStereotypedElementConditionF(new String[]{"typedef"}) );
    allClasses.removeAll( allConditions );

    // filter only general purpose types, assuming the script-specific custom types would be described in their use pages
    allClasses = WrapModelElement.filter( allClasses, WrapClassifier.getAntStandardTypeCondition() );

    if ( !allClasses.isEmpty() ) {
      request.setAttribute("title", "Types");
      render( (Object) allClasses, "renderTOCClassesList", request, response );
    }
%>
</body>
</html>