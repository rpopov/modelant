Maven support for modelant repository
--------------------------------------

The **repository.maven** module provides:
* a Maven archetype [modelant.repository.maven.archetype](modelant.repository.maven.archetype/index.html) to generate the directories structure and files of a modelant maven project for a specific modeling language
* a Maven plugin [modelant.repository.maven.plugin](modelant.repository.maven.plugon/index.html) to generate the API for a metamodel.

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
