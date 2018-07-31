/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 5.04.2018 г.
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
