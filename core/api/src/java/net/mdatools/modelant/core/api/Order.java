/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
