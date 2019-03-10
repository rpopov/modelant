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
import javax.jmi.reflect.RefAssociationLink;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.model.ConstructProcedure;

public interface AssociationName extends Name<PackageName> {

  /**
   * @param rootPackage not null extent
   * @return the non-null Class (factory) this name describes
   * @throws JmiException
   */
  RefAssociation getMetaAssociation(RefPackage rootPackage) throws JmiException;

  /**
   * @return non-null producer of transformations of source model links to target model links in the same direction, e.g. the source of the produced
   *         link is the object that corresponds to the source of the original link, the same is for link targets
   */
  public ConstructProcedure<RefAssociationLink> newForwardLinkProduction();

  /**
   * @return non-null producer of transformations of source model links to target model links in the opposite direction, e.g. the source of the produced
   *         link is the object that corresponds to the target of the original link, the same is for link targets
   */
  public ConstructProcedure<RefAssociationLink> newBackwardLinkProduction();

}