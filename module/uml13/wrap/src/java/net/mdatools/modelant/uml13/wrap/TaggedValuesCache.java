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

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.repository.Cache;

import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.extensionmechanisms.TaggedValue;

/**
 * Internal cache of tagged values relative to a model elements. This cache maps a model element to a list
 * of tagged values. In order effectively to handle tagged  values this class must load() them. If the tagged
 * values are not loaded, this class does not chache anything.
 *
 * Its lookup() method returns a non-empty collection of tag values. Might be empty.
 *
 * @see Cache
 */
public class TaggedValuesCache extends Cache<ModelElement, TaggedValue> {

  /**
   * @see net.mdatools.modelant.repository.Cache#register(javax.jmi.reflect.RefObject)
   */
  public void register(RefObject refObject) {
    TaggedValue tag;
    
    if ( refObject instanceof TaggedValue  ) {    
      tag = (TaggedValue) refObject;
      put( tag.getModelElement(), tag );
    }
  }

  /**
   * @see net.mdatools.modelant.repository.Cache#unregister(javax.jmi.reflect.RefObject)
   */
  public void unregister(RefObject refObject) {
    if ( refObject instanceof TaggedValue  ) {    
      // remove the tagged value from the cached backward link
      remove( ((TaggedValue) refObject).getModelElement() );
    }
  }
}