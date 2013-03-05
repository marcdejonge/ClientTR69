/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : ServerComBundle
 *
 * Copyright © 2011 France Telecom
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author : Orange Labs R&D O.Beyler
 */
package com.francetelecom.admindm.com;
import java.net.DatagramPacket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
/**
 * @author mpcy8647
 */
public final class UDPConnectionRequest {
    /** HMAC-SHA1 algo constant.*/
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    /** protocol to be used for udp connection request.*/
    private static final String PROTOCOL = "HTTP/1.1";
    /** GET method cosntant.*/
    private static final String GET = "GET";
    /** ConnectionRequestUsername parameter name.*/
    private static final String CONNECTION_REQUEST_USERNAME =
        "ManagementServer.ConnectionRequestUsername";
    /** ConnectionRequestPassword parameter name.*/
    private static final String CONNECTION_REQUEST_PASSWORD =
        "ManagementServer.ConnectionRequestPassword";
    /** data model.*/
    private final IParameterData parameterData;
    /** datagram packet.*/
    private final DatagramPacket datagramPacket;
    /** request uri.*/
    private RequestURIParser requestURI = null;
    
    /**
     * <p>
     * Initiates a UDPConnectionRequest with the data model and the received UDP
     * packet from the ACS.
     * </p>
     * @param packet udp packet
     * @param pParameterData the parameter data
     */
    public UDPConnectionRequest(final IParameterData pParameterData,
            final DatagramPacket packet) {
        this.datagramPacket = packet;
        this.parameterData = pParameterData;
    }
    /**
     * <p>
     * Treat the received UDP Connection Request.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>first, the UDP Connection Request is validated.</li>
     * <li>based on the previous validation, the UDPConnectionRequest is
     * authenticated.</li>
     * <li>finally, if the two previous steps succeeded, it creates a Session
     * with the ACS.</li>
     * </ul>
     * </p>
     */
    public void treatUDPConnectionRequest() {
        if (validate()) {
            if (authenticate()) {
                // save the last UDP Connection request
                UDPServer.getInstance().setLastUDPConnectionRequest(this);
                // create a new Session
                parameterData.getCom().requestNewSessionByHTTPConnection();
            }
        }
    }
    /**
     * <p>
     * Get the parsed request uri.
     * </p>
     * @return request uri
     */
    public RequestURIParser getRequestUri() {
        return this.requestURI;
    }
    /**
     * <p>
     * Validate the UDPConnectionRequest.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>check the requirements specified by the RFC 2616 for an HTTP 1.1
     * request message. Currently, only the protocol part is checked. Some
     * improvements should be realized.</li>
     * <li>check if the Request List is "GET".</li>
     * <li>check if the current TS attribute value in the RequestURI is strictly
     * greatly than the one of the last validated and authenticated udp
     * connection request.</li>
     * <li>check if the current ID attribute value in the RequestURI is
     * different of the one of the last validated and authenticated udp
     * connection request.</li>
     * <li>check if the current UN attribute value in the RequestURI is equal to
     * the value of the IGD.ManagementServer.ConnectionRequestUsername parameter
     * value.</li>
     * </ul>
     * @return true if the validatation phase succeed else false
     * @todo Some improvements have to be done about the checking of the
     *       requirements of the RFC 2616
     */
    private boolean validate() {
        String stringData = new String(this.datagramPacket.getData());
        Log.debug("UDPConnectionRequest string data = " + stringData);
        StringTokenizer stringTokenizer = new StringTokenizer(stringData);
        // method
        String method = stringTokenizer.nextToken();
        if (!GET.equals(method)) {
            Log.debug("invalid method, should be " + GET + " instead of "
                    + method);
            return false;
        }
        // request uri parsing
        String requestUri = stringTokenizer.nextToken();
        requestURI = new RequestURIParser(requestUri);
        UDPConnectionRequest lastUDPConnectionRequest = UDPServer
                .getInstance().getLastUDPConnectionRequest();
        // protocol
        String protocol = stringTokenizer.nextToken();
        if (!PROTOCOL.equals(protocol)) {
            Log.debug("invalid protocol, should be " + PROTOCOL
                    + " instead of " + protocol);
            return false;
        }
        // get ts and check if ts > ts of the last authenticated & validated UDP
        // Connection Request
        long currentTs;
        try {
            currentTs = getTs(requestURI);
        } catch (Exception e) {
            Log.error("unable to extract the current TS attribute : "
                    + e.getMessage());
            e.printStackTrace();
            return false;
        }
        long previousTs = 0; // default case (UDP Connection Request never
        // received yet
        if (lastUDPConnectionRequest != null) {
            try {
                previousTs = getTs(lastUDPConnectionRequest.getRequestUri());
            } catch (Exception e) {
                Log.error("previousTs extraction should not throw an Exception",
                        e);
            }
        }
        if (currentTs <= previousTs) {
            Log.debug("invalid ts (must be strictly greater than the ts "
                    + "of the previous udp connection request");
            return false;
        }
        // get id and check if id != if of the last authenticated &
        // validated UDP Connection Request
        long currentId;
        try {
            currentId = getId(requestURI);
        } catch (Exception e) {
            Log.error("unable to extract the current id attribute");
            return false;
        }
        if (lastUDPConnectionRequest != null) {
            try {
                long previousId = getId(lastUDPConnectionRequest
                        .getRequestUri());
                if (previousId == currentId) {
                    return false;
                }
            } catch (Exception e) {
                Log.error("ERROR - this exception should not occured");
                return false;
            }
        }
        // get un and check if un =
        // IGD.ManagementServer.ConnectionRequestUsername
        String connectionRequestUsernameString = null;
        try {
            Parameter connectionRequestUsernameParameter = parameterData
                    .createOrRetrieveParameter(parameterData.getRoot()
                            + CONNECTION_REQUEST_USERNAME);
            connectionRequestUsernameString = connectionRequestUsernameParameter
                    .getTextValue(null);
        } catch (Fault e) {
            Log.error("unable to get the parameter ConnectionRequestUsername");
            return false;
        }
        String un = requestURI.getAttribute(RequestURIParser.UN);
        if (un == null) {
            Log.error("un attribute value null");
            return false;
        } else if (!un.equals(connectionRequestUsernameString)) {
            Log.error("invalid un attribute value. It is equal to " + un
                    + " but should be equal to "
                    + connectionRequestUsernameString);
            return false;
        }
        Log.info("UDP Connection Request validated");
        return true;
    }
    /**
     * <p>
     * Authenticate the UDPConnectionRequest.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>check if the RequestURI SIG attribute is equal to the computed
     * signature.</li>
     * </ul>
     * </p>
     * @return true if authentication phase succeed else false.
     */
    private boolean authenticate() {
        Log.debug("enter authenticate");

        try {
            Parameter connectionRequestPasswordParameter = parameterData
                    .createOrRetrieveParameter(parameterData.getRoot()
                            + CONNECTION_REQUEST_PASSWORD);
            String password = (String) connectionRequestPasswordParameter
                    .getValue();
            Log.debug("password = " + password);
            // create a secret key
            SecretKeySpec signinKey = new SecretKeySpec(password.getBytes(),
                    HMAC_SHA1_ALGORITHM);
            // get a Mac instance
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signinKey);
            // compute value
            String text = requestURI.getAttribute(RequestURIParser.TS)
                    + requestURI.getAttribute(RequestURIParser.ID)
                    + requestURI.getAttribute(RequestURIParser.UN)
                    + requestURI.getAttribute(RequestURIParser.CN);
            Log.debug("text = " + text);
            // computed signature (ensure lower case character)
            byte[] signature = mac.doFinal(text.getBytes());
            String computedSignature = new String(Hex.encodeHex(signature))
                    .toLowerCase();
            Log.debug("computed signature = " + computedSignature);
            // get the signature from the message
            String receivedSignature = requestURI
                    .getAttribute(RequestURIParser.SIG);
            // ensure lower case
            receivedSignature = receivedSignature.toLowerCase();
            if (receivedSignature == null) {
                Log.error("signature key null");
                return false;
            } else {
                if (!receivedSignature.equals(computedSignature)) {
                    Log.error("invalid signature");
                    return false;
                }
            }
        } catch (Fault e) {
            Log.error("unable to get the "
                    + "IGD.ManagementServer.ConnectionRequestPassword");
            return false;
        } catch (NoSuchAlgorithmException e) {
            Log.error("Unable to get the HMAC-SHA1 algo");
            return false;
        } catch (InvalidKeyException e) {
            Log.error("Unable to sign the key");
            return false;
        }
        Log.info("UDP Connection Request authenticated");
        return true;
    }

    /**
     * <p>
     * Extract the ts attribute value from the requestUri parameter and convert
     * it in int value.
     * </p>
     * @param requestUri the request uri
     * @return int value of the ts attribute.
     * @throws Exception if any error occurs such as the requestUri parameter is
     *             null or the ts attribute could not be parsed
     */
    private static long getTs(final RequestURIParser requestUri)
            throws Exception {
        if (requestUri != null) {
            String ts = requestUri.getAttribute(RequestURIParser.TS);
            return Long.parseLong(ts);
        } else {
            throw new Exception("parsed object null");
        }
    }
    /**
     * <p>
     * Extract the id attribute value from the requestUri parameter and convert
     * it in int value.
     * </p>
     * @param requestUri request uri
     * @return int value of the id parameter
     * @throws Exception if any error occurs (requestUri parameter null or id
     *             parameter not found.
     */
    private static long getId(final RequestURIParser requestUri)
            throws Exception {
        if (requestUri != null) {
            String id = requestUri.getAttribute(RequestURIParser.ID);
            Log.debug("getId = " + id);
            return Long.parseLong(id);
        } else {
            throw new Exception();
        }
    }
}
