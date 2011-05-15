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
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
/**
 * The Class Fault.
 */
public class Fault extends Exception implements RPCMethod {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** The Constant name. */
    private static final String NAME = "Fault";
    /** The faultcode. */
    private int faultcode;
    /**
     * Gets the faultcode.
     * @return the faultcode
     */
    public final int getFaultcode() {
        return faultcode;
    }
    /**
     * Gets the faultstring.
     * @return the faultstring
     */
    public final String getFaultstring() {
        return faultstring;
    }
    /**
     * Gets the ls set param values faults.
     * @return the ls set param values faults
     */
    public final List getLsSetParamValuesFaults() {
        return lsSetParamValuesFaults;
    }
    /** The faultstring. */
    private String faultstring;
    /** The ls set param values faults. */
    private List lsSetParamValuesFaults = new ArrayList();
    /**
     * Instantiates a new fault.
     * @param pFaultcode the faultcode
     * @param pFaultstring the faultstring
     * @param lsDetails the ls details
     */
    public Fault(final int pFaultcode, final String pFaultstring,
            final List lsDetails) {
        this(pFaultcode, pFaultstring, lsDetails, null);
    }
    /**
     * Instantiates a new fault.
     * @param pFaultcode the faultcode
     * @param pFaultstring the faultstring
     * @param lsDetails the list of details
     * @param exp the source Exception
     */
    public Fault(final int pFaultcode, final String pFaultstring,
            final List lsDetails, final Exception exp) {
        this.faultcode = pFaultcode;
        this.faultstring = pFaultstring;
        this.lsSetParamValuesFaults = lsDetails;
    }

    /**
     * Instantiates a new fault.
     * @param pFaultcode the faultcode
     * @param pFaultstring the faultstring
     */
    public Fault(final int pFaultcode, final String pFaultstring) {
        this(pFaultcode, pFaultstring, null, null);
    }
    /**
     * Instantiates a new fault.
     * @param pFaultcode the faultcode
     * @param pFaultstring the faultstring
     * @param exp the Exception
     */
    public Fault(final int pFaultcode, final String pFaultstring,
            final Exception exp) {
        this(pFaultcode, pFaultstring, null, exp);
    }

    /**
     * Instantiates a new fault.
     */
    public Fault() {
    }
    /**
     * Gets the name.
     * @return the name
     * @see com.francetelecom.admindm.api.RPCMethod#getName()
     */
    public final String getName() {
        return NAME;
    }
    /**
     * Perform.
     * @param session the session
     * @throws Fault the exception
     */
    public final void perform(final Session session) throws Fault {
        StringBuffer buffer = new StringBuffer("ACS return a fault code :");
        buffer.append(faultcode);
        buffer.append(" ");
        buffer.append(faultstring);
        if (faultcode == FaultUtil.FAULT_8005) {
            session.doASoapResponse(session.getLastRPCMethod());
        } else {
            if ("Inform".equals(session.getLastRPCMethod().getName())) {
                session.closeSession(false);
            }
        }
        Log.error(buffer.toString());
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
