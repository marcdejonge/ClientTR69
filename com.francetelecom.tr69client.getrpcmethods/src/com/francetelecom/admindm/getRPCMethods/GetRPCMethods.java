/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : GetRPCMethodsBundle
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
package com.francetelecom.admindm.getRPCMethods;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class GetRPCMethods.
 */
public final class GetRPCMethods implements RPCMethod {
    /** The Constant NAME. */
    private static final String NAME = "GetRPCMethods";
    /** The mng. */
    private final RPCMethodMngService mng;
    
    /**
     * The Constructor.
     * @param pMng the mng
     */
    protected GetRPCMethods(final RPCMethodMngService pMng) {
        this.mng = pMng;
    }
    /**
     * Gets the name.
     * @return the name
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
        session.doASoapResponse(new GetRPCMethodsResponse(mng));
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
