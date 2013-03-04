/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : TR69ClientAPI
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
 */ 
package com.francetelecom.admindm.soap;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
/**
 * The Class SetParamValuesFault.
 */
public final class SetParamValuesFault extends Exception implements RPCMethod {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** The Constant name. */
    private static final String NAME = "SetParamValuesFault";
    /** The parameter name. */
    private final String parameterName;
    /** The fault description. */
    private final String faultstring;
    /** The fault code. */
    private final int faultcode;
    /**
     * Instantiates a new sets the parameter values fault.
     * @param pParameterName the parameter name
     * @param pFaultcode the fault code
     * @param pFaultstring the fault string description
     */
    public SetParamValuesFault(final String pParameterName,
            final int pFaultcode, final String pFaultstring) {
        this.parameterName = pParameterName;
        this.faultcode = pFaultcode;
        this.faultstring = pFaultstring;
    }

    /**
     * Instantiates a new sets the parameter values fault.
     * @param pParameterName the parameter name
     * @param pFaultcode the fault code
     * @param pFaultstring the fault string description
     * @param e the exception
     */
    public SetParamValuesFault(final String pParameterName,
            final int pFaultcode, final String pFaultstring,
            final Exception e) {
        super(e);
        this.parameterName = pParameterName;
        this.faultcode = pFaultcode;
        this.faultstring = pFaultstring;
    }
    /**
     * Gets the name.
     * @return the name
     * @see RPCMethod#getName()
     */
    public String getName() {
        return NAME;
    }
    /**
     * Perform.
     * @param session the session
     * @see RPCMethod#perform(Session)
     */
    public void perform(final Session session) {
    }
    /**
     * Gets the parameter name.
     * @return the parameter name
     */
    public String getParameterName() {
        return parameterName;
    }
    /**
     * Gets the faultstring.
     * @return the faultstring
     */
    public String getFaultstring() {
        return faultstring;
    }
    /**
     * Gets the faultcode.
     * @return the faultcode
     */
    public int getFaultcode() {
        return faultcode;
    }
    /**
     * id of the RPCMethod Request by ACS.
     */
    private String id= null;
    /**
     * Gets the id.
     * @return the Id.
     */
	public String getId() { 
		return id;
	}
	/**
	 * Setter the Id.
	 */
	public void setId(String id) {
		this.id=id;		
	}

}
