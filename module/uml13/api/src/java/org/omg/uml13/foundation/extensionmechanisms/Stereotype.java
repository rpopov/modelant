package org.omg.uml13.foundation.extensionmechanisms;

/**
 * Stereotype object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Stereotype extends org.omg.uml13.foundation.core.GeneralizableElement {
    /**
     * Returns the value of attribute icon.
     * @return Value of attribute icon.
     */
    public java.lang.String getIcon();
    /**
     * Sets the value of icon attribute. See {@link #getIcon} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setIcon(java.lang.String newValue);
    /**
     * Returns the value of attribute baseClass.
     * @return Value of attribute baseClass.
     */
    public java.lang.String getBaseClass();
    /**
     * Sets the value of baseClass attribute. See {@link #getBaseClass} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setBaseClass(java.lang.String newValue);
    /**
     * Returns the value of reference requiredTag.
     * @return Value of reference requiredTag. Element type: {@link org.omg.uml13.foundation.extensionmechanisms.TaggedValue}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.extensionmechanisms.TaggedValue>*/ getRequiredTag();
    /**
     * Returns the value of reference extendedElement.
     * @return Value of reference extendedElement. Element type: {@link org.omg.uml13.foundation.core.ModelElement}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.ModelElement>*/ getExtendedElement();
    /**
     * Returns the value of reference stereotypeConstraint.
     * @return Value of reference stereotypeConstraint. Element type: {@link org.omg.uml13.foundation.core.Constraint}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.Constraint>*/ getStereotypeConstraint();
}
