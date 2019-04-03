<%--
  Render the 
     extends <interface>{,<interface>}
  section.
--%><%@
import javax.jmi.model.GeneralizableElement;
import java.util.List;

%>extends <%=wrapped.calculateQualifiedPackageProxyNameJmi() %><%
if (!((GeneralizableElement) wrapped.getWrapped()).getSupertypes().isEmpty()) { 
  for (GeneralizableElement supertype : (List<GeneralizableElement>)((GeneralizableElement) wrapped.getWrapped()).getSupertypes()) {
      %>, <%= MofElementWrapper.wrap(supertype).calculateQualifiedPackageProxyName() %><%
  }
} %>