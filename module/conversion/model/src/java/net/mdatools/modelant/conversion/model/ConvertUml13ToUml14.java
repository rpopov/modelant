/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.conversion.model;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.operation.model.CopyToMetaModel;

/**
 * Convert an UML 1.3 model to UML 1.4 as a transformation defined at metamodel level
 * {@linkplain  https://mdatools.net/wiki/index.php?title=Modelant_-_migration_to_maven#Convert_UML_1.3_to_UML_1.4}
 * @author Rusi Popov
 */
public class ConvertUml13ToUml14 extends CopyToMetaModel {

  /**
   * @param uml13SourceExtent not null extent with the UML 1.3 model
   */
  public ConvertUml13ToUml14(RefPackage uml13SourceExtent) {
    super( uml13SourceExtent, new Uml13ToUml14Mapping() );
  }
}
