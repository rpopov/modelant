Java Conventions
===========


Best Practices
--------------

**\[C10\]** Integrate the unit tests in the development (implementation) project, whereas the API project does not provide any tests. This way the API can be compiled first and independently of the implementation(s), avoiding cyclic dependencies and monolith development.

**\[C11\]** Extract an **integration test** project, consisting only of the **API test code**, with all needed dependencies and (default) implementations. There is no need of other than test/java directory.

Naming Conventions
------------------

**\[C20\]** For each language supported (i.e. metamodel), there are:

    * Maven project with artifact ID: **modelant.&lt;language name&gt;.metamodel**
    * The Maven project consists of the package: **net.mdatools.modelant.&lt;language name&gt;.metamodel**
    * The package contains:
        * Model factory, named: **&lt;language&gt;ModelFactory**, extending BaseModelFactory
        * Operation to print any elements of models in that language (metamodel), named: **Print&lt;language&gt;ModelElement**
        * Operation to compare models in that language, named: **Compare&lt;language&gt;Models**

**\[C21\]**  Functions and Operations naming convention: **&lt;Verb&gt;\[&lt;adjective&gt;&lt;direct object&gt;\]**

**\[C22\]**  Selectors naming convention: **Select\[&lt;adjective&gt;\]&lt;direct object&gt;** as a special case of Function