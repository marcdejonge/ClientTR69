package com.francetelecom.admindm.com.stunclient.impl;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import net.java.stun4j.StunException;
import net.java.stun4j.attribute.Attribute;
import net.java.stun4j.message.Message;
import net.java.stun4j.message.MessageFactory;
import net.java.stun4j.message.Request;
import net.java.stun4j.message.Response;
import net.java.stun4j.stack.TransactionID;
import com.francetelecom.acse.modus.util.StringUtil;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.com.stunclient.attribute.BindingChangeAttribute;
import com.francetelecom.admindm.com.stunclient.attribute.ConnectionRequestBindingAttribute;
import com.francetelecom.admindm.com.stunclient.attribute.UsernameAttribute;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.stunclient.ISTUNCLient;
/**
 * <p>
 * CPE STUN client.
 * </p>
 * <p>
 * This class allows to:
 * <ul>
 * <li>send BindingRequest in order to detect the network configuration</li>
 * </ul>
 * 
 * @author mpcy8647
 */
public final class STUNClient implements ISTUNCLient {
    /** The Constant DEFAULT_STUN_SERVER_PORT. */
    private static final int DEFAULT_STUN_SERVER_PORT = 3478;
    /** URL parameter name. */
    private static final String URL = "ManagementServer.URL";
    /** StunServerAddress parameter name. */
    private static final String STUN_SERVER_ADDRESS =
            "ManagementServer.STUNServerAddress";
    /** StunServerPort parameter name. */
    private static final String STUN_SERVER_PORT =
            "ManagementServer.STUNServerPort";
    /** STUNUsername parameter name. */
    private static final String STUN_USERNAME =
            "ManagementServer.STUNUsername";
    /** UDPConnectionRequestAddress parameter name. */
    private static final String UDP_CONNECTION_REQUEST_ADDRESS =
            "ManagementServer.UDPConnectionRequestAddress";
    /** NATDetected parameter name. */
    private static final String NAT_DETECTED = "ManagementServer.NATDetected";
    /** datagram socket used to send STUN messages. */
    private DatagramSocket socket;
    /** data model. */
    private final IParameterData parameterData;
    /** queue of Response stun message. */
    private final MessageQueue messageQueue;
    /** message processor. */
    private final MessageProcessor messageProcessor;
    /** binding maintainer. */
    private BindingMaintainer bindingMaintainer;
    /** value of the STUNUsername parameter. */
    private String stunUsernameValue;
    /** The udp connection request parameter. */
    private Parameter udpConnectionRequestParameter = null;
    /** The stun username parameter. */
    private Parameter stunUsernameParameter = null;
    /** The nat detected parameter. */
    private Parameter natDetectedParameter = null;
    /** The stun server address parameter. */
    private Parameter stunServerAddressParameter = null;
    /** The stun server port parameter. */
    private Parameter stunServerPortParameter = null;
    /** The url parameter. */
    private Parameter urlParameter;
    /**
     * <p>
     * Initiates a STUN client.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>create a Message queue for the Binding responses.</li>
     * <li>create a Message processor for the incoming Binding response.</li>
     * <li>create a Message sender for periodically sending Binding Requests.</li>
     * </ul>
     * </p>
     * 
     * @param pParameterData data model
     */
    public STUNClient(final IParameterData pParameterData) {
        this.parameterData = pParameterData;
        messageQueue = new MessageQueue();
        messageProcessor = new MessageProcessor(messageQueue);
        try {
            stunUsernameParameter =
                    parameterData.createOrRetrieveParameter(parameterData
                            .getRoot()
                                                            + STUN_USERNAME);
            udpConnectionRequestParameter =
                    parameterData
                            .createOrRetrieveParameter(parameterData.getRoot()
                                                       + UDP_CONNECTION_REQUEST_ADDRESS);
            natDetectedParameter =
                    parameterData.createOrRetrieveParameter(parameterData
                            .getRoot()
                                                            + NAT_DETECTED);
            stunServerAddressParameter =
                    parameterData
                            .createOrRetrieveParameter(parameterData.getRoot()
                                                       + STUN_SERVER_ADDRESS);
            stunServerPortParameter =
                    parameterData
                            .createOrRetrieveParameter(parameterData.getRoot()
                                                       + STUN_SERVER_PORT);
            urlParameter =
                    parameterData.createOrRetrieveParameter(parameterData
                            .getRoot()
                                                            + URL);
        } catch (Fault e) {
            Log.error("Should not occured", e);
        }
    }
    /**
     * Start the STUN client. The following actions are performed:
     * <ul>
     * <li>Create a new STUN message queue for the incoming STUN messages.</li>
     * <li>Create a STUN message processor (seperated thread processus) based
     * on the queue of incoming STUN messages.</li>
     * <li>Discover the binding (if behind a nat).</li>
     * </ul>
     * 
     * @param pSocket socket to be used by the STUN client
     */
    public void start(final DatagramSocket pSocket) {
        this.socket = pSocket;
        messageProcessor.start();
        // determine the binding
        Log.debug("start binding discovery");
        new BindingDiscovery(this, getSTUNServerAddress(), getSTUNServerPort());
        // TODO consider revision
        stunUsernameValue = stunUsernameParameter.getTextValue(null);
        if (stunUsernameValue == null) {
            stunUsernameValue = "";
        }
    }
    /**
     * <p>
     * Stop gracefully the STUN client.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>stop the STUN Binding Request sender thread.</li>
     * <li>stop the STUN message processor thread.</li>
     * <li>clean the incoming STUN messages queue</li>
     * </ul>
     * </p>
     * <p>
     * The start method must be called in the past.
     * </p>
     */
    public void stop() {
        // stop the binding maintainer
        bindingMaintainer.stop();
        // stop the STUN message processor thread
        messageProcessor.stop();
        // clean the incoming STUN message queue
        messageQueue.clearQueue();
    }
    /**
     * <p>
     * Gets the binding maintainer.
     * </p>
     * 
     * @return the binding maintainer
     */
    public BindingMaintainer getBindingMaintainer() {
        return bindingMaintainer;
    }
    /**
     * <p>
     * Add a new Stun message received from the datagram socket.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>the message is first decoded.</li>
     * <li>if the message is a response, it is added to the queue of message.</li>
     * </ul>
     * </p>
     * 
     * @param message message
     */
    public void addMessage(final byte[] message) {
        Log.debug("add a new message in the StunMessage queue");
        try {
            Message stunMessage =
                    Message.decode(message, (char) 0, (char) message.length);
            if (stunMessage instanceof Response) {
                Log.debug("stun Response type messasge");
                messageQueue.addResponse((Response) stunMessage);
            }
        } catch (StunException e) {
            e.printStackTrace();
        }
    }
    /**
     * <p>
     * Start the binding maintainer thread.
     * </p>
     */
    protected void startBindingMaintainer() {
        // binding maintainer
        bindingMaintainer = new BindingMaintainer(parameterData, this);
        bindingMaintainer.start();
    }
    /**
     * Send BindingRequest STUN message to the ACS.<br/> This method MUST be
     * used only when NAT binding changes are detected. The following actions
     * are performed:
     * <ul>
     * <li>Create a BindingRequest.</li>
     * <li>Add to it the CONNECTION-REQUEST-BINDING attribute</li>
     * <li>Add to it the BINDING-CHANGE attribute</li>
     * <li>Add to it the USERNAME attribute if applicable (meaning the
     * username is a non empty string).</li>
     * <li>Ensure the CHANGE-REQUEST attribute isn't set.</li>
     * <li>Ensure the RESPONSE-ADDRESS attribute isn't set.</li>
     * <li>Encode the BindingRequest.</li>
     * <li>Create a DatagramPacket containing the encoded BindingRequest.</li>
     * <li>Create a new STUNMessageSend that is responsible to send the
     * message (ensure reliability).</li>
     * </ul>
     */
    private void sendBindingRequestOnNATBindingChange() {
        Log.debug("send BindingRequest on NAT Binding change");
        Request bindingRequest = MessageFactory.createBindingRequest();
        try {
            bindingRequest.setTransactionID(TransactionID
                    .createTransactionID().getTransactionID());
        } catch (StunException e1) {
            Log.error("unable to set the transactionID");
        }
        // create CONNECTION-REQUEST-BINDING attribute
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
        // create BINDING-CHANGE attribute
        BindingChangeAttribute bindingChangeAttribute =
                BindingChangeAttribute.createBindingChangeAttribute('b');
        try {
            bindingRequest.addAttribute(bindingChangeAttribute);
            Log.info("add BINDING-CHANGE attribute");
        } catch (StunException e2) {
            Log.error("unable to add the BINDING-CHANGE attribute");
        }
        // add the USERNAME attribute if the username is non empty
        if (!"".equals(stunUsernameValue)) {
            UsernameAttribute usernameAttribute =
                    UsernameAttribute.createUsernameAttribute('c',
                            stunUsernameValue);
            try {
                bindingRequest.addAttribute(usernameAttribute);
                Log.info("add USERNAME attribute");
            } catch (StunException e) {
                Log.error("unable to add the USERNAME attribute");
            }
        }
        // ensure the RESPONSE-ADDRESS attribute isn't set
        if (bindingRequest.getAttribute(Attribute.RESPONSE_ADDRESS) != null) {
            bindingRequest.removeAttribute(Attribute.RESPONSE_ADDRESS);
        }
        // ensure the CHANGE-REQUEST attribute isn't set
        if (bindingRequest.getAttribute(Attribute.CHANGE_REQUEST) != null) {
            bindingRequest.removeAttribute(Attribute.CHANGE_REQUEST);
        }
        byte[] encodedBindingRequest = null;
        try {
            encodedBindingRequest = bindingRequest.encode();
        } catch (StunException e) {
            Log.error("unable to encode the BindingRequest");
            return;
        }
        DatagramPacket packet =
                new DatagramPacket(encodedBindingRequest,
                        encodedBindingRequest.length);
        String stunServer = getSTUNServerAddress();
        try {
            packet.setAddress(InetAddress.getByName(stunServer));
            packet.setPort(getSTUNServerPort());
            // create a new STUNMessageSender
            STUNMessageSender sender = new STUNMessageSender(packet, socket);
            // add the sender to the expectedBindingResponse queue
            String transactionIdString =
                    new String(bindingRequest.getTransactionID());
            messageProcessor.addProcessor(transactionIdString, sender);
            messageProcessor.addProcessor(transactionIdString,
                    new BindingResponseProcessor(this));
            // start the sender
            sender.start();
        } catch (UnknownHostException e) {
            Log.error("unable to lookup the ACS stun server " + stunServer);
        }
    }
    /**
     * Set the last mapped host and port.
     * 
     * @param host host part of the last received stun mapped address
     *            attribute
     * @param port port part of the last received stun mapped address
     *            attribute
     */
    public void setLastReceivedMappedAddress(final String host, final int port) {
        String oldValue = udpConnectionRequestParameter.getTextValue("");
        String value = host + ":" + port;
        String localValue = getAddress() + ":" + getPort();
        try {
            if (oldValue == null || !oldValue.equals(value)) {
                udpConnectionRequestParameter.setValue(value);
                // check if address/port translation is on going
                boolean isSameAddress = localValue.equals(value);
                if (!isSameAddress) {
                    // translation in use
                    Log.info("address/port translation in use: " + value);
                    sendBindingRequestOnNATBindingChange();
                }
                natDetectedParameter.setValue(Boolean.valueOf(!isSameAddress));
            }
        } catch (Fault e) {
            try {
                udpConnectionRequestParameter.setValue(oldValue);
            } catch (Fault e1) {
                Log.error("unable to restore old value", e);
            }
            Log.error("unable to set the parameter", e);
        }
    }
    /**
     * <p>
     * Get the STUN server address.
     * </p>
     * <p>
     * If the IGD.ManagementServer.StunServerAddress is not set, the
     * IGD.ManagementServer.URL parameter is used (only the host portion).
     * </p>
     * 
     * @return the server address
     */
    private String getSTUNServerAddress() {
        String stunServer = null;
        stunServer = stunServerAddressParameter.getTextValue(null);
        if ((stunServer == null) || ("".equals(stunServer))) {
            // get the value of the url parameter
            String url = urlParameter.getTextValue("");
            // extract the host portion
            try {
                String[] tokens = StringUtil.simpleSplit(url, "http://");
                if (tokens.length == 2) {

                    String address = tokens[1];
                    Log.debug("url sans http = " + address);
                    int index = address.indexOf(":");
                    if (index != -1) {
                        // host:port case
                        Log.debug("cas host:port");
                        stunServer = address.substring(0, index);
                    } else {
                        // host case (no port)
                        // look for "/" character
                        Log.debug("cas host");
                        index = address.indexOf("/");
                        if (index != -1) {
                            stunServer = address.substring(0, index);
                        } else {
                            Log.error("unable to extract stun server address for the URL : " + url + " !");
                        }
                    }
                }
            } catch (Exception e1) {
                Log.error("unable to split url = " + url + " with :", e1);
            }
        }
        Log.debug("stun server address = " + stunServer);
        return stunServer;
    }
    /**
     * <p>
     * Get the STUN port.
     * </p>
     * <p>
     * By default return 3478.
     * </p>
     * 
     * @return the port
     */
    private int getSTUNServerPort() {
        int result = DEFAULT_STUN_SERVER_PORT;
        Long port = (Long) stunServerPortParameter.getValue();
        if (port != null) {
            result = port.intValue();
        }
        return result;
    }
    /**
     * Get the address of the datagram socket.
     * 
     * @return address (ip form)
     */
    public String getAddress() {
        Log.debug("datagramsocket.getinetAddress.gethostaddress = "
                  + socket.getLocalAddress().getHostAddress());
        return this.socket.getLocalAddress().getHostAddress();
    }
    /**
     * Get the port of the datagram socket.
     * 
     * @return port
     */
    public int getPort() {
        return this.socket.getLocalPort();
    }
    /**
     * Gets the socket used by the STUN client.
     * 
     * @return socket
     */
    public DatagramSocket getSocket() {
        return socket;
    }
    /**
     * Gets the message processor.
     * 
     * @return the message processor
     */
    public MessageProcessor getMessageProcessor() {
        return messageProcessor;
    }
    /**
     * Gets the public address.
     * 
     * @return the public address
     */
    public String getPublicAddress() {
        return udpConnectionRequestParameter.getTextValue("");
    }
}
