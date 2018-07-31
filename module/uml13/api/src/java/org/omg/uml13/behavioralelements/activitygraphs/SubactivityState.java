/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.activitygraphs;

/**
 * SubactivityState object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface SubactivityState extends org.omg.uml13.behavioralelements.statemachines.SubmachineState {
    /**
     * Returns the value of attribute isDynamic.
     * @return Value of attribute isDynamic.
     */
    public boolean isDynamic();
    /**
     * Sets the value of isDynamic attribute. See {@link #isDynamic} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setDynamic(boolean newValue);
    /**
     * Returns the value of attribute dynamicArguments.
     * @return Value of attribute dynamicArguments.
     */
    public org.omg.uml13.foundation.datatypes.ArgListsExpression getDynamicArguments();
    /**
     * Sets the value of dynamicArguments attribute. See {@link #getDynamicArguments} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setDynamicArguments(org.omg.uml13.foundation.datatypes.ArgListsExpression newValue);
    /**
     * Returns the value of attribute dynamicMultiplicity.
     * @return Value of attribute dynamicMultiplicity.
     */
    public org.omg.uml13.foundation.datatypes.Multiplicity getDynamicMultiplicity();
    /**
     * Sets the value of dynamicMultiplicity attribute. See {@link #getDynamicMultiplicity} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setDynamicMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity newValue);
}
