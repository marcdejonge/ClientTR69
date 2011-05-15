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
* Class RemoteAccess.
* @author OrangeLabs R&D
*/
public class RemoteAccess  {
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
     public RemoteAccess(
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

        paramPort = createPort();
        paramEnable = createEnable();
        paramSupportedProtocols = createSupportedProtocols();
        paramProtocol = createProtocol();
    }


    /**
     * Serial ID use for serialize.
     */
     private static final long serialVersionUID = 1L;
    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramPort;

    /**
    * Getter method of Port.
    * @return _Port
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamPort() {
        return paramPort;
    }

    /**
    * Create the parameter Port
    * @return Port
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createPort() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Port");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.addCheck(new CheckMaximum(65535));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramEnable;

    /**
    * Getter method of Enable.
    * @return _Enable
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamEnable() {
        return paramEnable;
    }

    /**
    * Create the parameter Enable
    * @return Enable
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createEnable() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Enable");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.BOOLEAN);
        param.addCheck(CheckBoolean.getInstance());
        param.setValue(Boolean.FALSE);
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramSupportedProtocols;

    /**
    * Getter method of SupportedProtocols.
    * @return _SupportedProtocols
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamSupportedProtocols() {
        return paramSupportedProtocols;
    }

    /**
    * Create the parameter SupportedProtocols
    * @return SupportedProtocols
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createSupportedProtocols() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "SupportedProtocols");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramProtocol;

    /**
    * Getter method of Protocol.
    * @return _Protocol
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamProtocol() {
        return paramProtocol;
    }

    /**
    * Create the parameter Protocol
    * @return Protocol
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createProtocol() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Protocol");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.setValue("");
        param.setWritable(true);
        return param;
        }


}