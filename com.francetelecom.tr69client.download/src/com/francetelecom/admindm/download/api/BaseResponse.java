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
package com.francetelecom.admindm.download.api;
import java.io.Serializable;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class BaseResponse.
 */
public abstract class BaseResponse implements RPCMethod, Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** The status. */
    private int status;
    /** The start time. */
    private long startTime;
    /** The complete time. */
    private long completeTime;
    /**
     * Gets the name.
     * @return the name
     */
    public abstract String getName();
    /**
     * Perform.
     * @param session the session
     * @throws Fault the fault
     */
    public void perform(final Session session) throws Fault {
    }
    /**
     * Sets the status.
     * @param pStatus the new status
     */
    public void setStatus(final int pStatus) {
        this.status = pStatus;
    }
    /**
     * Gets the status.
     * @return the status
     */
    public int getStatus() {
        return status;
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
