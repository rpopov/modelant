/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package net.mdatools.modelant.core.operation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.core.api.diff.InstanceDifference;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.match.ConsideredEqual;
import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.core.operation.element.PrintModelElement;
import net.mdatools.modelant.core.operation.element.RetrieveAssociations;
import net.mdatools.modelant.core.operation.element.RetrieveAttributes;
import net.mdatools.modelant.core.operation.model.topology.CacheClassResults;
import net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMap;
import net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMapImpl;
import net.mdatools.modelant.core.operation.model.topology.ModelTopology;
import net.mdatools.modelant.core.operation.model.topology.Node;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Identify structural differences between two models loaded in separate extents in the current repository. They
 * are treated as different versions of a common model, so one of them is referred as <b>old</b>, whereas the
 * other is referred as <b>new</b>. The comparison is done according explicitly provided:<ul>
 * <li> <b>matching criteria</b> stating for each metamodel class what attributes and associations to compare
 *      bound to an instance in the old object and to an instance in the new model, in order to treat both
 *      instances as corresponding.
 * <li> <b>changeDetectionCriteria</b> relaxing the matching criteria, so that any not matching objects, due to the
 *      matching criteria, but matching due to this one are considered as moved or changed elements.
 * </ul>
 * <b>A model elements from the "new" model is treated as equal to a model element from the "old" model, if:</b><ul>
 * <li>both they have equal (primitive) values of the attributes<br/>AND
 * <li>they refer equal objects through associations
 * </ul>
 * Model elements, for which there are no criteria (attributes or associations) specified for their class
 * are treated as NOT EQUAL.
 * </b>
 * @author Rusi Popov
 */
public class CompareModels implements Function<RefPackage, ModelComparisonResult> {

  private static final Logger LOGGER = Logger.getLogger( CompareModels.class.getName() );

  private static final PrintModelElement PRINT_MODEL_ELEMENT = new PrintModelElement();

  /**
   * The criteria to use for exactly matching model elements. Thus, elements that match
   * according to this criteria are treated as equal / same, whereas any other elements are treated
   * as added or deleted.
   */
  private final MatchingCriteria exactMatchCriteria;

  /**
   * The attributes and associations to disregard, when identifying the exactly matching model elements.
   * For example, when matching MOF metamodels, MOF specified the "qualifiedName" derived attribute, which
   * would have different values for relocaated model elements
   */
  private final MatchingCriteria exceptionMatchCriteria;

  /**
   * The list of explicit bindings/mappings between elements from and to models
   */
  private final List<ConsideredEqual> bindings = new ArrayList<>();

  /**
   * non-null model to compare to as a previous version
   */
  private final RefPackage sourceModelExtent;

  private final Function<RefObject, Collection<String>> retrieveAssociations = new CacheClassResults(new RetrieveAssociations());

  private final Function<RefObject, Collection<String>> retrieveAttributes = new CacheClassResults(new RetrieveAttributes());


  /**
   * Use the same exact match and relaxed match criteria, this way treating as not identical the elements,
   * that are different according to the matching criteria.
   *
   * @param exactMatchCriteria not null criteria stating for each metamodel class what attributes and associations
   *   to compare bound to an instance in the old object and to an instance in the new model, in order to treat both
   *   instances as corresponding. Thus, elements that match according to this criteria are treated as equal / same,
   *   whereas any other elements are treated as added or deleted.
   * @param exceptionMatchCriteria non null criteria which attributes and associations to skip in the identification
   *   of elements differences.
   * @param bindings non null list defines explicitly listed objects as equals (even though they are not equal in the
   *   sense of exactMatchCriteria). These are model elements that should be treated as a-priori equal.
   * @param sourceModelExtent not null extent holding the model, treated as "old"
   * @see #CompareModels(MatchingCriteria, MatchingCriteria, List, RefPackage)
   */
  public CompareModels(MatchingCriteria exactMatchCriteria,
                       MatchingCriteria exceptionMatchCriteria,
                       List<ConsideredEqual> bindings,
                       RefPackage sourceModelExtent) {
    if ( exactMatchCriteria == null ) {
      throw new IllegalArgumentException("Expected non-null exact match criteria provided");
    }
    this.exactMatchCriteria = exactMatchCriteria;

    if ( exceptionMatchCriteria == null ) {
      throw new IllegalArgumentException("Expected non-null exceptions of the match provided");
    }
    this.exceptionMatchCriteria = exceptionMatchCriteria;


    if ( bindings == null ) {
      throw new IllegalArgumentException("Expected non-null list of in advance known equal elements");
    }
    this.bindings.addAll(bindings);

    if ( sourceModelExtent == null ) {
      throw new IllegalArgumentException("Expected non-null extent of the \"old\" model");
    }
    this.sourceModelExtent = sourceModelExtent;
  }

