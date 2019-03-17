<%--
  This template generates a template

--%><%@
import net.mdatools.modelant.mof14.maven.generator.MofElementWrapper;
%><%
 String component;

 component = (String) context.get("component");

 String className = wrapped.calculateQualifiedWrapperClassName( component );

%><%="<" %>%@page import="<%=className %>"
%<%=">" %>