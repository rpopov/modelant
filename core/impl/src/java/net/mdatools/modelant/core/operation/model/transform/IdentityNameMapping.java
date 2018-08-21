/**
 *
 */
/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.transform;

import java.util.Map;

import javax.jmi.model.Association;
import javax.jmi.reflect.RefAssociation;
import javax.jmi.reflect.RefAssociationLink;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;
import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.Operation;
import net.mdatools.modelant.core.api.Procedure;
import net.mdatools.modelant.core.api.model.NameMapping;
import net.mdatools.modelant.core.api.name.AssociationName;
import net.mdatools.modelant.core.api.name.ClassName;
import net.mdatools.modelant.core.api.name.EnumValueName;
import net.mdatools.modelant.core.api.name.FieldName;
import net.mdatools.modelant.core.api.name.StructName;

/**
 * Identity names mapping
 */
public final class IdentityNameMapping implements NameMapping {

	/**
	 * @see NameMapping#mapMetaClass(ClassName, RefPackage, RefPackage, Map)
	 */
	public Procedure<RefObject> mapMetaClass(ClassName name, RefPackage sourceExtent, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap) {
		RefClass targetRefClass;

		targetRefClass = name.getMetaClass(targetExtent);

		return new Procedure<RefObject>() {
			public void execute(RefObject sourceObject) throws RuntimeException, IllegalArgumentException {
			  RefObject result;

			  result = targetRefClass.refCreateInstance(null);

			  objectsMap.put( sourceObject, result );
			}

      /**
       * @see java.lang.Object#toString()
       */
      public String toString() {
        return "identity mapMetaClass: "+name;
      }
		};
	}

	/**
	 * @see NameMapping#mapMetaAssociation(AssociationName, RefPackage, RefPackage, Map)
	 */
	public Procedure<RefAssociationLink> mapMetaAssociation(AssociationName association, RefPackage sourceExtent, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap) {
	  Procedure<RefAssociationLink> result;
		RefAssociation assoc;

		assoc = association.getMetaAssociation(targetExtent);

		if (((Association) assoc.refMetaObject()).isDerived()) { // the derived associations are not copied!
		  result = Procedure.EMPTY;

		} else {
  		result = new Procedure<RefAssociationLink>() {
  			public void execute(RefAssociationLink source) throws RuntimeException, IllegalArgumentException {
  				RefObject targetFirst;
  				RefObject targetSecond;

  				targetFirst = objectsMap.get(source.refFirstEnd());
  				targetSecond = objectsMap.get(source.refSecondEnd());

  				assoc.refAddLink(targetFirst, targetSecond);
  			}

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {
          return "identity mapMetaAssociation: "+association;
        }
  		};
		}
		return result;
	}

	/**
	 * @see NameMapping#mapMetaFieldName(FieldName, RefPackage, RefPackage, Map)
	 */
	public Procedure<RefObject> mapMetaFieldName(FieldName name,
	                                             RefPackage sourceExtent,
	                                             RefPackage targetExtent, Map<RefObject, RefObject> objectsMap) {
    return (Procedure<RefObject>) name.constructTransfromation().construct( sourceExtent, targetExtent, objectsMap, this );
	}

  /**
   * @see NameMapping#mapEnum(net.mdatools.modelant.core.api.name.EnumValueName)
   */
  public EnumValueName mapEnum(EnumValueName value) {
    return value;
  }

  /**
   * @see NameMapping#mapStruct(StructName, RefPackage, Map)
   */
  public Operation<RefStruct> mapStruct(StructName structName, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap) {
    return structName.constructCopyOperation().construct( targetExtent, objectsMap, this );
  }
}