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
%><%@page import="net.mdatools.modelant.util.FormatHelper,
                  org.omg.uml13.wrap.foundation.core.*,
                  org.omg.uml13.foundation.core.*,
                  org.omg.uml13.foundation.extensionmechanisms.Stereotype,
                  net.mdatools.modelant.core.wrap.Factories,
                  java.util.*"
%><%
  // Describe the current class as a task definition as part of another task's description, with no attributes and nested elements
  // to avoid infinite loops.
  // It is called alwais nested in the context of a renderDeclarationAsAntTypeElement

  String path;
  String javadocPath;
  String pagePath;
  WrapClassifier wrapSuperclass;

  wrapSuperclass = (WrapClassifier) wrapped.wrap( wrapped.getSuperclass() );
  
  
  javadocPath = (String) request.getAttribute("javadoc.dir");

  // set in the request the path to the page that contains this class rendered
  // note: the description of one class might include descriptions of other classes
  //       which complicates the references to the javadoc. Therefore the main class
  //       described on the page just sets the path to the page making it constant for the page's rendering

  path = wrapped.formatQualifiedNamespaceName();
  pagePath = (String) request.getAttribute("page.path");
  assert pagePath != null : "Expected a page path provided";

  javadocPath = pagePath
                + "/"
                + wrapped.formatQualifiedNamespaceName().replace(".", "/")
                + "/"
                + wrapped.getName();
                  
%><tr>
    <td valign="top"><a href="<%=javadocPath%>.html"><%=wrapped.getName() %></a></td>
    <td valign="top"><%=wrapped.getDocumentation() %><%
     if ( wrapSuperclass != null ) {
      %> <%=wrapSuperclass.getDocumentation() %><%
     } 
    %></td>
  </tr>