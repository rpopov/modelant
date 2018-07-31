/*
 * Copyright (c) i:FAO AG 2014. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 * 
 * Created on 30.03.2014
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