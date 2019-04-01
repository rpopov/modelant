<%--
  Render the 
    [package <interface>;]
  section.
--%><%@
import javax.jmi.model.GeneralizableElement;
import javax.jmi.model.ModelElement;
%><%
  String packageName;

  packageName = wrapped.calculatePackageName();
  if ( !packageName.isEmpty() ) {
%>package <%=wrapped.calculatePackageName() %>;<%
  }
%>
