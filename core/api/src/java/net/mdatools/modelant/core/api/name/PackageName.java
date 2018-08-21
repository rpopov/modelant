/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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