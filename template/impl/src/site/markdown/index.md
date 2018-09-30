modelant Template Implementation
================================

The implementation of modelant Templates mechanism uses a significant rework of Jasper - the JSP Engine of Apache Tomcat 3.3.x. It provides the following packages:

* **net.mdatools.modelant.template** - provides the template engine implementation, which collaborates with the JSP compiler to translate the templates to Java
* **org.apache.jasper.compiler** - implementation of the Template compiler.