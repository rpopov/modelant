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
  * Render an ANT description of a typedef/macrodef/taskdef
  *
--%><%@page wraps="org.omg.uml13.wrap.foundation.core.WrapUmlClass"
%><%@page import="net.mdatools.modelant.util.FormatHelper,
                  org.omg.uml13.foundation.core.*,
                  org.omg.uml13.wrap.foundation.core.*,
                  java.util.*"
%><%

  String   className = wrapped.getName();

  Classifier superclass;
  WrapClassifier viewSuperclass;

  Collection attributes;
  Collection associationEnds;

  String path;
  String javadocPath;
  String cssPath;

  Set<String> processed;
  Collection superclasses;
  Collection operations;
  Iterator<Classifier> superclassIterator;
  Iterator<org.omg.uml13.foundation.core.Operation> operationIterator;
  org.omg.uml13.foundation.core.Operation operation;
  WrapOperation viewOperation;
  WrapParameter wrapParameter;

  String name;

  viewSuperclass = (WrapClassifier) wrapped.wrap( wrapped.getSuperclass() );

  processed = new HashSet<String>();
  attributes = wrapped.getAttributes();
  associationEnds = wrapped.getAssociationEnds();

  javadocPath = (String) request.getAttribute("javadoc.dir");

  path = wrapped.formatQualifiedNamespaceName();
  cssPath = path.replace(".", "/").replaceAll("[a-zA-Z0-9]+","..");

%><html>
<head>
  <!--meta name="robots" value="noindex" /-->
  <meta http-equiv="Content-Language" content="en-us">
  <link rel="stylesheet" type="text/css" href="<%=cssPath %>/stylesheet.css"/>
  <title><%=className %></title>
</head>
<body>
<h2><a name="taskname"><%=className %></a></h2>
<h3>Description</h3>
<%
  if ( viewSuperclass == null ) {   // this is a macro

%><%=wrapped.getDocumentation() %><%

    if ( !attributes.isEmpty() ) {

%><h3>Parameters</h3>
<table border="1" cellspacing="0" class="visible">
  <thead>
    <th valign="top" width="15%"><b>Attribute</b></th>
    <th valign="top" width="75%"><b>Description</b></th>
    <th valign="top" width="10%"><b>Required</b></th>
  </thead>
  <tbody>
<%
    // declaration of attributes
    render( wrapped.wrap(attributes), "renderDeclaration", request, response);
%>
  </tbody>
</table>
<%
    }

    if ( !associationEnds.isEmpty() ) {

%>
<br/>
<h3>Nested elements</h3>
<table border="1" cellspacing="0"  width="100%" class="visible">
  <thead>
    <th valign="top" width="15%"><b>Element</b></th>
    <th valign="top" width="85%"><b>Description</b></th>
  </thead>
  <tbody>
<%
      // declaration of association attributes
      render( wrapped.wrap(associationEnds), "renderDeclarationAsAntElement", request, response);
    }
%>  </tbody>
</table>
<%
  } else { // this is a typedef/taskdef

%><%=wrapped.getDocumentation() %><%=viewSuperclass.getDocumentation() %><%

    if ( javadocPath != null
         && !javadocPath.isEmpty()
         && !viewSuperclass.formatQualifiedName().startsWith("org.apache.") ) {
      path = wrapped.formatQualifiedNamespaceName();
      javadocPath = path.replace(".", "/").replaceAll("[a-zA-Z0-9]+","..")
                    + "/" + javadocPath
                    + "/"
                    + viewSuperclass.formatQualifiedName().replace(".","/");
    } else {
      javadocPath = null;
    }

%>
<h3>Implementation class</h3>
<% if ( javadocPath != null ) { %><a href="<%=javadocPath%>.html"><% } %><%=viewSuperclass.formatQualifiedName() %></a>
<br/>
<h3>Parameters</h3>
<table border="1" cellspacing="0"  width="100%" class="visible">
  <thead>
    <th valign="top" width="15%"><b>Attribute</b></th>
    <th valign="top" width="75%"><b>Description</b></th>
    <th valign="top" width="10%"><b>Type</b></th>
  </thead>
  <tbody>
<%

    // investigate the operations of the superclass(es) to reveal attributes and associations
    superclasses = wrapped.getAllSuperclasses();

    // identify attributes
    superclassIterator = superclasses.iterator();
    while ( superclassIterator.hasNext() ) {
      superclass = superclassIterator.next();

      // check the set* operations
      operations = ((WrapClassifier) wrapped.wrap( superclass )).getOperations();

      operationIterator = operations.iterator();
      while ( operationIterator.hasNext() ) {
        operation = operationIterator.next();

        viewOperation = (WrapOperation) wrapped.wrap( operation );

        name = operation.getName();
        if ( name.startsWith("set")
             && operation.getParameter().size() ==  2
             && viewOperation.isPublic() ) { // 1 parameter & 1 result

          name = name.substring(3);
          if ( !processed.contains( name ) ) {
            processed.add( name );

            render( wrapped.wrap(operation), "renderDeclarationAsAntAttribute", request, response);
          }
        }
      }
    }

%>
  </tbody>
</table>
<h3>Nested elements</h3>
<table border="1" cellspacing="0"  width="100%" class="visible">
  <thead>
    <th valign="top" width="15%"><b>Element</b></th>
    <th valign="top" width="85%"><b>Description</b></th>
  </thead>
<tbody>
<%

    // identify nested elements
    superclassIterator = superclasses.iterator();
    while ( superclassIterator.hasNext() ) {
      superclass = superclassIterator.next();

      operations = ((WrapClassifier) wrapped.wrap( superclass )).getOperations();

      operationIterator = operations.iterator();
      while ( operationIterator.hasNext() ) {
        operation = operationIterator.next();

        viewOperation = (WrapOperation) wrapped.wrap( operation );

        name = operation.getName();
        if ( viewOperation.isPublic() ) {

          if ( name.startsWith("create") && operation.getParameter().size() ==  1 ) {
            name = name.substring(6);
          } else if ( name.startsWith("addConfigured") && operation.getParameter().size() ==  2 ) {
            name = name.substring(13);
            // this may be a type
          } else if ( name.startsWith("add") && operation.getParameter().size() ==  2 ) {
            name = name.substring(3);
            // this may be a type

          } else { // the method is not for nested elements support
            name = null;
          }
          if ( name != null ) {
            wrapParameter = ((WrapParameter) wrapped.wrap(operation.getParameter().get(0)));

            if ( name.isEmpty() ) { // type support
              render( wrapParameter, "renderDeclarationAsAntTypeElement", request, response);

            } else if ( "Task".equals( name ) ) { // task container
%>  <tr>
    <td valign="top">any Ant task</td>
    <td valign="top"><%=viewOperation.getDocumentation() %> <%=wrapParameter.getDocumentation() %></td>
  </tr>
<%
            } else { // nested elements support
              if ( !processed.contains( name ) ) {
                processed.add( name );
%>  <tr>
    <td valign="top"><%=name %></td>
    <td valign="top"><%=viewOperation.getDocumentation() %> <%=wrapParameter.getDocumentation() %></td>
  </tr>
<%
              }
            }
          }
        }
      }
    }
%>
  </tbody>
</table>
<%
  }

%><b>Use</b> &lt;typedef resource="<%=wrapped.formatQualifiedNamespaceName() %>.xml"/&gt;
</body>
</html>