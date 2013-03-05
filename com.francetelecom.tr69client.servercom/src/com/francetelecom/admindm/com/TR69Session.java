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
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import org.kxml2.io.KXmlParser;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParserException;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.inform.Inform;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class Session.
 */
public final class TR69Session implements Session {
    private static final String INDENT_OUTPUT = "http://xmlpull.org/v1/doc/features.html#indent-output";
    /** The Constant HTTP_200. */
    private static final int HTTP_200 = 200;
    /** The Constant HTTP_204. */
    private static final int HTTP_204 = 204;
    /** The session id. */
    private String sessionId = "";
    /**
     * Gets the session id.
     * @return the session id
     */
    public String getSessionId() {
        return sessionId;
    }
    /** The management serverURL. */
    private String serverURL = "";
    // private ManagementServerDDTO managementServer = new
    // ManagementServerDDTO();
    /**
     * Sets the headers.
     * @param httpConnection the new headers
     * @throws ProtocolException the protocol exception
     */
    private void setHeaders(final HttpURLConnection httpConnection)
            throws ProtocolException {
        httpConnection.setRequestMethod("POST");
        // no user interact [like pop up]
        httpConnection.setAllowUserInteraction(false);
        httpConnection.setDoOutput(true); // want to send
        httpConnection.setRequestProperty("Content-type", "text/xml");
        httpConnection.setRequestProperty("Hostname", "cpe");
        if (sessionId != null) {
            Log.info("use this sessionID to send msg:" + sessionId);
            httpConnection.setRequestProperty("Cookie", "JSESSIONID="
                    + sessionId);
        } else {
            Log.info("No sessionID is present when sending msg");
        }
    }
    /** The hold request. */
    private boolean holdRequest;
    /** The parameter data. */
    private final IParameterData parameterData;
    /** The rpc mng. */
    private final RPCMethodMngService rpcMng;
    private long retryCount;
    /**
     * Gets the parameter data.
     * @return the parameter data
     */
    public IParameterData getParameterData() {
        return parameterData;
    }
    /**
     * Instantiates a new session.
     * @param pParameterData the parameter data
     * @param rpcMng the rpc mng
     */
    public TR69Session(final IParameterData pParameterData,
            RPCMethodMngService rpcMng, final long retry) {
        parameterData = pParameterData;
        this.rpcMng = rpcMng;
        this.retryCount = retry;
        Log.debug("create TR69Session with retry" + retry);
    }
    /** The last rpc method. */
    private RPCMethod lastRPCMethod;
    /** The is close. */
    private boolean isClose = false;
    /**
     * Gets the last rpc method.
     * @return the last rpc method
     * @see com.francetelecom.admindm.api.Session#getLastRPCMethod()
     */
    public RPCMethod getLastRPCMethod() {
        return lastRPCMethod;
    }
    /**
     * Run.
     * @throws Fault the exception
     */
    public void run() throws Fault {
        Log.info("create session");
        Parameter param = parameterData.getParameter(parameterData.getRoot()
                + "ManagementServer.URL");
        if (param != null) {
            serverURL = param.getTextValue(getSessionId());
            Log.debug("Open session on ACS: " + serverURL);
        }
        if ("".equals(serverURL)) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append("ManagementServer.URL is not defined");
            throw new Fault(FaultUtil.FAULT_9002, error.toString());
        }
        lastRPCMethod = new Inform(parameterData, retryCount);
        lastRPCMethod.setId(id);
        holdRequest = false;
        Object[] outgoingRequest;
        if (parameterData.getEventsArray().length > 0) {
            outgoingRequest = parameterData.getLsOutgoingRequest().toArray();
            // CPE send his Inform request");
            lastRPCMethod.perform(this);
            // CPE send his outGoingRequest only if ACS doesn't ask to hold
            // request.
            RPCEncoder encoder;
            for (int i = 0; i < outgoingRequest.length && !isClose; i++) {
            	while (holdRequest) {
            		doSoapRequest(null,"");
            	}
                lastRPCMethod = (RPCMethod) outgoingRequest[i];
                encoder = rpcMng.findRPCMethodEncoder(lastRPCMethod);
                doSoapRequest(encoder.encode(lastRPCMethod),lastRPCMethod.getId());
            }
            // CPE has finish to send his request.
            // It's time for ACS to give their request.
            doSoapRequest(null,id);
        } else {
            Log.debug("No event -> no session");
        }
    }
    /**
     * Do a soap response.
     * @param method the method
     * @throws Fault the exception
     */
    public void doASoapResponse(final RPCMethod method) throws Fault {
        RPCEncoder encoder = rpcMng.findRPCMethodEncoder(method.getName());
        doSoapRequest(encoder.encode(method),getId());
    }
    private String id ="1";
    public String getId() {return id;}
    /**
     * Parses the document.
     * @param incomingRequest the incoming request
     * @throws Fault the exception
     */
    protected void parse(final Document incomingRequest) throws Fault {
    	
        RPCMethod rpcMethod;
        Element element;
        RPCDecoder decoder;
        String rpcName;
        RPCEncoder encoder = rpcMng.findRPCMethodEncoder("Fault");
        Element root;
        try {
            root = incomingRequest.getRootElement();
        } catch (RuntimeException e) {
            StringBuffer error;
            error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": received an invalid http/soap message. ");
            error.append("It has been discarded.");
            error.append(e.getMessage());
            throw new Fault(FaultUtil.FAULT_9002, error.toString(), e);
        }        
        Element eHeader = null;
        extractNameSpace(root);
        
        
        int index = root.indexOf(null, Soap.getSoapEnvNameSpacePrefix() + ":Header", 0);
        if (index >= 0) {
            System.out.println("found HEADER");
            eHeader = root.getElement(index);
            index = eHeader.indexOf(null, "cwmp:HoldRequests", 0);
            Element eHoldRequest = null;
            if (index >= 0) {
                eHoldRequest = eHeader.getElement(index);
            }            
            holdRequest = (eHoldRequest != null && "1".equals(eHoldRequest
                    .getText(0)));
            
            Element eId = null;
            index = eHeader.indexOf(null, Soap.getCWMPNameSpacePrefix()+":ID", 0);
            if (index >= 0) {
                eId = eHeader.getElement(index);
                id = eId.getText(0);
                System.out.println("id->"+id);
            }
            else {
                System.out.println("unable to found id-"+Soap.getCWMPNameSpacePrefix());
            }
        }
        else {
         System.out.println("found NO HEADER");
        }
        index = root.indexOf(null, Soap.getSoapEnvNameSpacePrefix() + ":Body", 0);
        if (index < 0) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": read message without body.");
            throw new Fault(FaultUtil.FAULT_9002, error.toString());
        }
        Element eBody = root.getElement(index);
        int n = eBody.getChildCount();
        for (int i = 0; i < n; i++) {
            element = eBody.getElement(i);
            if (element == null) {
                continue;
            }
            rpcName = element.getName();
            int pPosition = rpcName.indexOf(':');
            if (pPosition > 0) {
                rpcName = rpcName.substring(pPosition + 1);
            }
            decoder = rpcMng.findRPCMethodDecoder(rpcName);
            if (decoder != null) {
                try {
                    Log.debug("decode found");
                    rpcMethod = decoder.decode(element);
                    rpcMethod.setId(id);
                    Log.debug("perform");
                    rpcMethod.perform(this);
                    Log.debug("end perform");
                } catch (Fault e) {
                	e.setId(id);
                    doSoapRequest(encoder.encode(e),id);
                } catch (XmlPullParserException e) {
                    StringBuffer error;
                    error = new StringBuffer(FaultUtil.STR_FAULT_9002);
                    error.append(": parsing XML failed.");
                    Fault fault = new Fault(FaultUtil.FAULT_9000, error
                            .toString(), e);
                    fault.setId(id);
                    doSoapRequest(encoder.encode(fault),id);
                }
            } else {
                StringBuffer error;
                error = new StringBuffer(FaultUtil.STR_FAULT_9000);
                error.append(": ");
                error.append(rpcName);
                error.append(" is not defined");
                rpcMethod = new Fault(FaultUtil.FAULT_9000, error.toString());
                if (encoder != null) {
                	rpcMethod.setId(id);
                    doSoapRequest(encoder.encode(rpcMethod),id);
                }
            }
        }
    }
    protected void extractNameSpace(Element root) {
        int nb= root.getAttributeCount();
        for (int i=0;i<nb;i++){
            
        	//String [] out = root.getAttributeName(i).split(":");
            // JDK 1.3 compatibily purpose
        	String [] out = null;
        	
        	try {
				out = root.getAttributeName(i).split(":");
			} catch (Exception e) {
				out = null;
			}
            
			if (out != null) {
	            String out2 = root.getAttributeValue(i);
	            int index = 0;            
	            if (out.length == 2){
	                index = 1;                
	            }
	            if (Soap.getSoapEncNameSpace().equals(out2)){
	                Soap.setSoapEncNameSpacePrefix(out[index]);
	            }
	            if (Soap.getSoapEnvNameSpace().equals(out2)){
	                Soap.setSoapEnvNameSpacePrefix(out[index]);
	            }
	            if (Soap.getCWMPNameSpace().equals(out2)){
	                Soap.setCWMPNameSpacePrefix(out[index]);
	            }
			}
        }
        
    }
    /**
     * Do soap request.
     * @param element the element
     * @param Id the id
     * @throws Fault the exception
     */
    public void doSoapRequest(final Element element, final String Id) throws Fault {
        try {
            if (!isClose) {
                URL httpUrl = new URL(serverURL);
                HttpURLConnection httpConnexion = (HttpURLConnection) httpUrl
                        .openConnection();
                setHeaders(httpConnexion);
                if (element != null) {
                    Document doc = Soap.getDocument(element, Id);
                    OutputStreamWriter writer;
                    writer = new OutputStreamWriter(httpConnexion
                            .getOutputStream());
                    KXmlSerializer serial = new KXmlSerializer();
                    serial.setOutput(writer);
                    doc.write(serial);
                    ByteArrayOutputStream logDebug;
                    logDebug = new ByteArrayOutputStream();
                    writer = new OutputStreamWriter(logDebug);
                    Log.debug("send post :");
                    serial.setFeature(INDENT_OUTPUT, true);
                    serial.setOutput(writer);
                    doc.write(serial);
                    Log.debug(logDebug.toString());
                } else {
                    Log.info("send empty post");
                }
                httpConnexion.getOutputStream().close();
                httpConnexion.connect();
                int httpCode = httpConnexion.getResponseCode();
                extractSessionId(httpConnexion);
                Log.info("Receive http response :" + httpCode);
                if (httpCode == HTTP_200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(httpConnexion
                                    .getInputStream()));
                    try {
                        KXmlParser parser = new KXmlParser();
                        parser.setInput(in);
                        Document dom = new Document();
                        dom.parse(parser);
                        KXmlSerializer serial = new KXmlSerializer();
                        ByteArrayOutputStream logDebug;
                        logDebug = new ByteArrayOutputStream();
                        OutputStreamWriter writer = new OutputStreamWriter(
                                logDebug);
                        serial.setOutput(writer);
                        serial.setFeature(INDENT_OUTPUT, true);
                        Log.debug("receive :");
                        dom.write(serial);
                        Log.debug(logDebug.toString());
                        parse(dom);
                    } catch (XmlPullParserException e) {
                        Log.error("received bad message : ", e);                       
                    }
                    in.close();
                } else if (httpCode == HTTP_204){
                	Log.info("set holdRequest to false");
                	holdRequest = false;
                }
            }
        } catch (IOException e) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append("IOException while connect on \"");
            error.append(serverURL);
            error.append("\"");
            throw new Fault(FaultUtil.FAULT_9002, error.toString(), e);
        }
    }
    /**
     * Extract the Session Id from the header field of the httpConnection.
     * @param httpConnection the http connection
     */
    private void extractSessionId(final HttpURLConnection httpConnection) {
        try {
            String cookies = httpConnection.getHeaderField("Set-Cookie");
            if (cookies != null) {
                Log.info("cookies Header" + cookies);
                String separators ="[//=;/]";
                String[] tokens = cookies.split(separators);
                sessionId = null;
                for (int i = 0; i < tokens.length; i++) {
                    if ("JSESSIONID".equals(tokens[i])) {
                        sessionId = tokens[i + 1];
                        Log.info("found session ID " + sessionId);
                    }
                }
            }
        } catch (Exception e) {
            Log.error("Exception" + e.getMessage());
        }
    }
    /**
     * Close the current session.
     * @param isSuccessfull the is successfully
     */
    public void closeSession(final boolean isSuccessfull) {
        isClose = true;
        if (!isSuccessfull) {
            Log.error("the session is close unsuccessfully");
        }
    }
}
