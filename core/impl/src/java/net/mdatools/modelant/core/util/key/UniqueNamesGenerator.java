/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.util.key;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the generation of unique strings by mapping each one
 * to a counter. The strings generated are:
 *   original
 *   original1
 *   ...
 *   originalN <pre>
 * Usage:
 *
 *    name = constructUniqueName( name_suggestion );
 *
 * </pre>
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class UniqueNamesGenerator {

  /**
   * Maps:
   *   objects to actual names
   *   names to repetition counters used to generate next unique name
   */
  private Map<String, Integer> wordToCounterMap = new HashMap<String, Integer>();

  /**
   * @return a non-null unique name based on the original
   */
  public String getUnique(String original) {
    String result;
    Integer counter;

    counter = wordToCounterMap.get( original );
    if ( counter == null ) {
      counter = new Integer(0);
      result = original;

    } else { // the name is already bound, generate unique name
      counter = new Integer(counter.intValue() +1);
      result = original+counter;
    }
    wordToCounterMap.put( original, counter );

    return result;
  }
}