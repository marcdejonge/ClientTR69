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
* Class Result.
* @author OrangeLabs R&D
*/
public class Result  {
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
     public Result(
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

        paramDNSServerIP = createDNSServerIP();
        paramStatus = createStatus();
        paramIPAddresses = createIPAddresses();
        paramHostNameReturned = createHostNameReturned();
        paramResponseTime = createResponseTime();
        paramAnswerType = createAnswerType();
    }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDNSServerIP;

    /**
    * Getter method of DNSServerIP.
    * @return _DNSServerIP
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDNSServerIP() {
        return paramDNSServerIP;
    }

    /**
    * Create the parameter DNSServerIP
    * @return DNSServerIP
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDNSServerIP() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DNSServerIP");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(45));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramStatus;

    /**
    * Getter method of Status.
    * @return _Status
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamStatus() {
        return paramStatus;
    }

    /**
    * Create the parameter Status
    * @return Status
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createStatus() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Status");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.STRING);
        param.setValue("");
	    String[] values = {
	                       "Success",
	                       "Error_DNSServerNotAvailable",
	                       "Error_HostNameNotResolved",
	                       "Error_Timeout",
	                       "Error_Other",
                          };
		param.addCheck(new CheckEnum(values));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramIPAddresses;

    /**
    * Getter method of IPAddresses.
    * @return _IPAddresses
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamIPAddresses() {
        return paramIPAddresses;
    }

    /**
    * Create the parameter IPAddresses
    * @return IPAddresses
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createIPAddresses() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "IPAddresses");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.STRING);
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramHostNameReturned;

    /**
    * Getter method of HostNameReturned.
    * @return _HostNameReturned
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamHostNameReturned() {
        return paramHostNameReturned;
    }

    /**
    * Create the parameter HostNameReturned
    * @return HostNameReturned
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createHostNameReturned() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "HostNameReturned");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramResponseTime;

    /**
    * Getter method of ResponseTime.
    * @return _ResponseTime
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamResponseTime() {
        return paramResponseTime;
    }

    /**
    * Create the parameter ResponseTime
    * @return ResponseTime
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createResponseTime() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ResponseTime");
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
    private com.francetelecom.admindm.model.Parameter paramAnswerType;

    /**
    * Getter method of AnswerType.
    * @return _AnswerType
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamAnswerType() {
        return paramAnswerType;
    }

    /**
    * Create the parameter AnswerType
    * @return AnswerType
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createAnswerType() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "AnswerType");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.STRING);
        param.setValue("");
	    String[] values = {
	                       "None",
	                       "Authoritative",
	                       "NonAuthoritative",
                          };
		param.addCheck(new CheckEnum(values));
        param.setWritable(false);
        return param;
        }


}