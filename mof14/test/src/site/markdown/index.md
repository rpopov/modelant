modelant.mof14.test
===================

Integration tests of MOF 1.4 models processed through the core functionality of modelant, as of **\[A4\]** 

<!-- MACRO{toc} -->

Tests
-----

* CompareMof14ModelsUml13toUml14Test - compare the UML 1.3 and UML 1.4 metamodels considered as models (instances) of MOF 1.4 metamodel, providing no initial elements correspondence.
* CompareMof14ModelsUml13toUml14WithEqualTest - compare the UML 1.3 and UML 1.4 metamodels considered as models (instances) of MOF 1.4 metamodel, providing initial obvious elements correspondence.
* CompareSameMof14ModelsTest - compare MOF 1.4 metamodel with itself, considering that it is a MOF 1.4 model
* Mof14CompareModelantAndOriginalUml13MetamodelsTest  - Compare the extesion of UML 1.3 metamodel for modelant to the original UML 1.3 metamodel. Prove the model comparison algorithm works correctly by finding some differences.
* Mof14CompareModelantAndOriginalUml14MetamodelsTest  - Compare the extension of UML 1.4 metamodel for modelant to the original UML 1.4 metamodel. Prove the model comparison algorithm works correctly by finding some differences.
* Mof14CompareSameUml13MetamodelsTest - Test the metamodels comparison by comparing UML 1.3 to itself. Prove the model comparison algorithm works correctly by finding no differences.
* Mof14CompareSameUml14MetamodelsTest - Test the metamodels comparison by comparing UML 1.4 to itself. Prove the model comparison algorithm works correctly by finding no differences.