package org.omg.uml13.behavioralelements.commonbehavior;

/**
 * Signal object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Signal extends org.omg.uml13.foundation.core.Classifier {
    /**
     * Returns the value of reference reception.
     * @return Value of reference reception. Element type: {@link org.omg.uml13.behavioralelements.commonbehavior.Reception}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.commonbehavior.Reception>*/ getReception();
    /**
     * Returns the value of reference context.
     * @return Value of reference context. Element type: {@link org.omg.uml13.foundation.core.BehavioralFeature}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.BehavioralFeature>*/ getContext();
    /**
     * Returns the value of reference sendAction.
     * @return Value of reference sendAction. Element type: {@link org.omg.uml13.behavioralelements.commonbehavior.SendAction}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.commonbehavior.SendAction>*/ getSendAction();
}
