/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 1, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.name;

import java.util.logging.Level;

import javax.jmi.model.ModelElement;
import javax.jmi.model.Namespace;
import javax.jmi.model.Tag;

/**
 * Identify the method's name as if it was decorated with a specific tag
 * as defined in JMI 1.0/MOF 1.4 to search tag with
 * See section 4.6.3 of the JMI Specification
 * @author Rusi Popov
 */
public class ConstructMethodName implements ConstructName {

  /**
   * not null decorated
   */
  private final ConstructName decorated;

  /**
   * The <code>JAVAX_JMI_METHOD_SUBSTITUTE_NAME</code> JMI standard tag ID for name substitution
   * See Section 4.6.3 of JMI 1.0 Specification, bound to a Package.
   * Assumption: the tag affects the methods in classes in that package or nested
   */
  private static final String JAVAX_JMI_METHOD_PREFIX = "javax.jmi.methodPrefix";

  /**
   * @param tagId not null tag ID to lookup
   * @param decorated not null
   */
  public ConstructMethodName(ConstructName decorated) {
    assert decorated != null : "Expected non-null decorated";

    this.decorated = decorated;
  }

  /**
   * @see net.mdatools.modelant.mof14.maven.generator.name.ConstructName#constructName(javax.jmi.model.ModelElement)
   */
  public String constructName(ModelElement element) {
    String result;
    Tag tag;

    if ( element != null ) {
      tag = getPackageTag(element.getContainer(), JAVAX_JMI_METHOD_PREFIX );
      if ( tag != null ) {
        result = (String) tag.getValues().get( 0 );

        LOGGER.log( Level.FINE, "tag {0} = {1}", new String[] {JAVAX_JMI_METHOD_PREFIX, result});
      } else {
        result = decorated.constructName( element );
      }
    } else {
      result = "";
    }
    return result;
  }

  /**
   * Find recursively the tag bound to the provided package (actually container) or its parent
   * @param element not null container (JMI package)
   * @param tagId not null
   * @return the tag found or null
   */
  private Tag getPackageTag(Namespace element, String tagId) {
    Tag result;
    ModelElement container;

    result = ConstructName.getTag(element, JAVAX_JMI_METHOD_PREFIX );
    if ( result == null ) {
      container = element.getContainer();

      if ( container != null ) { // still not the outermost package
        result = getPackageTag( element.getContainer(), tagId );
      }
    }
    return result;
  }
}
