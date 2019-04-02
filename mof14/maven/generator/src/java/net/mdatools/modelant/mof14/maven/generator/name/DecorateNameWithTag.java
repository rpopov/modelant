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

import javax.jmi.model.ModelElement;
import javax.jmi.model.ModelPackage;
import javax.jmi.model.Tag;

/**
 * Identify the element's name as if it was decorated with a specific tag
 * as defined in JMI 1.0/MOF 1.4 to search tag with
 * @author Rusi Popov
 */
public class DecorateNameWithTag implements ConstructName {

  /**
   * not null tagid to lookup
   */
  private final String tagId;

  /**
   * not null decorated
   */
  private final ConstructName decorated;

  /**
   * The <code>JAVAX_JMI_SUBSTITUTE_NAME</code> JMI standard tag ID for name substitution
   */
  public static final String JAVAX_JMI_SUBSTITUTE_NAME = "javax.jmi.substituteName";

  /**
   * @param tagId not null tag ID to lookup
   * @param decorated not null
   */
  public DecorateNameWithTag(String tagId, ConstructName decorated) {
    assert tagId != null : "Expected non-null tagId";
    assert decorated != null : "Expected non-null decorated";

    this.tagId = tagId;
    this.decorated = decorated;
  }


  /**
   * @see net.mdatools.modelant.mof14.maven.generator.name.ConstructName#constructName(javax.jmi.model.ModelElement)
   */
  public String constructName(ModelElement element) {
    String result;
    Tag tag;

    if ( element != null ) {
      tag = getTag( element );
      if ( tag != null ) {
        result = (String) tag.getValues().get( 0 );

        LOGGER.log( Level.FINE, "tag {0} name = {1}", new String[] {tagId, result});
      } else {
        result = decorated.constructName( element );
      }
    } else {
      result = null;
    }
    return result;
  }

  /**
   * Retrieves the tag assigned to the MOF object provided
   *
   * @param element non null whose tags to check
   * @return the MOF Tag associated with the model element or null
   */
  private Tag getTag(ModelElement element) {
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
