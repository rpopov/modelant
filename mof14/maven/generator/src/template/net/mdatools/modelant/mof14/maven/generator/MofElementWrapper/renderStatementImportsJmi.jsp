<%--
  Render the 
    {import <interface>;}
  section.
--%><%@
import javax.jmi.model.GeneralizableElement;
import javax.jmi.model.ModelElement;

import net.mdatools.modelant.mof14.maven.generator.MofElementWrapper;

import java.util.List;

%><% 
  for (GeneralizableElement supertype : (List<GeneralizableElement>)((GeneralizableElement) wrapped.getWrapped()).getSupertypes()) {
%>import <%= MofElementWrapper.wrap(supertype).calculateQualifiedInterfaceNameJmi() %>;
<%
  }     
%>