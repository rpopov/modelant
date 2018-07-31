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

import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Selector;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Selector of a metamodel package with the provided qualified name
 * @author popovr
 */
public class SelectMetaPackage implements Selector<RefPackage, RefObject> {

  /**
   * The qualified name of a metaclass
   * not null
   */
  private final String qualifiedName;

  /**
   * @param qualifiedName not null, not empty name of a MOF Class instance in the metamodel
   */
  public SelectMetaPackage(String qualifiedName) {
    if ( qualifiedName == null || qualifiedName.trim().isEmpty() ) { // iteration on model class instances
      throw new IllegalArgumentException( "Empty name provided ");
    }

    this.qualifiedName = qualifiedName;
  }

  /**
   * @param
   * @return non-null list of one element - the metaobject, describing the metaclass
   */
  public Collection<RefObject> execute(RefPackage sourceExtent) throws JmiException {
    Collection<RefObject> result;

    // locate the meta class (in metaClass) within the metamodel
    result = new ArrayList<>();
    result.add(Navigator.getMetaPackage( sourceExtent, qualifiedName ).refMetaObject());

    return result;
  }
}