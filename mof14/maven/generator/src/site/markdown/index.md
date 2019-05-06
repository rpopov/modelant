Generate Language API from a MOF 1.4 Metamodel
==============================================

<!-- MACRO{toc} -->

Notes
------

* The MDR resolves MOF 1.3 (maybe 1.4) DataTypes with nested CORBA type definition like:

```
<Model:DataType xmi.id = 'a33DD6F650276'
  name = 'Boolean' annotation = ''
  isRoot = 'true' isLeaf = 'true' isAbstract = 'false' visibility = 'public_vis' >
  <Model:DataType.typeCode>
    <XMI.CorbaTypeCode>
      <XMI.CorbaTcBoolean/>
    </XMI.CorbaTypeCode>
  </Model:DataType.typeCode>
</Model:DataType>
```
  as instances of AliasType, being alias of a PrimitiveType instance representing the actual external / CORBA data type. See org.netbeans.lib.jmi.xmi.XmiContext.resolveCorbaAlias(Node)
  
Procedures
----------
