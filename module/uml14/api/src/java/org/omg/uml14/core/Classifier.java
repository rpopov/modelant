package org.omg.uml14.core;

/**
 * Classifier object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Classifier extends org.omg.uml14.core.GeneralizableElement, org.omg.uml14.core.Namespace {
    /**
     * Returns the value of reference feature.
     * @return Value of reference feature. Element type: {@link org.omg.uml14.core.Feature}
     */
    public java.util.List/*<org.omg.uml14.core.Feature>*/ getFeature();
    /**
     * Returns the value of reference powertypeRange.
     * @return Value of reference powertypeRange. Element type: {@link org.omg.uml14.core.Generalization}
     */
    public java.util.Collection/*<org.omg.uml14.core.Generalization>*/ getPowertypeRange();
}
