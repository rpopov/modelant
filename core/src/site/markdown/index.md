core
=====

The **modelant core** implements the core algotithms on models of modelant.

<!-- MACRO{toc} -->

Intefaces
---------

The **modelant core** tries to isolate as much as possible the individualt functions (methods) and opertaions on models, alloowing them to be composed in other functions and operations, still allowing them to evolve independently of other methods and reasons for change. This approach tries to apply the Single Responsibility Principle in a pure form, therefore the operations on models are provided as implementations of the follwoing interfaces:

* net.mdatools.modelant.core.api.Function, defined as
```
public interface Function<A,R> {

  /**
   * @param argument to apply this function (instance) on
   * @return the result of applying the function on the provided argument
   * @throws RuntimeException
   * @throws IllegalArgumentException when the operation is not defined for the provided arguments
   */
  R execute(A argument) throws RuntimeException, IllegalArgumentException;
}
```
* net.mdatools.modelant.core.api.Operation, defined as:
```
/**
 * The unary operation is a single argument function (mapping) from one set to itself
 * @param <T> the type of the argument and result
 */
public interface Operation<T> extends Function<T, T> {
  Operation<?> DEFAULT = new Identity<>();
}
```
* net.mdatools.modelant.core.api.Condition 
```
public interface Condition<A> {

  /**
   * @param argument the argument to apply this condition (instance) to
   * @return true if this condition set up represents a tautology and false otherwise.
   * @throws IllegalArgumentException when the condition is not defined for the provided arguments
   * @throws RuntimeException in any case of failed processing
   */
  boolean eval(A argument) throws RuntimeException, IllegalArgumentException;
}
```

The common principle applied is that any implementations of these interfaces are:

* constructed with all additional parameters, that should never change
* immutable, providing stateless operation

Together with the net.mdatools.modelant.core.api.operation.Compose (high order) operation on Operations, these interfaces allow applying more functional programming model, focused on clear desigm avoiding coupling, clear responsibilites. 

Query Models
-----

The **core** package provides the **Select** interface, whose implementations are queries over the contents of a model repository.  

Compare Models
-----

It turned out that:

  * the implemented model comparison algorithm compares the UML 1.3 models very well, so the following models matching criteria are enough:
    * net.mdatools.modelant.core.api.match.MatchingCriteria.NAME_AND_NAMESPACE_MATCH
    * net.mdatools.modelant.core.api.match.MatchingCriteria.NAME_MATCH
  * the comparison of MOF models (metamodels) requires skipping / disregarding some fields, like **qualifiedName** derived attribute:
    * the comparison requires specifying attributes and associations to exclude from the exact match comparison 
  * Added explicit interface net.mdatools.modelant.core.api.diff.Export for exporting the results of models comparison (instances of net.mdatools.modelant.core.api.diff.ModelComparisonResult):
    * Provided a default export mechanism - the net.mdatools.modelant.core.operation.model.ModelComparisonResultImpl.toString() method
    * Provided a structured text export mechanism - net.mdatools.modelant.core.operation.model.export.StructuredTextExport which exports the contents in a JSON-like structure
    * The model comparison result combines the added (deleted) model elements, considering their nesting, defined  in the matching criteria, so that added (deleted) are indicated the whole composites, instead of reporting  their parts.

Use the CompareModels operation:

```
// compare the models without a priori known correspondence
compare = new CompareModels( MatchingCriteria.NAME_MATCH,
                             MatchingCriteria.NONE,
                             ConsideredEqual.toList( new HashMap<>() ),
                             sourceModel );
comparisonResult = compare.execute( targetModel );
```

Copy Model
-----

Example:
```
copy = new CopyModel( sourceModel );
correspondence = copy.execute( targetModel );
```
Copy a Model from Metamodel X to Metamodel Y
-----

Example:
```
copy = new CopyToMetaModel( sourceModel, metamodelMapping );
correspondence = copy.execute( targetModel );
```
Actually Copy Model is a CopyToMetaModel with IDENTITY transformation of the metamodel.

Compilation Dependencies
-----

Project dependencies:

```
<dependencies>  
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.repository.api</artifactId>
    <version>3.1.0-SNAPSHOT</version>
  </dependency>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.core.api</artifactId>
    <version>3.1.0-SNAPSHOT</version>
  </dependency>    
</dependencies>  
```
Execution Dependencies
-----

Project dependencies:
```
<dependencies>  
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.repository.impl</artifactId>
    <version>3.1.0-SNAPSHOT</version>
  </dependency>
  <dependency>
    <groupId>net.mdatools</groupId>
    <artifactId>modelant.core.impl</artifactId>
    <version>3.1.0-SNAPSHOT</version>
  </dependency>    
</dependencies>
```

Modules
-------

* [Core API](modelant.core.api/index.html)
* [Core Implementation](modelant.core.impl/index.html)
* [Core Tests/Specification](modelant.core.test/index.html)
