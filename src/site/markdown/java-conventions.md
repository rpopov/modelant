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

https://gist.github.com/goldbattle/9283399

https://www.oracle.com/technetwork/java/codeconventions-150003.pdf

https://google.github.io/styleguide/javaguide.html

https://github.com/raywenderlich/java-style-guide/blob/master/README.markdown

https://github.com/twitter/commons/blob/master/src/java/com/twitter/common/styleguide.md

https://www.javacodegeeks.com/2012/10/java-coding-conventions-considered-harmful.html