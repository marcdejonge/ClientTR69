/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DownloadBundle
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

package com.francetelecom.admindm.download;
import java.util.Iterator;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.download.api.IEngine;
import com.francetelecom.admindm.download.api.TransferComplete;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class TransferCompleteResponse.
 */
public final class TransferCompleteResponse implements RPCMethod {

    /** The engine. */
    private final IEngine engine;

    /**
     * The Constructor.
     * @param pEngine the engine
     */
    public TransferCompleteResponse(final IEngine pEngine) {
        this.engine = pEngine;
    }

    /** The Constant name. */
    public static final String NAME = "TransferCompleteResponse";
    /**
     * Gets the name.
     * @return the name
     * @see com.francetelecom.admindm.api.RPCMethod#getName()
     */
    public String getName() {
        return NAME;
    }
    /**
     * Perform.
     * @param session the session
     * @throws Fault the exception
     */
    public void perform(final Session session) throws Fault {
        TransferComplete transfer = null;
        IParameterData parameterData = session.getParameterData();
        try {
            transfer = (TransferComplete) session.getLastRPCMethod();
        } catch (ClassCastException e) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": have a ");
            error.append(session.getLastRPCMethod().getName());
            error.append(" instead of TransferComplete");
            throw new Fault(FaultUtil.FAULT_9002, error.toString(), e);
        }
        if (transfer == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": have a null ");
            error.append(" instead of TransferComplete as last RPCMethod");
            throw new Fault(FaultUtil.FAULT_9002, error.toString());
        }
        engine.getLastTransferComplete().remove(transfer);
        Iterator<EventStruct> itEvents = transfer.getLsEvent().iterator();
        while (itEvents.hasNext()) {
            parameterData.deleteEvent(itEvents.next());
        }
        parameterData.removeOutgoingRequest(transfer);
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
