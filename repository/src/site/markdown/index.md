modelant.repository
===================

The **repository** module provides an interface ([API](modelant.repository.api/index.html)) and an implementation ([impl](modelant.repository.impl/index.html)) of a a model repository compliant with Java Metadata Interface [(JMI 1.0)](https://jcp.org/en/jsr/detail?id=40). The repository implements strictly the [JMI 1.0 (JSR-40)](https://jcp.org/en/jsr/detail?id=40) specification. The interface ([API](modelant.repository.api/index.html)) module defines how to instantate the repository and how create and locate its extents. This API is proprietary, as JMI does not specify it. The JMI repository implementation is Netbeans Meta-Data Repository (MDR). Tt is possible to use another implementation of the JMI (JSR-40) specification, easily integrated with the API through the standard Java Services framework. 

The [JMI 1.0 (JSR-40)](https://jcp.org/en/jsr/detail?id=40) defines rules to build the interfaces to access a model in a specific language (metamodel), held in a JMI-compliant repository. Therefore the **repository** module provides also a Maven archetype to generate the Maven project structure, the language-specific interfaces in source, the a pom.xml to compile and publish them as a standalone .jar file:

* **api** module with the interfaces of the Repository module
* **impl** module with the implementation of the API, following [\[A4\]](../architecture.html)
* **maven** module with:
  * a maven archetype **modelant.repository.maven.archetype 3.1.0** to generate the directories structure and files of a modelant maven project for a specific modeling 
  * a plugin to generate the API for a metamodel, set up in a POM

The follwoing presents how to use the 

<!-- MACRO{toc} -->

Dependencies
------------
In order to use the model repository integrated in modelant out-of-the-box, needed are the following dependencies:

```
<dependencies>  
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.repository.api</artifactId>
    <version>${revision}</version>
  </dependency>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.repository.impl</artifactId>
    <version>${revision}</version>
  </dependency>
</dependencies>  
```

Instantiate a Repository
------------------------

In general, the way to instantiate the modelant models **repostory**, as of [\[A4\]](../architecture.html) use the snippet:

```
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
...
modelRepository = ModelRepositoryFactory.construct(storage file);
```
Nevertheless, once the repository is instantiated, it must be closed when done and its resources have to be released. Therefore apply the following idiom of the repository's lifecycle:

```
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
...
  boolean commit;
       
  modelRepository = ModelRepositoryFactory.construct(storage file);
  try {
    modelRepository.beginTransaction();
    try {
      .. 
      manipulate
      ... 
      commit = true;
    } catch (Exception ex) { // rollback
      commit = false;
    } finally {
      modelRepository.endTransaction(commit);
    }
  } finally {
    modelRepository.shutdown()
  }
```    

See also
--------

* [Repository API](repository.api/index.html)
* [Repository Implementation](repository.impl/index.html)
* [Maven support for modeling](repository.maven/index.html)
