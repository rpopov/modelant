Usage
=====

The modules of modelant are Java libraries, that are to be integrated in modelant and client other programs as their dependencies. In addition modelant publishes its complete and independent components as Maven plugins, so they could be used through Maven or Gradle. Here we provide just examples, see the documentation of the modelant modules for detailed explanations.

<!-- MACRO{toc} -->

Example use of the modelant modules
-----------------------------------

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


Example use of modelant components as Maven plugins
---------------------------------------------------

Modelant provides Maven plugins that publish completed services on models. See the corresponding modelant.&lt;component&gt;.maven.&lt;plugin name&gt; modulles.  
For example:

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
    
Detailed documentation
----------------------

* [modelant.repository](modelant.repository/usage.html)
* [modelant.core](modelant.core/usage.html)
* [modelant.template](modelant.template/usage.html)
* [modelant.mof14](modelant.mof14/usage.html)
* [modelant.uml13](modelant.uml13/usage.html)
* [modelant.uml14](modelant.uml14/usage.html)
* [modelant.conversion](modelant.conversion/usage.html)
