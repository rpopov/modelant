/*
 * Copyright (c) i:FAO AG 2010. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 25.02.2010
 */
package net.mdatools.modelant.mof;

import javax.jmi.model.EnumerationType;

import net.mdatools.modelant.core.wrap.Factory;

/**
 * This class is a factory of wrapper objects for each MOF model object
 * @author Rusi Popov
 */
public class MofWrapperFactory extends Factory {

  /**
   * @see net.mdatools.modelant.core.wrap.Factory#getMappedClass(java.lang.Class)
   */
  protected Class getMappedClass(Class mofClass) {
    Class result;

    if ( EnumerationType.class.isAssignableFrom( mofClass ) ) {
      result = MofEnumWrapper.class;

    } else {
      result = MofElementWrapper.class;
    }
    return result;
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Factory#getProperty(java.lang.String)
   */
  public String getProperty(String name) {
    return null;
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Factory#getMappedRootClasses()
   */
  protected Class[] getMappedRootClasses() {
    return new Class[]{javax.jmi.model.ModelElement.class};
  }
}