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
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Interface Session.
 */
public interface Session {
    /**
     * Gets the session id.
     * @return the session id
     */
    String getSessionId();
    /**
     * Do a soap response.
     * @param method the method
     * @throws Fault the fault
     */
    void doASoapResponse(RPCMethod method) throws Fault;
    /**
     * Do soap request.
     * @param element the element
     * @param id the Id of the source request
     * @throws Fault the exception
     */
    void doSoapRequest(Element element , String id) throws Fault;
    /**
     * Gets the last rpc method.
     * @return the last rpc method
     */
    RPCMethod getLastRPCMethod();
    /**
     * Close session.
     * @param isSuccessfull the is successfull
     */
    void closeSession(boolean isSuccessfull);
    /**
     * Gets the parameter data.
     * @return the parameter data
     */
    IParameterData getParameterData();
}
