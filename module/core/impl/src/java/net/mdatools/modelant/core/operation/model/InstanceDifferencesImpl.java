/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.diff.AssociationDifference;
import net.mdatools.modelant.core.api.diff.InstanceDifference;
import net.mdatools.modelant.core.operation.element.PrintModelElement;
import net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMap;

/**
 * Hold all identified changes/differences between xObject and yObject
 */
public class InstanceDifferencesImpl implements InstanceDifference {

  private final List<String> attributesWithDifferences = new ArrayList<>();
  private final List<AssociationDifference> associationDiffs = new ArrayList<>();

  /**
   * Invariant:
   *   correspondents.map(xObject)=correspondents.getRepresentative(yObject)
   */
  private final CachedModelElement xObject;
  private final CachedModelElement yObject;

  /**
   * The frozen calculation of the differences/changes.
   * Completed by {@link #completeChangeDetection()}
   */
  private final EquivalenceClassesMap<RefObject> correspondents;
  private final Iterator<String> attributesIterator;
  private final Iterator<String> associationsIterator;

  private static final PrintModelElement PRINT_MODEL_ELEMENT = new PrintModelElement("  ");
  private static final PrintModelElement PRINT_NESTED_MODEL_ELEMENT = new PrintModelElement("    ");

  /**
   * correspondents.map(xObject)=correspondents.getRepresentative(yObject)
   * xObject.attributeNames = yObject.attributeNames
   * xObject.assiciationNames = yObject.assiciationNames
   * @param xCorrespondent
   * @param yCorrespondent
   * @param equivalenceClasses
   * @param correspondents
   */
  public InstanceDifferencesImpl(CachedModelElement xCorrespondent,
                                 CachedModelElement yCorrespondent,
                                 EquivalenceClassesMap<RefObject> equivalenceClasses) {
    this.xObject = xCorrespondent;
    this.yObject = yCorrespondent;

    this.correspondents = equivalenceClasses;

    assert correspondents.map(xObject.getWrapped())==correspondents.getRepresentative(yObject.getWrapped())
           : "Expected x and y pertain to mapped equivalence classes";

    assert xObject.getAssociationNames().equals(yObject.getAssociationNames())
           : "The compared objects should have the same set of association names";

    assert xObject.getAttributeNames().equals(yObject.getAttributeNames())
           : "The compared objects should have the same set of attribute names";

    this.attributesIterator = xObject.getAttributeNames().iterator();
    this.associationsIterator = xObject.getAssociationNames().iterator();
  }


  /**
   * Detect the first change, that identifies this as a change (not a match), still saving execution resources.
   * Complete the change detection with {@link #completeChangeDetection()}
   * @see net.mdatools.modelant.core.api.diff.InstanceDifference#isExactMatch()
   */
  public boolean isExactMatch() {
    ShouldSuspendDiff suspendOnAnyChange = new ShouldSuspendDiff() {
      public boolean shouldSuspend() {
        return !attributesWithDifferences.isEmpty()
               || !associationDiffs.isEmpty();
      }
    };

    compareFeatures( suspendOnAnyChange );
    compareAssociations( suspendOnAnyChange );

    return attributesWithDifferences.isEmpty()
           && associationDiffs.isEmpty();
  }

  /**
   * Complete the change detection
   */
  public void completeChangeDetection() {
    ShouldSuspendDiff neverSuspend = new ShouldSuspendDiff() {
      public boolean shouldSuspend() {
        return false;
      }
    };
    compareFeatures( neverSuspend );
    compareAssociations( neverSuspend );
  }

  /**
   * Compare the values of the features (attributes and methods) of the from- and to- object
   * and lists those that have different values
   */
  private void compareFeatures(ShouldSuspendDiff whenToSuspend) {
    String attributeName;
    Object newValue;
    Object oldValue;

    while ( !whenToSuspend.shouldSuspend()
            && attributesIterator.hasNext()) {
      attributeName = attributesIterator.next();
      newValue = xObject.getAttributeValue( attributeName );
      oldValue = yObject.getAttributeValue( attributeName );

      if ( !(newValue == oldValue
             || newValue != null
                && oldValue != null
                && PRINT_MODEL_ELEMENT.execute( newValue ).equals( PRINT_MODEL_ELEMENT.execute( oldValue )))) { // not equal values found
        attributesWithDifferences.add( attributeName );
      }
    }
  }

  /**
   * Compare the associated model elements to the from- and to- object in any associations.
   * POST-CONDITION:<ul>
   * <li> for each listed association name in associationDiffs a pair is registered
   *      &lt;association&gt;.added and &lt;association&gt;.deleted to list the changes
   *      in those associations. No more than
   * </ul>
   */
  private void compareAssociations(ShouldSuspendDiff whenToSuspend) {
    String associationName;
    AssociationDiff diff;

    while ( !whenToSuspend.shouldSuspend()
            && associationsIterator.hasNext()) {
      associationName = associationsIterator.next();

      diff = new AssociationDiff( correspondents,
                                  associationName,
                                  xObject.getAssociationValue( associationName ),
                                  yObject.getAssociationValue( associationName ) );

      if ( !diff.isEmpty() ) {
        associationDiffs.add( diff );
      }
    }
  }


  /**
   * Requires {@link #completeChangeDetection()}
   * @see net.mdatools.modelant.core.api.diff.InstanceDifference#getAttributesWithDifferences()
   */
  public final List<String> getAttributesWithDifferences() {
    return attributesWithDifferences;
  }

