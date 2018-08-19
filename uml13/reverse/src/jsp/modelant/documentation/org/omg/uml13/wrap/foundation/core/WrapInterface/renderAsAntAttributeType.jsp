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
  * This template renders an ANT description of a typedef/macrodef/taskdef
  *
--%><%@page wraps="org.omg.uml13.wrap.foundation.core.WrapInterface"
%><%@page import="net.mdatools.modelant.util.FormatHelper"
%><%

  final String STD_CLASS = "java.lang.";
  final String UTL_CLASS = "java.util.";
  final String ANT_CLASS = "org.apache.tools.ant.types.";

  String   name = wrapped.getName();

  if ( name.startsWith( STD_CLASS ) ) {
    name = name.substring( STD_CLASS.length() );
  } else if ( name.startsWith( UTL_CLASS ) ) {
    name = name.substring( UTL_CLASS.length() );
  } else if ( name.startsWith( ANT_CLASS ) ) {
    name = name.substring( ANT_CLASS.length() );
  }
%><%= name %>