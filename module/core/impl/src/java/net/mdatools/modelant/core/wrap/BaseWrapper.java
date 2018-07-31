/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.wrap;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import net.mdatools.modelant.core.api.wrap.Wrapper;
import net.mdatools.modelant.core.api.wrap.WrapperFactory;


/**
 * <b>This class is the root class for all wrapper classes.</b><br>
 * INVARIANT:<ul>
 * <li> the wrapped object is never null
 * <li> the factory object is never null
 * <li> the factory is exactly one that created this object
 * </ul>
 * Additional methods (not related actually to the wrapping function of this class) are added to ease the
 * use of this class for code generation.
 * @author Rusi Popov
 */
public abstract class BaseWrapper<A> implements Serializable, Wrapper<A> {

  /**
   * The object this wrapper class wraps to allow its rendering. The reflective
   * interface of the model element is used in oder to make this class
   * independent of the actual model and interface.
   */
  private final A wrapped;

  /**
   * This is the factory the subclasses must use to build wrappers of
   * the model objects reachable through the current wrapped model object.
   * It makes sure the proper class is instantiated reflecting the actual
   * interface of the model object.
   */
  private final WrapperFactory factory;

  /**
   * @param wrapped is non-null object to wrap
   * @param factory is the non-null factory that created this
   */
  protected BaseWrapper(A wrapped, WrapperFactory factory) {

    assert wrapped != null : "Expected a non-null wrapped object";
    assert factory != null : "Expected a non-null factory";

    this.wrapped = wrapped;
    this.factory = factory;
  }


  /**
   * CONVENTION:<ul>
   * <li> when the wrapper is used to create a new wrapped object, it might happen that this method needs public access.
   * <li> when wrapping a wrapper, this method is overridden, this way granting the outer-most wrapper
   *      access to the nested-most wrapped object (bypassing the wrappers)
   * </ul>
   * @return the deepest wrapped object
   */
  public final A getWrapped() {
    return wrapped;
  }

  /**
   * The factory for this instance
   * @return the wrapper factory this was constructed with
   */
  protected final WrapperFactory getFactory() {
    return factory;
  }

  /**
   * Call this method in subclasses in order to instantiate the wrapper class
   * that actually corresponds to the class of the object toWrap
   * @param toWrap is the object to wrap in another wrapper class. Might be null;
   * @return null, when null provided, otherwise a properly initialized
   *         wrapper class that wraps toWrap
   * @throws IllegalArgumentException when mapping is not possible
   * @see Factories#wrap(Object)
   */
  public final Wrapper<A> wrap(A toWrap) throws IllegalArgumentException {
    return factory.wrap( toWrap );
  }

  /**
   * Call this method in subclasses in order to instantiate the wrapper class
   * that actually corresponds to the class of the object toWrap
   * @param toWrap is the object to wrap in another wrapper class. Might be null;
   * @return null, when null provided, otherwise a properly initialized
   *         collection of wrappers that wrap the elements of toWrap
   * @throws IllegalArgumentException when mapping is not possible
   * @see Factories#wrap(Collection)
   */
  public final List<Wrapper<A>> wrap(Collection<A> toWrap) throws IllegalArgumentException {
    return factory.wrap( toWrap );
  }
}