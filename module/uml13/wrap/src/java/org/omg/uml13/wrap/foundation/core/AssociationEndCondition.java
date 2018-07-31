/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package org.omg.uml13.wrap.foundation.core;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.core.AssociationEnd;


/**
 * This class allows providing additional condition upon the Association Ends
 * to select. Note that it is always applied on 'thisEnd' association ends, bound to
 * the wrapped class.
 * @author rpopov
 */
public abstract class AssociationEndCondition extends ModelElementCondition<AssociationEnd> { 
  public AssociationEndCondition(Factory factory) {
    super( factory );
  }
}