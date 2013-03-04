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
package com.francetelecom.admindm.api;
import java.util.Observable;
import com.francetelecom.admindm.model.IParameterData;
/**
 * The Interface ICom.
 */
public interface ICom extends Runnable {
    /**
     * Sets the rPC mng.
     * @param pRpcMng the new rPC mng
     */
    void setRPCMng(RPCMethodMngService pRpcMng);
    /**
     * Sets the parameter data.
     * @param pParameterData the new parameter data
     */
    void setParameterData(IParameterData pParameterData);
    /**
     * Request new session in case of request by an ACS connection.
     */
    void requestNewSessionByHTTPConnection();
    /**
     * Request new session.
     */
    void requestNewSession();
    /**
     * Update.
     * @param o the observable
     * @param arg the argument
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    void update(final Observable o, final Object arg);
    /**
     * Sets the running.
     * @param b the b
     */
    void setRunning(boolean b);
}
