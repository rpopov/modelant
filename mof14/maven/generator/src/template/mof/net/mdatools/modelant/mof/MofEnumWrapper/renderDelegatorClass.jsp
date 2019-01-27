<%--
  This template produces the wrapper class for a class as a model element

  Assumes that the wrapper and the wrapped classes are in the same package, thus the import is skipped

--%><%@page wraps="net.mdatools.modelant.mof.MofEnumWrapper"
%><%@page import="net.mdatools.modelant.mof.MofEnumWrapper"
%><%

 String delegatorPackage;

 delegatorPackage = (String) request.getAttribute("component");

%>/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package <%=wrapped.calculateWrapperPackageName( delegatorPackage ) %>;

import net.mdatools.modelant.core.wrap.Factory;
import <%=wrapped.calculateQualifiedBaseWrapperClassName( delegatorPackage ) %>;

/**
 * This is a wrapper of <%=wrapped.calculateQualifiedClassName() %> that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class <%=wrapped.calculateSimpleWrapperClassName() %> extends <%=wrapped.calculateSimpleBaseWrapperClassName() %> {

  public <%=wrapped.calculateSimpleWrapperClassName() %>(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public <%=wrapped.calculateSimpleWrapperClassName() %>(String value, Factory factory) {
    super( value, factory );
  }
}