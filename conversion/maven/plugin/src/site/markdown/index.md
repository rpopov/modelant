modelant.convension.maven.plugin
================================

Any operations in terms of **\[A20\]** from [modelant.conversion.model](../../modelant.conversion.model/index.html) are published as maven plugins.
Possible are the following use cases:

* convert a UML 1.3 model to UML 1.4 model - use the [uml13-to-uml14](./plugin-info.html) maven plugin
* reverse engineer an XML Schema into an UML 1.4 model - use the [xsd-to-uml13](../../../modelant.uml13/modelant.uml13.maven/modelant.uml13.maven.plugin/plugin-info.html) maven plugin to produce UML 1.3 model and apply the [uml13-to-uml14](./plugin-info.html) maven plugin to convert it to UML 1.4
* reverse a DTD into  into an UML 1.4 model - use the [dtd-to-xsd](./plugin-info.html) maven plugin to produce an XML Schema from the DTD, on the outcome apply the [xsd-to-uml13](../../../modelant.uml13/modelant.uml13.maven/modelant.uml13.maven.plugin/plugin-info.html) maven plugin to produce UML 1.3 model and apply the [uml13-to-uml14](./plugin-info.html) maven plugin to convert it to UML 1.4
* ... and many more...

<!-- MACRO{toc} -->

See also
--------
* [modelant maven plugin goals](./plugin-info.html)