/*
 * Copyright (c) 2010,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package net.mdatools.modelant.uml13.wrap.task;

import javax.jmi.reflect.RefBaseObject;

import net.mdatools.modelant.core.util.ModelElementPrinter;
import net.mdatools.modelant.core.wrap.Factories;

import org.apache.tools.ant.Project;
import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.wrap.foundation.core.WrapClassifier;
import org.omg.uml13.wrap.foundation.core.WrapModelElement;

/**
 * This get task allows navigation through the UML 1.3 specific associations that are unidirectional
 * in the UML 1.3 meta-model, but are useful in the opposite direction.
 * Supported paths<ul>
 * <li> tag = the tag values associated to the model element
 * <li> stereotype = the stereotype assigned to the model element
 * <li> associationEnd = the collection of association ends associated
 *        to the model element. Valid only for Classifier instances.
 * </ul>
 * This task and its subclasses could be used also as a selector in iterator, select, search, etc, tasks
 * as it inherits from the general GetTask 
 */
public class GetTask extends net.mdatools.modelant.core.task.reflective.GetTask {

  private static final String PATH_TAG_VALUE = "tag";
  private static final String PATH_STEREOTYPE = "stereotype";
  private static final String PATH_ASSOC_END = "associationEnd";


  /**
   * @see net.mdatools.modelant.core.task.reflective.GetTask#getValue(java.lang.Object, java.lang.String)
   */
  protected Object getValue(Object element, String path) {
    Object result = null;

    if ( PATH_TAG_VALUE.equalsIgnoreCase( path ) ) {
      if ( element instanceof ModelElement ) {
        result = ((WrapModelElement) Factories.wrap( element )).getTaggedValues();
      } else {
        log( "Expected an instance of UML 1.3 ModelElement, instead of "+new ModelElementPrinter( element )
             +"  for path: "+path, Project.MSG_VERBOSE );
      }
    } else if ( PATH_STEREOTYPE.equalsIgnoreCase( path ) ) {
      if ( element instanceof ModelElement ) {
        result = ((WrapModelElement) Factories.wrap( element )).getStereotype();
      } else {
        log( "Expected an instance of UML 1.3 ModelElement, instead of "+new ModelElementPrinter( element )
             +"  for path: "+path, Project.MSG_VERBOSE );
      }
    } else if ( PATH_ASSOC_END.equalsIgnoreCase( path ) ) {
      if ( element instanceof Classifier ) {
        result = ((WrapClassifier) Factories.wrap( element )).getAssociationEnds( );
      } else {
        log( "Expected an instance of UML 1.3 Classifier, instead of "+new ModelElementPrinter( element )
             +"  for path: "+path, Project.MSG_VERBOSE );
      }        
    } else if ( element instanceof RefBaseObject ) {
      result = super.getValue( element, path );
    } else {
      log( "Expected an instance of amy model class, instead of "+new ModelElementPrinter( element )
           +"  for path: "+path, Project.MSG_VERBOSE );      
    }
    return result;
  }
}