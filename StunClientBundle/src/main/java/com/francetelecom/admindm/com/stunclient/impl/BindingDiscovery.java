/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.impl;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import net.java.stun4j.StunException;
import net.java.stun4j.message.MessageFactory;
import net.java.stun4j.message.Request;
import net.java.stun4j.message.Response;
import net.java.stun4j.stack.TransactionID;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.com.stunclient.attribute.ConnectionRequestBindingAttribute;
/**
 * The Class BindingDiscovery.
 * @author mpcy8647
 */
public class BindingDiscovery implements Processor {
    /** stun client. */
    private final STUNClient stunClient;
    /** stun server address. */
    private final String stunServerAddress;
    /** stun server port. */
    private final int stunServerPort;
    /**
     * Instantiates a new binding discovery.
     * @param stunClient the stun client
     * @param serverAddress the server address
     * @param serverPort the server port
     */
    public BindingDiscovery(STUNClient stunClient, String serverAddress,
            int serverPort) {
        this.stunClient = stunClient;
        this.stunServerAddress = serverAddress;
        this.stunServerPort = serverPort;
        // send a binding request
        sendBindingRequest();
    }
    /**
     * Process.
     * @param response the response
     */
    public synchronized void process(Response response) {
        stunClient.startBindingMaintainer();
    }
    /**
     * <p>
     * Send a periodic binding Request.
     * </p>
     * <p>
     * These binding requests contains the following attributes:
     * <ul>
     * <li>the CONNECTION-REQUEST-BINDING attribute.</li>
     * <li>the USERNAME attribute if the IGD.ManagementServer.STUNUsername
     * parameter is set.</li>
     * </ul>
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>create a BindingRequest.</li>
     * <li>Add to it the CONNECTION-REQUEST-BINDING attribute.</li>
     * <li>Add to it the USERNAME attribute if applicable.</li>
     * <li>Set the transactionId.</li>
     * <li>Encode the BindingRequest.</li>
     * <li>Create a DatagramPacket containing the BindingRequest.</li>
     * <li>Send the DatagramPacket through the DatagramSocket.</li>
     * </ul>
     * </p>
     */
    private void sendBindingRequest() {
        Log.debug("sendPeriodicBindingRequest");
        // create a Binding request
        Request bindingRequest = MessageFactory.createBindingRequest();
        // add the CONNECTION-REQUEST-BINDING attribute
        ConnectionRequestBindingAttribute connectionRequestBindingAttribute =
                ConnectionRequestBindingAttribute
                        .createConnectionRequestBindingAttribute('a');
        try {
            bindingRequest.addAttribute(connectionRequestBindingAttribute);
            Log.info("add CONNECTION-REQUEST-BINDING attribute");
        } catch (StunException e2) {
            Log
                    .error("unable to add the CONNECTION-REQUEST-BINDING attribute");
        }
        // add the USERNAME attribute if the username is non empty
        // TODO consider revision, the USERNAME attribute and the
        // MESSAGE-INTEGRITY
        // must be added only if and only if a Binding Error Response (code
        // 401)
        // is received.
        // the MESSAGE-INTEGRITY attribute mustn't be send in the next Binding
        // Requests.
        // it is the responsability of the STUN server to request it.
        // if (!"".equals(username)) {
        // UsernameAttribute usernameAttribute = UsernameAttribute
        // .createUsernameAttribute('b', username);
        // try {
        // bindingRequest.addAttribute(usernameAttribute);
        // Log.info("add USERNAME attribute");
        // } catch (StunException e) {
        // Log.error("unable to add the USERNAME attribute");
        // }
        // }
        // set the transactionId
        try {
            bindingRequest.setTransactionID(TransactionID
                    .createTransactionID().getTransactionID());
        } catch (StunException e1) {
            Log.error("unable to set the transactionID");
        }
        // encoding
        byte[] encodedBindingRequest = null;
        try {
            encodedBindingRequest = bindingRequest.encode();
        } catch (StunException e) {
            Log.error("unable to encode the BindingRequest");
            return;
        }
        // sending
        DatagramPacket packet =
                new DatagramPacket(encodedBindingRequest,
                        encodedBindingRequest.length);
        String stunServer = this.stunServerAddress;
        try {
            packet.setAddress(InetAddress.getByName(stunServer));
            packet.setPort(this.stunServerPort);
            STUNMessageSender messageSender =
                    new STUNMessageSender(packet, stunClient.getSocket());
            stunClient.getMessageProcessor().addProcessor(
                    new String(bindingRequest.getTransactionID()),
                    messageSender);
            stunClient.getMessageProcessor().addProcessor(
                    new String(bindingRequest.getTransactionID()),
                    new BindingResponseProcessor(stunClient));
            stunClient.getMessageProcessor().addProcessor(
                    new String(bindingRequest.getTransactionID()), this);
            messageSender.start();
        } catch (UnknownHostException e) {
            Log.error("unable to lookup the ACS stun server " + stunServer);
        } 
    }
}
