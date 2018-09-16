modelant.repository
===================

The **repository** module provides an interface ([API](modelant.repository.api/index.html)) and an implementation ([impl](modelant.repository.impl/index.html)) of a a model repository compliant with Java Metadata Interface [(JMI 1.0)](https://jcp.org/en/jsr/detail?id=40). The repository implements strictly the [JMI 1.0 (JSR-40)](https://jcp.org/en/jsr/detail?id=40) specification. The interface ([API](modelant.repository.api/index.html)) module defines how to instantate the repository and create and locate its extents. This API is proprietary, as JMI does not specify it. The JMI repository implementation is Netbeans Meta-Data Repository (MDR). It is possible to use any implementation of the JMI (JSR-40) specification, easily integrated with the API through the standard Java Services framework. 

The [JMI 1.0 (JSR-40)](https://jcp.org/en/jsr/detail?id=40) defines rules to build the interfaces to access a model in a specific modeling language (metamodel), held in a JMI-compliant repository. Therefore the **repository** module provides also a Maven archetype to generate the Maven project structure, the language-specific interfaces in source, the a pom.xml to compile and publish them as a standalone .jar file.

<!-- MACRO{toc} -->

Compilation Dependencies
------------------------
The compilation against the model repository needs only the dependency:

```
<dependencies>  
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.repository.api</artifactId>
    <version>${revision}</version>
  </dependency>
</dependencies>  
```

Execution Dependencies
----------------------
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

As of [\[A4\]](../architecture.html) instantiate the modelant models **repostory** with the snippet:

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

Modules
-------

* [Repository API](modelant.repository.api/index.html)
* [Repository Implementation](modelant.repository.impl/index.html)
* [Maven support for modeling](modelant.repository.maven/index.html)