/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.wrap;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Any wrapper of instances of <b>A</b>, used for compile time control of objects' wrapping
 * @param <A> the wrapped type
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface Wrapper<A> {

  /**
   * A common logger for all wrappers
   */
  Logger LOGGER = Logger.getLogger( Wrapper.class.getName() );


  /**
   * CONVENTION:<ul>
   * <li> when the wrapper is used to create a new wrapped object, it might happen that this method needs public access.
   * <li> when wrapping a wrapper, this method is overridden, this way granting the outer-most wrapper
   *      access to the nested-most wrapped object (bypassing the wrappers)
   * </ul>
   * @return the deepest wrapped object
   */
  A getWrapped();

  /**
   * Call this method in subclasses in order to instantiate the wrapper class
   * that actually corresponds to the class of the object toWrap
   * @param toWrap is the object to wrap in another wrapper class. Might be null;
   * @return null, when null provided, otherwise a properly initialized
   *         wrapper class that wraps toWrap
   * @throws IllegalArgumentException when mapping is not possible
   */
  Wrapper<A> wrap(A toWrap) throws IllegalArgumentException;

  /**
   * Call this method in subclasses in order to instantiate the wrapper class
   * that actually corresponds to the class of the object toWrap
   * @param toWrap is the object to wrap in another wrapper class. Might be null;
   * @return null, when null provided, otherwise a properly initialized
   *         collection of wrappers that wrap the elements of toWrap
   * @throws IllegalArgumentException when mapping is not possible
   */
 List<Wrapper<A>> wrap(Collection<A> toWrap) throws IllegalArgumentException;
}
