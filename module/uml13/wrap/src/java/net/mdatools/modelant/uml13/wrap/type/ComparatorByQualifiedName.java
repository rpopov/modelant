/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml13.wrap.type;

import javax.jmi.reflect.RefBaseObject;

import net.mdatools.modelant.core.task.comparison.ModelElementComparator;
import net.mdatools.modelant.core.wrap.Factories;

import org.omg.uml13.wrap.foundation.core.WrapModelElement;

/**
 * Compare model elements by their UML 1.3 qualified names 
 */
public class ComparatorByQualifiedName implements ModelElementComparator {
  
  public int compare(RefBaseObject o1, RefBaseObject o2) {
    int result;
    String rep1;
    String rep2;
    
    try {
      rep1 = ((WrapModelElement)Factories.wrap( o1 )).formatQualifiedName();
      rep2 = ((WrapModelElement)Factories.wrap( o2 )).formatQualifiedName();
      result = rep1.compareTo( rep2 );
    } catch (Exception ex) {
      result = 0;
    }
    return result;
  }    
}