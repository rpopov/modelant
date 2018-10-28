modelant.uml13.maven.plugin
===========================

modelant publishes a Maven plugin ```net.mdatools:modelant.uml13.maven:3.1.0``` with the following goals:

 * ```xsd-to-uml13``` - reverse engineer an XML Schema to UML 1.3 model
 * ```db-to-uml13``` - reverse engineer a relational database to UML 1.3 model
 * ```java-to-uml13``` - reverse engineer java code to UML 1.3 model
 * ```compare-uml13-models``` - identify the changes of a source (original) model needed to convert it into a target model  

<!-- MACRO{toc} -->

Please note:

 * the [dtd-to-xsd plugin](../../../modelant.conversion/modelant.conversion.maven/modelant.conversion.maven.plugin/index.html), composed with xsd-to-uml13 allows reverse engineering of DTDs to UML 1.3 
 * that the [UML 1.4 plugin](../../../modelant.uml14/modelant.uml14.maven/modelant.uml14.maven.plugin/index.html) allows converting the UML 1.3 models to UML 1.4, which transfers these features to UML 1.4 too. 

Reverse engineering of XSD to UML 1.3 model
-------------------------------------------

```
<plugin>
  <groupId>net.mdatools</groupId>
  <artifactId>modelant.uml13.maven.plugin</artifactId>
  <version>3.2.0</version>
  <executions>
    <execution>
      <phase>compile</phase>
      <goals>
        <goal>xsd-to-uml13</goal>
      </goals>
      <configuration>
        <schemaFile>...</schemaFile>
        <outputFile>...</outputFile>
      </configuration>
    </execution>
  </executions>
</plugin>
```

where:

  * **schemaFile**  The schema file to reverse engineer.
  * **outputFile**  The name of the file where to export the produced UML 1.3 model in XMI 1.2 format

NOTE: The used **compile** phase is default and recommended only. Any other phase would work too.

See [modelant.uml13.reverse](../../modelant.uml13.reverse/index.html) module for details.

Reverse engineer a database schema to UML 1.3 model
---------------------------------------------------

```
<plugin>
  <groupId>net.mdatools</groupId>
  <artifactId>modelant.uml13.maven.plugin</artifactId>
  <version>3.2.0</version>
  <executions>
    <execution>
      <phase>compile</phase>
      <goals>
        <goal>reverseEngineerDatabaseInUml13</goal>
      </goals>
      <configuration>
        <driver>...</driver>
        <url>...</url>
        <user>...</user>
        <password>...</password>
        <schema>...</schema>
        <outputFile>...</outputFile>
      </configuration>
    </execution>
  </executions>
</plugin>
```

where:

  * **driver**  The java class name of the database driver to connect the database. The .jar with that class file should be provided as a dependency of this plugin
  * **outputFile**  The name of the file where to export the produced UML 1.3 model in XMI 1.2 format
  * **password**  Database user's password
  * **schema**  Database schema to reverse engineer
  * **url**  Database driver-specific URL
  * **user**  Database user to connect the database
  * **workDir**  The directory where to store the repository files
  * **outputFile**  The name of the file where to export the produced UML 1.3 model in XMI 1.2 format

NOTE: The used **compile** phase is recommended only. Any other phase would work too.

See [modelant.uml13.reverse](../../modelant.uml13.reverse/index.html) module for details.

Reverse engineer Java sources to UML 1.3 model
----------------------------------------------

```
  <plugin>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.uml13.maven.javauml</artifactId>
    <version>3.2.0</version>        
    <executions>
      <execution>
        <phase>compile</phase>
        <goals>
          <goal>reverseEngineerJavaInUml13</goal>
        </goals>
        <configuration>
          <sourcepath>...</sourcepath>
          <outputDirectory>...</outputDirectory>
          <includeDependencySources>false</includeDependencySources>
          <includeTransitiveDependencySources>false</includeTransitiveDependencySources>
          <debug>true</debug>
          <verbose>true</verbose>              
        </configuration>
      </execution>
    </executions>
  </plugin>
```

where:

  * **sourcepath**  The source paths where the subpackages are located. The sourcepath can contain multiple paths by separating them with a colon (:) or a semi-colon (;).
  * **outputDirectory**  The destination directory where to store the generated model file.

NOTE: The compile phase is set by default, so in &lt;execution&gt; we overwrite it.

See [modelant.uml13.reverse](../../modelant.uml13.reverse/index.html) module for details.

Comparison of UML 1.3 models
----------------------------

```
<build>
  <plugins>
    <plugin>
      <groupId>net.mdatools</groupId>
      <artifactId>modelant.uml13.maven.plugin</artifactId>
      <version>3.3.0</version>
      <executions>
        <execution>
          <phase>compile</phase>
          <goals>
            <goal>compare-uml13-models</goal>
          </goals>
          <configuration>
            <sourceMetamodel>src/resource/source.xml</sourceMetamodel>
            <targetMetamodel>src/resource/target.xml</targetMetamodel>
            <export implementation="net.mdatools.modelant.core.operation.model.export.StructuredTextExport"/>
          </configuration>
        </execution>
      </executions>
    </plugin>      
  </plugins>      
</build>
```

where:

  * **sourceMetamodel**: refers the XMI file of the definition of the metamodel, considered as old/previous version/source in the comparison
  * **targetMetamodel** refers the XMI file of the definition of the metamodel, considered as new/current version/target in the comparison
  * **equals** a list of externally defined mathed elements, overriding the uniform comparison rules
    * **equal** defines a single match of a set of source metamodel elements to a set of target metamodel elements, that should be considered equal, overriding the comparison rules
        * **source** the qualified name of an element in the source metamodel, in the format: ```&lt;owner name&gt;{::&lt;owner name&gt;}::&lt;element name&gt;```
        * **target** the qualified name of an element in the target metamodel, in the format: ```&lt;owner name&gt;{::&lt;owner name&gt;}::&lt;element name&gt;```
  * **export** Define the class to export the results of the models comparison. The default implementation uses its toString(). Provide any implementation of ```net.mdatools.modelant.core.api.diff.Export``` interface. 
 Example:
  ```<export implementation="net.mdatools.modelant.core.operation.model.export.StructuredTextExport"/>``` 
It exports the text in a JSON-like format, suitable for collapsing/expanding and manual analysis. See below.


Pre-defined correspondence and formatting the differences printout 
------------------------------------------------------------------

In this example the MOF 1.4 ModelElement in the source model with name "Data_Types", found in the ModelElement with name "Foundation" (as a namespace) corresponds to the MOF 1.4 ModelElement in the target model, named "Data_Types". The resported outcome is in a JSON-like format. 
  
```
<build>
  <plugins>
    <plugin>
      <groupId>net.mdatools</groupId>
      <artifactId>modelant.uml13.maven.plugin</artifactId>
      <version>3.3.0</version>
      <executions>
        <execution>
          <phase>compile</phase>
          <goals>
            <goal>compare-uml13-models</goal>
          </goals>
          <configuration>
            <sourceMetamodel>...</sourceMetamodel>
            <targetMetamodel>...</targetMetamodel>
            <equals>
              <equal>
                 <source>Logical_View::domain</source>
                 <target>domain</target>
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

The outcome of model comparison formatted by ```net.mdatools.modelant.core.operation.model.export.StructuredTextExport``` is like:

```
{
  deleted = {list of source model elements, that do not exist in the target model},
  added = {list of target model elements, that do not exist in the source model},
  changed = {list of pairs of corresponding source and target model elements, that have some of the attributes or associations changed},
  exactMatch = {list of pairs of corresponding source and target model elements, that match exactly}
}
```
