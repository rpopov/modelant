/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.mof14.metamodel;

import net.mdatools.modelant.core.operation.model.topology.Equals;
import net.mdatools.modelant.core.operation.model.topology.ListCriteria;
import net.mdatools.modelant.core.operation.element.PrintElementRestricted;

/**
 * The operation to print any element of MOF 1.4 models, i.e. instances of MOF 1.4 ModelElement class,
 * by printing their name and container.
 * Stateless. Thread safe.
 * <pre>
 * Usage:
 *
 *   print = new PrintMof14();
 *
 *   toString = print.execute(mof14ModelElement); // as many times as needed
 * </pre>
 * @author Rusi Popov
 */
public class PrintMof14ModelElement extends PrintElementRestricted {

  private static final ListCriteria MOF14_CRITERIA = new ListCriteria();
  static {
    MOF14_CRITERIA.add(new Equals("ModelElement", "name", "container"));
  }

  public PrintMof14ModelElement() {
    this("");
  }

  public PrintMof14ModelElement(String prefix) {
    super( prefix, MOF14_CRITERIA );
  }
}