  /**
   * @param targetModelExtent The extent where the new model is loaded
   * @return a non-null result of comparison of newRootPackage treated as a "new version" of a model
   *         and oldRootPackage, treated as its "old version". The result describes the changes
   *         happened in the old vresion in order to produce the new one. result.getEquals() != null
   *         The ADDED/DELETED model elements are ordered by LEVEL descending, metaclass ascending,
   *         values ascending
   * @throws IllegalArgumentException
   */
  public ModelComparisonResult execute(RefPackage targetModelExtent) throws IllegalArgumentException {
    ModelComparisonResult result;
    EquivalenceClassesMap<RefObject> equals;
    ModelTopology newTopology;
    ModelTopology oldTopology;
    Collection<RefObject> allOldNodes;
    Collection<RefObject> allNewNodes;
    List<InstanceDifference> changes;
    List<InstanceDifference> exactMatches;

    allNewNodes = Navigator.getAllObjects(targetModelExtent);
    allOldNodes = Navigator.getAllObjects(sourceModelExtent);

    newTopology = new ModelTopology();
    newTopology.load(exactMatchCriteria, allNewNodes);

    oldTopology = new ModelTopology();
    oldTopology.load(exactMatchCriteria, allOldNodes);

    equals = new EquivalenceClassesMapImpl<>();

    defineExternallyProvidedEquals( sourceModelExtent,
                                    targetModelExtent,
                                    bindings,
                                    equals);

    excludeEquivalent(oldTopology, newTopology, equals);

    findEquals(oldTopology, newTopology, equals);

    changes = new ArrayList<>();
    exactMatches = new ArrayList<>();

    detectElementChanges(exactMatches, changes, equals);

    result = new ModelComparisonResultImpl(exactMatchCriteria,
                                           newTopology.getContents(), // not matched nodes
                                           oldTopology.getContents(), // not matched nodes
                                           changes,
                                           exactMatches);
    newTopology.clear();
    oldTopology.clear();

    return result;
  }

  /**
   * Bind in equivalenceClasses the elements, treated as equal by some extenral criteria
   * @param sourceRootPackage
   * @param targetRootPackage
   * @param consideredEquals
   * @param equivalenceClasses
   */
  private void defineExternallyProvidedEquals(RefPackage sourceRootPackage,
                                              RefPackage targetRootPackage,
                                              List<ConsideredEqual> consideredEquals,
                                              EquivalenceClassesMap<RefObject> equivalenceClasses) {
    Collection<RefObject> sourceObjects;
    Collection<RefObject> targetObjects;

    for (ConsideredEqual binding : consideredEquals) {
      sourceObjects = binding.selectOld().execute(sourceRootPackage);
      targetObjects = binding.selectNew().execute(targetRootPackage);

      // map sourceObjects as a class to targetObjects as a class
      if (!sourceObjects.isEmpty() && !targetObjects.isEmpty()) {
        equivalenceClasses.add(sourceObjects, targetObjects);
      }
    }
  }

