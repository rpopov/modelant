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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.reflect.RefBaseObject;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.core.operation.element.PrintElementRestricted;
import net.mdatools.modelant.core.operation.element.PrintModelElement;
import net.mdatools.modelant.core.util.Navigator;
import net.mdatools.modelant.core.util.key.Hash;

/**
 * This class represents a single model element with its associated other elements and attributes
 * among the attributes and associations defined in the owner Topology.
 * <b>
 * In a separate step each node should be assigned the nodes that represent the associated model
 * elements among those that are contained in the topology.
 * </b>
 * @param <V>
 * @see Node#assignAssociatedNodes(Map)
 */
public class Node<V extends RefObject> {

  /**
   * This is a common logger
   */
  private static Logger LOGGER = Logger.getLogger( Node.class.getPackage().getName() );


  private static final PrintModelElement PRINT_MODEL_ELEMENT = new PrintModelElement();

  /**
   * The wrapped model element this node is for
   */
  private final V wrapped;

  /**
   * The attribute values to compare,
   * Contains the names of all attributes of the model element, as defined in the mdetamodel,
   * mapped to their actual values. null values are allowed.
   * Used for nodes matching
   */
  private final Map<String, List<String>> attributeValues = new HashMap<>(3);
  private final int attributesHash;

  /**
   * The model elements that are associated to the wrapped model element.
   * referredModelElements.get(key) == wrapped.refGet(key) (as a list)
   * Used for nodes matching
   */
  private final Map<String, Collection<V>> referredModelElements = new HashMap<>(3);

  /**
   * The effective list of Nodes that reference this
   */
  private final List<Node<V>> referers = new ArrayList<>();

  /**
   * The common criteria to compare model elements (and print the wrapped model element)
   */
  private final MatchingCriteria criteria;

  /**
   * The number of model elements still not matched to correspondents in the other model.
   * Invariant:
   *   non-negative
   *   level == count(referredModelElements.values().values()\{processed nodes})
   */
  private int level;

  /**
   * Stores the current key when calculating it is not possible
   */
  private Key key;

  /**
   * Represents the wrapped model element as a Node with its associations and attributes
   * @param wrapped is not-null
   * @param criteria
   */
  public Node(V wrapped, MatchingCriteria criteria) {
    Iterator<String> attributesIterator;
    String attribute;
    List<?> values;
    List<String> printableValues;
    String stringValue;
    int valuesHash;

    this.wrapped = wrapped;
    this.criteria = criteria;
    this.level = 0;

    valuesHash = 0;

    attributesIterator = criteria.getAttributes(wrapped).iterator();
    while ( attributesIterator.hasNext() ) {
      attribute = attributesIterator.next();

      valuesHash <<= 1;
      try {
        values = Navigator.collectValues( wrapped,
                                          attribute,
                                          LOGGER.isLoggable( Level.FINE ));

        // convert all structures, enum, etc. values into printable & comparable ones, independent of their the order
        printableValues = new ArrayList<>();
        for ( Object value: values ) {
          if ( value instanceof RefBaseObject || value instanceof RefStruct ) {
            stringValue = PRINT_MODEL_ELEMENT.execute( value ).toUpperCase();

            valuesHash += Hash.hash( stringValue );
            printableValues.add(stringValue);

          } else if ( value != null ) {
            stringValue = value.toString().toUpperCase();

            valuesHash += Hash.hash( stringValue );
            printableValues.add(stringValue);
          }
        }
        attributeValues.put(attribute, printableValues );
      } catch (Exception ex) {
        LOGGER.log( Level.FINE,
                    "Model element: {0} does not support attribute: {1}",
                    new Object[]{PRINT_MODEL_ELEMENT.execute(getWrapped()), attribute});
      }
    }
    this.attributesHash = valuesHash;

    assignKey();
  }

  private void assignKey() {
    key = new Key(getWrapped(), getLevel(), attributesHash);
  }

  /**
   * This method assigns the Nodes that represent the model elements associated to the wrapped one
   * and sets in level the number of referenced objects.
   * @param modelToNodeMap non-null
   */
  public void assignAssociatedNodes(Map<V, Node<V>> modelToNodeMap) {
    Iterator<String> associationsIterator;
    String association;
    Collection<V> associated;
    Node<V> associatedNode;

    associationsIterator = criteria.getAssociations(getWrapped()).iterator();
    while ( associationsIterator.hasNext() ) {
      association = associationsIterator.next();

      try {
        associated = (Collection<V>) Navigator.collectValues( wrapped,
                                                              association,
                                                              LOGGER.isLoggable( Level.FINE ));

        for (V associatedModelElement: associated ) {
          associatedNode = modelToNodeMap.get( associatedModelElement );

          assert associatedNode != null
                : "Expected a node bound for object "+PRINT_MODEL_ELEMENT.execute(associatedModelElement);

          level++;

          associatedNode.referers.add(this);
        }
        referredModelElements.put( association, associated );
      } catch (Exception ex) {
        LOGGER.log( Level.FINE,
                    "Model element: {0} does not support association: {1}",
                    new Object[]{PRINT_MODEL_ELEMENT.execute(getWrapped()), association});
      }
    }
  }

