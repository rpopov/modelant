UML 1.3
=======

API and Metamodel Usage
-----------------------

Load a UML 1.3 model

```
repository = ModelRepositoryFactory.construct(workDirectory);
modelFactory = repository.loadMetamodel("UML13");
modelExtent = modelFactory.instantiate("model extent name");
repository.readIntoExtent(modelExtent, "model file path" );
  or
modelFactory.readModel(modelExtent, "model file path");
```

Reverse engineering of XSD to UML 1.3 model
-------------------------------------------

See [modelant.uml13.reverse](modelant.uml13.reverse/index.html) module

Reverse engineer a database schema to UML 1.3 model
---------------------------------------------------

See [modelant.uml13.reverse](modelant.uml13.reverse/index.html) module

Reverse engineer Java sources to UML 1.3 model
----------------------------------------------

See [modelant.uml13.reverse](modelant.uml13.reverse/index.html) module