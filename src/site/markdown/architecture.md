Architecture
============

**\[A1\]** modelant provides a set of Maven plugins to generate the specific artifacts of another (target) project like:

  * a plugin to generate the domain classes
  * a plugin to generate POJO
  * a plugin to generate the persistence tier
  * a plugin to generate modelant JMI API component for a new metamodel

Then a script to generate the artifacts for a specific project should be a combination of plugins running in a common environment, provided by the common modelant core, utils, repository, API and loaded metamodel and model(s).

**\[A2\]** The Maven project for modelant sets dependencies between the project's modules. If the execution environment would be Maven through Maven plugins, they would use the same dependencies. Then, organize modelant as a set of plugins to call the contents of the dependencies behind the scenes, **assuming** that the separate Maven plugins will share the classpath and the dependent components in it.

**\[A3\]** In the long run the migration of modelant to Maven would be just a migration for itself. It does not bring better knowledge of the platforms - it is just another experience with IoC (Plexus) and dependency tracking and spending time in Maven's internals. Maven is closed by itself, so the integration of modelant in anything else would require new migration efforts. 

**Note:**
    Reading the [documentation of Nexus](https://books.sonatype.com/mcookbook/reference/index.html) I see that the OSGi platform is started as a separate process, so that any communication with it would require developing web services in OSGi and their client(s) in Maven. This seems to be a big leap to make. Thus, in order to have quickly the needed result, migrate modelant to Maven as plugins **\[A3\]**.

**\[A4\]** Organize every module of modelant as Maven projects/modules with unified module names:

  * **&lt;module name&gt;** - the Maven project containing the module's artifacts, namely:
    * **api** (API) module only with:
        * the interfaces the actual module to implement
        * a Factory class:
            * named **&lt;Interface Name&gt;Factory**
            * providing **construct** method(s), that use the idiom to load (and probably make Singleton or cache) an instance of the implementation (see below)
    * **impl** (implementation) module that:
        * implements the API interfaces
        * publishes the implementations as services, as of [Java standard Service Loader](https://docs.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html) mechanism:
            * for each implemented service interface 
            * define a file **src\resources\META-INF\services\\&lt;interface name&gt;**
                * the contents of the file is the qualified name of the implementation class
                * this allows the ServiceLoader class (and the API's factory class) instantiate the concrete implementation
        * defines unit tests in **test\java** directory
    * **maven** (Maven implementation) module with sub-modules:
        * **plugin** - a general module that contains the MOJOs of the plugin 
        * **&lt;explicit plugin name&gt;** - in case the general module **plugin** is not enough or special plugin setup is needed
        * **archetype** - a module defining a Maven archetype
    * **test** module that:
        * does black-box, *integration testing* of the module, its sub-modules and the other required modules (dependencies) through module's public interfaces
        * actually specifies the API and any of its implementations through executable tests
          **NOTE:** As the test suite for Maven plugins is a set of Maven projects and Maven provides specific means for that, the plugin integration tests are organized within the plugin project, following **\[C4.1\]**.

**\[A5\]** Unit tests: 

  * The module's **unit tests** are included in the module's project itself in **test** directory

**\[A6\]** The modules of modelant are either common or metamodel-specific ones. The metamodel-specific modules are named after the language they contain metamodel of.

**\[A7\]** &lt;withdrawn&gt;

**\[A8\]** The following is the structure of modelant 3.1.0 as tiers:

  * repository
  * core
  * template
  * mof14
  * uml13 
  * uml14
  * conversion

**\[A9\]** modelant provides a common logic to the **UML 1.4** metamodel + **DI 1.0** (TODO: check!), as these are completed standards. Any other model for processing in modelant must pass a **&lt;metamodel&gt;-to-UML 1.4** transformation.

**\[A10\]** The **core** module of modelant provides the model- and metamodel transformation mechanism. 

**\[A11\]** Every **metamodel** supported in modelant:

  * employs the **core** module to set up a transformation from it to UML 1.4, as of \[A9\]
  * publishes the transformation as a Maven plugin, which could be employed to transform the model to UML 1.4 in a phase 
      * after the model load
        and
      * before the model's processing

**\[A12\]** The **root pom.xml** uses:

  * **&lt;dependencyManagement&gt;** element to define the components' versions, so that the projects refer the dependencies just with group and artifact id
  * **&lt;pluginManagement&gt;** element to define the plugins' versions and setup parameters, so that the projects refer the locally used plugins just with group and artifact id and possibly override some parameters.