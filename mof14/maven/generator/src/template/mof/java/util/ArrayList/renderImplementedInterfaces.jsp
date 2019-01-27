<%--
  This template produces IMPLEMENTS {interface} clause of the wrapper class
  The wrapped object is the non-empty list of implemented interfaces.

--%><%@page wraps="java.util.ArrayList"
%><%@page import="net.mdatools.modelant.mof.*, java.util.*"
%><%

  Iterator interfaceIterator;
  Class implementedInterface;
  boolean skip;

%>implements <%

  skip = true;
  interfaceIterator = wrapped.iterator();
  while ( interfaceIterator.hasNext() ) {
    implementedInterface = (Class) interfaceIterator.next();
    if ( !skip ) {
      %>, <%
    }
    skip = false;
    %><%=implementedInterface.getName() %><%
  }
  %> <%
%>