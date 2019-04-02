<%--
  Render the 
    [package <interface>;]
  section.
--%><%@
import javax.jmi.model.GeneralizableElement;
import javax.jmi.model.ModelElement;
%><%
  String packageName;

  packageName = wrapped.calculatePackageNameJmi();
  if ( !packageName.isEmpty() ) {
%>package <%=packageName %>;<%
  }
%>
