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
 * Parameter
 *   title - the title to show
 *
--%><%@page wraps="java.util.ArrayList"
%><%@page import="org.omg.uml13.wrap.modelmanagement.WrapUmlPackage,
                  java.util.*,
                  org.omg.uml13.wrap.foundation.core.*,
                  org.omg.uml13.foundation.core.*,
                  org.omg.uml13.wrap.foundation.extensionmechanisms.*,
                  org.omg.uml13.foundation.extensionmechanisms.*,
                  org.omg.uml13.wrap.modelmanagement.*,
                  org.omg.uml13.modelmanagement.*,
                  net.mdatools.modelant.uml13.reverse.Convention,
                  net.mdatools.modelant.core.wrap.Factories"
%><%

  UmlClass thisClass;
  WrapUmlClass wrapClass;
  WrapClassifier wrapSuperClass;

  String className;
  String path;
  String rootPath;
  String title;

  Iterator classIterator;

  if ( !wrapped.isEmpty() ) {

    title = (String) request.getAttribute("title");

%>
<table border="1" cellspacing="0" width="100%" class="visible">
  <thead>
    <th valign="top" width="15%"><b><%=title %></b></th>
    <th valign="top" width="85%"><b>Description</b></th>
  </thead>
  <tbody>
<%
    WrapModelElement.sortByName( wrapped );

    classIterator = wrapped.iterator();
    while ( classIterator.hasNext() ) {
      thisClass = (UmlClass) classIterator.next();
      wrapClass = (WrapUmlClass) Factories.wrap( thisClass );
      wrapSuperClass = (WrapClassifier) Factories.wrap( wrapClass.getSuperclass() );

      rootPath = wrapClass.formatPackageName().replaceAll("[a-zA-Z0-9]+(\\.|$)", "../");
      path = wrapClass.formatPackageName().replace(".", "/");

%><tr>
  <td valign="top" width="15%"><a href="<%=rootPath %><%=path %>/<%=thisClass.getName() %>.html" target="mainFrame"><%=thisClass.getName() %></a></td>
  <td valign="top" width="85%"><%=wrapClass.getDocumentation() %><%
     if ( wrapSuperClass != null ) {
      %> <%=wrapSuperClass.getDocumentation() %><%
     }
   %></td>
</tr>
<%
    }
%></tbody></table><br/>
<%
  }
%>