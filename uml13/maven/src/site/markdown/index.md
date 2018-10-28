UML 1.3
=======

The whole support of modelant for UML 1.3 through Maven plugins is presented in details in the [plugin module](modeant.uml13.maven.plugin/index.html).

<!-- MACRO{toc} -->

Convert a DTD to XSD
--------------------
```
<plugin>
  <groupId>net.mdatools</groupId>
  <artifactId>modelant.conversion.maven.plugin</artifactId>
  <version>3.2.0</version>
  <executions>
    <execution>
      <phase>compile</phase>
      <goals>
        <goal>dtd-to-xsd/goal>
      </goals>
      <configuration>
        <dtdFile>...</dtdFile>
        <xsdFile>...</xsdFile>
      </configuration>
    </execution>
  </executions>
</plugin>
```

where:

  * **dtdFile**  The DTD file to convert to XSD
  * **xsdFile**  The name of the XSD file to produce.

NOTE: The used **compile** phase is the defaulut and recommended only. Any other phase would work too.

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

All parameters are listed with 

```
  mvn help:describe -Dplugin=net.mdatools:modelant.uml13.maven.javauml:3.3.0 -Ddetail
```

NOTE: The compile phase is set by default, so in &lt;execution&gt; we overwrite it.
