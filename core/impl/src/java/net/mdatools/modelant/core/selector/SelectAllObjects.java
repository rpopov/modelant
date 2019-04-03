/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.selector;

import java.util.Collection;

import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Select;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Selector of all model elements, which are instances of the classes in the wrapped extent.
 * @author popovr
 */
public class SelectAllObjects implements Select<RefPackage, RefObject> {

  /**
   * @param sourceExtent not null extent where to collect all instances in
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