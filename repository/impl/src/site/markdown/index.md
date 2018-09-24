Default Model Repository
------------------------

The mdoelant (default) Model Repository is provided in this **modelant.repository.impl** .jar. Use it with:
```
<dependencies>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.repository.impl</artifactId>
    <version>${revision}</version>
  </dependency>
</dependencies>
```

Implement a Model Repository
----------------------------

In order to integrate in modelant, any Model Repository should implement the Repository Service Provider Interface (SPI) as follows:

* Implement **net.mdatools.modelant.repository.spi.ModelRepositorySetup** intterface, where:
    * its **initialize(File workDir)** method initializes the repository to hold its temporary files (if any) in the working directory provided;
    * its **loadMetamodel()** methods could return instances of **net.mdatools.modelant.repository.spi.BaseModelFactory**, which alreday provides means for integration using the ModelRepository API
* As of **\[A4\]**, use the java Services framework to provide the implementation class name of the Repository by storing the class name in implementation jar's file:
```
META-INF/services/net.mdatools.modelant.repository.spi.ModelRepositorySetup
```

The default modelant repository provides **loadMetamodel()** that searches for implementations of **net.mdatools.modelant.repository.api.ModelFactory** through the java Services framework. Thus, each modelant metamodels implementation provides a subclass of **net.mdatools.modelant.repository.spi.BaseModelFactory**, by implementing only the two abstract methods:

* **getMetamodelPath()** to retrurn the physical location of the MOF 1.4 file with the metamodel to load, so that this ModelFactory produces instances (models) of;
* **getModelPackageName()** to return the name of the outer-most MOF 1.4 package, holding all other MOF packages of the metamodel it represents. This package becomes the repostory extent the metamodel is loaded in.

This way the implementation of a metamodel is separated from the implementation of the JMI repository, but a new implementation of the Reposotory would require re-implementing the metamodel lookup mechanism.

Example Implementations
-----------------------

The provided implementations of MOF 1.4, UML 1.3 and UML 1.4 metamodels just subclass **BaseModelFactory**. See:

* [MOF 1.4](../../modelant.mof14)
* [UML 1.3](../../modelant.uml13)
* [UML 1.4](../../modelant.uml14)
