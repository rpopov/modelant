/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.topology;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.match.MatchingCriteria;

/**
 * A simple matching criteria for model elements, just by providing the same list of
 * attributes and associations to all objects.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class SimpleCriteria implements MatchingCriteria {

  /**
   * A non-null list of attributes/paths to use
   */
  private final List<String> attributes;

  /**
   * A non-null list of associations/paths to use
   */
  private final List<String> associations;

  /**
   * @param attributes is a non-null comma-separated list of attributes/paths to use
   * @param associations is a non-null comma-separated list of associations/paths to use
   */
  public SimpleCriteria(String attributes, String associations) {
    this.attributes = parse(attributes);
    this.associations = parse(associations);
  }

  /**
   * Use explicitly provided lists of attribute names and association names.
   * Take a snapshot of the lists, so they could be reused and changed later, not affecting the criteria
   * @param attributes not null
   * @param associations not null
   */
  public SimpleCriteria(List<String> attributes, List<String> associations) {
    this.attributes = new ArrayList<>(attributes);
    this.associations = new ArrayList<>(associations);
  }


  /**
   * Parses a comma-separated list of words
   * @param list is a non-null, possibly empty list of words
   * @return a non-null, but possibly empty, list of separate words out of the list provided
   */
  private List<String> parse(String list) {
    List<String> result;
    StringTokenizer tokenizer;

    result = new ArrayList<String>();
    tokenizer = new StringTokenizer( list, ", " );
    while ( tokenizer.hasMoreElements() ) {
      result.add( (String) tokenizer.nextElement() );
    }
    return result;
  }


  /**
   * @see net.mdatools.modelant.core.api.match.MatchingCriteria#getAttributes(javax.jmi.reflect.RefObject)
   */
  public List<String> getAttributes(RefObject forObject) {
    return attributes;
  }


  /**
   * @see net.mdatools.modelant.core.api.match.MatchingCriteria#getAssociations(javax.jmi.reflect.RefObject)
   */
  public List<String> getAssociations(RefObject forObject) {
    return associations;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder;

    builder = new StringBuilder();
    builder.append( "SimpleCriteria [")
           .append( "[attributes=" ).append( attributes )
           .append( ", associations=" ).append( associations )
           .append( "]" );

    return builder.toString();
  }
}