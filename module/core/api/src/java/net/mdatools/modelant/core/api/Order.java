/*
 * Copyright (c) i:FAO AG 2014. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 28.04.2014
 */
package net.mdatools.modelant.core.api;

import java.util.Comparator;

import javax.jmi.reflect.RefBaseObject;

/**
 * Comparator of model and metamodel elements (instances) so that they can be ordered
 * according specific criteria.
 * @author Rusi Popov
 */
public interface Order extends Comparator<RefBaseObject> {

}
