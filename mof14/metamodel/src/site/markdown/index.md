MOF 1.4 Metamodel
=================

<!-- MACRO{toc} -->

Load the metamodel
------------------

```
modelFactory = modelRepository.initialize("MOF14");
model = modelFactory.instantiate("model extent name");
... 
modelRepository.readIntoExtent(model, "model file path");
  or
modelFactory.readModel(model, "model file path");
```

When processing MOF metamodels (which are models, written in MOF 1.4), then use the pattern:

```
sourceExtent = repository.constructMofExtent( "SOURCE" );
repository.readIntoExtent( sourceExtent, sourceMetamodel );
```

For example, when comparing two metamodels:

```
repository.readIntoExtent( sourceExtent, sourceMetamodel );
repository.readIntoExtent( targetExtent, targetMetamodel );
compare = new CompareMof14Models(bindings, sourceExtent);
result = compare.execute( targetExtent );
```