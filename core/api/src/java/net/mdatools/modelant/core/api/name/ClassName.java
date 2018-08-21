/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.name;

import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefAssociation;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;

/**
 * It turned out that<ul>
 * <li> the Classes in MOF are nested in Packages
 * <li> the Enums are nested in Packages or Classes
 * <li> the Structs are nested in Packages or Classes
 * </ul>
 * While we still represent the Enums and Structs as Class Names, the parent cannot be
 * just a single type, thus Name is set as parent.
 * @author Rusi Popov
 */
public interface ClassName extends Name<Name<?>> {

  /**
   * @param rootPackage not null extent
   * @return the non-null Class (factory) this name describes
   * @throws JmiException
   */
  RefClass getMetaClass(RefPackage rootPackage) throws JmiException;

	default RefAssociation locateAssociation(RefPackage targetExtent) {
		RefAssociation result;
		RefPackage newMetaPackage;

    if ( getOwner() instanceof PackageName ) {
      newMetaPackage = ((PackageName) getOwner()).getMetaPackage( targetExtent );
      result = newMetaPackage.refAssociation( getName() );
    } else {
      throw new IllegalArgumentException(this + " should be a class name in order to lookup the corresponding *AssociationClass instance");
    }
		return result;
	}
}