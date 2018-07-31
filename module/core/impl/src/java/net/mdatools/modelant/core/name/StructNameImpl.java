/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 14.10.2017
 */
package net.mdatools.modelant.core.name;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jmi.reflect.InvalidNameException;
import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefBaseObject;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;
import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.Operation;
import net.mdatools.modelant.core.api.model.ConstructOperation;
import net.mdatools.modelant.core.api.model.ConstructProcedure;
import net.mdatools.modelant.core.api.model.NameMapping;
import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.api.name.PackageName;
import net.mdatools.modelant.core.api.name.StructName;

/**
 * @author Rusi Popov
 */
public class StructNameImpl extends NameImpl<Name<?>> implements StructName {
  public StructNameImpl(String packageName) {
    super(packageName);
  }

  public StructNameImpl(Name<?> parent, String name) {
    super(parent, name);
  }

  public StructNameImpl(RefPackage extent, RefStruct struct) {
    this(constructOwnerClassName(struct.refTypeName(), extent),
          getStructName(struct));
  }

  /**
   * @param struct not null
   * @return the name of the Struct's class from its qualified name
   */
  private static String getStructName(RefStruct struct) {
    String result;
    List<String> names;

    names = struct.refTypeName();
    result = names.get(names.size()-1);

    return result;
  }

  /**
   * @param qualifiedName not null, not empty qualified name of the Enum Class
   * @param targetExtent not null
   * @return the class name of that Enum
   */
  private static Name<?> constructOwnerClassName(List<String> qualifiedName, RefPackage targetExtent) {
    Name<?> result;
    String name;
    RefBaseObject namespace;

    namespace = targetExtent;
    result = null;
    for (int i=0; i<qualifiedName.size()-1; i++) {
      name = qualifiedName.get( i );

      if ( namespace instanceof RefPackage ) { // the current name is a name of a package or class, packageName is a name of a package so far
        try {
          namespace = ((RefPackage) namespace).refPackage( name );
          result = new PackageNameImpl((PackageName) result, name);

        } catch (InvalidNameException ex) { // name is not a package, it could be only a class
          namespace = ((RefPackage) namespace).refClass( name );
          result = new ClassNameImpl((PackageName) result, name);
        }
      } else { // namespace is a Class owning at most an enumeration
        throw new IllegalArgumentException("Resolving "+qualifiedName
                                           +" struct name, reached "+PRINT_MODEL_ELEMENT.execute( namespace )
                                           +" which cannot contain nested "+name);
      }
    }
    return result;
  }

  /**
   * @see net.mdatools.modelant.core.name.NameImpl#constructName(net.mdatools.modelant.core.api.name.Name, java.lang.String)
   */
  public Name<Name<?>> constructName(Name<?> parent, String name) {
    return new StructNameImpl(parent, name);
  }

  /**
   * @see net.mdatools.modelant.core.api.name.Name#constructTransfromation()
   */
  public ConstructProcedure<?> constructTransfromation() {
    throw new IllegalStateException("This method should not be called by design");
  }

  /**
   * The Structs are instantiated through the model package in contrast to the classes,
   * and there is no
   * so locate the package in the target model that contains the target struct type and
   * instantiate that struct.
   * @param rootPackage not null extent
   * @param fieldValues TODO
   * @return non-null structure instance
   * @throws JmiException when the struct does not exist
   */
  private RefStruct construct(RefPackage targetExtent, List<?> fieldValues) throws JmiException {
    RefStruct result;
    Name<?> structClassNamespace;
    PackageName structClassNamespaceNamespace;
    RefPackage structClassNamespacePackage;
    RefPackage structClassPackage;
    RefClass structClassClass;

    if ( getOwner() == null ) {
      throw new InvalidNameException( toString(), "Expected a parent class or package name provided");
    }

    structClassNamespace = getOwner();
    structClassNamespaceNamespace = (PackageName) structClassNamespace.getOwner();

    if ( structClassNamespaceNamespace != null ) {
      structClassNamespacePackage = structClassNamespaceNamespace.getMetaPackage( targetExtent );
    } else {
      structClassNamespacePackage = targetExtent;
    }

    // in enumClassNamespaceNamespace, enumClassNamespace could be a class or package
    // try both options, which one will work
    try {
      structClassPackage = structClassNamespacePackage.refPackage( structClassNamespace.getName() );
      result = structClassPackage.refCreateStruct(getName(), fieldValues );

    } catch (InvalidNameException ex) {
      structClassClass = structClassNamespacePackage.refClass( structClassNamespace.getName() );
      result = structClassClass.refCreateStruct(getName(), fieldValues );
    }
    return result;
  }


  /**
   * @see net.mdatools.modelant.core.api.name.StructName#constructCopyOperation()
   */
  public ConstructOperation<RefStruct> constructCopyOperation() {
    return new ConstructOperation<RefStruct>() {
      public Operation<RefStruct> construct(RefPackage targetExtent,
                                            Map<RefObject, RefObject> objectsMap,
                                            NameMapping valueMapping) {
        return new Operation<RefStruct>() {
          public RefStruct execute(RefStruct struct) throws RuntimeException, IllegalArgumentException {
            List<Object> values;

            values = new ArrayList<>();
            for (String name : (List<String>) struct.refFieldNames()) {
              values.add( struct.refGetValue( name ) );
            }
            return StructNameImpl.this.construct( targetExtent, values );
          }
        };
      }
    };
  }
}