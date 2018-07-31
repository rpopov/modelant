package org.omg.uml14.statemachines;

/**
 * SignalEvent object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface SignalEvent extends org.omg.uml14.statemachines.Event {
    /**
     * Returns the value of reference signal.
     * @return Value of reference signal.
     */
    public org.omg.uml14.commonbehavior.Signal getSignal();
    /**
     * Sets the value of reference signal. See {@link #getSignal} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setSignal(org.omg.uml14.commonbehavior.Signal newValue);
}
