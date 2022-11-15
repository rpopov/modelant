/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model;

import java.util.List;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.diff.InstanceDifference;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.diff.ModelDifference;
import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.core.operation.element.PrintModelElement;

/**
 * Represent the comparison results in terms of:
 * <ul>
 * <li>list of deleted model elements
 * <li>list of added model elements
 * <li>differences of the individual objects matched as changed
 * </ul>
 */
class ModelComparisonResultImpl implements ModelComparisonResult {

  static final PrintModelElement PRINT_MODEL_ELEMENT = new PrintModelElement();

  private final List<ModelDifference> deleted;

  private final List<ModelDifference> added;

  private final List<InstanceDifference> changed;

  private final List<InstanceDifference> exactMatches;

  private final MatchingCriteria matchingCriteria;

  /**
   * @param matchingCriteria not null
   * @param added not null
   * @param deleted not null
   * @param changes not null
   * @param exactMatches not null
   */
  public ModelComparisonResultImpl(MatchingCriteria matchingCriteria,
                                   List<RefObject> added,
                                   List<RefObject> deleted,
                                   List<InstanceDifference> changes,
                                   List<InstanceDifference> exactMatches) {
    assert matchingCriteria != null : "Expected non-null matching criteria";
    assert added != null : "Expected non-null added obects";
    assert deleted != null : "Expected non-null deleted obects";
    assert changes != null : "Expected non-null obect differences";
    assert exactMatches != null : "Expected non-null exactly matched objects";

    this.matchingCriteria = matchingCriteria;
    this.deleted = ModelDifferenceImpl.findModelDifferences(deleted, matchingCriteria);
    this.added = ModelDifferenceImpl.findModelDifferences(added, matchingCriteria);
    this.changed = changes;
    this.exactMatches = exactMatches;
  }

  /**
   * @see net.mdatools.modelant.core.api.diff.ModelComparisonResult#getDeleted()
   */
  public final List<ModelDifference> getDeleted() {
    return deleted;
  }

  /**
   * @see net.mdatools.modelant.core.api.diff.ModelComparisonResult#getAdded()
   */
  public final List<ModelDifference> getAdded() {
    return added;
  }

  /**
   */
  public final List<InstanceDifference> getChanged() {
    return changed;
  }

  public List<InstanceDifference> getExactlyMatched() {
    return exactMatches;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append( "ModelComparisonResult {"+System.lineSeparator() );
/*
    builder.append( "matchingCcriteria=" );
    builder.append( matchingCriteria );
    builder.append( ","+System.lineSeparator() );
*/
    if ( deleted != null ) {
      builder.append( "deleted=" );
      builder.append( deleted );
      builder.append( ","+System.lineSeparator() );
    }
    if ( added != null ) {
      builder.append( "added=" );
      builder.append( added );
      builder.append( ","+System.lineSeparator() );
    }
    if ( changed != null ) {
      builder.append( "changed=" );
      builder.append(  changed );
      builder.append( ","+System.lineSeparator() );
    }
/**    
    if ( exactMatches != null ) {
      builder.append( "exactMatches=" );
      builder.append( exactMatches );
      builder.append( ","+System.lineSeparator() );
      builder.append( exactMatches.size()+"  elements" );
      builder.append( System.lineSeparator() );
    }
 **/    
    builder.append( "exactMatches= NOT PRINTED" );
    builder.append( System.lineSeparator() );
    builder.append( "}" );

    return builder.toString();
  }

  /**
   * @see net.mdatools.modelant.core.api.diff.ModelComparisonResult#getMatchingCriteria()
   */
  public MatchingCriteria getMatchingCriteria() {
    return matchingCriteria;
  }
}