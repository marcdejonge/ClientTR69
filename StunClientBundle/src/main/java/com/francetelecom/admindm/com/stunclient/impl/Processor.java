/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.impl;
import net.java.stun4j.message.Response;
/**
 * <p>
 * This interface specifies a STUN Expected Response.
 * </p>
 * 
 * @author mpcy8647
 * 
 */
public interface Processor {
    /**
     * <p>
     * This method is invoked by the {@link MessageProcessor} when the
     * response of a previous Binding request is received.
     * </p>
     */
    public void process(Response response);
}
