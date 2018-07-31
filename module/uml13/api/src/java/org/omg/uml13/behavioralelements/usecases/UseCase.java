package org.omg.uml13.behavioralelements.usecases;

/**
 * UseCase object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface UseCase extends org.omg.uml13.foundation.core.Classifier {
    /**
     * Returns the value of reference extend.
     * @return Value of reference extend. Element type: {@link org.omg.uml13.behavioralelements.usecases.Extend}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.usecases.Extend>*/ getExtend();
    /**
     * Returns the value of reference extend2.
     * @return Value of reference extend2. Element type: {@link org.omg.uml13.behavioralelements.usecases.Extend}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.usecases.Extend>*/ getExtend2();
    /**
     * Returns the value of reference include.
     * @return Value of reference include. Element type: {@link org.omg.uml13.behavioralelements.usecases.Include}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.usecases.Include>*/ getInclude();
    /**
     * Returns the value of reference include2.
     * @return Value of reference include2. Element type: {@link org.omg.uml13.behavioralelements.usecases.Include}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.usecases.Include>*/ getInclude2();
    /**
     * Returns the value of reference extensionPoint.
     * @return Value of reference extensionPoint. Element type: {@link org.omg.uml13.behavioralelements.usecases.ExtensionPoint}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.usecases.ExtensionPoint>*/ getExtensionPoint();
}
