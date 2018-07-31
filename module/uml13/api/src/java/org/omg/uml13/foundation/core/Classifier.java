package org.omg.uml13.foundation.core;

/**
 * Classifier object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Classifier extends org.omg.uml13.foundation.core.GeneralizableElement, org.omg.uml13.foundation.core.Namespace {
    /**
     * Returns the value of reference feature.
     * @return Value of reference feature. Element type: {@link org.omg.uml13.foundation.core.Feature}
     */
    public java.util.List/*<org.omg.uml13.foundation.core.Feature>*/ getFeature();
    /**
     * Returns the value of reference participant.
     * @return Value of reference participant. Element type: {@link org.omg.uml13.foundation.core.AssociationEnd}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.AssociationEnd>*/ getParticipant();
    /**
     * Returns the value of reference powertypeRange.
     * @return Value of reference powertypeRange. Element type: {@link org.omg.uml13.foundation.core.Generalization}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.Generalization>*/ getPowertypeRange();
}
