package org.omg.uml14.core;

/**
 * Method object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Method extends org.omg.uml14.core.BehavioralFeature {
    /**
     * Returns the value of attribute body.
     * @return Value of attribute body.
     */
    public org.omg.uml14.datatypes.ProcedureExpression getBody();
    /**
     * Sets the value of body attribute. See {@link #getBody} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setBody(org.omg.uml14.datatypes.ProcedureExpression newValue);
    /**
     * Returns the value of reference specification.
     * @return Value of reference specification.
     */
    public org.omg.uml14.core.Operation getSpecification();
    /**
     * Sets the value of reference specification. See {@link #getSpecification} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setSpecification(org.omg.uml14.core.Operation newValue);
}
