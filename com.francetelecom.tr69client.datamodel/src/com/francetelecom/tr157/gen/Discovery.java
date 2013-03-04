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
* Class Discovery.
* @author OrangeLabs R&D
*/
public class Discovery  {
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
     public Discovery(
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

        paramService = createService();
        paramRootDevice = createRootDevice();
        paramServiceNumberOfEntries = createServiceNumberOfEntries();
        paramDeviceNumberOfEntries = createDeviceNumberOfEntries();
        paramRootDeviceNumberOfEntries = createRootDeviceNumberOfEntries();
    }


    /**
     * Serial ID use for serialize.
     */
     private static final long serialVersionUID = 1L;
    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramService;

    /**
    * Getter method of Service.
    * @return _Service
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamService() {
        return paramService;
    }

    /**
    * Create the parameter Service
    * @return Service
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createService() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Service");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.ANY);
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramRootDevice;

    /**
    * Getter method of RootDevice.
    * @return _RootDevice
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamRootDevice() {
        return paramRootDevice;
    }

    /**
    * Create the parameter RootDevice
    * @return RootDevice
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createRootDevice() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "RootDevice");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.ANY);
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramServiceNumberOfEntries;

    /**
    * Getter method of ServiceNumberOfEntries.
    * @return _ServiceNumberOfEntries
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamServiceNumberOfEntries() {
        return paramServiceNumberOfEntries;
    }

    /**
    * Create the parameter ServiceNumberOfEntries
    * @return ServiceNumberOfEntries
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createServiceNumberOfEntries() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ServiceNumberOfEntries");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
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
    private com.francetelecom.admindm.model.Parameter paramDeviceNumberOfEntries;

    /**
    * Getter method of DeviceNumberOfEntries.
    * @return _DeviceNumberOfEntries
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDeviceNumberOfEntries() {
        return paramDeviceNumberOfEntries;
    }

    /**
    * Create the parameter DeviceNumberOfEntries
    * @return DeviceNumberOfEntries
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDeviceNumberOfEntries() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DeviceNumberOfEntries");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
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
    private com.francetelecom.admindm.model.Parameter paramRootDeviceNumberOfEntries;

    /**
    * Getter method of RootDeviceNumberOfEntries.
    * @return _RootDeviceNumberOfEntries
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamRootDeviceNumberOfEntries() {
        return paramRootDeviceNumberOfEntries;
    }

    /**
    * Create the parameter RootDeviceNumberOfEntries
    * @return RootDeviceNumberOfEntries
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createRootDeviceNumberOfEntries() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "RootDeviceNumberOfEntries");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }


}