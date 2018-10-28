modelant.uml13.maven.javauml
============================

This is a maven MOJO / goal implementation of reverse engineering Java sources to UML 1.3 models. It is an adaptation of 
the Maven pplugin for JavaDoc and inherited most of its parameters. Refer to it for additional details. 

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

  * **sourcepath** - the source paths where the subpackages are located. The sourcepath can contain multiple paths by separating them with a colon (:) or a semi-colon (;).
  * **outputDirectory** - the destination directory where to store the generated model file.  