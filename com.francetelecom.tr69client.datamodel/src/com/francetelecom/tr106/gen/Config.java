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

package com.francetelecom.tr106.gen;
import com.francetelecom.admindm.model.*;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.soap.Fault;
/**
* Class Config.
* @author OrangeLabs R&D
*/
public class Config  {
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
     public Config(
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

        paramPersistentData = createPersistentData();
        paramConfigFile = createConfigFile();
    }


    /**
     * Serial ID use for serialize.
     */
     private static final long serialVersionUID = 1L;
    /**
    * Arbitrary user data that MUST persist across CPE reboots. 
    */
    private com.francetelecom.admindm.model.Parameter paramPersistentData;

    /**
    * Getter method of PersistentData.
    * @return _PersistentData
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamPersistentData() {
        return paramPersistentData;
    }

    /**
    * Create the parameter PersistentData
    * @return PersistentData
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createPersistentData() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "PersistentData");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    * A dump of the currently running configuration on the CPE. This 
    * enables the ability to backup and restore the last known good state 
    * the CPE. It returns a vendor-specific document that defines the 
    * of the CPE. The document MUST be capable of restoring the CPE's 
    * when written back to the CPE using SetParameterValues.An alternative 
    * this parameter, e.g. when the configuration file is larger than the 
    * size limit, is to use the Upload and Download RPCs with a FileType 
    * ''1 Vendor Configuration File''. 
    */
    private com.francetelecom.admindm.model.Parameter paramConfigFile;

    /**
    * Getter method of ConfigFile.
    * @return _ConfigFile
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamConfigFile() {
        return paramConfigFile;
    }

    /**
    * Create the parameter ConfigFile
    * @return ConfigFile
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createConfigFile() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ConfigFile");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(32768));
        param.setValue("");
        param.setWritable(true);
        return param;
        }


}