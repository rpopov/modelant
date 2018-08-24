MOF 1.4
=======

<!-- MACRO{toc} -->

Usage
-----

Even though, the metamodel of MOF 1.4 is built in the Repository, there is still a way to treat MOF metamodels as models and them explicitly using the common mechanisms of Modelant:

```
modelFactory = modelRepository.initialize("MOF14");
model = modelFactory.instantiate("model extent name");
... 
modelRepository.readIntoExtent(model, "model file path");
  or
modelFactory.readModel(model, "model file path");
```

When processing MOF metamodels (which are models, written in MOF 1.4), then use the pattern:

```
sourceExtent = repository.constructMofExtent( "SOURCE" );
repository.readIntoExtent( sourceExtent, sourceMetamodel );
```

For example, when comparing two metamodels:

```
repository.readIntoExtent( sourceExtent, sourceMetamodel );
repository.readIntoExtent( targetExtent, targetMetamodel );
compare = new CompareMof14Models(bindings, sourceExtent);
result = compare.execute( targetExtent );
```

Maven Plugin: Compare MOF 1.4 Metamodels
-----

Usage:
```
  <build>
    <plugins>
      <plugin>
        <groupId>net.mdatools</groupId>
        <artifactId>modelant.mof14.maven.plugin</artifactId>
        <version>${revision}</version>
        <executions>
          <execution>
            <phase>compile</phase>
            <goals>
              <goal>compare-metamodels</goal>
            </goals>
            <configuration>
              <sourceMetamodel>...</sourceMetamodel>
              <targetMetamodel>...</targetMetamodel>
              <equals>
                <equal>
                   <source>Foundation::Data_Types</source>
                   <target>Data_Types</target>
                </equal>
                ...
                <export implementation="net.mdatools.modelant.core.operation.model.export.StructuredTextExport"/>
              </equals>
            </configuration>
          </execution>
        </executions>
      </plugin>
    </plugins>
  </build>
```

where:

  * **sourceMetamodel**  refers the XMI file of the definition of the metamodel, considered as old/previous version/source in the comparison
  * **targetMetamodel**  refers the XMI file of the definition of the metamodel, considered as new/current version/target in the comparison
  * **equals**  a list of externally defined mathed elements, overriding the uniform comparison rules
  * **equal**  defines a single match of a set of source metamodel elements to a set of target metamodel elements, that should be considered equal, overriding the comparison rules
  * **source**  the qualified name of an element in the source metamodel, in the format: &lt;owner name&gt;{::&lt;owner name&gt;}::&lt;element name&gt;
  * **target**  the qualified name of an element in the target metamodel, in the format: &lt;owner name&gt;{::&lt;owner name&gt;}::&lt;element name&gt;
  * **export**  Define the mechanism to export the results of the models comparison. The default implementation uses its toString(). Alternative:
```  
  <export implementation="net.mdatools.modelant.core.operation.model.export.StructuredTextExport"/>
```
  It exports the text in a JSON-like format, suitable for collapsing/expanding and manual analysis.
