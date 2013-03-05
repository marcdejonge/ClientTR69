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
* Class SampleSet.
* @author OrangeLabs R&D
*/
public class SampleSet  {
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
     public SampleSet(
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

        paramName = createName();
        paramStatus = createStatus();
        paramTimeReference = createTimeReference();
        paramReportSamples = createReportSamples();
        paramEnable = createEnable();
        paramForceSample = createForceSample();
        paramSampleInterval = createSampleInterval();
        paramReportEndTime = createReportEndTime();
        paramFetchSamples = createFetchSamples();
        paramReportStartTime = createReportStartTime();
        paramSampleSeconds = createSampleSeconds();
        paramParameterNumberOfEntries = createParameterNumberOfEntries();
    }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramName;

    /**
    * Getter method of Name.
    * @return _Name
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamName() {
        return paramName;
    }

    /**
    * Create the parameter Name
    * @return Name
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createName() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Name");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(128));
        param.setValue("");
        param.setWritable(true);
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
        param.setType(ParameterType.STRING);
        param.setValue("");
	    String[] values = {
	                       "Disabled",
	                       "Enabled",
	                       "Trigger",
                          };
		param.addCheck(new CheckEnum(values));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramTimeReference;

    /**
    * Getter method of TimeReference.
    * @return _TimeReference
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamTimeReference() {
        return paramTimeReference;
    }

    /**
    * Create the parameter TimeReference
    * @return TimeReference
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createTimeReference() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "TimeReference");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.DATE);
        param.setValue(new Long(0));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramReportSamples;

    /**
    * Getter method of ReportSamples.
    * @return _ReportSamples
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamReportSamples() {
        return paramReportSamples;
    }

    /**
    * Create the parameter ReportSamples
    * @return ReportSamples
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createReportSamples() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ReportSamples");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.addCheck(new CheckMinimum(1));
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
    private com.francetelecom.admindm.model.Parameter paramForceSample;

    /**
    * Getter method of ForceSample.
    * @return _ForceSample
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamForceSample() {
        return paramForceSample;
    }

    /**
    * Create the parameter ForceSample
    * @return ForceSample
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createForceSample() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ForceSample");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setHidden(true);
        param.setType(ParameterType.BOOLEAN);
        param.addCheck(CheckBoolean.getInstance());
        param.setValue(Boolean.FALSE);
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramSampleInterval;

    /**
    * Getter method of SampleInterval.
    * @return _SampleInterval
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamSampleInterval() {
        return paramSampleInterval;
    }

    /**
    * Create the parameter SampleInterval
    * @return SampleInterval
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createSampleInterval() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "SampleInterval");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.addCheck(new CheckMinimum(1));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramReportEndTime;

    /**
    * Getter method of ReportEndTime.
    * @return _ReportEndTime
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamReportEndTime() {
        return paramReportEndTime;
    }

    /**
    * Create the parameter ReportEndTime
    * @return ReportEndTime
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createReportEndTime() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ReportEndTime");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.DATE);
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramFetchSamples;

    /**
    * Getter method of FetchSamples.
    * @return _FetchSamples
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamFetchSamples() {
        return paramFetchSamples;
    }

    /**
    * Create the parameter FetchSamples
    * @return FetchSamples
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createFetchSamples() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "FetchSamples");
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
    private com.francetelecom.admindm.model.Parameter paramReportStartTime;

    /**
    * Getter method of ReportStartTime.
    * @return _ReportStartTime
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamReportStartTime() {
        return paramReportStartTime;
    }

    /**
    * Create the parameter ReportStartTime
    * @return ReportStartTime
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createReportStartTime() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ReportStartTime");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.DATE);
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramSampleSeconds;

    /**
    * Getter method of SampleSeconds.
    * @return _SampleSeconds
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamSampleSeconds() {
        return paramSampleSeconds;
    }

    /**
    * Create the parameter SampleSeconds
    * @return SampleSeconds
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createSampleSeconds() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "SampleSeconds");
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
    private com.francetelecom.admindm.model.Parameter paramParameterNumberOfEntries;

    /**
    * Getter method of ParameterNumberOfEntries.
    * @return _ParameterNumberOfEntries
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamParameterNumberOfEntries() {
        return paramParameterNumberOfEntries;
    }

    /**
    * Create the parameter ParameterNumberOfEntries
    * @return ParameterNumberOfEntries
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createParameterNumberOfEntries() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ParameterNumberOfEntries");
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