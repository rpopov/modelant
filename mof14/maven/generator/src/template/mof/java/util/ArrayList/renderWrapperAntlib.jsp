<%--
  This template produces the ANTLIB definition with the core macros of these wrapper classes
  Parameters:

    package.name - is the non-empty package name of the factory class to generate
    class.name - is the non-empty name of the factory class to generate

    component is non-null component name to generate

--%><%@page wraps="java.util.ArrayList"
%><%@page import="net.mdatools.modelant.mof.*,
                  net.mdatools.modelant.core.wrap.Factories,
                  java.util.*"
%><%

  String metamodel;
  String className;
  String component;
  String product;

  metamodel = (String) request.getAttribute("metamodel");
  component = (String) request.getAttribute("component");
  className = (String) request.getAttribute("class.name");
  product  = (String) request.getAttribute("product");

%><?xml version="1.0"?>

<!--
  Tasks, types and macros of ModelAnt <%=metamodel %> <%=component %> wrapper.<br/>
  The <%=metamodel %> core wrappers provide the common complex logic as a middle tier between the model itself and the user code. <br/>
  Usage:<br/><br/>

   &lt;typedef resource="net/mdatools/<%=product %>/<%=metamodel %>/<%=component %>/antlib.xml"/&gt;

  -->
<antlib xmlns:current="ant:current">

   <!--
     Common task, types inherided from the
     -->
  <typedef resource="net/mdatools/modelant/core/antlib.xml"/>
  <typedef resource="<%=metamodel %>/antlib.xml"/>

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