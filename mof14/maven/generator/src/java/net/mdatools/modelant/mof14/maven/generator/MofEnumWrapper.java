/*
 * Copyright (c) i:FAO AG 2012. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 12.06.2012
 */
package net.mdatools.modelant.mof14.maven.generator;

import javax.jmi.model.ModelElement;

import net.mdatools.modelant.core.api.wrap.WrapperFactory;

/**
 * Provides specific methods and rendering for MOF Enum instances
 * @author Rusi Popov
 */
public class MofEnumWrapper extends MofElementWrapper {

  /**
   * @param wrapped
   * @param factory
   */
  public MofEnumWrapper(ModelElement wrapped, WrapperFactory factory) {
    super( wrapped );
  }
}
