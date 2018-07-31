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

import java.util.Collection;

import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Selector;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Selector of all model elements, that are instances of a metaclass
 * @author popovr
 */
public class SelectMetaclassInstances implements Selector<RefPackage, RefObject> {

  /**
   * The qualified name of a metaclass
   * not null
   */
  private final String metaclass;

  /**
   * @param metaclass Path in the metamodel to the metaclass whose instances (in the model/extent) are to be
   * processed. Format of metaclass attribute: {&lt;package&gt;::}&lt;meta class&gt;
   */
  public SelectMetaclassInstances(String metaclass) {
    if ( metaclass == null || metaclass.trim().isEmpty() ) { // iteration on model class instances
      throw new IllegalArgumentException( "Empty metaclass provided ");
    }
    this.metaclass = metaclass;
  }

  /**
   * @see Selector#select()
   */
  public Collection<RefObject> execute(RefPackage sourceExtent) throws JmiException {
    Collection<RefObject> result;

    // locate the meta class (in metaClass) within the metamodel
    result = Navigator.getMetaClass( sourceExtent, metaclass ).refAllOfClass();

    return result;
  }
}