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
* Class Stats.
* @author OrangeLabs R&D
*/
public class Stats  {
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
     public Stats(
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

        paramTotalPacketsReceived = createTotalPacketsReceived();
        paramQuarterHourPacketsSent = createQuarterHourPacketsSent();
        paramTotalBytesReceived = createTotalBytesReceived();
        paramCurrentDayBytesReceived = createCurrentDayBytesReceived();
        paramCurrentDayPacketsSent = createCurrentDayPacketsSent();
        paramTotalPacketsSent = createTotalPacketsSent();
        paramCurrentDayInterval = createCurrentDayInterval();
        paramCurrentDayPacketsReceived = createCurrentDayPacketsReceived();
        paramQuarterHourBytesReceived = createQuarterHourBytesReceived();
        paramQuarterHourPacketsReceived = createQuarterHourPacketsReceived();
        paramTotalBytesSent = createTotalBytesSent();
        paramConnectionUpTime = createConnectionUpTime();
        paramQuarterHourInterval = createQuarterHourInterval();
        paramCurrentDayBytesSent = createCurrentDayBytesSent();
        paramQuarterHourBytesSent = createQuarterHourBytesSent();
    }


    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramTotalPacketsReceived;

    /**
    * Getter method of TotalPacketsReceived.
    * @return _TotalPacketsReceived
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamTotalPacketsReceived() {
        return paramTotalPacketsReceived;
    }

    /**
    * Create the parameter TotalPacketsReceived
    * @return TotalPacketsReceived
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createTotalPacketsReceived() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "TotalPacketsReceived");
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
    private com.francetelecom.admindm.model.Parameter paramQuarterHourPacketsSent;

    /**
    * Getter method of QuarterHourPacketsSent.
    * @return _QuarterHourPacketsSent
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamQuarterHourPacketsSent() {
        return paramQuarterHourPacketsSent;
    }

    /**
    * Create the parameter QuarterHourPacketsSent
    * @return QuarterHourPacketsSent
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createQuarterHourPacketsSent() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "QuarterHourPacketsSent");
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
    private com.francetelecom.admindm.model.Parameter paramTotalBytesReceived;

    /**
    * Getter method of TotalBytesReceived.
    * @return _TotalBytesReceived
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamTotalBytesReceived() {
        return paramTotalBytesReceived;
    }

    /**
    * Create the parameter TotalBytesReceived
    * @return TotalBytesReceived
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createTotalBytesReceived() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "TotalBytesReceived");
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
    private com.francetelecom.admindm.model.Parameter paramCurrentDayBytesReceived;

    /**
    * Getter method of CurrentDayBytesReceived.
    * @return _CurrentDayBytesReceived
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamCurrentDayBytesReceived() {
        return paramCurrentDayBytesReceived;
    }

    /**
    * Create the parameter CurrentDayBytesReceived
    * @return CurrentDayBytesReceived
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createCurrentDayBytesReceived() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "CurrentDayBytesReceived");
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
    private com.francetelecom.admindm.model.Parameter paramCurrentDayPacketsSent;

    /**
    * Getter method of CurrentDayPacketsSent.
    * @return _CurrentDayPacketsSent
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamCurrentDayPacketsSent() {
        return paramCurrentDayPacketsSent;
    }

    /**
    * Create the parameter CurrentDayPacketsSent
    * @return CurrentDayPacketsSent
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createCurrentDayPacketsSent() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "CurrentDayPacketsSent");
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
    private com.francetelecom.admindm.model.Parameter paramTotalPacketsSent;

    /**
    * Getter method of TotalPacketsSent.
    * @return _TotalPacketsSent
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamTotalPacketsSent() {
        return paramTotalPacketsSent;
    }

    /**
    * Create the parameter TotalPacketsSent
    * @return TotalPacketsSent
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createTotalPacketsSent() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "TotalPacketsSent");
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
    * Number of seconds since the beginning of the period used for 
    * of CurrentDay statistics. The device MAY align the beginning of each 
    * interval with days in the UTC time zone, but does not need to do so. 
    */
    private com.francetelecom.admindm.model.Parameter paramCurrentDayInterval;

