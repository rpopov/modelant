<%--
  This template generates a template

--%><%@
import net.mdatools.modelant.mof14.maven.generator.MofElementWrapper;
%><%

 String className = wrapped.calculateQualifiedInterfaceName();

%><%="<" %>%@page import="<%=className %>"
%<%=">" %>