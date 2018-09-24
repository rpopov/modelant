/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.name;

import java.util.Map;

import javax.jmi.model.Classifier;
import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Procedure;
import net.mdatools.modelant.core.api.model.ConstructProcedure;
import net.mdatools.modelant.core.api.model.NameMapping;
import net.mdatools.modelant.core.api.name.ClassName;
import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.api.name.PackageName;

/**
 * A key in class mapping
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class ClassNameImpl extends NameImpl<Name<?>> implements ClassName {
  public ClassNameImpl(String packageName) {
    super(packageName);
  }

  public ClassNameImpl(PackageName parent, String name) {
    super(parent, name);
  }

  public ClassNameImpl(ClassName parent, String name) {
    super(parent, name);
  }

  ClassNameImpl(Name<?> parent, String name) {
    super(parent, name);
  }


  public ClassNameImpl(Classifier classifier) {
    super(new PackageNameImpl(classifier.getContainer()), classifier.getName());
  }



  /**
   * @param qualifiedName not null
   * @return the Class Name that represents the qualified name provided,
   * @throws IllegalArgumentException when qualifiedName is empty
   */
  public static ClassName parseQualifiedClassName(String qualifiedName) throws IllegalArgumentException {
    ClassName result;
    PackageName pack;
    String[] names;

    pack = null;
    names = qualifiedName.split( METAMODEL_PATH_SEPARATOR_PARSE );
    for (int i=0; i<names.length-1; i++) {
      pack = new PackageNameImpl( pack, names[i] );
    }
    if (names.length > 0) {
      result = new ClassNameImpl(pack, names[names.length-1]);
    } else {
      result = null;
    }
    return result;
  }

  /**
   * @param rootPackage not null extent
   */
  public RefClass getMetaClass(RefPackage rootPackage) throws JmiException {
    RefClass result;
    RefPackage ownerPackage;

    assert rootPackage != null : "Expected a non-null package";

    if ( getOwner() == null ) {
      ownerPackage = rootPackage;
    } else if ( getOwner() instanceof PackageName ) {
      ownerPackage = ((PackageName) getOwner()).getMetaPackage( rootPackage );
    } else {
      throw new IllegalArgumentException(this + " should be a class name in order to lookup the corresponding *Class instance");
    }

    try {
      result = ownerPackage.refClass( getName() );
    } catch (JmiException ex) {
      throw new IllegalArgumentException("Looking up the class "+ this
                                       + " reached " + PRINT_MODEL_ELEMENT.execute( ownerPackage )
                                       + " for which retrieving the nested class '"+getName()+"'"
                                       + " caused ",ex);
    }
    return result;
  }

  /**
   * @see net.mdatools.modelant.core.name.NameImpl#constructName(net.mdatools.modelant.core.api.name.Name, java.lang.String)
   */
  public Name<Name<?>> constructName(Name<?> parent, String name) {
    return new ClassNameImpl(parent, name);
  }

  /**
   * @see net.mdatools.modelant.core.api.name.Name#constructTransfromation()
   */
  public ConstructProcedure<?> constructTransfromation() {
    return new ConstructProcedure<RefObject>() {
      public Procedure<RefObject> construct(RefPackage sourceExtent, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap, NameMapping valueMapping) {
        RefClass targetClass;

        targetClass = ClassNameImpl.this.getMetaClass( targetExtent );

        return new Procedure<RefObject>() {
          public void execute(RefObject original) throws RuntimeException, IllegalArgumentException {
            RefObject result;

            result = targetClass.refCreateInstance( null );
            objectsMap.put( original, result );
          }
        };
      }
    };
  }
}