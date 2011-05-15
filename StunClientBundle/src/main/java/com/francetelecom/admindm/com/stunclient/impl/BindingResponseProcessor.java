/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.impl;
import net.java.stun4j.attribute.Attribute;
import net.java.stun4j.attribute.MappedAddressAttribute;
import net.java.stun4j.message.Response;
import com.francetelecom.admindm.api.Log;
/**
 * <p>
 * This class is used to process a STUN response message received through the
 * primary socket.
 * </p>
 * @author mpcy8647
 * 
 */
public class BindingResponseProcessor implements Processor {
    /** associated stun client */
    private final STUNClient stunClient;
    public BindingResponseProcessor(STUNClient client) {
        this.stunClient = client;
    }
    /**
     * <p>
     * Treat the message response.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>extracts from the {@link Response} object, the
     * {@link MappedAddressAttribute} attribute.</li>
     * <li>from this attribute, extracts the address and the port.</li>
     * <li>send these two values to the {@link STUNClient} object.</li>
     * </ul>
     * 
     */
    public void process(Response response) {
        Log.debug("process response with the BindingResponseProcessor");
        // get the MappedAttribute
        MappedAddressAttribute mappedAttribute =
                (MappedAddressAttribute) response
                        .getAttribute(Attribute.MAPPED_ADDRESS);
        // extract address and port
        String mappedAddress =
                mappedAttribute.getAddress().getSocketAddress().getAddress()
                        .getHostAddress();
        int mappedPort =
                mappedAttribute.getAddress().getSocketAddress().getPort();
        // set the last mapped address
        this.stunClient
                .setLastReceivedMappedAddress(mappedAddress, mappedPort);
    }
}
