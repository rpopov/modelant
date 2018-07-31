/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.name;

import java.util.Map;

import javax.jmi.model.Association;
import javax.jmi.model.Classifier;
import javax.jmi.model.Feature;
import javax.jmi.model.ModelElement;
import javax.jmi.model.MofPackage;
import javax.jmi.reflect.RefAssociationLink;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Procedure;
import net.mdatools.modelant.core.api.model.ConstructProcedure;
import net.mdatools.modelant.core.api.model.NameMapping;
import net.mdatools.modelant.core.api.name.ClassName;
import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.operation.element.PrintModelElement;
import net.mdatools.modelant.core.util.key.Hash;

/**
 * A key in package/type/value mapping
 * @param <P> the type of the parent/owner name
 * @author Rusi Popov
 */
public class NameImpl<P extends Name<?>> implements Name<P> {

  /**
   * A common constant of "no name" and "stop mapping", as null is not name acceptable value
   */
  public static final Name<?> NO_MAP_NAME = new NameImpl<>("$NO_MAP_NAME$");

  protected static final PrintModelElement PRINT_MODEL_ELEMENT = new PrintModelElement();

  private final P owner;
  private final String name;
  private final int hash;

  /**
   * Construct root name
   * @param name not null, not empty
   */
  protected NameImpl(String name) {
    this(name, null);
  }

  /**
   * Construct a name in the context of the owner name
   * @param owner may be null, when null provided this is equal to new Name(name)
   * @param name not null, not empty
   */
  protected NameImpl(P owner, String name) {
    this(name, owner);
  }

  /**
   * Construct a name in the context of the owner name
   * @param owner not null
   * @param name not null, not empty
   */
  private NameImpl(String name, P owner) {
    int hash;

    assert name != null : "Expected non-null name";

    this.name = name;
    this.owner = owner;

    hash = getClass().hashCode();
    if ( owner != null ) {
      hash = (hash << 2) + owner.hashCode();
    }
    hash = (hash << 2) + Hash.hash( name );
    this.hash = hash;
  }

  /**
   * @param modelElement non-null model element
   * @return the qualified name of the meta object (MOF element (class) of the metamodel), that describes
   *         the provided model element
   */
  public static ClassName constructQualifiedMetaObjectName(RefObject modelElement) {
    return (ClassName) constructQualifiedElementName( (ModelElement) modelElement.refMetaObject() );
  }


  /**
   * @param metaObject non-null MOF object
   * @return the qualified name of the MOF element, calculated down the containment relation
   */
  public static Name constructQualifiedElementName(ModelElement metaObject) {
    Name result;

    if ( metaObject instanceof MofPackage ) {
      result = new PackageNameImpl( (PackageNameImpl) constructQualifiedElementName( metaObject.getContainer() ),
                                    metaObject.getName());
    } else if ( metaObject instanceof Feature ) {
      result = new FieldNameImpl( (ClassNameImpl) constructQualifiedElementName( metaObject.getContainer() ),
                                  metaObject.getName());
    } else if ( metaObject instanceof Association ) {
      result = new AssociationNameImpl( (PackageNameImpl) constructQualifiedElementName( metaObject.getContainer() ),
                              				  metaObject.getName());
    } else if ( metaObject instanceof Classifier ) {
      result = new ClassNameImpl( (PackageNameImpl) constructQualifiedElementName( metaObject.getContainer() ),
                              		metaObject.getName());
    } else {
      result = null;
    }
    return result;
  }


  public final boolean isEmpty() {
    boolean result;
    result = name.isEmpty();
    return result;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public final int hashCode() {
    return hash;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public final boolean equals(Object obj) {
    boolean result;
    NameImpl other;

    result = obj == this;

    if ( !result && obj != null && obj.getClass() == getClass() ) {
      other = (NameImpl) obj;

      result = (owner == null && other.owner == null
                || owner != null && owner.equals( other.owner ))
               && name.equals( other.name );
    }
    return result;
  }

  /**
   * @return the qualified name this represents
   */
  public String toString() {
    String result;

    if ( owner == null ) {
      result = name;
    } else {
      result = owner.toString()+METAMODEL_PATH_SEPARATOR+name;
    }
    return result;
  }

  /**
   * @return
   * @see net.mdatools.modelant.core.api.name.Name#getOwner()
   */
  public final P getOwner() {
    return owner;
  }

  /**
   * @return
   * @see net.mdatools.modelant.core.api.name.Name#getName()
   */
  public final String getName() {
    return name;
  }

  /**
   * @param qualifiedName not null
   * @return the Name that represents the qualified name provided,
   */
  public static Name parseQualifiedName(String qualifiedName) {
    NameImpl result;
    String[] names;

    result = null;
    names = qualifiedName.split( METAMODEL_PATH_SEPARATOR_PARSE );
    for (String name:names) {
      result = new NameImpl( result, name );
    }
    return result;
  }

	/**
	 * @see net.mdatools.modelant.core.api.name.Name#constructName(net.mdatools.modelant.core.api.name.Name, java.lang.String)
	 */
	public Name<P> constructName(P parent, String name) {
		return new NameImpl<P>(parent, name);
	}

  /**
   * @see net.mdatools.modelant.core.api.name.Name#constructNoTransfromation()
   */
  public final ConstructProcedure<RefAssociationLink> constructNoTransfromation() {
    return new ConstructProcedure<RefAssociationLink>() {
      public Procedure<RefAssociationLink> construct(RefPackage sourceExtent, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap, NameMapping valueMapping) {
        return Procedure.EMPTY;
      }
    };
  }

  /**
   * Override in subclasses
   * @see net.mdatools.modelant.core.api.name.Name#constructTransfromation()
   */
  public ConstructProcedure<?> constructTransfromation() {
    return new ConstructProcedure<RefAssociationLink>() {
      public Procedure<RefAssociationLink> construct(RefPackage sourceExtent, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap, NameMapping valueMapping) {
        return Procedure.EMPTY;
      }
    };
  }

  /**
   * Override in subclasses
   * @return true
   * @see net.mdatools.modelant.core.api.name.Name#canWrite(javax.jmi.reflect.RefPackage)
   */
  public boolean canWrite(RefPackage targetExtent) {
    return true;
  }
}