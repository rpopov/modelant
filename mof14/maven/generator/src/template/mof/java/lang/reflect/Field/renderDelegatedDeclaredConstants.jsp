<%--
  This template produces delegate method in a Base Delegator class for the wrapped method

--%><%@page wraps="java.lang.reflect.Field"
%><%@page import="net.mdatools.modelant.mof.*, java.util.*, java.lang.reflect.*"
%>
  /**
   * @see <%=wrapped.getDeclaringClass().getName() %>#<%=wrapped.getName() %>
   */
  public static final <%= wrapped.getType().getCanonicalName() %> <%=wrapped.getName() %> = <%=wrapped.getDeclaringClass().getCanonicalName() %>.<%=wrapped.getName() %>;