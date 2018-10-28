modelant.uml14.metamodel
========================

modelant publishes the UML 1.4 metamodel component that allows program load, manipulation and export of of UML 1.4 models, together with an operation to compare UML 1.4 models.

See the [dedicated plugin documentation](./modelant.uml14.maven.plugin/index.html)

<!-- MACRO{toc} -->

Usage
-----

Load a UML 1.4 model

```
ModelRepository repository;
ModelFactory modelFactory; 
org.omg.uml14.Uml14Package model;

repository = ModelRepositoryFactory.construct(workDirectory);
modelFactory = repository.loadMetamodel("UML14");
model = modelFactory.instantiate("model name");
repository.readIntoExtent(model, "model file path" );
```

Use the org.omg.uml14.Uml14Package interface to navigate the model  and retrieve the model elements.