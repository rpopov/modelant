/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.statemachines;

/**
 * Guard object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Guard extends org.omg.uml13.foundation.core.ModelElement {
    /**
     * Returns the value of attribute expression.
     * @return Value of attribute expression.
     */
    public org.omg.uml13.foundation.datatypes.BooleanExpression getExpression();
    /**
     * Sets the value of expression attribute. See {@link #getExpression} for 
     * description on the attribute.
     * @param newValue New value to be set.
     */
    public void setExpression(org.omg.uml13.foundation.datatypes.BooleanExpression newValue);
    /**
     * Returns the value of reference transition.
     * @return Value of reference transition.
     */
    public org.omg.uml13.behavioralelements.statemachines.Transition getTransition();
    /**
     * Sets the value of reference transition. See {@link #getTransition} for 
     * description on the reference.
     * @param newValue New value to be set.
     */
    public void setTransition(org.omg.uml13.behavioralelements.statemachines.Transition newValue);
}
