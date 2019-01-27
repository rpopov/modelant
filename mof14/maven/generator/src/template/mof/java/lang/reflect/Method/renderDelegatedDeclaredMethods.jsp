<%--
  This template produces delegate method in a Base Delegator class for the wrapped method

--%><%@page wraps="java.lang.reflect.Method"
%><%@page import="net.mdatools.modelant.mof.*, java.util.*, java.lang.reflect.*"
%><%
  Class[] parameterTypes;
  Class[] exceptionTypes;


  boolean first;

%>
  /**
   * @see <%=wrapped.getDeclaringClass().getName() %>#<%=wrapped.getName() %>(<%

     // render the types of the method parameters
     first = true;
     for (Class parameter: wrapped.getParameterTypes()) {
       if ( !first ) {
        %>, <%
       }
       %><%=parameter.getName() %><%
       first = false;
     }
 %>)
   */
  public <%
    if ( Modifier.isStatic( wrapped.getModifiers() ) ) {
      %>static <%
    }
/*
    if ( Modifier.isFinal( wrapped.getModifiers() ) ) {
      %>final <%
    }
*/
// Obviously the delegating method cannot be abstract

  %><%= wrapped.getReturnType().getCanonicalName() %> <%= wrapped.getName() %>(<%

   parameterTypes = wrapped.getParameterTypes();
   for (int i = 0; i < parameterTypes.length; i++) {
     if ( i > 0 ) {
       %>, <%
     }
     %><%= parameterTypes[i].getCanonicalName() %> arg<%=i %><%
   }
%>)<%

   exceptionTypes = wrapped.getExceptionTypes();
   if ( exceptionTypes.length > 0 ) {
     %> throws <%
    for (int i = 0; i < exceptionTypes.length; i++) {
      if ( i > 0 ) {
        %>, <%
      }
      %><%= exceptionTypes[i].getCanonicalName() %><%
    }
   }

%> {
    <%
      if ( !wrapped.getReturnType().equals( Void.TYPE ) ) {
        %>return <%
      }

      if ( Modifier.isStatic( wrapped.getModifiers() ) ) {
        %><%= wrapped.getDeclaringClass().getName() %><%
      } else {
        %>getDelegate()<%
      }
      %>.<%= wrapped.getName() %>(<%

      parameterTypes = wrapped.getParameterTypes();
      for (int i = 0; i < parameterTypes.length; i++) {
        if ( i > 0 ) {
          %>, <%
        }
        %>arg<%=i %><%
      }
%>);
  }
