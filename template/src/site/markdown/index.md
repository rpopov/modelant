Template Engine
===============

<!-- MACRO{toc} -->


The **template** component of ModelAnt 2.25 is migrated as follows:

  * **api** module with the interfaces of the Repository module
  * **impl** module with the implementation of the API, following [A4]
  * **maven** module with:
    * a plugin to compile the templates from the source directory, set up in a POM
    * a maven archetype modelant.template.maven.archetype to generate the directories structure and default templayes files of a new  maven project

Usage
-----

As of the convention above:

  * Construct a Template Engine with the unique name stated in the template compilation context:
```
engine = TemplateEngineFactory.construct(templateCompilationContext)
```
  * Lookup the Template Engine after its instantiation, as many times as needed:
```
engine = TemplateEngineFactory.getEngine(uniqueName)
```
  * Use the Template Engine to render an object by naming a template
```
engine.render(targetFile, targetObject, templateName, bindings)
```
  * Use the Template Engine to render an object, inlined in the rendering of another object:
```
engine.render(targetObject, templateName, context)
```

The template is identified by the **class name** of the object to render and the **template name** (when provided explicitly) or the **name of the calling method** in ```engine.render(targetObject, context)```.

Template Syntax
---------------

The templates are stored in files with **.jsp** extension in order to reuse existing JSP editors with syntax highlight and additional support in JSP authoring.

  * The **template** consists of **template element**s, starting with optional imports and comments:
```
<template> ::= {<scriptlet import>|<scriptlet comment>}{<template element>}
```
  * The **scriptlet import** is a standard java import declaration, allowed in the Java language version of the underlying JRE:
```
<scriptlet import> ::= "<%@" <import> "%>"
<import> ::= standard java import declaration
```
  * The **template element** is either a static text, dynamic expression, scriptlet, or comment:
```
<template element> ::= <static text> | <expression> | <scriptlet> | <scriptlet comment>
```
  * The **static text** is strasferred to the output as it is. It should not contain **&lt;%** substring
```
<static text> ::= string of any charaters, not containing <% substring
```
  * The **expression** provides the dynamic content in the text. These are standard JSP scriptlet expressions, translated to Java as expressions in the place they are used:
```
<expression> ::= "<%=" <java expression> "%>"
<java expression> ::= standard expression in Java
```
  * The **scriptlet** embeds java code to control the rendering of the nested template elements. These are standard JSP scriptlets:
```
<scriptlet> ::= "<%" <java statement> "%>"
<java statement> ::= <java fragment> {"%>" <template element> "<%" <java fragment>}
```
  * The **scriptlet comments** describe the template. They cannot be nested. Translated to Java as comments between **/\*** and **\*/**:
```
<scriptlet comment> ::= "<%--" text "--%>"
```
  * Each template **renders an instance** of a specific java class. The qualified class name of the rendered class or interface is identified by the names of the nested directories, relative to the template root directory, by concatenating them, separated by dotc "." (i.e. the dots replace the directories separators "/" or "\").
  For example:
    * java/lang/String/hello.jsp is a template rendering instances of java.lang.String
    * java/util/ArrayList/world.jsp is a template rendering instances of java.util.ArrayList
    
NOTE: The syntax of of the templates is changed (simplified), compared to the syntax in 2.25 version.  

Within the template the following objects are available:
    * '''wrapped''' - the non-null object being rendered in this template (analogous to ''this'' in the methods)
    * '''context''' - the TemplateContext intstance the rendering happens in. It provides the output writer to construct contents in.
    * '''engine'''  - the template engine to render the nested templates applied

Project Directories
-------------------

According to the ModelAnt conventions for Maven, the templates are located in **/src/template** project directory

Compilation Dependencies
------------------------
Any use of the modelant Templates should use the following dependencies:
```
<dependencies>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.template.api</artifactId>
    <version>${revision}</version>
  </dependency>
</dependencies>
```

Execution Dependencies
----------------------
The use of modelant Templates can be integrated in modelant or independent of modelant. The following depedency is just enough to run:

```
<dependencies>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.template.impl</artifactId>
    <version>${revision}</version>
  </dependency>
</dependencies>
```

Modules
-------

* [modelant Template API](modelant.template.api/index.html)
* [modelant Template Implementation](modelant.template.impl/index.html)
* [modelant Template Maven Integration](modelant.template.maven/index.html)