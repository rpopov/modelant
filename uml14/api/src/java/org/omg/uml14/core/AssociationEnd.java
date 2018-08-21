/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * AssociationEnd object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface AssociationEnd extends org.omg.uml14.core.ModelElement {
    /**
     * Returns the value of attribute isNavigable.
     * @return Value of attribute isNavigable.
     */
    public boolean isNavigable();
    /**
     * Sets the value of isNavigable attribute. See {@link #isNavigable} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setNavigable(boolean newValue);
    /**
     * Returns the value of attribute ordering.
     * @return Value of attribute ordering.
     */
    public org.omg.uml14.datatypes.OrderingKind getOrdering();
    /**
     * Sets the value of ordering attribute. See {@link #getOrdering} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setOrdering(org.omg.uml14.datatypes.OrderingKind newValue);
    /**
     * Returns the value of attribute aggregation.
     * @return Value of attribute aggregation.
     */
    public org.omg.uml14.datatypes.AggregationKind getAggregation();
    /**
     * Sets the value of aggregation attribute. See {@link #getAggregation} for 
     * description on the attribute.
     * @param newValue New value to be set.
     */
    public void setAggregation(org.omg.uml14.datatypes.AggregationKind newValue);
    /**
     * Returns the value of attribute targetScope.
     * @return Value of attribute targetScope.
     */
    public org.omg.uml14.datatypes.ScopeKind getTargetScope();
    /**
     * Sets the value of targetScope attribute. See {@link #getTargetScope} for 
     * description on the attribute.
     * @param newValue New value to be set.
     */
    public void setTargetScope(org.omg.uml14.datatypes.ScopeKind newValue);
    /**
     * Returns the value of attribute multiplicity.
     * @return Value of attribute multiplicity.
     */
    public org.omg.uml14.datatypes.Multiplicity getMultiplicity();
    /**
     * Sets the value of multiplicity attribute. See {@link #getMultiplicity} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setMultiplicity(org.omg.uml14.datatypes.Multiplicity newValue);
    /**
     * Returns the value of attribute changeability.
     * @return Value of attribute changeability.
     */
    public org.omg.uml14.datatypes.ChangeableKind getChangeability();
    /**
     * Sets the value of changeability attribute. See {@link #getChangeability} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setChangeability(org.omg.uml14.datatypes.ChangeableKind newValue);
    /**
     * Returns the value of reference association.
     * @return Value of reference association.
     */
    public org.omg.uml14.core.UmlAssociation getAssociation();
    /**
     * Sets the value of reference association. See {@link #getAssociation} for 
     * description on the reference.
     * @param newValue New value to be set.
     */
    public void setAssociation(org.omg.uml14.core.UmlAssociation newValue);
    /**
     * Returns the value of reference qualifier.
     * @return Value of reference qualifier. Element type: {@link org.omg.uml14.core.Attribute}
     */
    public java.util.List/*<org.omg.uml14.core.Attribute>*/ getQualifier();
    /**
     * Returns the value of reference participant.
     * @return Value of reference participant.
     */
    public org.omg.uml14.core.Classifier getParticipant();
    /**
     * Sets the value of reference participant. See {@link #getParticipant} for 
     * description on the reference.
     * @param newValue New value to be set.
     */
    public void setParticipant(org.omg.uml14.core.Classifier newValue);
    /**
     * Returns the value of reference specification.
     * @return Value of reference specification. Element type: {@link org.omg.uml14.core.Classifier}
     */
    public java.util.Collection/*<org.omg.uml14.core.Classifier>*/ getSpecification();
}