    /**
    * Getter method of CurrentDayInterval.
    * @return _CurrentDayInterval
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamCurrentDayInterval() {
        return paramCurrentDayInterval;
    }

    /**
    * Create the parameter CurrentDayInterval
    * @return CurrentDayInterval
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createCurrentDayInterval() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "CurrentDayInterval");
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
    private com.francetelecom.admindm.model.Parameter paramCurrentDayPacketsReceived;

    /**
    * Getter method of CurrentDayPacketsReceived.
    * @return _CurrentDayPacketsReceived
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamCurrentDayPacketsReceived() {
        return paramCurrentDayPacketsReceived;
    }

    /**
    * Create the parameter CurrentDayPacketsReceived
    * @return CurrentDayPacketsReceived
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createCurrentDayPacketsReceived() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "CurrentDayPacketsReceived");
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
    private com.francetelecom.admindm.model.Parameter paramQuarterHourBytesReceived;

    /**
    * Getter method of QuarterHourBytesReceived.
    * @return _QuarterHourBytesReceived
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamQuarterHourBytesReceived() {
        return paramQuarterHourBytesReceived;
    }

    /**
    * Create the parameter QuarterHourBytesReceived
    * @return QuarterHourBytesReceived
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createQuarterHourBytesReceived() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "QuarterHourBytesReceived");
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
    private com.francetelecom.admindm.model.Parameter paramQuarterHourPacketsReceived;

    /**
    * Getter method of QuarterHourPacketsReceived.
    * @return _QuarterHourPacketsReceived
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamQuarterHourPacketsReceived() {
        return paramQuarterHourPacketsReceived;
    }

    /**
    * Create the parameter QuarterHourPacketsReceived
    * @return QuarterHourPacketsReceived
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createQuarterHourPacketsReceived() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "QuarterHourPacketsReceived");
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
    private com.francetelecom.admindm.model.Parameter paramTotalBytesSent;

    /**
    * Getter method of TotalBytesSent.
    * @return _TotalBytesSent
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamTotalBytesSent() {
        return paramTotalBytesSent;
    }

    /**
    * Create the parameter TotalBytesSent
    * @return TotalBytesSent
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createTotalBytesSent() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "TotalBytesSent");
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
    private com.francetelecom.admindm.model.Parameter paramConnectionUpTime;

    /**
    * Getter method of ConnectionUpTime.
    * @return _ConnectionUpTime
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamConnectionUpTime() {
        return paramConnectionUpTime;
    }

    /**
    * Create the parameter ConnectionUpTime
    * @return ConnectionUpTime
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createConnectionUpTime() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ConnectionUpTime");
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
    * Number of seconds since the beginning of the period used for 
    * of QuarterHour statistics. The device MAY align the beginning of 
    * QuarterHour interval with real-time quarter-hour intervals, but does 
    * need to do so. 
    */
    private com.francetelecom.admindm.model.Parameter paramQuarterHourInterval;

    /**
    * Getter method of QuarterHourInterval.
    * @return _QuarterHourInterval
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamQuarterHourInterval() {
        return paramQuarterHourInterval;
    }

    /**
    * Create the parameter QuarterHourInterval
    * @return QuarterHourInterval
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createQuarterHourInterval() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "QuarterHourInterval");
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
    private com.francetelecom.admindm.model.Parameter paramCurrentDayBytesSent;

    /**
    * Getter method of CurrentDayBytesSent.
    * @return _CurrentDayBytesSent
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamCurrentDayBytesSent() {
        return paramCurrentDayBytesSent;
    }

    /**
    * Create the parameter CurrentDayBytesSent
    * @return CurrentDayBytesSent
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createCurrentDayBytesSent() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "CurrentDayBytesSent");
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
    private com.francetelecom.admindm.model.Parameter paramQuarterHourBytesSent;

    /**
    * Getter method of QuarterHourBytesSent.
    * @return _QuarterHourBytesSent
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamQuarterHourBytesSent() {
        return paramQuarterHourBytesSent;
    }

    /**
    * Create the parameter QuarterHourBytesSent
    * @return QuarterHourBytesSent
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createQuarterHourBytesSent() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "QuarterHourBytesSent");
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


}