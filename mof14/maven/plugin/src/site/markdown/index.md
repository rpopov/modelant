modelant.mof14.maven.plugin
===========================

Modelant publishes a Maven plugin that compares two models written in MOF 1.4. The regular practice is to use such models
as definitions of other modeling languages - metamodels. In MDA it is important to know how two (meta)models correspond to
each other or what changes were introduced in a model. This is achieved by comparing such models, which is the MOF 1.4 Model Comparison plugin '''net.mdatools:modelant.mof14.maven:3.1.0''', goal:compare-mof14-metamodels), which compares 
two models and reports the differences, that are needed to convert the source model into the target one.

<!-- MACRO{toc} -->

Usage
-----

Below are provided two usage scenarios 

### General Scenario

If both (meta)models are provided in the plugin's classpath, the simplest way to compare them is just to provide the relative path to them. The plugin will report the detected changes in a basic form:

```
  <plugin>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.mof14.maven.plugin</artifactId>
    <version>@project.version@</version>
    <executions>
      <execution>
        <phase>compile</phase>
        <goals>
          <goal>compare-mof14-metamodels</goal>
        </goals>
        <configuration>
          <sourceMetamodel>src/resource/01-12-02_Diff_modelant.xml</sourceMetamodel>
          <targetMetamodel>src/resource/01-02-15_Diff_modelant.xml</targetMetamodel>
        </configuration>
      </execution>
    </executions>
  </plugin>      
```
where:

  * **sourceMetamodel**: refers the XMI file of the definition of the metamodel, considered as old/previous version/source in the comparison
  * **targetMetamodel** refers the XMI file of the definition of the metamodel, considered as new/current version/target in the comparison
  * **equals** a list of externally defined mathed elements, overriding the uniform comparison rules
    * **equal** defines a single match of a set of source metamodel elements to a set of target metamodel elements, that should be considered equal, overriding the comparison rules
        * **source** the qualified name of an element in the source metamodel, in the format: ```&lt;owner name&gt;{::&lt;owner name&gt;}::&lt;element name&gt;```
        * **target** the qualified name of an element in the target metamodel, in the format: ```&lt;owner name&gt;{::&lt;owner name&gt;}::&lt;element name&gt;```
  * **export** Define the class to export the results of the models comparison. The default implementation uses its toString(). Example:
  ```<export implementation="net.mdatools.modelant.core.operation.model.export.StructuredTextExport"/>``` 
It exports the text in a JSON-like format, suitable for collapsing/expanding and manual analysis.

### Comparison of UML 1.3 and UML 1.4 metamodels

modelant already provides the metamodels of UML 1.4 and UML 1.4, so making them available in the plugin's classpath is just referrng them as project dependencies:

```
  <dependencies>
    <dependency>
      <groupId>net.mdatools</groupId>
      <artifactId>modelant.mof14.metamodel</artifactId>
      <version>@project.version@</version>
    </dependency>
    <dependency>
      <groupId>net.mdatools</groupId>
      <artifactId>modelant.mof14.maven.plugin</artifactId>
      <version>@project.version@</version>
    </dependency> 
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>net.mdatools</groupId>
        <artifactId>modelant.mof14.maven.plugin</artifactId>
        <version>@project.version@</version>
        <executions>
          <execution>
            <phase>compile</phase>
            <goals>
              <goal>compare-mof14-metamodels</goal>
            </goals>
            <configuration>
              <sourceMetamodel>src/resource/01-12-02_Diff_modelant.xml</sourceMetamodel>
              <targetMetamodel>src/resource/01-02-15_Diff_modelant.xml</targetMetamodel>
            </configuration>
          </execution>
        </executions>
      </plugin>      
    </plugins>      
  </build>
```

As a result, the following changes from UML 1.3 to UML 1.4 were reported:

### Formatting the differences printout for specific further processing 


Usage:
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

where:
;sourceMetamodel: refers the XMI file of the definition of the metamodel, considered as old/previous version/source in the comparison
;targetMetamodel: refers the XMI file of the definition of the metamodel, considered as new/current version/target in the comparison
;equals: a list of externally defined mathed elements, overriding the uniform comparison rules
;equal: defines a single match of a set of source metamodel elements to a set of target metamodel elements, that should be considered equal, overriding the comparison rules
;source: the qualified name of an element in the source metamodel, in the format: &lt;owner name&gt;{::&lt;owner name&gt;}::&lt;element name&gt;
;target: the qualified name of an element in the target metamodel, in the format: &lt;owner name&gt;{::&lt;owner name&gt;}::&lt;element name&gt;
;export: Define the mechanism to export the results of the models comparison. The default implementation uses its toString(). Alternative:
  <export implementation="net.mdatools.modelant.core.operation.model.export.StructuredTextExport"/>
It exports the text in a JSON-like format, suitable for collapsing/expanding and manual analysis.



Based on the report above, the [changes from UML 1.3 to UML 1.4](../../../modelant.conversion/index.html) are identified.
