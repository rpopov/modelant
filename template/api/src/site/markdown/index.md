Template Compilation Context
----------------------------

The Template Compilation Context provides:

  * **encoding** is the encoding as text files of all templates in the context and encoding of the produced output.
  * **template source directory**  the root directory, relatively which the templates' directories and files are located.
  Default: **${project.build.sourceDirectory}/../template**
  * **java directory**  the root directory where to translate the template to temporary java files.<br/>Default: **${project.build.directory}/java**
  * **class directory**  the root directory where to compile the java files the templates are translated to.<br/>Default: **${project.build.outputDirectory}**
  * **unique name**  a unique name of this template context among all other contexts used simultaneously. It identifies the context and makes the template names in it unique amnong the template names in all contexts.<br/>Examples: **domain**, **view**, **persistence**, **db**
  * **keepGenerated**  indicates if the translation of the template to java should be kept (true) for review, instead of being deleted after the compilation (false)<br/>Default: **false**
  * **compileForDebug**  indicates if the compilation of the template should include debug data (true) in the produced class.<br/>Default: **false**
