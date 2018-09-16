modelant Maven archetype for model API
--------------------------------------

The **repository.maven.archetype** is a maven archetype **modelant.repository.maven.archetype 3.1.0** used to generate the directories structure and files of a modelant maven project for a specific modeling language:
   * it provides a pom.xml stating:
      * the plugin needed to generate the sources as part of the build
      * the dependencies needed to compile the API sources
   * it compiles and installs the model JMI API for that language         
   NOTE: As the maven archetypes **do not provide working** mechanism for post-processing (archetype-post-generate.groovy), the generation of the API source will be just the first phase of the generated POM (generate sources) through a corresponding plugin

   
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
   