package org.omg.uml14.core;

/**
 * TaggedValue object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface TaggedValue extends org.omg.uml14.core.ModelElement {
    /**
     * Returns the value of attribute dataValue.
     * @return Value of dataValue attribute. Element type: {@link java.lang.String}
     */
    public java.util.Collection/*<java.lang.String>*/ getDataValue();
    /**
     * Returns the value of reference modelElement.
     * @return Value of reference modelElement.
     */
    public org.omg.uml14.core.ModelElement getModelElement();
    /**
     * Sets the value of reference modelElement. See {@link #getModelElement} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setModelElement(org.omg.uml14.core.ModelElement newValue);
    /**
     * Returns the value of reference type.
     * @return Value of reference type.
     */
    public org.omg.uml14.core.TagDefinition getType();
    /**
     * Sets the value of reference type. See {@link #getType} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setType(org.omg.uml14.core.TagDefinition newValue);
    /**
     * Returns the value of reference referenceValue.
     * @return Value of reference referenceValue. Element type: {@link org.omg.uml14.core.ModelElement}
     */
    public java.util.Collection/*<org.omg.uml14.core.ModelElement>*/ getReferenceValue();
}
