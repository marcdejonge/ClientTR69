/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : AddObjectBundle
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
package com.francetelecom.admindm.addObject;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class AddObjectResponse.
 */
public final class AddObjectResponse implements RPCMethod {
    /** The Constant NAME. */
    private static final String NAME = "AddObjectResponse";
    /** The instance number. */
    private long instanceNumber;
    /** The status. */
    private int status;
    /**
     * Gets the instance number.
     * @return the instance number
     */
    public long getInstanceNumber() {
        return instanceNumber;
    }
    /**
     * Sets the instance number.
     * @param pInstanceNumber the new instance number
     */
    public void setInstanceNumber(final long pInstanceNumber) {
        this.instanceNumber = pInstanceNumber;
    }
    /**
     * Gets the status.
     * @return the status
     */
    public int getStatus() {
        return status;
    }
    /**
     * Sets the status.
     * @param pStatus the new status
     */
    public void setStatus(final int pStatus) {
        this.status = pStatus;
    }
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
