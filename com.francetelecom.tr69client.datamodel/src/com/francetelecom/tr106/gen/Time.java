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
* Class Time.
* @author OrangeLabs R&D
*/
public class Time  {
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
     public Time(
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

        paramLocalTimeZone = createLocalTimeZone();
        paramNTPServer1 = createNTPServer1();
        paramNTPServer2 = createNTPServer2();
        paramNTPServer3 = createNTPServer3();
        paramNTPServer4 = createNTPServer4();
        paramCurrentLocalTime = createCurrentLocalTime();
        paramNTPServer5 = createNTPServer5();
    }


    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramLocalTimeZone;

    /**
    * Getter method of LocalTimeZone.
    * @return _LocalTimeZone
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamLocalTimeZone() {
        return paramLocalTimeZone;
    }

    /**
    * Create the parameter LocalTimeZone
    * @return LocalTimeZone
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createLocalTimeZone() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "LocalTimeZone");
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
    private com.francetelecom.admindm.model.Parameter paramNTPServer1;

    /**
    * Getter method of NTPServer1.
    * @return _NTPServer1
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamNTPServer1() {
        return paramNTPServer1;
    }

    /**
    * Create the parameter NTPServer1
    * @return NTPServer1
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createNTPServer1() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "NTPServer1");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramNTPServer2;

    /**
    * Getter method of NTPServer2.
    * @return _NTPServer2
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamNTPServer2() {
        return paramNTPServer2;
    }

    /**
    * Create the parameter NTPServer2
    * @return NTPServer2
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createNTPServer2() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "NTPServer2");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramNTPServer3;

    /**
    * Getter method of NTPServer3.
    * @return _NTPServer3
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamNTPServer3() {
        return paramNTPServer3;
    }

    /**
    * Create the parameter NTPServer3
    * @return NTPServer3
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createNTPServer3() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "NTPServer3");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramNTPServer4;

    /**
    * Getter method of NTPServer4.
    * @return _NTPServer4
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamNTPServer4() {
        return paramNTPServer4;
    }

    /**
    * Create the parameter NTPServer4
    * @return NTPServer4
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createNTPServer4() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "NTPServer4");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramCurrentLocalTime;

    /**
    * Getter method of CurrentLocalTime.
    * @return _CurrentLocalTime
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamCurrentLocalTime() {
        return paramCurrentLocalTime;
    }

    /**
    * Create the parameter CurrentLocalTime
    * @return CurrentLocalTime
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createCurrentLocalTime() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "CurrentLocalTime");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.DATE);
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramNTPServer5;

    /**
    * Getter method of NTPServer5.
    * @return _NTPServer5
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamNTPServer5() {
        return paramNTPServer5;
    }

    /**
    * Create the parameter NTPServer5
    * @return NTPServer5
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createNTPServer5() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "NTPServer5");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(true);
        return param;
        }


}