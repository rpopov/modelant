/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 1, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.name;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.model.ModelElement;
import javax.jmi.model.ModelPackage;
import javax.jmi.model.Tag;

/**
 * A general mechanism of identifying names, considering all their decorations and oevrriding
 * with tags of other mechanisms.
 * @author Rusi Popov
 */
public interface ConstructName {

  /**
   * Common logger for all instances
   */
  Logger LOGGER = Logger.getLogger( ConstructName.class.getName() );

  /**
   * @param element whose name is to retrieve. It could be null for generality
   * @return non-null formatted name. It is empty if not defined
   */
  String constructName(ModelElement element);

  /**
   * Retrieves the tag assigned to the MOF object provided
   *
   * @param element non null whose tags to check
   * @param tagId not null tagId
   * @return the MOF Tag associated with the model element or null
   */
  static Tag getTag(ModelElement element, String tagId) {
    Tag result;
    ModelPackage extent;
    Tag tag;
    Iterator<Tag> tagsIterator;

    extent = (ModelPackage) element.refOutermostPackage();

    // find the tag bound to the model element
    result = null;
    tagsIterator = extent.getTag().refAllOfClass().iterator();
    while ( result == null && tagsIterator.hasNext() ) {
      tag = tagsIterator.next();

      if ( tagId.equals( tag.getTagId() ) && tag.getElements().contains( element ) ) {
        result = tag;
      }
    }
    return result;
  }
}
