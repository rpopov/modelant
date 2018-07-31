/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 22.04.2018 г.
 */
package net.mdatools.modelant.core.api;

import java.util.Collection;

/**
 * Filter the collection result of a selector and produce a sub-collection of it
 * @param <T> the type of the argument&result collections' contents
 * @author Rusi Popov
 */
public interface Filter<T> extends Selector<Collection<T>, T> {
}