  /**
   * Requires {@link #completeChangeDetection()}
   * @see net.mdatools.modelant.core.api.diff.InstanceDifference#getAssociationDiffs()
   */
  public final List<AssociationDifference> getAssociationDiffs() {
    return associationDiffs;
  }

  /**
   * @see net.mdatools.modelant.core.api.diff.InstanceDifference#getXObject()
   */
  public final RefObject getXObject() {
    return xObject.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.api.diff.InstanceDifference#getYObject()
   */
  public final RefObject getYObject() {
    return yObject.getWrapped();
  }

  /**
   * The differences found in a single association, no matter if it is x-to-one or x-to-many
   */
  private static class AssociationDiff implements AssociationDifference {
    private final String associationName;
    private final List<RefObject> xMinusY = new ArrayList<>();
    private final List<RefObject> yMinusX = new ArrayList<>();

    /**
     * @param correspondents not null
     * @param associationName not null association name, existing in x and y objects
     * @param xAssociated not null
     * @param yAssociated not null
     */
    public AssociationDiff(EquivalenceClassesMap<RefObject> correspondents,
                           String associationName,
                           Object xAssociated,
                           Object yAssociated) {
      Set<RefObject> xAssociatedRepresentatives;
      Set<RefObject> yAssociatedRepresentatives;
      RefObject mapped;

      this.associationName = associationName;

      if ( xAssociated instanceof Collection ) { // yAssociated instance of Collection too

        yAssociatedRepresentatives = correspondents.getRepresentativesOrSelf( (Collection<RefObject>) yAssociated );
        xAssociatedRepresentatives = correspondents.getRepresentativesOrSelf( (Collection<RefObject>) xAssociated );

        // identify the differences

        for(RefObject xRepresentative: xAssociatedRepresentatives) {
          if ( !yAssociatedRepresentatives.contains( correspondents.map( xRepresentative )) ) {
            xMinusY.add( xRepresentative );
          }
        }
        for(RefObject yRepresentative: yAssociatedRepresentatives) {
          if ( !xAssociatedRepresentatives.contains( correspondents.map( yRepresentative )) ) {
            yMinusX.add( yRepresentative );
          }
        }
      } else if ( xAssociated != null ) { // yAssociated = null or yAssociated instanceof RefObject

        if ( yAssociated == null ) {
          xMinusY.add( (RefObject) xAssociated );

        } else { // xAssociated != null, yAssociated != null
          mapped = correspondents.map( (RefObject) xAssociated );

          if (mapped != correspondents.getRepresentative((RefObject) yAssociated)) { // orphan vs mapped; mapped vs orphan; mapped <> mapped - all cases mean change
            xMinusY.add( (RefObject) xAssociated );
            yMinusX.add( (RefObject) yAssociated );
          }
        }
      } else if ( yAssociated != null ) { // xAssociated == null
        yMinusX.add( (RefObject) yAssociated );
      }

// Commented out only for performance optimization, as it takes half of the comparison time
//          Collections.sort( xMinusY, new OrderByRestrictedPrint(changeDetectionCriteria));
//          Collections.sort( yMinusX, new OrderByRestrictedPrint(changeDetectionCriteria));
    }

    /**
     * @see net.mdatools.modelant.core.api.diff.AssociationDifference#getAssociationName()
     */
    public final String getAssociationName() {
      return associationName;
    }

    /**
     * @see net.mdatools.modelant.core.api.diff.AssociationDifference#getXMinusY()
     */
    public final List<RefObject> getXMinusY() {
      return xMinusY;
    }

    /**
     * @see net.mdatools.modelant.core.api.diff.AssociationDifference#getYMinusX()
     */
    public final List<RefObject> getYMinusX() {
      return yMinusX;
    }

    /**
     * @return true if this represents no actual difference
     */
    public boolean isEmpty() {
      return xMinusY.isEmpty() && yMinusX.isEmpty();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
      StringBuilder builder = new StringBuilder();

      builder.append( "AssociationDiff {\n");
      if ( associationName != null ) {
        builder.append( "    associationName=" ).append( associationName ).append( ",\n" );
      }
      if ( xMinusY != null ) {
        builder.append( "    xMinusY=" ).append( PRINT_NESTED_MODEL_ELEMENT.execute(xMinusY) ).append( ",\n" );
      }
      if ( yMinusX != null ) {
        builder.append( "    yMinusX=" ).append( PRINT_NESTED_MODEL_ELEMENT.execute(yMinusX) ).append( "\n" );
      }
      builder.append( "}" );
      return builder.toString();
    }
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append( "InstanceDifferences {\n" );
    if ( getXObject() != null ) {
      builder.append( "  xObject=" ).append( PRINT_MODEL_ELEMENT.execute(getXObject())).append( ",\n" );
    }
    if ( getYObject() != null ) {
      builder.append( "  yObject=" ).append(PRINT_MODEL_ELEMENT.execute(getYObject())).append( ",\n" );
    }
    if ( attributesWithDifferences != null ) {
      builder.append( "  attributesWithDifferences=" ).append( attributesWithDifferences ).append( ",\n" );
    }
    if ( associationDiffs != null ) {
      builder.append( "  associationDiffs=" ).append( associationDiffs ).append( "\n" );
    }
    builder.append( "}" );
    return builder.toString();
  }

  /**
   * Criteria when to suspend the calculation of the differences
   */
  private interface ShouldSuspendDiff {
    boolean shouldSuspend();
  }
}