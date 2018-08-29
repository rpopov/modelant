Procedures
==========

<!-- MACRO{toc} -->

Java Setup Procedure for Windows 
--------------------------------

In Control Panel \ System \ Advanced \ Environment Variables define the global variable:

    JAVA_HOME=C:\PROGRA~1\Java\jdk1.8.0_172\
  
Note that the **\Program Files** directory is referred using its 8:3 file name, **excluding any spaces in the path**.

Eclipse Setup Procedure for Windows 
-----------------------------------

As of http://wiki.eclipse.org/Eclipse.ini Eclipse uses the PATH to search the JVM to run. In Windows the path to the default installation of JAVA executable contains a space, **which causes problems in resolving Maven dependencies referring ${java.home}** in Eclipse environment. Therefore it is needed to set the path to JAVA using the 8:3 file names:

  * Edit **eclipse.ini** file in the Eclipe's root directory:
  * Add before the **-vmargs** line the contents:

  
    -vm
    C:\PROGRA~1\Java\jdk1.8.0_172\bin\javaw.exe
  
where the path is an example of the 8:3 reference to the javaw.exe of of the JVM to use.

Maven Setup Procedure for Windows 
---------------------------------

  * Install Maven in a directory
  * Set the environment variable:
```  
M2_HOME = <Maven installation directory in **8:3 name format**>
```

  * Include in the environment PATH:

```  
PATH=%M2_HOME%\bin;%PATH%
```

  * Edit the **%M2_HOME%\bin\mvn.cmd** file (Windows) **after** the line 
```   
@setlocal
```    
   insert the lines:

```   
if "%DEBUG_PORT%" == "" set DEBUG_PORT=8000
if "%DEBUG_SUSPEND%" == "" set DEBUG_SUSPEND=n
set MAVEN_DEBUG_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=%DEBUG_SUSPEND%,address=%DEBUG_PORT%
```
    
Compile and Deploy Skipping tests 
---------------------------------

Facts:

  * The test of the API package are specifications of the API and black-box tests of any implementation (IMPL) behind the API.
  * While compiling the API tests, there is no implementation yet, so these tests fail.
  * Maven complains when test-scoped dependencies establish cyclic graph.
  * The failed API tests block publishing the API, which fails the compilation of the IMPL module.
  * Moving the API tests into the IMPL module degrades them to control of a single implementation. 

Therefore:

  * Extracted is a separate **test** module to be processed last.
  * All integration/API tests are moved there, while keeping the unit tests in the modules.
  * The **test** module does not contain actual implementation (only tests).

Thus, run as **separate commands**:

  * In the components projects:
  
``` 
mvn clean deploy
```

  * In the integration test project:

```  
mvn test
```

  * Or just in the common root project:

```
mvn install
```
    
Install, Skip Tests 
-------------------

As of https://maven.apache.org/surefire/maven-surefire-plugin/examples/skipping-tests.html

  * Skip running the tests, but compile them:

``` 
mvn install -DskipTests
```
  * Skip even compiling the tests:

```
mvn install -Dmaven.test.skip=true
```

Debug a Test 
------------

The default in Modelant project is Maven to run the tests in a dedicated JVM. The control on the debug port and suspend of that JVM is done through the POM properties:

    DEBUG_PORT (default: 7000)
    DEBUG_SUSPEND (default: n)
    
Usage:

    mvn test -DDEBUG_SUSPEND=y

Debug a Maven Plugin 
--------------------

Maven runs the plugins in the same JVM that runs Maven. According to the setup procedure, the control of the Maven JVM is done through the OS environment variables:

    DEBUG_PORT (default: 8000)
    DEBUG_SUSPEND (default: n)
    
Usage:

    set DEBUG_SUSPEND=y  
    mvn test

Disable a profile
-----------------

    mvn .... -P !profile-name

  
Analyze the Maven phase-to-plugin & goal bindings 
-------------------------------------------------

    mvn fr.jcgay.maven.plugins:buildplan-maven-plugin:list-phase
    
Build the modelant site 
-----------------------

Use the "sites" generated for the modelant's modules and combine them in a single site by calling the site plugin:
  
    mvn site:site

The site is produced in the modelant's **taget/staged-site** directory.

Deploy it to the hosting site with:

    mvn site:deploy
  
Or just call the **site-deploy** phase of the **site** lifecycle:

    mvn site-deploy

Publishing an archetype 
-----------------------

Implemented following the process in 

  * https://maven.apache.org/archetype/maven-archetype-plugin/advanced-usage.html
  * https://maven.apache.org/archetype/maven-archetype-plugin/specification/archetype-metadata.html
  * https://maven.apache.org/archetype/archetype-models/archetype-descriptor/archetype-descriptor.html

  
    mvn clean install archetype:update-local-catalog

Publish in Maven Central
------------------------

In order to publish the modelant components in Maven Central (through OSSHR) activate the **production** profile:

  * In root pom.xml change the modelant version to a release version - remove the -SNAPSHOT suffix
  * Run 

```
mvn clean deploy -P production
```
  * Log in [OSSRH/Sonatype staging repository](https://oss.sonatype.org/service/local/staging/deploy/maven2)
  * In Build Promotion \ Staging Repositories menu find the **net.mdatools** repository
  * **Close** the repository
  * In the **Activity** tab review the status of the components if they pass the quality gates. 
  * If the artifacts did not pass the quality gates:
    * drop the repository
    * fix the problems locally
    * repeat the procedure    
  * In case the of passed quality gates, the components will be uploaded to Maven Central
  
References:
  
  * [Based on](https://www.youtube.com/watch?v=dXR4pJ_zS-0&feature=youtu.be)
  * [Release](https://central.sonatype.org/pages/apache-maven.html#performing-a-release-deployment)
  * [Resolve the identified problems](https://www.youtube.com/watch?v=N7KXuvi_2SE&feature=youtu.be)
  * [See also](https://central.sonatype.org/articles/2016/Feb/02/free-video-series-easy-publishing-to-the-central-repository/)
  
