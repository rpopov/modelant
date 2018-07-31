/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 03.12.2017
 */
package net.mdatools.modelant.core.operation.model;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.operation.model.transform.IdentityNameMapping;

/**
 * Copy a model to another extent.
 * The copy operation is represented as a special case of copying model from/to
 * the same metamodel.<pre>
 * Usage:
 *
 *   copy = new CopyModel(fromExtent)
 *
 *   copy.execute(toExtent);
 * </pre>
 * @see CopyToMetaModel#execute(RefPackage)
 * @author Rusi Popov
 */
public class CopyModel extends CopyToMetaModel {

  /**
   * @param fromExtent not null
   */
  public CopyModel(RefPackage fromExtent) {
    super( fromExtent, new IdentityNameMapping() );
  }
}
