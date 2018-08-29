Usage
=====

<!-- MACRO{toc} -->

Usage in Maven environment
--------------------------

Load the modelant API and components as regular dependencies of the client code, use the published Java API implementations.
For example:

    <dependencies>
      ...
      <dependency>
        <groupId>net.mdatools</groupId>
        <artifactId>modelant.conversion.model</artifactId>
        <version>3.3.0</version>
      </dependency>
      <dependency>
        <groupId>net.mdatools</groupId>
        <artifactId>modelant.uml13.metamodel</artifactId>
        <version>3.3.0</version>
      </dependency>
      ...
    </dependencies>


Usage as Maven plugins
----------------------

Modelant provides Maven plugins that publish completed services on models. See the corresponding modelant.&lt;component&gt;.maven.&lt;plugin name&gt; modulles.  
For example:

pom.xml includes:

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
    
This allows Maven to call the plugin as part of the *compile* phase. 