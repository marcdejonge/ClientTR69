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
* Class NSLookupDiagnostics.
* @author OrangeLabs R&D
*/
public class NSLookupDiagnostics  {
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
     public NSLookupDiagnostics(
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

        paramInterface = createInterface();
        paramResult = createResult();
        paramTimeout = createTimeout();
        paramNumberOfRepetitions = createNumberOfRepetitions();
        paramResultNumberOfEntries = createResultNumberOfEntries();
        paramSuccessCount = createSuccessCount();
        paramDNSServer = createDNSServer();
        paramDiagnosticsState = createDiagnosticsState();
        paramHostName = createHostName();
    }


    /**
     * Serial ID use for serialize.
     */
     private static final long serialVersionUID = 1L;
    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramInterface;

    /**
    * Getter method of Interface.
    * @return _Interface
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamInterface() {
        return paramInterface;
    }

    /**
    * Create the parameter Interface
    * @return Interface
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createInterface() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Interface");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramResult;

    /**
    * Getter method of Result.
    * @return _Result
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamResult() {
        return paramResult;
    }

    /**
    * Create the parameter Result
    * @return Result
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createResult() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Result");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.ANY);
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramTimeout;

    /**
    * Getter method of Timeout.
    * @return _Timeout
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamTimeout() {
        return paramTimeout;
    }

    /**
    * Create the parameter Timeout
    * @return Timeout
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createTimeout() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Timeout");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramNumberOfRepetitions;

    /**
    * Getter method of NumberOfRepetitions.
    * @return _NumberOfRepetitions
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamNumberOfRepetitions() {
        return paramNumberOfRepetitions;
    }

    /**
    * Create the parameter NumberOfRepetitions
    * @return NumberOfRepetitions
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createNumberOfRepetitions() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "NumberOfRepetitions");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramResultNumberOfEntries;

    /**
    * Getter method of ResultNumberOfEntries.
    * @return _ResultNumberOfEntries
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamResultNumberOfEntries() {
        return paramResultNumberOfEntries;
    }

    /**
    * Create the parameter ResultNumberOfEntries
    * @return ResultNumberOfEntries
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createResultNumberOfEntries() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ResultNumberOfEntries");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramSuccessCount;

    /**
    * Getter method of SuccessCount.
    * @return _SuccessCount
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamSuccessCount() {
        return paramSuccessCount;
    }

    /**
    * Create the parameter SuccessCount
    * @return SuccessCount
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createSuccessCount() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "SuccessCount");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDNSServer;

    /**
    * Getter method of DNSServer.
    * @return _DNSServer
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDNSServer() {
        return paramDNSServer;
    }

    /**
    * Create the parameter DNSServer
    * @return DNSServer
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDNSServer() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DNSServer");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        return param;
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
	                       "Error_DNSServerNotResolved",
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
    private com.francetelecom.admindm.model.Parameter paramHostName;

    /**
    * Getter method of HostName.
    * @return _HostName
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamHostName() {
        return paramHostName;
    }

    /**
    * Create the parameter HostName
    * @return HostName
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createHostName() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "HostName");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        return param;
        }


}