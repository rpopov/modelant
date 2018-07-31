/*
 * Copyright (c) i:FAO AG 2012. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on Nov 8, 2012
 */
package net.mdatools.modelant.core.selector;

import java.util.ArrayList;
import java.util.Collection;

import javax.jmi.model.ModelElement;
import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Selector;
import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Selector of a metamodel class with the provided qualified name
 * @author popovr
 */
public class SelectByQualifiedName implements Selector<RefPackage, RefObject> {

  /**
   * The qualified name of the object(s) to retrieve
   * not null
   */
  private final String qualifiedName;

  /**
   * @param qualifiedName not null, not empty name of a MOF Class instance in the metamodel
   */
  public SelectByQualifiedName(String qualifiedName) {
    if ( qualifiedName == null || qualifiedName.trim().isEmpty() ) { // iteration on model class instances
      throw new IllegalArgumentException( "Empty qualified name provided ");
    }
    this.qualifiedName = qualifiedName;
  }

  /**
   * @param
   * @return non-null list of one element - the metaobject, describing the metaclass
   */
  public Collection<RefObject> execute(RefPackage sourceExtent) throws JmiException {
    Collection<RefObject> result;
    Collection<RefObject> allObjects;
    String[] parsedQualifiedName;

    parsedQualifiedName = Name.parseQualifiedName( qualifiedName );

    result = new ArrayList<>();

    allObjects = Navigator.getAllObjects( sourceExtent );
    for (RefObject object : allObjects) {
      if ( matches((ModelElement) object, parsedQualifiedName, parsedQualifiedName.length-1) ) {
        result.add(object);
      }
    }

    assert !result.isEmpty()
           : "Expected a non-empty selection found for qualified name: "+qualifiedName;
    return result;
  }

  /**
   * @param object not null
   * @param parsedQualifiedName not null parsed qualified name
   * @param i < parsedQualifiedName.length
   * @return true if object's name = parsedQualifiedName[i] and object's namespace matches the
   *         parsed name [i-1]
   */
  private boolean matches(ModelElement object, String[] parsedQualifiedName, int i) {
    boolean result;

    if ( i < 0 ) {
      result = true;

    } else if ( object == null ) { // nothing to match
      result = false;

    } else {
      result = parsedQualifiedName[i].equals( object.getName() )
               && ( i == 0
                    && object.getContainer() == null // matched the root package
                    || i > 0
                       && matches( object.getContainer(), parsedQualifiedName, i-1 ));
    }
    return result;
  }
}