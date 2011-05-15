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
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Interface Factory.
 */
public interface Factory {
    
    /**
     * Creates the instance and return status,
     * status = 0 if the Object has been created.<br/>
     * status = 1 if the Object has been validated and committed
     * but not yet applied.<br/>
     * @param instanceId the instance id
     * @param data the data
     * @return the status [O:1] 
     * @throws Fault the fault
     */
    int createObjectInstance(long instanceId, IParameterData data)
            throws Fault;
}
