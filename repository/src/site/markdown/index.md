Repository
----------

The **repository** component of [modelant](/) contains:

  * **api** module with the interfaces of the Repository module
  * **impl** module with the implementation of the API, following [A4]
  * **maven** module with:
    * a maven archetype **modelant.repository.maven.archetype 3.1.0** to generate the directories structure and files of a modelant maven project for a specific modeling language:
       * it provides a pom.xml stating:
          * the plugin needed to generate the sources as part of the build
          * the dependencies needed to compile the API sources
       * it compiles and installs the model JMI API for that language         
       NOTE: As the maven archetypes **do not provide working** mechanism for post-processing (archetype-post-generate.groovy), the generation of the API source will be just the first phase of the generated POM (generate sources) through a corresponding plugin<br/>
    * a plugin to generate the API for a metamodel, set up in a POM
      * parameters:
        * **metamodelUrl** - where to load the metamodel from
        * **sourceDirectory** - where to generate the API sources
        * **workDirectory** - where to keep the work / temp files
