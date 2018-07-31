/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.order;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jmi.model.GeneralizableElement;
import javax.jmi.reflect.RefBaseObject;

import net.mdatools.modelant.core.api.Order;

/**
 * State a list of metamodel classes to order their instances.
 * The instances of different (listed) classes are ordered according to the positions of those classes in the list.
 * Instances of not listed classes are just kept as they are, after any instances of listed classes.
 * @author Rusi Popov
 */
public class OrderByClass implements Order {

  /**
   * List of class names defining their order
   */
  private final List<String> classNames = new ArrayList<String>();
  private final Set<String> knownNotCoveredClasses = new HashSet<String>();
  
  public int compare(RefBaseObject o1, RefBaseObject o2) {
    int index1;
    int index2;

    index1 = getIndexOf((GeneralizableElement) o1.refMetaObject());
    index2 = getIndexOf((GeneralizableElement) o2.refMetaObject());
    
    return index1-index2;
  }

  /**
   * post-condition:
   *   knownNotCoveredClasses is updated with classes known not pertaining to the list
   * @param target not null MOF Element representing a superclass of a model element to order
   * @return the minimal index of the model class of the target or of its superclasses in classNames list. If 
   *         no (super)class found among classNames, MAX_INT is returned  
   */
  private int getIndexOf(GeneralizableElement target) {
    int result;
    String metaClassName;
    
    metaClassName = target.getName();
    
    result = classNames.indexOf( metaClassName );
    if ( result < 0 ) { // not listed explicitly, search superclasses
      result = Integer.MAX_VALUE;
      
      if ( !knownNotCoveredClasses.contains( metaClassName ) ) { // still decision on the class is not made        
        for (GeneralizableElement superClass : (List<GeneralizableElement>) target.getSupertypes()) {
          result = Math.min( result, getIndexOf( superClass ) );        
        }
        if ( result == Integer.MAX_VALUE ) { // the class is not covered
          knownNotCoveredClasses.add( metaClassName );
        }
      }
    }
    return result;
  }
  
  /**
   * Add a single class name to the list of classes. The order of a model element's class name within the list 
   * compared the order other elements' class name defines the order of those elements.
   * with the order of other
   */
  public ClassName createClass() {
    return new ClassName();
  }
  
  /**
   * State the text contents of &lt;class&gt; element as a single class name to order against.
   */
  public class ClassName {
    public void addText(String className) {
      classNames.add( className );
    }
  }
}