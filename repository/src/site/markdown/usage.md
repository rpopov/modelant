<!-- MACRO{toc} -->

Usage
-----

  * The implementation of the Metamodel Repository should provide the class name of its implementation in the file:
    
    **META-INF/services/net.mdatools.modelant.repository.api.ModelRepository**
    
  * Provide the Metamodel Repository implementation as a dependency in the classpath of the implementation.
    boolean commit;
```     
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
  * The implementation of the Metamodel (factory) should provide the class name of its implementation in the file:
  
    **META-INF/services/net.mdatools.modelant.repository.spi.ModelFactorySetup**
    
  * The implementation of the Metamodel should implement the **net.mdatools.modelant.repository.spi.ModelFactorySetup** interface. This way any metamodels should provide at least a minimum functionality defined by the ModelFactory interface.
  * Provide the Metamodel Repository implementation as a dependency in the classpath of the implementation.

Execution Dependencies
----
```
<dependencies>
   <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.reposotory.impl</artifactId>
    <version>3.1.1-SNAPSHOT</version>
  </dependency>
</dependencies>
```

Generate a Maven project for a metamodel API module
-----

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
