package com.francetelecom.admindm.getParameterValues;

import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class MockSession.
 */
public final class MockSession implements Session {
    /**
     * Close session.
     * @param isSuccessfull the is successfull
     */
    public void closeSession(final boolean isSuccessfull) {
    }
    public RPCMethod soapResponse=null;
    /**
     * Do a soap response.
     * @param method the method
     * @throws Fault the fault
     */
    public void doASoapResponse(final RPCMethod method) throws Fault {
        soapResponse = method;
    }
    /**
     * Do soap request.
     * @param element the element
     * @param id the id
     * @throws Fault the fault
     */
    public void doSoapRequest(final Element element,final String id) throws Fault {
    }
    /** The last rpc. */
    private RPCMethod lastRPCMethod;
    
    /**
     * Sets the last rpc method.
     * @param lastRPCMethod the last rpc method
     */
    public void setLastRPCMethod(final RPCMethod pLastRPCMethod) {
        this.lastRPCMethod = pLastRPCMethod;
    }
    /**
     * Instantiates a new mock session.
     * @param pLastRPC the last rpc
     */
    public MockSession() {
    }
    /**
     * Gets the last rpc method.
     * @return the last rpc method
     * @see com.francetelecom.admindm.api.Session#getLastRPCMethod()
     */
    public RPCMethod getLastRPCMethod() {
        return lastRPCMethod;
    }
    IParameterData data = new ParameterData();
    /**
     * Gets the parameter data.
     * @return the parameter data
     * @see com.francetelecom.admindm.api.Session#getParameterData()
     */
    public IParameterData getParameterData() {
        return data;
    }
    /** The session id. */
    private String sessionId = "";
    /**
     * Sets the session id.
     * @param pSessionId the new session id
     */
    public void setSessionId(String pSessionId) {
        this.sessionId = pSessionId;
    }
    /**
     * Gets the session id.
     * @return the session id
     * @see com.francetelecom.admindm.api.Session#getSessionId()
     */
    public String getSessionId() {
        return sessionId;
    }
}
