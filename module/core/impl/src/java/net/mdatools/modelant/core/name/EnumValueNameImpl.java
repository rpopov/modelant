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

import java.util.List;

import javax.jmi.reflect.InvalidNameException;
import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefBaseObject;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefEnum;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.name.EnumValueName;
import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.api.name.PackageName;

/**
 * A key in package/type/value mapping
 * @author Rusi Popov
 */
public class EnumValueNameImpl extends NameImpl<Name<?>> implements EnumValueName {

  public EnumValueNameImpl(String name) {
    super(name);
  }

  public EnumValueNameImpl(Name<?> parentName, String name) {
    super(parentName, name);
  }

  public EnumValueNameImpl(RefPackage targetExtent, RefEnum enumValue) {
    this(constructEnumClassName(enumValue.refTypeName(), targetExtent), enumValue.toString());
  }

  /**
   * @param qualifiedName not null, not empty qualified name of the Enum Class
   * @param targetExtent not null
   * @return the class name of that Enum
   */
  private static Name<?> constructEnumClassName(List<String> qualifiedName, RefPackage targetExtent) {
    Name<?> result;
    String name;
    Name packageName;
    RefBaseObject namespace;

    namespace = targetExtent;
    packageName = null;
    for (int i=0; i<qualifiedName.size()-1; i++) {
      name = qualifiedName.get( i );

      if ( namespace instanceof RefPackage ) { // the current name is a name of a package or class, packageName is a name of a package so far
        try {
          namespace = ((RefPackage) namespace).refPackage( name );
          packageName = new PackageNameImpl((PackageName) packageName, name);

        } catch (InvalidNameException ex) { // name is not a package, it could be only a class
          namespace = ((RefPackage) namespace).refClass( name );
          packageName = new ClassNameImpl((PackageName) packageName, name);
        }
      } else { // namespace is a Class owning at most an enumeration
        throw new IllegalArgumentException("Resolving "+qualifiedName
                                           +" enumeration name, reached "+PRINT_MODEL_ELEMENT.execute( namespace )
                                           +" which cannot contain nested "+name);
      }
    }
    if ( qualifiedName.size() > 0 ) {
      result = new ClassNameImpl(packageName, qualifiedName.get( qualifiedName.size()-1 ));
    } else {
      result = null;
    }
    return result;
  }

  /**
   * @see net.mdatools.modelant.core.api.name.Name#constructName(net.mdatools.modelant.core.api.name.Name, java.lang.String)
   */
  public Name<Name<?>> constructName(Name<?> parent, String name) {
    return new EnumValueNameImpl(parent, name);
  }

  /**
   * @see net.mdatools.modelant.core.api.name.EnumValueName#lookupValue(javax.jmi.reflect.RefPackage)
   */
  public RefEnum lookupValue(RefPackage targetExtent) throws JmiException {
    RefEnum result;
    Name enumClassName;
    Name enumClassNamespace;
    PackageName enumClassNamespaceNamespace;
    RefPackage enumClassNamespacePackage;
    RefPackage enumClassPackage;
    RefClass enumClassClass;

    if ( getOwner() == null
         || getOwner().getOwner() == null) {
      throw new InvalidNameException( toString(), "Expected a parent class and package name provided");
    }

    enumClassName = getOwner();
    enumClassNamespace = enumClassName.getOwner();
    enumClassNamespaceNamespace = (PackageName) enumClassNamespace.getOwner();

    if ( enumClassNamespaceNamespace != null ) {
      enumClassNamespacePackage = enumClassNamespaceNamespace.getMetaPackage( targetExtent );
    } else {
      enumClassNamespacePackage = targetExtent;
    }

    // in enumClassNamespaceNamespace, enumClassNamespace could be a class or package
    // try both options, which one will work
    try {
      enumClassPackage = enumClassNamespacePackage.refPackage( enumClassNamespace.getName() );
      result = enumClassPackage.refGetEnum( enumClassName.getName(), getName() );

    } catch (InvalidNameException ex) {
      enumClassClass = enumClassNamespacePackage.refClass( enumClassNamespace.getName() );
      result = enumClassClass.refGetEnum( enumClassName.getName(), getName() );
    }
    return result;
  }
}