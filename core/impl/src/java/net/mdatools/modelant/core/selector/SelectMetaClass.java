/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
 * Selector of a metamodel class with the provided qualified name
 * @author popovr
 */
public class SelectMetaClass implements Selector<RefPackage, RefObject> {

  /**
   * The qualified name of a metaclass
   * not null
   */
  private final String qualifiedName;

  /**
   * @param qualifiedName not null, not empty name of a MOF Class instance in the metamodel
   */
  public SelectMetaClass(String qualifiedName) {
    if ( qualifiedName == null || qualifiedName.trim().isEmpty() ) { // iteration on model class instances
      throw new IllegalArgumentException( "Empty metaclass provided ");
    }

    this.qualifiedName = qualifiedName;
  }

  /**
   * @param sourceExtent not null model extent where to lookup the metaclass in
   * @return non-null list of one element - the metaobject, describing the metaclass
   */
  public Collection<RefObject> execute(RefPackage sourceExtent) throws JmiException {
    Collection<RefObject> result;

    // locate the meta class (in metaClass) within the metamodel
    result = new ArrayList<>();
    result.add(Navigator.getMetaClass( sourceExtent, qualifiedName ).refMetaObject());

    return result;
  }
}