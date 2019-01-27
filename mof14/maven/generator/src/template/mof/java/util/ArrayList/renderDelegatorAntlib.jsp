<%--
  This template produces the ANTLIB definition with the core macros of these wrapper classes
  Parameters:

    package.name - is the non-empty package name of the factory class to generate
    class.name - is the non-empty name of the factory class to generate

    component is non-null component name to generate
    wrapped.product is the non-null name of the mdatools product to build in
    wrapped.component is the non-null name of the component to wrap

--%><%@page wraps="java.util.ArrayList"
%><%@page import="net.mdatools.modelant.mof.*,
                  net.mdatools.modelant.core.wrap.Factories,
                  java.util.*"
%><%

  String metamodel;
  String className;
  String component;
  String wrappedComponent;
  String product;
  String wrappedProduct;

  metamodel = (String) request.getAttribute("metamodel");
  component = (String) request.getAttribute("component");
  className = (String) request.getAttribute("class.name");
  wrappedComponent = (String) request.getAttribute("wrapped.component");
  product  = (String) request.getAttribute("product");
  wrappedProduct  = (String) request.getAttribute("wrapped.product");

%><?xml version="1.0"?>

<!--
  Tasks, types and macros of ModelAnt <%=metamodel %> <%=component %> wrapper.<br/>
  The <%=metamodel %> core wrappers provide the common complex logic as a middle tier between the model itself and the user code. <br/>
  Usage:<br/><br/>

   &lt;typedef resource="net/mdatools/<%=product %>/<%=metamodel %>/<%=component %>/antlib.xml"/&gt;

  -->
<antlib xmlns:current="ant:current">

  <!--
     Common task, types inherided from the <%=wrappedComponent %> component
    -->
  <typedef resource="net/mdatools/<%=wrappedProduct %>/<%=metamodel %>/<%=wrappedComponent %>/antlib.xml"/>

  <!--
    Regsiters a factory of <%=metamodel %> wrapper objects adding more features extending the JMI standard.
    Register this factory before using model wrappers.
    -->
  <macrodef name="<%=metamodel %>.<%=component %>.register.factory">
    <sequential>
      <wrap.factory.register factoryClass="<%=className %>"/>
    </sequential>
  </macrodef>
</antlib>