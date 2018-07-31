/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 4.03.2018 г.
 */
package net.mdatools.modelant.core.operation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.diff.ModelDifference;
import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.core.operation.element.PrintModelElement;
import net.mdatools.modelant.core.util.Navigator;
import net.mdatools.modelant.core.util.map.MapToList;

/**
 * A single model element added or deleted to a model
 */
class ModelDifferenceImpl implements ModelDifference {
  /**
   * This is a common logger
   */
  private static final Logger LOGGER = Logger.getLogger( ModelDifferenceImpl.class.getPackage().getName() );

  /**
   * The model element this change consists of
   * Not null
   */
  private final RefObject wrapped;

  /**
   * The nested differences
   */
  private final MapToList<String, ModelDifference> associations = new MapToList<>();


  public ModelDifferenceImpl(RefObject element) {
    this.wrapped = element;
  }


  /**
   * @see net.mdatools.modelant.core.api.diff.ModelDifference#getElement()
   */
  public RefObject getElement() {
    return wrapped;
  }


  /**
   * @see net.mdatools.modelant.core.api.diff.ModelDifference#getAssociations()
   */
  public Map<String, Collection<ModelDifference>> getAssociations() {
    return associations.toMap();
  }


  /**
   * @param all non-null list of added or deleted model elements to be reconstructed as model differences
   * @param matchingCriteria not null
   * @return non-null collection of model differences nested according to the matching criteria
   */
  public static List<ModelDifference> findModelDifferences(List<RefObject> all,
                                                           MatchingCriteria matchingCriteria) {
    List<ModelDifference> result;
    Map<RefObject, ModelDifferenceImpl> mapElementDifference;
    boolean isRoot;

    mapElementDifference = new HashMap<>();

    for (RefObject element: all) {
      mapElementDifference.put( element, new ModelDifferenceImpl(element) );
    }

    result = new ArrayList<>();
    for (ModelDifferenceImpl difference : mapElementDifference.values() ) {
      isRoot = difference.bindToParentDifferences( mapElementDifference, matchingCriteria );

      if ( isRoot ) { // this is a top-level / not nested difference
        result.add( difference );
      }
    }
    return result;
  }


  /**
   * Bind the difference to the other differences from mapElementDifference as a nested one, according to
   * the matchingCriteria
   * @param mapElementDifference not null
   * @param matchingCriteria not null
   * @return true if the difference has no parent difference(s)
   */
  private boolean bindToParentDifferences(Map<RefObject, ModelDifferenceImpl> mapElementDifference,
                                          MatchingCriteria matchingCriteria) {
    boolean result;

    Iterator<String> associationsIterator;
    String association;
    Collection<RefObject> associated;
    ModelDifferenceImpl parent;

    result = true;

    associationsIterator = matchingCriteria.getAssociations(getElement()).iterator();
    while ( associationsIterator.hasNext() ) {
      association = associationsIterator.next();

      try {
        associated = (Collection<RefObject>) Navigator.collectValues( getElement(),
                                                                      association,
                                                                      LOGGER.isLoggable( Level.FINE ));
        for (RefObject associatedModelElement: associated ) {
          parent = mapElementDifference.get( associatedModelElement );

          if ( parent != null ) {
            result = false;
            parent.add( association, this );
          }
        }
      } catch (Exception ex) {
        LOGGER.log( Level.FINE,
                    "Model element: {0} does not support association: {1}",
                    new Object[]{ModelComparisonResultImpl.PRINT_MODEL_ELEMENT.execute(getElement()), association});
      }
    }
    return result;
  }


  private void add(String association, ModelDifference difference) {
    associations.put( association, difference);
  }


  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder result = new StringBuilder(128);

    result.append( "{\r\n element: " )
          .append( new PrintModelElement().execute( wrapped ) );
    if ( !associations.isEmpty() ) {
      for (Map.Entry<String, Collection<ModelDifference>> entry: associations.entrySet()) {
        result.append( ",\r\n being " )
              .append( entry.getKey() )
              .append( " of:" )
              .append( entry.getValue() );
      }
    }
    result.append( "\r\n}" );

    return result.toString();
  }
}