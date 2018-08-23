Conventions
===========

Elicited conventions and lessons learned

GAV Coordinates
---------------

**\[C1\]** All components of modelant have the same group name **net.mdatools**.

**\[C2\]** Each component of modelant has its artifact ID as a path starting form **modelant**.

**\[C3\]** The versioning is semantic **&lt;major&gt;.&lt;minor&gt;.&lt;patch&gt;** starting from 3 major version, as modelant for Maven is a completely new version of modelant 2.x

Maven Directories
-----------------

**\[C4\]** Even though modelant is migrated to Maven, I decide not to follow the "Maven standard" directories structure in terms:

  * "main" is no more alternative/opposition of "test" directory name
  * all languages are treated equally
  * **src/&lt;language&gt;** directory contains the source code in the corresponding language:
    * java
    * model
    * template
    * ...
  * **src/resources** directory contains the resources to be included directly in the result artifact
  * **src/site** directory contains
    * **site.xml** file of the documentation
    * **markdown** directory contains the manually-written files in [Markdown format](https://daringfireball.net/projects/markdown/syntax) to be included in the generated documentation, like:
        * index.md
        * faq.md
  * **test/&lt;language&gt;** directory contains the source code in the corresponding language:
    * java
    * maven (see \[A5\], \[C8\]) 
    * ...    
  * **test/resources** directory contains the test resources to be included directly in the test artifact
  * **target/site** directory contains the generated site/javadoc **for the component** (as of maven convention), because:
    * the site is not the source code, but it is derived from it, like the compiled code
    * the component's site is zipped and published with the component
    * the component's source code is zipped and published with the component in order to support development with and debug of modelant is a third party component
  * any components are published in the Maven repositories, thus no **lib** directory is needed. In case components / libraries cannot be found in any public repository, extract them as modules and define their POM to export them in a public repositories.

**\[C4.1\]** Maven plugin integration tests are in 

   * **test/maven** directory, containing:
      * **setting.xml** file for the maven instance to run for the integration tests    
      * **&lt;test or issue name&gt;** sub-directory for **each test**, containing: 
         * **pom.xml**, as of \[C4\] below.
         * other specific files and directpries
      
  
  **NOTE:** The practice applied by *maven-invoker-plugin* to use a copy in a temporary local repository copy is considered as really complicating the testing and debugging, therefore it is avoided. Instead, the maven plugin integration test use the plugins already published (installed) in the local host's repository. See: [Plugin testing](https://maven.apache.org/plugin-developers/plugin-testing.html)
  
**\[C4.2\]** Testing the plugin uses the https://maven.apache.org/plugins/maven-invoker-plugin plugin defined in the **maven-plugin** profile.
              
**\[C4.3\]** As the Maven invoker/test plugin copies the pom.xml and all test files to the **target** directory, these files may use project properties, that are inlined while copying, for example:

  * the current artifact version is referred as **@project.version@**
  * the current version of the parent pom is referred as **@project.parent.version@**
  
**\[C4.4\]** The root Maven POM.xml defines the profiles to compile / build the artifacts in the whole set of modules. 

**\[C4.5\]** Due to some Maven specifics, the activation of the Maven profiles cannot be based on contents of the POM.xml itself. The activation of the **maven-plugin** Maven profile for plugin development uses the presence of **test/maven** directory. This wya there is no maven plugin development without a corresponding test procedure.


Testing
-------

**\[C10\]** Integrate the unit tests in the development (implementation) module, whereas the API module does not provide any tests. This way the API can be compiled first and independently of the implementation(s), avoiding cyclic dependencies and monolith development.

**\[C11\]** Extract an **integration test** module, consisting only of the **API test code**, with all needed dependencies and (default) implementations. There is no need of other than test/java directory. This module can be compiled later than the API, considering its dependencies, whereas its compilation does not produce new artifacts.

**\[C12\]** Publish modelant with its code and javadoc too. This will support the further development based on those components and IDE integration:

  * https://maven.apache.org/plugin-developers/cookbook/attach-source-javadoc-artifacts.html
  * Verify the generated documentation, according to https://maven.apache.org/plugin-developers/plugin-documenting.html&lt;br/&gt;The maven-docck-plugin can be run:

    mvn docck:check

Documentation
-------------

**\[C20\]** Any product documentation is part of the product, stored in **src/site/markdown** directory, under the same source control as the rest of the product or module it is for.

**Note** that the markdown was chosen as documentation format because it is naturally rendered by GitHub where modelant is published, this way allowing review of the documentation and its history in (almost) the same human-readable presentation, as when it is published.

**\[C21\]** Any product updates must keep up to date its documentation and tests, treating the tests as an active form of the documention (specification).

**\[C22\]** The product documentation in pom-packaged module (i.e. a module that contains other modules) provides the common documentation for its modules. Thus **modelant** module (the root) provides the commmon architecture, conventions and design documents, valid for each module of modelant.

GIT Branching
-------------

**\[C40\]** Name the branches, related to a specific issue in the issue tracking system, with the issue ID.