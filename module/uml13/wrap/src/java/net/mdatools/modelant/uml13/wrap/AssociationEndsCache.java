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
package net.mdatools.modelant.uml13.wrap;

import java.util.Collection;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.repository.Cache;

import org.omg.uml13.foundation.core.AssociationEnd;
import org.omg.uml13.foundation.core.ModelElement;

/**
 * This class is a cache of association ends bound to a classifier
 */
public class AssociationEndsCache extends Cache<ModelElement, AssociationEnd> {

  /**
   * @see net.mdatools.modelant.repository.Cache#register(javax.jmi.reflect.RefObject)
   */
  public void register(RefObject refObject) {    
    AssociationEnd end;
    
    if ( refObject instanceof AssociationEnd ) {
      end = (AssociationEnd) refObject;
      put( end.getType(), end );
    }
  }
  

  /**
   * @see net.mdatools.modelant.repository.Cache#unregister(javax.jmi.reflect.RefObject)
   */
  public void unregister(RefObject refObject) {
    Collection ends;
    
    if ( refObject instanceof AssociationEnd ) {
      // remove the end from the cached backward links
      ends = get( ((AssociationEnd) refObject).getType() );
      ends.remove( refObject );
    }
  }
}