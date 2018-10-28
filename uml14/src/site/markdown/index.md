UML 1.4
=======

Usage
-----

* Load a UML 1.4 model

```
repository = ModelRepositoryFactory.construct(workDirectory);
modelFactory = repository.loadMetamodel("UML14");
modelExtent = modelFactory.instantiate("model extent name");
repository.readIntoExtent(modelExtent, "model file path" );
  or
modelFactory.readModel(modelExtent, "model file path");
```

Compare UML 1.4 models
----------------------

See [modelant.uml14.maven.plugin](modelant.uml14.maven/modelant.uml14.maven.plugin/index.html) module