  /**
   * Exclude from the topologies the elements that are known as equivalent by any external means
   * @param sourceTopology not null
   * @param targetTopology not null
   * @param equivalenceClasses
   */
  private void excludeEquivalent(ModelTopology sourceTopology,
                                 ModelTopology targetTopology,
                                 EquivalenceClassesMap<RefObject> equivalenceClasses) {

    // exclude from both topologies the already matched (externally) nodes
    for (RefObject representative : equivalenceClasses.getXKeys()) {
      sourceTopology.remove( equivalenceClasses.getEquivalents( representative ) );
      targetTopology.remove( equivalenceClasses.getEquivalents( equivalenceClasses.map( representative ) ) );
    }
  }

  /**
   * Identify the equal objects/model elements from this and the provided topology and modifies this
   * and the provided topologies listing only the not matched elements. This way, this method has
   * three results - the exact mapping it found and the modified topologies - this and the parameter
   * one.
   * POST-CONDITION:<ul>
   * <li> only model elements that have correspondents are mapped to equivalence classes in knownEquivalences
   * </ul>
   * As of https://mdatools.net/mantis/view.php?id=6 (issue 0000006), compared are only nodes of the same
   * generation of ready nodes.
   * @param sourceTopology non-null source model topology
   * @param targetTopology non-null target model topology
   * @param equivalenceClasses non-null existing/known externally equivalences,
   *        left with the correspondence between model elements form this topology to the
   *        corresponding element form the other topology, including the initial know equivalence
   *        classes. knownEquivalences is modified to be the result.
   */
  private void findEquals(ModelTopology sourceTopology,
                          ModelTopology targetTopology,
                          EquivalenceClassesMap<RefObject> equivalenceClasses) {
    int iteration;
    Node<RefObject> sourceRepresentative;
    Node<RefObject> targetRepresentative;
    List<Node<RefObject>> matchedSources;
    List<Node<RefObject>> matchedTargets;

    List<Node<RefObject>> sourceGenerationReady;
    List<Node<RefObject>> targetGenerationReady;

    List<Node<RefObject>> collectedMatchedSources;
    List<Node<RefObject>> collectedMatchedTargets;

    List<Node<RefObject>> unmatchedSources;

    unmatchedSources = new ArrayList<>();

    collectedMatchedSources = new ArrayList<>();
    collectedMatchedTargets = new ArrayList<>();

    iteration = 0;

    sourceGenerationReady = sourceTopology.getGenerationOfReady();
    targetGenerationReady = targetTopology.getGenerationOfReady();

    while ( !sourceGenerationReady.isEmpty() ) {
      iteration++;

      while ( !sourceGenerationReady.isEmpty() ) {
        sourceRepresentative = sourceGenerationReady.get(0);

        matchedTargets = Node.findReadyMatches(equivalenceClasses, sourceRepresentative, targetGenerationReady );

        LOGGER.log( Level.FINER,
                    "Matching {0} found at the target side {1}",
                    new Object[] {PRINT_MODEL_ELEMENT.toPrint(sourceRepresentative),
                                  PRINT_MODEL_ELEMENT.toPrint(matchedTargets)} );

        if ( matchedTargets.isEmpty() ) { // sourceRepresentative was not matched to anything at the other side

          // exclude sourceRepresentative from further matching to guarantee the termination
          unmatchedSources.add( sourceRepresentative );
          sourceGenerationReady.remove( sourceRepresentative );

        } else {
          targetRepresentative = matchedTargets.get(0);

          matchedSources = Node.findReadyMatches(equivalenceClasses, targetRepresentative, sourceGenerationReady );

          LOGGER.log( Level.FINER,
                      "Matching {0} found at the source side {1}",
                      new Object[] {PRINT_MODEL_ELEMENT.toPrint(targetRepresentative),
                                    PRINT_MODEL_ELEMENT.toPrint(matchedSources)} );

          assert matchedSources.contains( sourceRepresentative )
                 : "Expected this node pertains to its equivalence class";

          // define the equivalence class matchedAtThisSide mapped to the equivalence class matchedAtOtherSide
          equivalenceClasses.add( Node.unwrap(matchedSources),
                                  Node.unwrap(matchedTargets) );

          targetGenerationReady.removeAll( matchedTargets );
          sourceGenerationReady.removeAll( matchedSources );

          collectedMatchedSources.addAll( matchedSources );
          collectedMatchedTargets.addAll( matchedTargets );
        }
      } // targetGenerationReady contains unmatched target nodes - they could not be matched anymore
        // sourceGenerationReady is empty,
        // unmatchedSources contains the unmatched source nodes - they cannot be matched anymore

      LOGGER.log( Level.FINE,
                  "Iteartion {0}, found as deleted: {1}",
                  new Object[] {iteration, PRINT_MODEL_ELEMENT.toPrint( unmatchedSources )} );
      LOGGER.log( Level.FINE,
                  "Iteartion {0}, found as added: {1}",
                  new Object[] {iteration, PRINT_MODEL_ELEMENT.toPrint( targetGenerationReady )} );

      sourceTopology.removeFromReadyNodes( unmatchedSources );  // this and targetTopology contain the next generation ready nodes
      unmatchedSources.clear();

      targetTopology.removeFromReadyNodes( targetGenerationReady );  // this and targetTopology contain the next generation ready nodes
      targetGenerationReady.clear();

      // produce the next generation in readyNodes
      sourceTopology.removeFromTopology( collectedMatchedSources );
      targetTopology.removeFromTopology( collectedMatchedTargets );

      collectedMatchedSources.clear();
      collectedMatchedTargets.clear();

      sourceGenerationReady = sourceTopology.getGenerationOfReady();
      targetGenerationReady = targetTopology.getGenerationOfReady();
    } // any nodes left in this or other topology were not matched or participate in one or more circular dependencies
  }

