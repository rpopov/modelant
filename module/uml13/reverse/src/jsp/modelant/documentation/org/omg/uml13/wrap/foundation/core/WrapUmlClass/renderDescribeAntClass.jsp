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

  String className = wrapped.getName();
  List<String> serviceNames = (List<String>) request.getAttribute("service.names"); // the set*-method names that are not treated as attributes

  Classifier superclass;
  WrapClassifier wrapSuperclass;

  Collection attributes;
  Collection associationEnds;

  String path;
  String javadocPath;
  String pagePath;

  Set<String> processed;
  Collection superclasses;
  Collection operations;
  List operationsToRender;
  Iterator<Classifier> superclassIterator;
  Iterator<org.omg.uml13.foundation.core.Operation> operationIterator;
  org.omg.uml13.foundation.core.Operation operation;
  WrapOperation wrapOperation;
  Parameter parameter;
  WrapParameter wrapParameter;
  Classifier type;
  WrapClassifier wrapType;

  String name;
  boolean headerPrinted;
  Classifier conditionClass;
  Stereotype stereotype;

  String explicitTextSupport = "";

  wrapSuperclass = (WrapClassifier) wrapped.wrap( wrapped.getSuperclass() );

  processed = new HashSet<String>();
  attributes = wrapped.getAttributes();
  associationEnds = wrapped.getAssociationEnds();

  javadocPath = (String) request.getAttribute("javadoc.dir");

  // set in the request the path to the page that contains this class rendered
  // note: the description of one class might include descriptions of other classes
  //       which complicates the references to the javadoc. Therefore the main class
  //       described on the page just sets the path to the page making it constant for the page's rendering

  path = wrapped.formatQualifiedNamespaceName();
  pagePath = (String) request.getAttribute("page.path");
  if ( pagePath == null ) {
    pagePath = path.replace(".", "/").replaceAll("[a-zA-Z0-9]+","..");
    request.setAttribute("page.path", pagePath);
  }

  conditionClass = WrapDataType.locateDataType( wrapped.getModel(), org.apache.tools.ant.taskdefs.condition.Condition.class.getName()  );

  stereotype = wrapped.getStereotype();

  if ( stereotype != null && "macrodef".equals( stereotype.getName() ) ) { // this is a macro, wrapSuperclass == null

%><%=wrapped.getDocumentation() %><%

    if ( !attributes.isEmpty() ) {

%><h3>Parameters</h3>
<table border="1" cellspacing="0" width="100%" class="visible">
  <thead>
    <th valign="top" width="15%"><b>Attribute</b></th>
    <th valign="top" width="85%"><b>Description</b></th>
  </thead>
  <tbody>
<%
    // declaration of attributes
    render( wrapped.wrap(attributes), "renderDeclaration", request, response);

%>  </tbody>
</table>
<%
    }

    if ( !associationEnds.isEmpty() ) {

%><br/>
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

%>  </tbody>
</table>
<%
    }
  } else { // this is a typedef/taskdef

%><%=wrapped.getDocumentation() %> <%

  if ( wrapSuperclass != null) {
%><%=wrapSuperclass.getDocumentation() %><%
  }


  if ( wrapSuperclass != null
       && javadocPath != null
       && !javadocPath.isEmpty()
       && !wrapSuperclass.formatQualifiedName().startsWith("org.apache.") ) {
    javadocPath = pagePath
                  + "/" + javadocPath
                  + "/"
                  + wrapSuperclass.formatQualifiedName().replace(".", "/");
 %>
<h3>Implementation class</h3>
<a href="<%=javadocPath%>.html"><%=wrapSuperclass.formatQualifiedName() %></a>
<br/>
<%
  }

    // investigate the operations of the superclass(es) to reveal attributes and associations
    superclasses = wrapped.getAllSuperclasses();
    operationsToRender = new ArrayList();

    // identify attributes
    superclassIterator = superclasses.iterator();
    while ( superclassIterator.hasNext() ) {
      superclass = superclassIterator.next();

      // check the set* operations
      operations = ((WrapClassifier) wrapped.wrap( superclass )).getOperations();

      operationIterator = operations.iterator();
      while ( operationIterator.hasNext() ) {
        operation = operationIterator.next();

        wrapOperation = (WrapOperation) wrapped.wrap( operation );

        name = operation.getName();
        if ( name.startsWith("set")
             && operation.getParameter().size() ==  2
             && wrapOperation.isPublic()
             && !processed.contains( name ) ) { // 1 parameter & 1 result & not inherited/overriding ANT service methods

          name = name.substring(3);
          if ( !processed.contains( name )
               && !serviceNames.contains( name )) {
            processed.add( name );

            operationsToRender.add( operation );
          }
        }
      }
    }

    WrapModelElement.sortByName( operationsToRender );

    if ( !operationsToRender.isEmpty() ) {

%><h3>Parameters</h3>
<table border="1" cellspacing="0"  width="100%" class="visible">
  <thead>
    <th valign="top" width="15%"><b>Attribute</b></th>
    <th valign="top" width="75%"><b>Description</b></th>
    <th valign="top" width="10%"><b>Type</b></th>
  </thead>
  <tbody>
<%
      render( wrapped.wrap(operationsToRender), "renderDeclarationAsAntAttribute", request, response );

%></tbody></table>
<%
    }

    headerPrinted = false;

    // identify nested elements
    superclassIterator = superclasses.iterator();
    while ( superclassIterator.hasNext() ) {
      superclass = superclassIterator.next();

      operations = ((WrapClassifier) wrapped.wrap( superclass )).getOperations();

      operationIterator = operations.iterator();
      while ( operationIterator.hasNext() ) {
        operation = operationIterator.next();

        wrapOperation = (WrapOperation) wrapped.wrap( operation );

        name = operation.getName();
        if ( wrapOperation.isPublic() ) {

          if ( name.startsWith("create") && operation.getParameter().size() ==  1 ) {
            name = name.substring(6);
          } else if ( name.startsWith("addConfigured") && operation.getParameter().size() ==  2 ) {
            name = name.substring(13);
            // this may be a type
          } else if ( name.startsWith("add") && operation.getParameter().size() ==  2 ) {
            name = name.substring(3);
            // this may be a type

            if ( name.equals("Text") ) { // explicit text support, no need of element description
               name = null;
               explicitTextSupport = wrapOperation.getDocumentation();
            }
          } else { // the method is not for nested elements support
            name = null;
          }
          if ( name != null ) {
            if ( !headerPrinted ) {
               headerPrinted = true;
%>
<h3>Nested elements</h3>
<table border="1" cellspacing="0"  width="100%" class="visible">
  <thead>
    <th valign="top" width="15%"><b>Element</b></th>
    <th valign="top" width="85%"><b>Description</b></th>
  </thead>
  <tbody>
<%
            }
            parameter = (Parameter) operation.getParameter().get(0);
            wrapParameter = (WrapParameter) wrapped.wrap(parameter);

            type = parameter.getType();
            wrapType = (WrapClassifier) wrapped.wrap( type );

            if ( "Task".equals( name ) ) { // task container - addTask(Task) method
%>  <tr>
    <td valign="top">Any <b>task</b></td>
    <td valign="top"><%=wrapOperation.getDocumentation() %> <%=wrapParameter.getDocumentation() %></td>
  </tr>
<%
            } else if ( name.isEmpty() ) { // type support - add(<type>) method

%>  <tr>
    <td valign="top"><%=wrapOperation.getDocumentation() %> <%=wrapParameter.getDocumentation() %></td>
    <td valign="top">Choose among:
<%
             render( wrapParameter, "renderDeclarationAsAntTypeElement", request, response);

%></td>
  </tr>
<%
            } else { // nested elements support
              if ( !processed.contains( name ) ) {
                processed.add( name );
%>  <tr>
    <td valign="top"><%= net.mdatools.modelant.util.FormatHelper.formatFirstLower( name ) %></td>
    <td valign="top"><%= wrapOperation.getDocumentation() %> <%=wrapParameter.getDocumentation() %>
<%
                render( wrapType, "renderDescribeAntClass", request, response);

%>    </td>
  </tr>
<%
              }
            }
          }
        }
      }
    }
    if ( headerPrinted ) {
%></tbody></table><%
    }
    if ( explicitTextSupport != null && !explicitTextSupport.isEmpty()  ) {
%><br/><b>Any nested text</b> <%= explicitTextSupport%><%
    }
  }
%>