Java Conventions
-----------------------

**\[JC1\]** For each language supported (i.e. metamodel), there are:

    * Maven project with artifact ID: **modelant.&lt;language name&gt;.metamodel**
    * The Maven project consists of the package: **net.mdatools.modelant.&lt;language name&gt;.metamodel**
    * The package contains:
        * Model factory, named: **&lt;language&gt;ModelFactory**, extending BaseModelFactory
        * Operation to print any elements of models in that language (metamodel), named: **Print&lt;language&gt;ModelElement**
        * Operation to compare models in that language, named: **Compare&lt;language&gt;Models**

**\[JC2\]**  Functions and Operations naming convention: **&lt;Verb&gt;\[&lt;adjective&gt;&lt;direct object&gt;\]**

**\[JC3\]**  Selectors naming convention: **Select\[&lt;adjective&gt;\]&lt;direct object&gt;** as a special case of Function