  /**
   * For each model element from the [new] model find the possible elements from the [old] model,
   * that are equivalent to it, according to the stated criteria, but have changed metamodel attributes
   * or changed metamodel associations. When there are N model elements in a equivalence class,
   * according to the criteria, in the new model, mapped to an equivalence class of M model elements
   * in the old model, then at most NxM pairs of elements, representing a change.
   * <br/>
   * For example, in the terms of UML 1.3 these could be [Uml]Class instances that changed their
   * UML Namespace or name.
   * Post-condition:<ul>
   * <ul> result maps elements from one (first) model to correspondent elements from the other (second) model, i.e.
   *      result.get(elemen1) == element2
   *           ==>
   *           correspondents.map(element1) == correspondents.get(element2)
   * <ul> result does not contain elements that are identically mapped to each other, i.e.
   *      result.get(element1) == element2
   *         ==>
   *         correspondents.getSet(element2) does not contain identically mapped elements for element1
   * <ul> for each mapped in result element there is no other element mapped that is equal to it
   * </ul>
   * @param exactMatches not null list to add the exact matches (not changed elements) detected
   * @param changes not null list where to add the changed elements detected
   * @param equivalenceClasses not null
   */
  private void detectElementChanges(List<InstanceDifference> exactMatches,
                                    List<InstanceDifference> changes,
                                    EquivalenceClassesMap<RefObject> equivalenceClasses) {

    // split each 1:1 mapped classes of equivalent elements into pairs of identical elements into exactMatches
    equivalenceClasses.getXKeys()
// The parallel execution causes loops in the buckets in the WeakHashMap used by MDR
//                      .parallelStream()
                      .forEach( xRepresentative -> compareRepresentatives(xRepresentative,
                                                                          exactMatches,
                                                                          changes, equivalenceClasses) );
  }

