<%--
  This template generates a template

--%><%@page wraps="net.mdatools.modelant.mof.MofEnumWrapper"
%><%@page import="net.mdatools.modelant.mof.MofEnumWrapper"
%><%
 String component;

 component = (String) request.getAttribute("component");

 String className = wrapped.calculateQualifiedWrapperClassName( component );

%><%="<" %>%@page wraps="<%=className %>"
%<%="><" %>%@page import="<%=className %>"
%<%=">" %>