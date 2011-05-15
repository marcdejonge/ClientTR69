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
* Class Firewall.
* @author OrangeLabs R&D
*/
public class Firewall  {
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
     public Firewall(
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

        paramConfig = createConfig();
        paramVersion = createVersion();
        paramLastChange = createLastChange();
    }


    /**
     * Serial ID use for serialize.
     */
     private static final long serialVersionUID = 1L;
    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramConfig;

    /**
    * Getter method of Config.
    * @return _Config
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamConfig() {
        return paramConfig;
    }

    /**
    * Create the parameter Config
    * @return Config
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createConfig() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Config");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.setValue("");
	    String[] values = {
	                       "High",
	                       "Low",
	                       "Off",
                          };
		param.addCheck(new CheckEnum(values));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramVersion;

    /**
    * Getter method of Version.
    * @return _Version
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamVersion() {
        return paramVersion;
    }

    /**
    * Create the parameter Version
    * @return Version
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createVersion() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Version");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(16));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramLastChange;

    /**
    * Getter method of LastChange.
    * @return _LastChange
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamLastChange() {
        return paramLastChange;
    }

    /**
    * Create the parameter LastChange
    * @return LastChange
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createLastChange() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "LastChange");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.DATE);
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }


}