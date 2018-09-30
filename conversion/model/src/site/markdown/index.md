modelant.convension.model
=========================

Provided are the pure java implementation(s) of operations in terms of **\[A20\]** for model manipulations. These operations are also published as Maven plugins in the [modelant.conversion.maven](../modelant.conversion.maven/index.html) project.


<!-- MACRO{toc} -->

See also:

* [modelant core operations](../../modelant.core/index.html)

Operation: Convert UML 1.3 to UML 1.4
-------------------------------------

Use this snippet to convert a UML 1.3 model to UML 1.4 programatically:

```
convert = new ConvertUml13ToUml14(sourceUml13Extent);
mappings = convert.execute(targetUml14Extent);
```

Maven dependencies:

```
<dependencies>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.conversion.model</artifactId>
    <version>${revision}</version>
  </dependency>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.uml13.metamodel</artifactId>
    <version>${revision}</version>
  </dependency>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.uml14.metamodel</artifactId>
    <version>${revision}</version>
  </dependency>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.core.impl</artifactId>
    <version>${revision}</version>
  </dependency>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.repository.impl</artifactId>
    <version>${revision}</version>
  </dependency>
</dependencies>
```
Note: the repository implementation could be replaced.
