/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DataModelBundle
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
 * Generated : 21 oct. 2009 by GenModel
 */
package com.francetelecom.tr157.gen;
import com.francetelecom.admindm.model.*;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.soap.Fault;
/**
* Class SelfTestDiagnostics.
* @author OrangeLabs R&D
*/
public class SelfTestDiagnostics  {
    /** The data. */
    private final IParameterData data;
    /** The base path. */
    private final String basePath;
     /**
     * Default constructor.
     * @param pData data model
     * @param pBasePath base path of attribute
     * @param pPersist persistence
     */
     public SelfTestDiagnostics(
            final IParameterData pData,
            final String pBasePath) {
        super();
        this.data = pData;
        this.basePath = pBasePath;
    }
    /**
     * Get the data.
     * @return the data
     */
    public final IParameterData getData() {
        return data;
    }

    /**
     * Get the basePath.
     * @return the basePath
     */
    public final String getBasePath() {
        return basePath;
    }

     /**
     * Initialiser.
     */ 
     public void initialize() throws Fault {
        com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath);
        param.setType(ParameterType.ANY);

        paramDiagnosticsState = createDiagnosticsState();
        paramResults = createResults();
    }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDiagnosticsState;

    /**
    * Getter method of DiagnosticsState.
    * @return _DiagnosticsState
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDiagnosticsState() {
        return paramDiagnosticsState;
    }

    /**
    * Create the parameter DiagnosticsState
    * @return DiagnosticsState
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDiagnosticsState() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DiagnosticsState");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.STRING);
        param.setValue("");
	    String[] values = {
	                       "None",
	                       "Requested",
	                       "Complete",
	                       "Error_Internal",
	                       "Error_Other",
                          };
		param.addCheck(new CheckEnum(values));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramResults;

    /**
    * Getter method of Results.
    * @return _Results
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamResults() {
        return paramResults;
    }

    /**
    * Create the parameter Results
    * @return Results
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createResults() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Results");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(1024));
        param.setValue("");
        param.setWritable(false);
        return param;
        }


}