Usage
=====

The modules of modelant are Java libraries, that are to be integrated in modelant and client other programs as their dependencies. In addition modelant publishes its complete and independent components as Maven plugins, so they could be used through Maven or Gradle. Here we provide just examples, see the documentation of the modelant modules for detailed explanations.

<!-- MACRO{toc} -->

Overview
--------

As of **[\[A4\]](../architecture.html)**, use the **ModelRepositoryFactory** to initialize and instantiate the Model repository (wrapping the Netbeans Metamodel Repository). The repository constructs **ModelFactory** instances, each as a factory to produce **extents** in a specific **metamodel**, i.e. modeling language. Each extent is a **javax.jmi.reflect.RefPackage** instance, that stores an instance (model) of that metamodel, this way supporting the creation, load, processing and writing models in different modeling languages. The modeling lanugaes (metamodels), supported by modelant 3+ are only those in OMG MOF 1.4 and XMI 1.1/XMI 1.2. Some specific selfcontained processes are also published as Maven plugins, this way reducing the need to write code and hiding the described complexity.

Example usage of modelant in code
---------------------------------

**modelant** is intended for direct use in Java code

```
import javax.jmi.reflect.RefPackage;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
import net.mdatools.modelant.repository.api.ModelRepository;
...
ModelRepository repository;
ModelFactory modelFactory;
RefPackage modelExtent;
...
repository = ModelRepositoryFactory.construct(workDirectory);

modelFactory = repository.loadMetamodel("UML13");

modelExtent = modelFactory.instantiate("model extent name");  
repository.readIntoExtent(modelExtent, "model file path" );
  or
modelFactory.readExtent(modelExtent, new File("model file path"));
...
use the modelant core and the metamodel implementation to query and manipulate the model
...
modelFactory.writeExtent(modelExtent, new File("model file path"));
```

Example of modelant Maven plugin
--------------------------------

**modelant** provides Maven plugins that publish complete processes on models. See the corresponding documentation.

```
<build>
  ...
  <plugins>
    <plugin>
      <groupId>net.mdatools</groupId>
      <artifactId>modelant.conversion.maven.plugin</artifactId>
      <version>3.3.0</version>
      <executions>
        <execution>
          <phase>compile</phase>
          <goals>
            <goal>uml13-to-uml14</goal>
          </goals>
          <configuration>
            <sourceModel>test/xmi/demo.xml</sourceModel>
            <targetModel>target/converted-to-UML14.xml</targetModel>
          </configuration>
        </execution>
      </executions>
    </plugin>
    ...
  </plugins>
</build>
```
    
Detailed documentation
----------------------

* [modelant.repository](modelant.repository/usage.html)
* [modelant.core](modelant.core/usage.html)
* [modelant.template](modelant.template/usage.html)
* [modelant.mof14](modelant.mof14/usage.html)
* [modelant.uml13](modelant.uml13/usage.html)
* [modelant.uml14](modelant.uml14/usage.html)
* [modelant.conversion](modelant.conversion/usage.html)