  /**
   * yRepresentative = correspondents.map( xRepresentative );
   * @param yRepresentative not null
   * @param xRepresentative not null
   * @param exactMatches not null list to add the exact matches (not changed elements) detected
   * @param changes not null list where to add the changed elements detected
   * @param equivalenceClasses not null
   */
  private void compareRepresentatives(final RefObject xRepresentative,
                                      final List<InstanceDifference> exactMatches,
                                      final List<InstanceDifference> changes,
                                      EquivalenceClassesMap<RefObject> equivalenceClasses) {
    RefObject yRepresentative;
    List<InstanceDifference> xDiffs;
    InstanceDifferencesImpl exactMatch;
    InstanceDifferencesImpl diff;
    Collection<CachedModelElement> yEquivalents;
    Collection<CachedModelElement> xEquivalents;

    Iterator<CachedModelElement> xEquivalentIterator;
    Iterator<CachedModelElement> yEquivalentIterator;
    CachedModelElement xCorrespondent;
    CachedModelElement yCorrespondent;

    Collection<String> attributeNames;
    Collection<String> associationNames;

    List<InstanceDifference> detectedChanges;

    attributeNames = retrieveAttributes.execute( xRepresentative );
    attributeNames.removeAll( exceptionMatchCriteria.getAttributes( xRepresentative ) );

    associationNames = retrieveAssociations.execute( xRepresentative );
    associationNames.removeAll( exceptionMatchCriteria.getAssociations( xRepresentative ) );

    yRepresentative = equivalenceClasses.map( xRepresentative );

    xEquivalents = CachedModelElement.cacheModel(equivalenceClasses.getEquivalents( xRepresentative ),
                                                attributeNames,
                                                associationNames);
    yEquivalents = CachedModelElement.cacheModel(equivalenceClasses.getEquivalents( yRepresentative ),
                                                attributeNames,
                                                associationNames);

    // collect the changes locally, before committing them to the non-local/shared changes list
    detectedChanges = new ArrayList<>();

    LOGGER.log( Level.FINE, "Each-to-each comparison of: {0}", new Integer(xEquivalents.size()));

    xEquivalentIterator = xEquivalents.iterator();
    while ( xEquivalentIterator.hasNext() ) {
      xCorrespondent = xEquivalentIterator.next();

      xDiffs = new ArrayList<>();
      exactMatch = null;

      yEquivalentIterator = yEquivalents.iterator();
      while ( exactMatch == null && yEquivalentIterator.hasNext() ) {
        yCorrespondent = yEquivalentIterator.next();

        diff = new InstanceDifferencesImpl( xCorrespondent,
                                            yCorrespondent,
                                            equivalenceClasses);
        if ( diff.isExactMatch() ) { // no diffs of yCorrespondent and xCorrespondent should be reported
          exactMatch = diff;
        } else {
          xDiffs.add( diff );
        }
      }

      if (exactMatch != null) {
        yEquivalentIterator.remove(); // yMatch excluded from further comparison
        xEquivalentIterator.remove(); // xMatch excluded from further comparison

        exactMatch.removeCoveredDiffs( detectedChanges ); // no previous comparisons of yMatch
        xDiffs.clear(); // no previous comparisons of xMatch

        synchronized(exactMatches) {
          exactMatches.add( exactMatch );
        }
      } else {
        detectedChanges.addAll( xDiffs );
      }
    } // detectedChanges = {(x,y)| x does not match exactly y}, there may be multiple occurrences of x or y

    synchronized (changes) {
      changes.addAll( filterOutRepeated( detectedChanges ) );
    }
  }


  /**
   * @param detectedChanges non-null {(x,y)| x does not match exactly y}, there may be multiple occurrences of x or y
   * @return a non-null list where any node x (or y) occur exactly once
   */
  private List<InstanceDifference> filterOutRepeated(List<InstanceDifference> detectedChanges) {
    List<InstanceDifference> result;
    InstanceDifference diff;

    result = new ArrayList<>();
    while (!detectedChanges.isEmpty()) {
      diff = detectedChanges.remove( 0 );
      result.add( diff );

      diff.removeCoveredDiffs( detectedChanges );
    }
    return result;
  }
}