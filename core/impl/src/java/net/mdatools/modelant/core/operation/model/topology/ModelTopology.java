/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.topology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.core.util.map.MapToCollection;
import net.mdatools.modelant.core.util.map.MapToSet;

/**
 * This class allows structurally comparing models, independently of their actual meta-models. It is
 * intended to serve the model change tracking.
 *
 * <pre>
 * Usage:
 *   new ModelTopology()
 *   load()
 *   findEquals() / findEquals(EquivalenceMap) returns the element matches
 *   getNotProcessed() returns not matched elements
 * </pre>
 *
 * NOTE: The instances should not be reused
 *
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class ModelTopology {

  private static final Logger LOGGER = Logger.getLogger( ModelTopology.class.getName() );

  /**
   * Maps node.level to set of nodes with that value
   *
   * @see #add(Node)
   * @see #remove(Node)
   */
  private final MapToCollection<Key, Node<RefObject>> nodes = new MapToSet<>();

  /**
   * Contains only nodes pertaining to <b>nodes</b> and having level()==0 == isReady()
   * Used for controlled iteration in topological order over the nodes.
   */
  private final List<Node<RefObject>> readyNodes = new ArrayList<>();

  /**
   * The correspondence from model elements to their internal representation as nodes
   */
  private final Map<RefObject, Node<RefObject>> elementToNodeMap = new IdentityHashMap<>( 101 );

  /**
   * @return true iff getNodes() is empty
   */
  public boolean isEmpty() {
    return nodes.isEmpty();
  }

  /**
   * Load into this model topology the listed associations and attributes, so that model processing
   * would treat as equal any model elements with same values of the listed attributes and equal
   * objects bound in the listed associations.
   *
   * @param criteria is a not null criteria defining the attribute names to evaluate for all model
   *          elements
   * @param elements is a non-null list of model elements to order
   */
  public void load(MatchingCriteria criteria, Collection<RefObject> elements) {
    Iterator<RefObject> elementsIterator;
    Iterator<Node<RefObject>> nodesIterator;
    RefObject element;
    Node<RefObject> node;

    assert criteria != null : "Expected non-null criteria";

    LOGGER.log(Level.INFO, "Start loading a model of {0} elements", elements.size());
    
    // enlist the model elements to order
    elementsIterator = elements.iterator();
    while ( elementsIterator.hasNext() ) {
      element = elementsIterator.next();

      node = new Node<>( element, criteria );
      elementToNodeMap.put( element, node );
    }

    LOGGER.log(Level.INFO, "Converted {0} elements to nodes", elementToNodeMap.size());

    // arrange the nodes in this topology according to its level (relations to other nodes it refers)
    nodesIterator = elementToNodeMap.values().iterator();
    while ( nodesIterator.hasNext() ) {
      node = nodesIterator.next();

      node.assignAssociatedNodes( elementToNodeMap );
      add( node );
    }

    LOGGER.log(Level.INFO, "Bound reerred elements {0}", elementToNodeMap.size());
  }


  /**
   * (Re)calculate the key and bind the node and identify the ready nodes
   * @param node
   */
  private void add(Node<RefObject> node) {
    nodes.put( node.getKey(), node );

    if ( node.isReady() ) {
      readyNodes.add( node );
    }
  }

  /**
   * @return a copy of the current ready nodes, this way forming a "generation of ready nodes",
   *         that could be manipulated independently of the current set of ready nodes
   */
  public final ArrayList<Node<RefObject>> getGenerationOfReady() {
    LOGGER.log(Level.INFO, "Next generation of {0} ready elements to compare", readyNodes.size());
    
    return new ArrayList<>(readyNodes);
  }


  /**
   * Removes from this topology all nodes from the equivalence class the representative is of
   * @param equivalents not null
   */
  public final void remove(Collection<RefObject> equivalents) {
    for (RefObject element : equivalents) {
      removeFromTopology( elementToNodeMap.get( element ) );
    }
  }


  /**
   * This method excludes the nodes provided from the owner topology, decreases the level of all
   * nodes that refer this and rearranges the topology to reflect level of the changed nodes.
   *
   * @param nodes is a collection of ready nodes
   */
  public void removeFromTopology(Collection<Node<RefObject>> nodes) {
    for (Node<RefObject> node : new ArrayList<>( nodes )) {
      removeFromTopology( node );
    }
  }

  /**
   * Exclude the node from the owner topology, decreasing the level of all nodes that refer this and
   * rearranges the topology to reflect level of the changed nodes. Because of the explicitly
   * provided mappings of nodes that to be treated as equal, but they are not, we cannot expect
   * anymore that
   * <ul>
   * <li>the nodes to remove are in ready state and
   * <li>when removing an object its referers are in the topology (because they might have been
   * removed)
   * </ul>
   *
   * @param node is a ready node
   */
  private void removeFromTopology(Node<RefObject> node) {
    Iterator<Node<RefObject>> referencingIterator;
    Node<RefObject> referer;
    boolean actuallyRemoved;

    actuallyRemoved = remove( node );

    assert actuallyRemoved : "Expected a node that existed in this topology " + node;

    // for each of the referencing node nodes - decrease number of referenced and rearrange
    referencingIterator = node.getReferers().iterator();
    while ( referencingIterator.hasNext() ) {
      referer = referencingIterator.next();

      actuallyRemoved = remove( referer );

      // assert actuallyRemoved : "Expected the referrer node exists in this topology "+referer;
      if ( actuallyRemoved ) {
        referer.decreaseLevel();
        add( referer );
      }
    }
  }


  /**
   * @return true if actually removed the node
   */
  private boolean remove(Node<RefObject> node) {
    if ( node.isReady() ) {
      readyNodes.remove( node );
    }
    return nodes.remove( node.getKey(), node );
  }

  /**
   * Exclude the listed nodes from the READY list, but keep them in the topology itself
   * @param targetGenerationReady not null
   */
  public void removeFromReadyNodes(List<Node<RefObject>> targetGenerationReady) {
    readyNodes.removeAll( targetGenerationReady );
  }

  /**
   * @return a non-null list of model elements that where left in the topology.
   */
  public List<RefObject> getContents() {
    List<RefObject> result;

    result = new ArrayList<RefObject>();

    // left are circular dependencies only (if any)
    for (Node<RefObject> node : nodes.values()) {
      result.add( node.getWrapped() );
    }
    return result;
  }


  /**
   * Leave this topology empy and ready to load another model
   */
  public void clear() {
    readyNodes.clear();
    nodes.clear();
    elementToNodeMap.clear();
  }


  public String toString() {
    return getClass().getSimpleName() + "{" + nodes + "}";
  }
}