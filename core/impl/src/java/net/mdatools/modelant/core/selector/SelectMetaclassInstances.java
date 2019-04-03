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
 * Selector of all model elements, that are instances of a metaclass
 * @author popovr
 */
public class SelectMetaclassInstances implements Select<RefPackage, RefObject> {

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
   * @param sourceExtent not null extent
   */
  public Collection<RefObject> execute(RefPackage sourceExtent) throws JmiException {
    Collection<RefObject> result;

    // locate the meta class (in metaClass) within the metamodel
    result = Navigator.getMetaClass( sourceExtent, metaclass ).refAllOfClass();

    return result;
  }
}