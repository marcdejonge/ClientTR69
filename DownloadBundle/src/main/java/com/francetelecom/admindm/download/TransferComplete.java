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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class TransferComplete.
 */
public final class TransferComplete implements RPCMethod, Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** The Constant name. */
    public static final transient String NAME = "TransferComplete";
    /** The command key. */
    private String commandKey = "";
    /** The fault structure. */
    private FaultStruct faultStruct = new FaultStruct();
    /** The start time. */
    private long startTime = 0;
    /** The complete time. */
    private long completeTime = 0;
    /** The list of event. */
    private List lsEvent = new ArrayList();
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
    }
    /**
     * Sets the command key.
     * @param pCommandKey the new command key
     */
    public void setCommandKey(final String pCommandKey) {
        this.commandKey = pCommandKey;
    }
    /**
     * Gets the command key.
     * @return the command key
     */
    public String getCommandKey() {
        return commandKey;
    }
    /**
     * Sets the fault structure.
     * @param pFaultStruct the new fault structure
     */
    public void setFaultStruct(final FaultStruct pFaultStruct) {
        this.faultStruct = pFaultStruct;
    }
    /**
     * Gets the fault structure.
     * @return the fault structure
     */
    public FaultStruct getFaultStruct() {
        return faultStruct;
    }
    /**
     * Sets the start time.
     * @param pStartTime the new start time
     */
    public void setStartTime(final long pStartTime) {
        this.startTime = pStartTime;
    }
    /**
     * Gets the start time.
     * @return the start time
     */
    public long getStartTime() {
        return startTime;
    }
    /**
     * Sets the complete time.
     * @param pCompleteTime the new complete time
     */
    public void setCompleteTime(final long pCompleteTime) {
        this.completeTime = pCompleteTime;
    }
    /**
     * Gets the complete time.
     * @return the complete time
     */
    public long getCompleteTime() {
        return completeTime;
    }
    /**
     * Sets the list of event.
     * @param pLsEvent the list of event
     */
    public void setLsEvent(final List pLsEvent) {
        this.lsEvent = pLsEvent;
    }
    /**
     * Gets the list of event.
     * @return the list of event
     */
    public List getLsEvent() {
        return lsEvent;
    }
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((commandKey == null) ? 0 : commandKey.hashCode());
        result = prime * result + (int) (completeTime ^ (completeTime >>> 32));
        result = prime * result
                + ((faultStruct == null) ? 0 : faultStruct.hashCode());
        result = prime * result + ((lsEvent == null) ? 0 : lsEvent.hashCode());
        result = prime * result + (int) (startTime ^ (startTime >>> 32));
        return result;
    }
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TransferComplete other = (TransferComplete) obj;
        if (commandKey == null) {
            if (other.commandKey != null) {
                return false;
            }
        } else if (!commandKey.equals(other.commandKey)) {
            return false;
        }
        if (completeTime != other.completeTime) {
            return false;
        }
        if (faultStruct == null) {
            if (other.faultStruct != null) {
                return false;
            }
        } else if (!faultStruct.equals(other.faultStruct)) {
            return false;
        }
        if (lsEvent == null) {
            if (other.lsEvent != null) {
                return false;
            }
        } else if (!lsEvent.equals(other.lsEvent)) {
            return false;
        }
        if (startTime != other.startTime) {
            return false;
        }
        return true;
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
