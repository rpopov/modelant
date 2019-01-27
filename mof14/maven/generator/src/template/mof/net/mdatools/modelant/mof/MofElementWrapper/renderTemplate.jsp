<%--
  This template generates a template

--%><%@page wraps="net.mdatools.modelant.mof.MofElementWrapper"
%><%@page import="net.mdatools.modelant.mof.MofElementWrapper"
%><%
 String component;

 component = (String) request.getAttribute("component");

 String className = wrapped.calculateQualifiedWrapperClassName( component );

%><%="<" %>%@page wraps="<%=className %>"
%<%="><" %>%@page import="<%=className %>"
%<%=">" %>