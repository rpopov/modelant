/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 19.11.2017
 */
package net.mdatools.modelant.core.api.diff;

import java.util.List;

import javax.jmi.reflect.RefObject;

/**
 * The differences found in a single association on X and Y matching objects
 * @author Rusi Popov
 */
public interface AssociationDifference {

  /**
   * @return not null association name
   */
  String getAssociationName();

  /**
   * @return non-null instances associated in the association, that are bound to X, but they have no correspondent
   *         bound in the same association to Y
   */
  List<RefObject> getXMinusY();

  /**
   * @return non-null instances associated in the association, that are bound to Y, but they have no correspondent
   *         bound in the same association to X
   */
  List<RefObject> getYMinusX();
}