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
  * Render a root list of classes shown in the left lower frame
  *
  * Parameters:
  *   title - the title to print
  *
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

  String title;

  UmlClass thisClass;
  UmlPackage thisPackage;
  Stereotype stereotype;

  WrapUmlClass wrapClass;
  WrapUmlPackage wrapPackage;

  String className;
  String path;

  Iterator classIterator;

  title = (String) request.getAttribute("title");

  // NOTE: the targets are not listed intensionally, becasue they mean nothng without the script they are defined in

  if ( !wrapped.isEmpty() ) {

%><table width="100%">
<thead>
  <th align="left"><b><%=title%></b></th>
</thead>
<tbody>
<%
    WrapModelElement.sortByName( wrapped );

    classIterator = wrapped.iterator();
    while ( classIterator.hasNext() ) {
      thisClass = (UmlClass) classIterator.next();

      thisPackage = (UmlPackage) thisClass.getNamespace();
      wrapPackage = (WrapUmlPackage) Factories.wrap( thisPackage );

      path = wrapPackage.formatQualifiedName().replace(".", "/");

%><tr><td nowrap><a href="<%=path %>/<%=thisClass.getName() %>.html" target="mainFrame"><%=thisClass.getName() %></a></td></tr>
<%
    }
%></tbody></table>
<br/>
<%
  }
%>