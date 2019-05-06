/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 6, 2019
 */
package net.mdatools.modelant.core.condition;

import net.mdatools.modelant.core.api.Condition;

/**
 * The inverse of the wrapped condition
 * @author Rusi Popov
 */
public class Not<A> implements Condition<A> {

  /**
   * not null
   */
  private final Condition<A> nested;

  /**
   * @param nested not null
   */
  public Not(Condition<A> nested) {
    assert nested != null : "Expecetd a non-null condition";
    this.nested = nested;
  }

  public boolean eval(A argument) throws RuntimeException, IllegalArgumentException {
    return !nested.eval( argument );
  }
}
