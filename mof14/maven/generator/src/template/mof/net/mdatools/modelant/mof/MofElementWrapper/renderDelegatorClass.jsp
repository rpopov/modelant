<%--
  This template produces the wrapper class for a class as a model element

  Assumes that the wrapper and the wrapped classes are in the same package, thus the import is skipped

--%><%@page wraps="net.mdatools.modelant.mof.MofElementWrapper"
%><%@page import="net.mdatools.modelant.mof.MofElementWrapper"
%><%

 String delegatorPackage;
 String wrapperPackage;
 String delegateClassName;

 delegatorPackage = (String) request.getAttribute("component");
 wrapperPackage   = (String) request.getAttribute("wrapped.component");

 delegateClassName= wrapped.calculateQualifiedWrapperClassName( wrapperPackage );

%>/*
 * Generated
 */
package <%=wrapped.calculateWrapperPackageName( delegatorPackage ) %>;

<%
  if ( !wrapped.isAbstract() ) {

%>import javax.jmi.reflect.RefPackage;
<%
  }
%>import net.mdatools.modelant.core.wrap.Factory;
import <%=wrapped.calculateQualifiedBaseWrapperClassName( delegatorPackage ) %>;

/**
 * This is a wrapper of <%=delegateClassName %> that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class <%=wrapped.calculateSimpleWrapperClassName() %> extends <%=wrapped.calculateSimpleBaseWrapperClassName() %> {

  public <%=wrapped.calculateSimpleWrapperClassName() %>(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

<%
  if ( !wrapped.isAbstract() ) {

%>  public <%=wrapped.calculateSimpleWrapperClassName() %>(RefPackage extent) {
    super( extent );
  }
<%
  }
%>}