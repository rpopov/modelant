Core API
========

The **modelant core API** defines the principles and interfaces implemented all over modelant. 

<!-- MACRO{toc} -->

Architectural Principles
------------------------

**\[A20\]** The main reasons to define a subclass of a Java class are:

* to add attributes - extending class' data / domain by mutiplyinng it with the domains of the addted attributes
* to alter the behavior of the class by overriding a method - keeping the domain, altering class' function(s)
* combination of above

These resons introduce coupling in the resons for changing the class/subclassing in the terms of the Single Responsibility Principle. It frequently happens that the use of a method/function introduces the provisining and use all other coupled data and other methods, which are not essential in that case. Therefore, implement the **functions and predicates** in modelant using "functional classes", defined as:

* implementing of exactly one of the interfaces from **core API**
    * Condition - the root interface for predicates
    * Filter
    * Function - the root interfaces for functions
    * Operation
    * Order
    * Procedure - the root interface of procedures (side effects)
    * Selector
    * Translate
* being **immutable (single-state)**, initialized through its constructor, having only **final fileds**
* the functions and conditions do not alter the state of the objects, it receives as constrictor or method arguments, whereas the procedures are allowed to alter only the argument of their execute method
* having no other public methods, that one defined in the interface and those of the Object class
* having any other methods of the class are either 
    * private 
    * protected abstract
    * protected final

In this organization:

* each class functional class' instance is thread safe and its behavior is deterministic (limited by other operations, not controlled by this class, that alter the argiments)
* each class, that needs the function of a functional class, delegates to an appropriately initialized instance of its
* the operation.Compose operation over instances of functional classes' actually is a higher order function that constructs functional class instance by composition in run time, close to the ideas of the functional programming
* the reason to subclass the functional classes is always only one - to change its function by composing functions in compile time in the form of extracting and implementing protected abstract methods
* the code becomes cleaner.


diff
-----

The **diff** package states the interfaces needed to compare models and identify model differences:

* diff\AssociationDifference
* diff\Export
* diff\InstanceDifference
* diff\ModelComparisonResult
* diff\ModelDifference

match
-----

The **match** package defines the parameters of and how to do the models comparison:
* match\ConsideredEqual
* match\MatchingCriteria

model
-----
model\ConstructOperation
model\ConstructProcedure
model\NameMapping

name
-----

Defined are names as means to lookup and identify (meta)model elements:

* name\AssociationName
* name\ClassName
* name\EnumValueName
* name\FieldName
* name\Name
* name\PackageName
* name\StructName

operation
-----

Operations on functional class instances:

* operation\Compose
* operation\Constant
* operation\Identity


wrap
-----

This is the mechanism to wrap the interfaces, provided by the Model Repository (as of JMI) and add additional methods, needed for OOP in the specific context(s) of modelant, code generation and more.

wrap\Wrapper
wrap\WrapperFactory
