/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.impl;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import net.java.stun4j.StunAddress;
import net.java.stun4j.StunException;
import net.java.stun4j.attribute.Attribute;
import net.java.stun4j.attribute.ResponseAddressAttribute;
import net.java.stun4j.message.MessageFactory;
import net.java.stun4j.message.Request;
import net.java.stun4j.message.Response;
import net.java.stun4j.stack.TransactionID;
import com.francetelecom.acse.modus.util.StringUtil;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.com.stunclient.attribute.ConnectionRequestBindingAttribute;
/**
 * <p>
 * This class implements the algorithm specified in the Annex G of TR069 -
 * Amendment 2 document.
 * </p>
 * 
 * @author mpcy8647
 * 
 */
public class AdvancedBindingPolicy implements Runnable, Processor {
    /** secondary socket used to send test STUN request binding message */
    private DatagramSocket secondarySocket = null;
    /** port on which is opened the primary socket */
    private final DatagramSocket primarySocket;
    /** port on which is opened the secondary socket */
    private final int secondaryPort;
    /** address of the stun server */
    private final String stunServerAddress;
    /** stun server port */
    private final int stunServerPort;
    /** stun client */
    private final STUNClient stunClient;
    /** STUNMinimumKeepAlivePeriod value */
    private final int stunMiniminKeepAlivePeriod;
    /** STUNMaximumKeepAlivePeriod value */
    private final int stunMaximumKeepAlivePeriod;
    /** current thread */
    private Thread thread = null;
    /** isStopped */
    private boolean isStopped = true;
    /** is the response binding received ? */
    private boolean responseReceived = false;
    /** current waiting time in use */
    private int currentWaitingTime;
    /** the last succeed waiting time */
    private int lastOkWaitingTime;
    /**
     * <p>
     * Initializes an AdvancedBindingPolicy.
     * </p>
     * <p>
     * The secondary port is set to the value of the primary port + 1. The
     * current waiting time is initialized to the value of the minimum keep
     * alive period. A new datagram socket is created.
     * </p>
     * 
     * @param stunClient stunclient
     * @param serverAddress server address
     * @param serverPort server port
     * @param minimunKeepAlivePeriod minimum keep alive period value
     * @param maximumKeepAliveValue maximum keep alive period value
     * 
     */
    public AdvancedBindingPolicy(final STUNClient stunClient,
            String serverAddress, int serverPort, int minimunKeepAlivePeriod,
            int maximumKeepAliveValue) {
        this.stunClient = stunClient;
        this.primarySocket = stunClient.getSocket();
        this.stunServerAddress = serverAddress;
        this.stunServerPort = serverPort;
        this.stunMiniminKeepAlivePeriod = minimunKeepAlivePeriod;
        this.stunMaximumKeepAlivePeriod = maximumKeepAliveValue;
        this.currentWaitingTime = minimunKeepAlivePeriod * 1000;
        this.secondaryPort = primarySocket.getLocalPort() + 1;
        // create a new datagram socket
        try {
            secondarySocket =
                    new DatagramSocket(secondaryPort, primarySocket
                            .getLocalAddress());
            Log.debug("secondary socket bound on " + secondarySocket.getLocalAddress());
        } catch (SocketException e) {
            Log
                    .error("unable to create the secondary datagram socket on the port "
                           + secondaryPort);
        }
    }
    /**
     * <p>
     * Start the thread
     * </p>
     * 
     */
    public void start() {
        isStopped = false;
        thread = new Thread(this);
        thread.start();
    }
    /**
     * <p>
     * Stop the thread.
     * </p>
     * 
     */
    public void stop() {
        isStopped = true;
    }
    /**
     * <p>
     * Run method.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>send a Binding request on the primary socket.</li>
     * <li>wait a certain amount of time (currentWaitingTime).</li>
     * <li>send a Binding request through the secondary socket.</li>
     * </ul>
     * </p>
     */
    public void run() {
        while (!isStopped) {
            // send a binding request through the primary socket
            Log.debug("send a Binding Request through the primary port");
            sendBindingRequest();
            // wait for a certain amount of time
            Log.debug("sleep for " + this.currentWaitingTime);
            try {
                Thread.sleep(this.currentWaitingTime);
            } catch (InterruptedException e) {
                Log.error("unable to sleep for " + currentWaitingTime);
            }
            // send the test STUN binding request through the secondary socket
            Log.debug("send a Binding Request through the secondary port");
            sendSTUNBindingTestMessage();
            Log.debug("sleep 2s");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            // check if the response has been received
            if (responseReceived == true) {
                // the binding has been maintained
                // increase the waiting time
                Log.debug("response received !!! ==> increase the delay");
                increaseWaitingTime();
            } else {
                Log.debug("response not received !!! ==> decrease the delay");
                decreaseWaitingTime();
            }
            // put in the initial state
            responseReceived = false;
        }
    }
    /**
     * <p>
     * Send a test STUN binding message from the secondary socket.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>creates a new Request.
     * </p>
     * <li>add or modify the RESPONSE-ADDRESS attribute.
     * </p>
     * <li>send it through the secondary socket.</li>
     * </ul>
     * </p>
     * 
     */
    private void sendSTUNBindingTestMessage() {
        Request bindingRequest = MessageFactory.createBindingRequest();
        // set the transactionID
        try {
            bindingRequest.setTransactionID(TransactionID
                    .createTransactionID().getTransactionID());
        } catch (StunException e) {
            Log.error("unable to create a TransactionID");
            return;
        }
        // check if the RESPONSE-ADDRESS attribute exist
        ResponseAddressAttribute responseAddressAttribute =
                (ResponseAddressAttribute) bindingRequest
                        .getAttribute(Attribute.RESPONSE_ADDRESS);
        if (responseAddressAttribute == null) {
            responseAddressAttribute = new ResponseAddressAttribute();
        }
        String[] tokens;
        try {
            tokens =
                    StringUtil.simpleSplit(stunClient.getPublicAddress(), ":");
            StunAddress address =
                    new StunAddress(tokens[0], Integer.parseInt(tokens[1]));
            responseAddressAttribute.setAddress(address);
            bindingRequest.addAttribute(responseAddressAttribute);
        } catch (StunException e) {
            Log.warn("should not occured");
        } catch (Exception e) {
            Log.error("should not occured", e);
        }
        // encode the STUN Binding Request message
        byte[] message;
        try {
            message = bindingRequest.encode();
        } catch (StunException e) {
            Log.error("unable to encode the STUN Binding Request message");
            return;
        }
        // create a DatagramPacket
        DatagramPacket packet = new DatagramPacket(message, message.length);
        try {
            packet.setAddress(InetAddress.getByName(this.stunServerAddress));
            packet.setPort(this.stunServerPort);
            stunClient.getMessageProcessor().addProcessor(
                    new String(bindingRequest.getTransactionID()), this);
            secondarySocket.send(packet);
        } catch (UnknownHostException e) {
            Log.error("unable to get the InetAddress of the STUN server");
        } catch (IOException e) {
            Log.error("unable to send the datagram packet through the socket");
        }
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
            STUNMessageSender messageSender = new STUNMessageSender(packet, primarySocket);
            
            // add a BindingResponse processor
            stunClient.getMessageProcessor().addProcessor(
                    new String(bindingRequest.getTransactionID()),
                    messageSender);
            stunClient.getMessageProcessor().addProcessor(
                    new String(bindingRequest.getTransactionID()),
                    new BindingResponseProcessor(stunClient));
            packet.setAddress(InetAddress.getByName(stunServer));
            packet.setPort(this.stunServerPort);
            messageSender.start();
        } catch (UnknownHostException e) {
            Log.error("unable to lookup the ACS stun server " + stunServer);
        } 
    }
    /**
     * 
     */
    public void process(Response response) {
        // a response has been received.
        Log.debug("response received in AdvancedBindingPolicy");
        responseReceived = true;
    }
    /**
     * <p>
     * Increase the waiting time duration.
     * </p>
     * <p>
     * The value of the waiting time is increased of 10s (10000 ms) if the
     * current value of waiting time is less than StunMaximumnKeepAlivePeriod -
     * 10 s. In all the other cases, the value isn't change.
     * </p>
     * 
     */
    private void increaseWaitingTime() {
        lastOkWaitingTime = currentWaitingTime;
        if (currentWaitingTime <= ((stunMaximumKeepAlivePeriod - 10) * 1000)) {
            currentWaitingTime = currentWaitingTime + 10000;
        }
    }
    /**
     * <p>
     * Decrease the waiting time duration.
     * </p>
     * <p>
     * The value is the value of the last succeed waiting time divided by 2.
     * If this value is less than the STUN minimum keep alive period value,
     * then the value is updated consequently.
     * </p>
     */
    private void decreaseWaitingTime() {
        if ((lastOkWaitingTime / 2) != currentWaitingTime) {
            currentWaitingTime = lastOkWaitingTime / 2;
        } else {
            currentWaitingTime = currentWaitingTime / 2;
        }
        if (currentWaitingTime < stunMiniminKeepAlivePeriod * 1000) {
            currentWaitingTime = stunMiniminKeepAlivePeriod * 1000;
        }
    }
}
