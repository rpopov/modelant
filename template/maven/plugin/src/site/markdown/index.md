Modelan Template Plugin
=======================

The mdodelant Template plugin publishes 2 MOJOs:

* **Apply Template** - to apply a template on a (String) object, found as a value of a Maven property.
* **Compile Template** - to compile a set of templates to .class files, which allows publishing the templates as compiled classes, not revealing their source code. Actually, the Template Engine does not impose that - it could work both with compled or templates distribited as sources, but in some cases the distribution of the templates in compiled form could be needed. The compilation can be used also for verification of the syntactical correctness of the templates.