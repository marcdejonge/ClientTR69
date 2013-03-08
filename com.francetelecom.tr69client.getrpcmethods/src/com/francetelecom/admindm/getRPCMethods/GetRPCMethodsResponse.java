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
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.api.Session;
/**
 * The Class GetRPCMethodsResponse.
 */
public final class GetRPCMethodsResponse implements RPCMethod {
    /** The mng. */
    private final RPCMethodMngService mng;
    /**
     * The Constructor.
     * @param pMng the mng
     */
    protected GetRPCMethodsResponse(final RPCMethodMngService pMng) {
        this.mng = pMng;
    }
    /** The Constant NAME. */
    private static final String NAME = "GetRPCMethodsResponse";
    /**
     * Gets the name.
     * @return the name
     * @see com.francetelecom.admindm.api.RPCMethod#getName()
     */
    public String getName() {
        return NAME;
    }
    /**
     * Gets the ls rpc methods.
     * @return the ls rpc methods
     */
    public List<String> getLsRPCMethods() {
        List<String> result;
        if (mng != null) {
            result = mng.getRPCMethods();
        } else {
            result = new ArrayList<String>();
        }
        return result;
    }
    /**
     * Perform.
     * @param session the session
     */
    public void perform(final Session session) {
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
