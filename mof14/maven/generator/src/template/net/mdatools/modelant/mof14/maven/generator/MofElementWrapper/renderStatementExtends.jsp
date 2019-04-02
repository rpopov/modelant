<%--
  Render the 
     extends <interface>{,<interface>}
  section.
--%><%@
import javax.jmi.model.GeneralizableElement;
import java.util.List;

%><% 
boolean firstPrinted = false; 

%>extends <%
if (!((GeneralizableElement) wrapped.getWrapped()).getSupertypes().isEmpty()) { 
  for (GeneralizableElement supertype : (List<GeneralizableElement>)((GeneralizableElement) wrapped.getWrapped()).getSupertypes()) {
    if ( firstPrinted ) {
      %>, <%
    }
    %><%= MofElementWrapper.wrap(supertype).calculateQualifiedInterfaceName() %><%
    firstPrinted = true;
  }
} else {
%>javax.jmi.ref.RefObject<%  
} %>