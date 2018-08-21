/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.name;

import java.util.Map;

import javax.jmi.model.Attribute;
import javax.jmi.model.Classifier;
import javax.jmi.model.ModelElement;
import javax.jmi.model.NameNotFoundException;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Procedure;
import net.mdatools.modelant.core.api.model.ConstructProcedure;
import net.mdatools.modelant.core.api.model.NameMapping;
import net.mdatools.modelant.core.api.name.ClassName;
import net.mdatools.modelant.core.api.name.FieldName;
import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.operation.model.transform.CopyAttributeImpl;

/**
 * A key in field mapping
 * @author Rusi Popov
 */
public class FieldNameImpl extends NameImpl<ClassName> implements FieldName {
  public FieldNameImpl(String name) {
    super(name);
  }

  public FieldNameImpl(ClassName parent, String name) {
    super(parent, name);
  }

  public FieldNameImpl(Attribute attribute) {
  	super(new ClassNameImpl((Classifier) attribute.getContainer()), attribute.getName());
  }

	/* (non-Javadoc)
	 * @see net.mdatools.modelant.core.api.name.Name#construct(net.mdatools.modelant.core.api.name.Name, java.lang.String)
	 */
	public Name<ClassName> constructName(ClassName parent, String name) {
		return new FieldNameImpl(parent, name);
	}

  /**
   * @see net.mdatools.modelant.core.api.name.Name#constructTransfromation()
   */
  public ConstructProcedure<RefObject> constructTransfromation() {
    return new ConstructProcedure<RefObject>() {
      private Procedure<RefObject> result;

      public Procedure<RefObject> construct(RefPackage sourceExtent,
                                            RefPackage targetExtent,
                                            Map<RefObject, RefObject> objectsMap,
                                            NameMapping valueMapping) {
        RefClass ownerRefClass;
        Classifier ownerClassifier;
        ModelElement feature;

        // lookup the attribute's description
        if ( getOwner() == null ) {
          throw new IllegalArgumentException("Expected "+this+" field has the owner class specified");
        }
        ownerRefClass = getOwner().getMetaClass( targetExtent );
        ownerClassifier = (Classifier) ownerRefClass.refMetaObject();

        try {
          feature = ownerClassifier.lookupElement( getName() );
          if ( feature instanceof Attribute
              && ((Attribute) feature).isChangeable()  ) {

           result = new CopyAttributeImpl( FieldNameImpl.this.getName(),
                                           FieldNameImpl.this.getName(),
                                           objectsMap,
                                           sourceExtent,
                                           targetExtent,
                                           valueMapping );
          } else {
            result = Procedure.EMPTY;
          }
        } catch (NameNotFoundException e) {
          throw new IllegalArgumentException(this+" field was not found in the target model");
        }
        return result;
      }

      /**
       * @see java.lang.Object#toString()
       */
      public String toString() {
        return String.valueOf( result );
      }
    };
  }
}