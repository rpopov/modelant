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
 * Selector of all model elements, which are instances of the classes in the wrapped extent.
 * @author popovr
 */
public class SelectAllObjects implements Selector<RefPackage, RefObject> {

  /**
   * @param argument not used
   * @return not null collection of all model objects, that are instances of any model class in the wrapped extent
   * @throws JmiException
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public Collection<RefObject> execute(RefPackage sourceExtent) throws JmiException {
    Collection<RefObject> result;

    result = Navigator.getAllObjects( sourceExtent );

    return result;
  }
}