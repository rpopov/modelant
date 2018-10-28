modelant.uml13.reverse
======================

modelant provides operations to reverse engineer relational database schemes, XML schemes and Java code back to UML 1.3
models. The decision to use UML 1.3 is based on the practical need of integration with [Rational Rose 2003](https://en.wikipedia.org/wiki/IBM_Rational_Rose_XDE) with XML plugin
and the produced UML models as XMI files can be imported there. This is not a restriction of applicability of modelant, as 
it provides mechanism to convert UML 1.3 models into UML 1.4 ones, which allows their import in [ArgoUML](http://argouml.tigris.org/), [WhiteStarUML](https://sourceforge.net/projects/whitestaruml/), [Enterprise Architect](https://sparxsystems.com/enterprise_architect_user_guide/14.0/model_publishing/importxmi.html) and [other](https://en.wikipedia.org/wiki/List_of_Unified_Modeling_Language_tools).     
 
<!-- MACRO{toc} -->

Database Reverse Engineering
----------------------------
The ```net.mdatools.modelant.uml13.reverse.ReverseDatabaseOperation``` class implements a reverse engineering logic for database schemas to an UML 1.3 model. The model produced is in fact a 
[Platform Specific Model](https://en.wikipedia.org/wiki/Model-driven_architecture), which might need additional processing and tuning.

Conventions on the produced model:

  * The database column types are converted to DataType instances named: ```&lt;type name&gt;[_&lt;column size&gt;[_&lt;column precision&gt;]]```. Additionally **length** and **scale** Tagged Values are bound to the actual DataType instance.
    * The **length** Tagged Value is mandatory, indicating the positions or characters needed to represent the value.  
    * The **scale** Tagged Value is optional. When not provided, the decimal places should be treated as 0.
  * Any comments found while reverse engineering the database are bound as **documentation** Tagged Value.
  * Each attribute pertaining to the table's primary key is bound the **primary_key** Tagged Value whose value is the sequence order of the column in the tible's primary key.
 

XSD Reverse Engineering 
-----------------------
The ```net.mdatools.modelant.uml13.reverse.ReverseXsdOperation``` implements the logic to reverse engineer an XML Schema (XSD) to an UML 1.3 model.

Conventions on the produced model:

 * The model elements (classes, association names) that represent elements in the output XML are marked with ```&lt;&lt;element&gt;&gt;``` stereotype.
 * For representation purposes local types (UML classes) could be introduced for XSD groups, unions, local / inlined types. All of them are marked with ```&lt;&lt;local type&gt;&gt;``` stereotype
 * The column types are converted to DataType instances named: ```&lt;type name&gt;[_&lt;column size&gt;[_&lt;column precision&gt;]]```. Additionally **length** and **scale** Tagged Values are bound to the actual DataType instance.
    * The **length** Tagged Value is mandatory, indicating the positions or characters needed to represent the value.  
    * The **scale** Tagged Value is optional. When not provided, the decimal places should be treated as 0. 
 * Any comments found while reverse engineering the XSD are bound as 'documentation' tagged values. These tagged values are compatible with the Rose's approach to documentation. They are optional.
 
 
Reverse Engineer Java (javadoc)
-------------------------------

The ```net.mdatools.modelant.uml13.reverse.ReverseEngineerJavaDoclet``` java standard Doclet 
generates UML classes for the java classes (and interfaces) reviewed. The model created presents the real packages, 
classes, interfaces, methods and attributes. The model produced is in fact a [Platform Specific Model](https://en.wikipedia.org/wiki/Model-driven_architecture), which might need additional processing and tuning.

Conventions on the produced model:

 * Nested classes and interfaces are presented as well, though ones that are marked as static in Java are not marked as static in the model.
 * The methods created are not bound to the exceptions they throw (due to some compatibility issues with Rose XML Plugin), though the exceptions and the stereotypes are registered in the model.
 * The classes that are not included for documenting, but are still referenced by the documented classes, are included in
the model as **data types** with the qualified class name. They are placed in the top-most package - the model itself.
 * Only one-way X-to-many associations are identified and created. The two-way associations (navigable in both directions)
are not identified.

Expected to find as system properties:

 * model - the name of the model to generate
 * targetDir - the absolute path to the directory where to create the result file
 * target - file name where to store the model. Assumed to be created in targetDir
 * workDir - (optional) the absolute path to the directory where to create the temporary files.
 
 
