/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DeleteObjectBundle
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
package com.francetelecom.admindm.deleteObject;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
/**
 * The Class DeleteObjectResponse.
 */
public final class DeleteObjectResponse implements RPCMethod {
    
    public DeleteObjectResponse(final int pStatus) {
        this.status = pStatus;
    }
    /** The Constant NAME. */
    private static final String NAME = "DeleteObjectResponse";
    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return NAME;
    }
    /** The status. */
    private final int status;
    /**
     * Perform.
     * @param session the session
     */
    public void perform(final Session session) {
    }
    
    /**
     * Gets the status.
     * @return the status
     */
    public int getStatus() {
        return status;
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
