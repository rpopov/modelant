<%--
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 --%><%@page wraps="org.omg.uml13.wrap.foundation.core.WrapParameter"
%><%@page import="net.mdatools.modelant.util.FormatHelper,
                  org.omg.uml13.wrap.foundation.core.*,
                  org.omg.uml13.foundation.core.*,
                  org.omg.uml13.wrap.foundation.extensionmechanisms.*,
                  org.omg.uml13.foundation.extensionmechanisms.*,
                  net.mdatools.modelant.core.wrap.Factories,
                  java.util.*"
%><%--
  Describes this parameter as an Ant attribite.
  Expected the name of this method is set*() and it has exactly 1 parameter.

--%><%
  Classifier type;
  WrapClassifier wrappedType;
  Collection subclasses;
  Iterator<Classifier> subclassIterator;
  Classifier subclass;
  WrapClassifier wrapSubclass;
  Stereotype stereotype;
  List typesToRender;

  type = wrapped.getType();
  wrappedType = (WrapClassifier) wrapped.wrap( type );

  // list the subclasses of "type" that are defined through typedef
  subclasses = wrappedType.getAllSubclasses();
  typesToRender = new ArrayList();

  subclassIterator = subclasses.iterator();
  while ( subclassIterator.hasNext() ) {
    subclass = subclassIterator.next();

    wrapSubclass = (WrapClassifier) wrapped.wrap(subclass);
    stereotype = wrapSubclass.getStereotype();

    if ( stereotype != null
         && ("typedef".equals( stereotype.getName() )
             || "taskdef".equals( stereotype.getName() ) )
         && !typesToRender.contains(subclass)) {
      typesToRender.add( subclass );
    }
  }

   WrapModelElement.sortByName( typesToRender );

   if ( !typesToRender.isEmpty() ) {
%>      <table border="1" cellspacing="0"  width="100%" class="visible">
        <thead>
          <th valign="top" width="15%"><b>Element</b></th>
          <th valign="top" width="85%"><b>Description</b></th>
        </thead>
        <tbody>
 <%
    subclassIterator = typesToRender.iterator();
    while ( subclassIterator.hasNext() ) {
      subclass = subclassIterator.next();

      wrapSubclass = (WrapClassifier) wrapped.wrap(subclass);

      // avoid infinite loops in nesting elements

      render( wrapSubclass, "renderDescribeNestedTask", request, response);
    }
%>
        </tbody>
    </table>
<%
   }
%>