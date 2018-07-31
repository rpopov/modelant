package org.omg.uml13.behavioralelements.commonbehavior;

/**
 * Instance object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Instance extends org.omg.uml13.foundation.core.ModelElement {
    /**
     * Returns the value of reference classifier.
     * @return Value of reference classifier. Element type: {@link org.omg.uml13.foundation.core.Classifier}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.Classifier>*/ getClassifier();
    /**
     * Returns the value of reference attributeLink.
     * @return Value of reference attributeLink. Element type: {@link org.omg.uml13.behavioralelements.commonbehavior.AttributeLink}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.commonbehavior.AttributeLink>*/ getAttributeLink();
    /**
     * Returns the value of reference linkEnd.
     * @return Value of reference linkEnd. Element type: {@link org.omg.uml13.behavioralelements.commonbehavior.LinkEnd}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.commonbehavior.LinkEnd>*/ getLinkEnd();
    /**
     * Returns the value of reference slot.
     * @return Value of reference slot. Element type: {@link org.omg.uml13.behavioralelements.commonbehavior.AttributeLink}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.commonbehavior.AttributeLink>*/ getSlot();
    /**
     * Returns the value of reference stimulus1.
     * @return Value of reference stimulus1. Element type: {@link org.omg.uml13.behavioralelements.commonbehavior.Stimulus}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.commonbehavior.Stimulus>*/ getStimulus1();
    /**
     * Returns the value of reference stimulus2.
     * @return Value of reference stimulus2. Element type: {@link org.omg.uml13.behavioralelements.commonbehavior.Stimulus}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.commonbehavior.Stimulus>*/ getStimulus2();
    /**
     * Returns the value of reference stimulus3.
     * @return Value of reference stimulus3. Element type: {@link org.omg.uml13.behavioralelements.commonbehavior.Stimulus}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.commonbehavior.Stimulus>*/ getStimulus3();
    /**
     * Returns the value of reference componentInstance.
     * @return Value of reference componentInstance.
     */
    public org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance getComponentInstance();
    /**
     * Sets the value of reference componentInstance. See {@link #getComponentInstance} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setComponentInstance(org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance newValue);
}
