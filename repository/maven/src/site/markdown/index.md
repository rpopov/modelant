Maven support for JMI
---------------------

The **modelant.repository.maven** module means to generate the JMI-compliant interfaces for a metamodel.

<!-- MACRO{toc} -->

Generate a Maven project for a metamodel API module
---------------------------------------------------

* In a parent directory create a new project with the artifact: 

```
mvn archetype:generate -DarchetypeGroupId=net.mdatools 
                       -DarchetypeArtifactId=modelant.repository.maven.archetype 
                       -DarchetypeVersion=3.1.0 
                       -DarchetypeCatalog=local 
                       -DmetamodelUrl=path-to-the-metamodel
```
   
* Build the newly created project with

```
mvn clean install
```

Modules
-------

* [A Maven archetype for a metamodel API project](modelant.repository.maven.archetype/index.html)
* [A Maven plugin to generate the JMI API for a metamodel](modelant.repository.maven.plugin/index.html)
