Origin
------

ModelAnt, further referred as *modelant*, was an extension of Ant to manipulate models:

* described in the OMG's metamodeling framework [Meta Object Facility/MOF 1.4](https://www.omg.org/cgi-bin/doc?formal/02-04-03)
* exchanged in [XML Metadata Interchange XMI 1.1, 1.2](https://www.omg.org/cgi-bin/doc?formal/02-01-01)
* through [Java Metadata Interface / JMI 1.0](https://jcp.org/en/jsr/detail?id=40)

Its integration wth Ant gave the name of the product. In order to ease its distribution, dependecies and version management, integrate with standard scripting languages, and apply industrial 
standards and conventions, it was migrated to Maven. Nevertheless the "Ant" part of its name stays to provide continuity of the product's name and indicate its power and
strength.

Purpose 
-------

Modelant provides JMI-compliant mechanism to read, process, export models in MOF 1.4, UML 1.3 and UML 1.4, including also an engine for generation of code (any texts) using Object-Oriented Programming
and templates. It is an extensible framework allowing adding support of other languages, described using MOF 1.4 metamodels.

Modelant provides Maven plugins to automate:

* the creation and comparison of UML 1.3, UML 1.4, MOF 1.4 models
* the generation of code from models
* the reverse engineering of java code, relational databases and XML DTD/XSD
* the transformatoion of models, e.g. tranformation of of UMl 1.3 models to UML 1.4.
* the comparison and change identification in UML 1.3/UML 1.4 models and MOF 1.4 metamodels.

The produced UML 1.3 models are suitable to be imported in:

* Rational Rose 2003 + XMI Plugin
* WhiteStar UML / Star UML
* Enterprise Architect
* other

as they employ MOF 1.3/MOF 1.4 as reading MOF 1.3 is internally converted to MOF1.4 and XMI 1.1 and XMI 1.2.

The converted to UML 1.4 models can be imported in:
 * Argo UML
 * Enterprise Architect
 * other
as they employ MOF 1.4 and XMI 1.1 and XMI 1.2.

Modelant wraps the [NetBeans Meta-Data Repository / MDR](https://netbeans.org/download/5_5/javadoc/org-netbeans-api-mdr/architecture-summary.html).