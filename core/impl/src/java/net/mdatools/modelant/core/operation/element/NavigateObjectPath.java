/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.element;

import java.util.Arrays;
import java.util.List;

import javax.jmi.reflect.InvalidNameException;
import javax.jmi.reflect.RefFeatured;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Starting from an object navigate a path of associations do some processing at the end of the path
 * <pre>
 * Format:
 *    empty
 * |  asssociationToOneName{.asssociationToOneName}
 * | [asssociationToOneName{.asssociationToOneName}.](associationToMany | attributeName)
 *
 *  associationToOneName = name
 *                         | METAOBJECT
 *                         | METACLASSNAME
 *                         | IPACKAGE
 *                         | OPACKAGE
 *
 *  associationToManyName = name
 *
 *  attributeName = name
 *                  | MOFID
 * where:
 *   name is the name of an association in *-to-one multiplicity. In the case when there is no attribute name,
 *     the last used name can be an association *-to-many
 *   MOFID refers the MOF ID of the object to process
 *   METAOBJECT retrieves the meta-object of the object to process. This allows reflective navigation.
 *   METACLASSNAME retrieves the qualified (in the meta-model) name of the meta-class for the processed object.
 *   IPACKAGE retrieves the immediate package in the meta-model the processed object is in
 *   OPACKAGE retrieves the outer-most package (the extent) where object processed is in
 *
 * </pre>
 * @author Rusi Popov (popovr@mdatools.net)
 */
public abstract class NavigateObjectPath<R> implements Function<RefFeatured, R> {

  private static final PrintModelElement PRINT_MODEL_ELEMENT = new PrintModelElement();


  private String[] parsedPath;

  /**
   * @param path a non-null path following *-to-one associations and ending optionally with attribute or *-to-many association
   */
  protected NavigateObjectPath(String path) {
    assert path != null : "Expected a non-null path provided";
    parsedPath = path.split( Navigator.OBJECT_PATH_SEPARATORS );
  }


  /**
   * @param path a non-null path following *-to-one associations and ending optionally with attribute or *-to-many association
   */
  protected NavigateObjectPath(String[] path) {
    assert path != null : "Expected a non-null path provided";
    parsedPath = path;
  }


  /**
   * @param start non-null object to start naviagtion from
   * @return the object processed
   * @throws RuntimeException
   * @throws IllegalArgumentException
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public final R execute(final RefFeatured start) throws RuntimeException, IllegalArgumentException {
    R result;
    Object current;
    Object associated;
    String itemName;

    if (start == null) {
      throw new IllegalArgumentException("Expected non-null object to start navigating from down the path "
                                         +Arrays.asList(parsedPath));
    }

    if (parsedPath.length == 0) { // empty path
      result = processEmptyPath(start);

    } else { // non-empty path to navigate
      result = null;
      current = start;

      for (int i=0; i< parsedPath.length; i++) { // INVARIANT: current instanceof RefFEatured
        itemName = parsedPath[i];

        try {
          associated = Navigator.getReflectiveValue( current, itemName );
        } catch (Exception ex) {
          throw new IllegalArgumentException("Starting from "+ PRINT_MODEL_ELEMENT.execute( start )
                                          + " down the path: " + pathUpTo( i )
                                          + " reached " + PRINT_MODEL_ELEMENT.execute( current )
                                          + " reading '"+itemName
                                          + "' on which caused ", ex);
        }

        if ( i < parsedPath.length-1 ) { // there are still associations to go through

          if (!(associated instanceof RefFeatured)) {
            throw new IllegalArgumentException(
                                            "Starting from "+ PRINT_MODEL_ELEMENT.execute( start )
                                            + " down the path: " + pathUpTo( i )
                                            + " reached " + PRINT_MODEL_ELEMENT.execute( current )
                                            + " for which accessing '"+itemName
                                            + "' produced "+PRINT_MODEL_ELEMENT.execute( associated )
                                            + " instead of the expected RefFEatured instance");
          }
          current = associated;

        } else { // the last association/attribute to set

          try {
            if ( associated instanceof RefFeatured ) {
              result = processLast(start, (RefFeatured) current, itemName, (RefFeatured) associated);
            } else {
              result = processLast(start, (RefFeatured) current, itemName, associated);
            }
          } catch (Exception ex) {
            throw new IllegalArgumentException("Starting from "+ PRINT_MODEL_ELEMENT.execute( start )
                                            + " down the path: " + pathUpTo( i )
                                            + " reached " + PRINT_MODEL_ELEMENT.execute( current )
                                            + " for which processing '"+itemName
                                            + "' caused ", ex);
          }
        }
      }
    }
    return result;
  }

  /**
   * @param i >=0
   * @return the path up to i-th element of paresedPath, excluding it
   */
  private List<String> pathUpTo(int i) {
    return Arrays.asList(Arrays.copyOf( parsedPath,i));
  }

  /**
   * Processing an EMPTY navigation path
   * @param start not null object the navigation started from
   * @return operation result
   */
  protected abstract R processEmptyPath(RefFeatured start);


  /**
   * Processing of the final association *-to-ONE in the path
   * @param start not null object the navigation started from
   * @param current not null object reached down the path EXCEPT the last name in that path
   * @param itemName the non-null, non-empty last name in the path, which is an association *-to-ONE,
   *        already validated as accessible through {@link Navigator#getReflectiveValue(Object, String)}
   * @param associated the reached last associated to current model element in the association itemName
   * @return operation result
   */
  protected abstract R processLast(RefFeatured start, RefFeatured current, String itemName, RefFeatured associated);

  /**
   * Processing of the final attribute or association *-to-MANY in the path
   * @param start not null object the navigation started from
   * @param value the attribute value or association *-to-MANY reached at the end of the path
   * @param itemName the non-null, non-empty last name in the path, which is an association *-to-MANY or attribute name,
   *        already validated as accessible through {@link Navigator#getReflectiveValue(Object, String)}
   * @return operation result
   */
  protected abstract R processLast(RefFeatured start, RefFeatured current, String itemName, Object value);
}
