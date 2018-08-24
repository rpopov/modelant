Template Engine 
===============

<!-- MACRO{toc} -->


The **template** component of ModelAnt 2.25 is migrated as follows:

  * **api** module with the interfaces of the Repository module
  * **impl** module with the implementation of the API, following [A4]
  * **maven** module with:
    * a plugin to compile the templates from the source directory, set up in a POM
    * a maven archetype modelant.template.maven.archetype 3.1.0 to generate the directories structure and files of the target maven project:
      * provide a pom.xml stating:
        * the plugin needed to generate the sources as part of the build
        * the dependencies needed to compile the API sources
      * compile and install the templates context <br/>NOTE: As the maven archetypes **do not provide working** mechanism for post-processing (archetype-post-generate.groovy), the generation of the API source will be just the first phase of the generated POM (generate sources) through a corresponding plugin<br/>
  * The encoding defined in the Template Context is only the encoding of the template files, whereas the generated Java files are in UTF-8

Usage
-----

As of the convention above:

  * Construct/lookup the Template Engine Factory to instantiate a Template Engine. Use the template compilation context, which provides a unique name to identify itself:

```  
engine = TemplateEngineFactory.constructEngine(templateCompilationContext)
```

  * Lookup the Template Engine after its instantiation, as many times as needed:

```  
engine = TemplateEngineFactory.getEngine(uniqueName)
```

  * Use the Template Engine to render an object by naming a template

```  
engine.render(targetFile, targetObject, templateName, bindings)
```

  * Within a template render another object:

```  
engine.render(targetObject, templateName, context)
```

Template Syntax
---------------

The templates are stored in files with **.jsp** extension in order to reuse existing JSP editors with syntax highlight and additional support in JSP authoring.

The template consists of **template element**s, which are:

  * text to generate in to the output
  * scriptlet expressions to provide the dynamic content in the text. These are standard JSP scriptlet expressions, translated to Java as expressions in the place they are used:
  
```  
scriptlet-expression = "<%=" java-expression "%>"
java-expression = standard expression in Java
```

  * scriptlets to calculate the dynamic content or control the rendering of the nested template elements. These are standard JSP scriptlets:
  
```  
scriptlet-expression = "<%" java-statement "%>"
java-statement = java-fragment {"%>" template-element* "<%" java-fragment} 
```
    
  Translated to Java as composition of the java-fragments and the translations of the nested template-elements.
  Expected to form standard Java statements.
  
  * scriptlet comments. These are comments in the template. They cannot be nested:

```  
scriptlet-comment = "<%--" text "--%>"
```
   
  Translated to Java as comments between /* and */.
  * scriptlet imports. These are standard java import declarations:
  
```  
scriptlet-import = "<%@" java-import-declaration* "%>"
```
   
  Translated to Java as contents between the package declaration and the class declaration. 
  Usually these are just import declarations, but additional java comments also could be added.
  
  * The template renders (wraps) an instance of a specific class. The qualified class name of the wrapped class or interface is identified by the names of the nested directories, relative to the template root directory, by concatenating them, separated by dotc "." (i.e. the dots replace the directories separators "/" or "\"). For example:
  java/lang/String/hello.jsp is a template rendering instances of java.lang.String
  java/util/ArrayList/world.jsp is a template rendering instances of java.util.ArrayList

Project Directories
-------------------

According to the ModelAnt conventions for Maven, the templates are located in<br/>**/src/template** project directory

Template Compilation Context
-----

The Template Compilation Context provides:

  * **encoding**  the common for all templates in the context encoding used when storing them as files and to produce the output text in.
  * **template source directory**  the root directory, relatively which the templates' directories and files are located.<br/>Default: **${project.build.sourceDirectory}/../template**
  * **java directory**  the root directory where to translate the template to temporary java files.<br/>Default: **${project.build.directory}/java**
  * **class directory**  the root directory where to compile the java files the templates are translated to.<br/>Default: **${project.build.outputDirectory}**
  * **unique name**  a unique name of this template context among all other contexts used simultaneously. It identifies the context and makes the template names in it unique amnong the template names in all contexts.<br/>Examples: **domain**, **view**, **persistence**, **db**
  * **keepGenerated**  indicates if the translation of the template to java should be kept (true) for review, instead of being deleted after the compilation (false)<br/>Default: **false**
  * **compileForDebug**  indicates if the compilation of the template should include debug data (true) in the produced class.<br/>Default: **false**

Any use of the ModelAnt Templates, either within ModelAnt or standalone, should use the following dependencies:

Compilation Dependencies
------------------------
```
<dependencies>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.template.api</artifactId>
    <version>3.1.1-SNAPSHOT</version>
  </dependency>
</dependencies>
```

Execution Dependencies
----------------------
```
<dependencies>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.template.impl</artifactId>
    <version>3.1.1-SNAPSHOT</version>
  </dependency>
</dependencies>
```

The protocol to use ModelAnt Templates in code is: 
[[Modelant - migration to maven#Protocol]]
