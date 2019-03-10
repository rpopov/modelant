/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.name;

import javax.jmi.model.Namespace;
import javax.jmi.reflect.InvalidNameException;
import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.api.name.PackageName;

/**
 * A key in package mapping
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class PackageNameImpl extends NameImpl<PackageName> implements PackageName {
  public PackageNameImpl(String packageName) {
    super(packageName);
  }

  public PackageNameImpl(PackageName parentName, String packageName) {
    super(parentName, packageName);
  }

  public PackageNameImpl(Namespace container) {
    super(container.getContainer() == null ? null: new PackageNameImpl(container.getContainer()),
          container.getName());
  }


  /**
   * @param qualifiedName not null
   * @return the Package Name that represents the qualified name provided,
   * @throws IllegalArgumentException when qualifiedName is empty
   */
  public static PackageName parseQualifiedPackageName(String qualifiedName) throws IllegalArgumentException {
    PackageNameImpl result;
    String[] names;

    result = null;
    names = qualifiedName.split( METAMODEL_PATH_SEPARATOR_PARSE );
    for (String name:names) {
      result = new PackageNameImpl( result, name );
    }
    return result;
  }

  /**
   * @param rootPackage the extent or another instance of a package proxy (class) to start navigating form
   * @return the metamodel package this describes, starting from the rootPackage extent
   * @throws JmiException
   * @see net.mdatools.modelant.core.api.name.PackageName#getMetaPackage(javax.jmi.reflect.RefPackage)
   */
  public RefPackage getMetaPackage(RefPackage rootPackage) throws JmiException {
    RefPackage result;

    assert rootPackage != null : "Expected a non-null package";

    if ( getOwner() == null ) {
      result = rootPackage;
    } else {
      result = getOwner().getMetaPackage( rootPackage );
    }

    try {
      result = result.refPackage( getName() );
    } catch (JmiException ex) {
      throw new IllegalArgumentException("Looking up the package "+ this
                                       + " reached " + PRINT_MODEL_ELEMENT.execute( result )
                                       + " for which retrieving the nested package '"+getName()+"'"
                                       + " caused ",ex);
    }
    return result;
  }

  /**
   * @see net.mdatools.modelant.core.api.name.Name#constructName(net.mdatools.modelant.core.api.name.Name, java.lang.String)
   */
  public Name<PackageName> constructName(PackageName parent, String name) {
    return new PackageNameImpl(parent, name);
  }
}