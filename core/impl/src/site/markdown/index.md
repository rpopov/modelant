Core Implementation
===================

This component implements the core algorithms in modelant, namely:

* CompareModels
    * CompareMof14Models
    * CompareUml13Models
    * CompareUml14Models
* CopyToMetaModel
    *  ConvertUml13ToUml14
    *  CopyModel
* ReverseDatabaseOperation
* ReverseXsdOperation
* Verify
    
Formatting and Printing
-----------------------    
    
* FormatWrapText
    *  FormatWrapTextJavaDoc
* FormatWordsString
    *  FormatAbbreviated
    *  FormatAllCapital
    *  FormatAllLower
    *  FormatFirstCapital
    *  FormatFirstLower
    *  FormatHumanReadable
    *  FormatPackageName
* PrintElementRestricted
    *  PrintMof14ModelElement
    *  PrintUml13ModelElement
    *  PrintUml14ModelElement
* PrintModelElement

Quering the model
-----------------

* Selector&lt;A, T&gt;
    *  SelectAllObjects
    *  SelectByQualifiedName
    *  SelectFixedElements&lt;A, T&gt;
    *  SelectMetaClass
    *  SelectMetaclassInstances
    *  SelectMetaPackage