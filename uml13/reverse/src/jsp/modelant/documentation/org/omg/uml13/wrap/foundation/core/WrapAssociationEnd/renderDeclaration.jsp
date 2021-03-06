<%--
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 --%><%@page wraps="org.omg.uml13.wrap.foundation.core.WrapAssociationEnd"
%><%@page import="org.omg.uml13.wrap.foundation.core.*,
                  net.mdatools.modelant.util.FormatHelper"
%><%

  WrapClassifier otherEndClass;

  otherEndClass = (WrapClassifier) wrapped.wrap( wrapped.getOtherAssociationEnd().getType() );

%>
<h4><%=otherEndClass.getName() %></h4><%=otherEndClass.getDocumentation() %>