modelant.uml13.metamodel
========================

modelant publishes the UML 1.3 metamodel component that allows program load, manipulation and export of of UML 1.3 models, together with an operation to compare UML 1.3 models.

See the [dedicated plugin documentation](./modelant.uml13.maven.plugin/index.html)

<!-- MACRO{toc} -->

Usage
-----

Load a UML 1.3 model

```
ModelRepository repository;
ModelFactory modelFactory; 
org.omg.uml13.Uml13Package model;

repository = ModelRepositoryFactory.construct(workDirectory);
modelFactory = repository.loadMetamodel("UML13");
model = modelFactory.instantiate("model name");
repository.readIntoExtent(model, "model file path" );
```

Use the org.omg.uml13.Uml13Package interface to navigate the model  and retrieve the model elements.