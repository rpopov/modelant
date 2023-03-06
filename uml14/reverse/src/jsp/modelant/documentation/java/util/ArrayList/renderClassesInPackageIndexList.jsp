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
  *   title - the title under which to list those classes
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
                  net.mdatools.modelant.core.wrap.Factories,net.mdatools.modelant.uml14.reverse.Convention,
                  javax.jmi.reflect.*"
%><%

  UmlClass thisClass;
  WrapUmlClass wrappedClass;

  String className;
  String path;
  String rootPath;
  String title;

  Iterator classIterator;

  if ( !wrapped.isEmpty() ) {
    title = (String) request.getAttribute("title");

    WrapModelElement.sortByName( wrapped );

%>
<table width="100%">
  <thead>
    <th align="left"><b><%=title %></b></th>
  </thead>
  <tbody>
<%
    classIterator = wrapped.iterator();
    while ( classIterator.hasNext() ) {
      thisClass = (UmlClass) classIterator.next();

      wrappedClass = (WrapUmlClass) Factories.wrap( thisClass );

      rootPath = wrappedClass.formatPackageName().replaceAll("[a-zA-Z0-9]+(\\.|$)", "../");
      path = wrappedClass.formatPackageName().replace(".", "/");

%><tr><td nowrap><a href="<%=rootPath %><%=path %>/<%=thisClass.getName() %>.html" target="mainFrame"><%=thisClass.getName() %></a></td></tr>
<%
    }
%></tbody></table><br/>
<%
  }
%>