/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 16.04.2018 г.
 */
package net.mdatools.modelant.core.api.name;

import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefPackage;

public interface PackageName extends Name<PackageName> {

  /**
   * @param rootPackage is the top-most package / the model's extent
   * @return the non-null meta package this PackageName describes, starting from the rootPackage/model extent
   * @throws JmiException if this meta package name is not a valid one
   */
  RefPackage getMetaPackage(RefPackage rootPackage) throws JmiException;

}