  /**
   * Find all ready nodes in this topology, that are equal to the provided node (from the other topology)
   * @param equivalenceClasses not null collected so far matches/classes of equivalence
   * @param nodeToMatch not null node to match
   * @param generationReady TODO
   * @return non-null list of all ready nodes in this topology, that match the provided node. The provided
   *         nodes are not mapped in the equivalence classes map, as they are found among the ready nodes,
   *         mapped in equivalence classes map only when they are reported in this result.
   */
  public static <V extends RefObject> List<Node<V>> findReadyMatches(EquivalenceClassesMap<V> equivalenceClasses,
                                                                     Node<V> nodeToMatch,
                                                                     List<Node<V>> generationReady) {
    List<Node<V>> result;

    result = new ArrayList<Node<V>>();

    assert nodeToMatch.isReady() : "Expected a ready node " + nodeToMatch;

    for (Node<V> target : generationReady) {
      if ( nodeToMatch.getKey().equals(target.getKey())
           && nodeToMatch.matches( target, equivalenceClasses ) ) {
        result.add( target );
      }
    }
    return result;
  }

  /**
   * Two nodes match if and only if their attribute values are equal and they are associated to
   * already matched nodes.
   * Treat the nodes with no attributes and associations as not comparable (different).
   * PRE-CONDITION:
   *   getLevel() == 0
   *   otherNode.getLeve() == 0
   *
   * @param otherNode is a non-null node whose wrapped element is of the same metaclass as of this one
   * @param equivalenceClasses holds the already matched pairs, which classes never are replaced or deleted (i.e. only new classes may be added)
   * @return true if this matches to the other node
   */
  private boolean matches(Node otherNode, EquivalenceClassesMap<V> equivalenceClasses) {
    boolean result;
    Map.Entry<String, List<String>> entry;
    Iterator<Map.Entry<String, List<String>>> attributeEntriesIterator;
    Iterator<String> associationNamesIterator;
    String thisAssociationName;
    Object thisAttributeValue;
    Object otherAttributeValue;
    Set<V> theseMappedRepersentatives;
    Set<V> otherRepersentatives;

    assert getLevel() == 0 : "Expeceted only level 0 objects are matched from, instead of "+getLevel();
    assert otherNode.getLevel() == 0 : "Expeceted only level 0 objects are matched to, instead of "+otherNode.getLevel();

    result = this.attributesHash == otherNode.attributesHash;

    // compare attributes
    attributeEntriesIterator = attributeValues.entrySet().iterator();
    while ( result && attributeEntriesIterator.hasNext() ) {
      entry = attributeEntriesIterator.next();

      thisAttributeValue = entry.getValue(); // not null
      otherAttributeValue= otherNode.attributeValues.get( entry.getKey() );

      result = thisAttributeValue.equals( otherAttributeValue );
    }

    // compare associations - make sure that the nodes refer the same matched objects
    associationNamesIterator = referredModelElements.keySet().iterator();
    while ( result && associationNamesIterator.hasNext() ) {
      thisAssociationName = associationNamesIterator.next();

      theseMappedRepersentatives= equivalenceClasses.getMappedRepresentatives( this.referredModelElements( thisAssociationName ));
      otherRepersentatives = equivalenceClasses.getRepresentatives( otherNode.referredModelElements( thisAssociationName ));

      result = theseMappedRepersentatives.equals( otherRepersentatives );
    }
    return result;
  }

  /**
   * @param associationName non-null association name
   * @return non-null set of model elements, this wrapped element refers in the association with the provided name
   */
  private Collection<V> referredModelElements(String associationName) {
    Collection<V> result;

    result = referredModelElements.get( associationName );
    if (result == null) {
      result = Collections.EMPTY_SET;
    }
    return result;
  }

  /**
   * Decreases the number of references this
   */
  public void decreaseLevel() {
    level--;
    assert level >= 0 : "Expected a non-negative number of objects this refers to";

    assignKey();
  }

  /**
   * @return the list of all nodes
   */
  public final List<Node<V>> getReferers() {
    return referers;
  }


  private int getLevel() {
    return level;
  }

  public final boolean isReady() {
    return level == 0;
  }

  public final V getWrapped() {
    return wrapped;
  }

  final Key getKey() {
    return key;
  }

  public String toString() {
    StringBuilder result = new StringBuilder();

    result.append("Node ")
          .append(level)
          .append(" ")
          .append(new PrintElementRestricted("  ",criteria).execute(wrapped));
    return result.toString();
  }

  /**
   * Convert the list of nodes to a list of their wrapped objects
   * @param nodes not null
   * @return not null list of the wrapped objects in the elements of the nodes
   */
  public static <V extends RefObject> Collection<V> unwrap(List<Node<V>> nodes) {
    Collection<V> result;

    result = new ArrayList<>();
    for (Node<V> node : nodes) {
      result.add( node.getWrapped() );
    }
    return result;
  }
}