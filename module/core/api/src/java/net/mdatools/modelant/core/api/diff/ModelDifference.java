/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 4.03.2018 г.
 */
package net.mdatools.modelant.core.api.diff;

import java.util.Collection;
import java.util.Map;

import javax.jmi.reflect.RefObject;

/**
 * A change in the model caused either by adding or deleting a model element to a model, which has no correspondent
 * in the other model compared.
 * The role of this difference indicates it is added/deleted, whereas the difference by itself does not.
 * @author Rusi Popov
 */
public interface ModelDifference {

  /**
   * @return the non-null element of this change - the element added/deleted to the corresponding model
   */
  RefObject getElement();

  /**
   * @return non-null map of association names to associated model differences in the same role of added/deleted
   *         to the {@link #getElement()} as the role of this to the model.
   */
  Map<String, Collection<ModelDifference>> getAssociations();
}
