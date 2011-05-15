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
* Class DeviceInfo.
* @author OrangeLabs R&D
*/
public class DeviceInfo  {
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
     public DeviceInfo(
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

        paramDescription = createDescription();
        paramManufacturerOUI = createManufacturerOUI();
        paramProvisioningCode = createProvisioningCode();
        paramHardwareVersion = createHardwareVersion();
        paramUpTime = createUpTime();
        paramEnabledOptions = createEnabledOptions();
        paramSerialNumber = createSerialNumber();
        paramAdditionalHardwareVersion = createAdditionalHardwareVersion();
        paramSoftwareVersion = createSoftwareVersion();
        paramManufacturer = createManufacturer();
        paramDeviceLog = createDeviceLog();
        paramModelName = createModelName();
        paramFirstUseDate = createFirstUseDate();
        paramProductClass = createProductClass();
        paramAdditionalSoftwareVersion = createAdditionalSoftwareVersion();
        paramDeviceStatus = createDeviceStatus();
    }


    /**
     * Serial ID use for serialize.
     */
     private static final long serialVersionUID = 1L;
    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDescription;

    /**
    * Getter method of Description.
    * @return _Description
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDescription() {
        return paramDescription;
    }

    /**
    * Create the parameter Description
    * @return Description
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDescription() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Description");
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
    * nullThis value MUST remain fixed over the lifetime of the device, 
    * across firmware updates. 
    */
    private com.francetelecom.admindm.model.Parameter paramManufacturerOUI;

    /**
    * Getter method of ManufacturerOUI.
    * @return _ManufacturerOUI
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamManufacturerOUI() {
        return paramManufacturerOUI;
    }

    /**
    * Create the parameter ManufacturerOUI
    * @return ManufacturerOUI
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createManufacturerOUI() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ManufacturerOUI");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(6));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramProvisioningCode;

    /**
    * Getter method of ProvisioningCode.
    * @return _ProvisioningCode
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamProvisioningCode() {
        return paramProvisioningCode;
    }

    /**
    * Create the parameter ProvisioningCode
    * @return ProvisioningCode
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createProvisioningCode() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ProvisioningCode");
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
    private com.francetelecom.admindm.model.Parameter paramHardwareVersion;

    /**
    * Getter method of HardwareVersion.
    * @return _HardwareVersion
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamHardwareVersion() {
        return paramHardwareVersion;
    }

    /**
    * Create the parameter HardwareVersion
    * @return HardwareVersion
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createHardwareVersion() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "HardwareVersion");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setMandatoryNotification(true);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramUpTime;

    /**
    * Getter method of UpTime.
    * @return _UpTime
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUpTime() {
        return paramUpTime;
    }

    /**
    * Create the parameter UpTime
    * @return UpTime
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUpTime() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UpTime");
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
    * Comma-separated list of the OptionName of each Option that is 
    * enabled in the CPE. The OptionName of each is identical to the 
    * element of the OptionStruct described in {{bibref|TR-069a2}}. Only 
    * options are listed whose State indicates the option is enabled. 
    */
    private com.francetelecom.admindm.model.Parameter paramEnabledOptions;

    /**
    * Getter method of EnabledOptions.
    * @return _EnabledOptions
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamEnabledOptions() {
        return paramEnabledOptions;
    }

    /**
    * Create the parameter EnabledOptions
    * @return EnabledOptions
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createEnabledOptions() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "EnabledOptions");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(1024));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    * nullThis value MUST remain fixed over the lifetime of the device, 
    * across firmware updates. 
    */
    private com.francetelecom.admindm.model.Parameter paramSerialNumber;

    /**
    * Getter method of SerialNumber.
    * @return _SerialNumber
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamSerialNumber() {
        return paramSerialNumber;
    }

    /**
    * Create the parameter SerialNumber
    * @return SerialNumber
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createSerialNumber() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "SerialNumber");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    * A comma-separated list of any additional versions. Represents any 
    * hardware version information the vendor might wish to supply. 
    */
    private com.francetelecom.admindm.model.Parameter paramAdditionalHardwareVersion;

    /**
    * Getter method of AdditionalHardwareVersion.
    * @return _AdditionalHardwareVersion
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamAdditionalHardwareVersion() {
        return paramAdditionalHardwareVersion;
    }

    /**
    * Create the parameter AdditionalHardwareVersion
    * @return AdditionalHardwareVersion
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createAdditionalHardwareVersion() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "AdditionalHardwareVersion");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramSoftwareVersion;

    /**
    * Getter method of SoftwareVersion.
    * @return _SoftwareVersion
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamSoftwareVersion() {
        return paramSoftwareVersion;
    }

    /**
    * Create the parameter SoftwareVersion
    * @return SoftwareVersion
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createSoftwareVersion() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "SoftwareVersion");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setMandatoryNotification(true);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramManufacturer;

    /**
    * Getter method of Manufacturer.
    * @return _Manufacturer
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamManufacturer() {
        return paramManufacturer;
    }

    /**
    * Create the parameter Manufacturer
    * @return Manufacturer
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createManufacturer() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Manufacturer");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDeviceLog;

    /**
    * Getter method of DeviceLog.
    * @return _DeviceLog
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDeviceLog() {
        return paramDeviceLog;
    }

    /**
    * Create the parameter DeviceLog
    * @return DeviceLog
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDeviceLog() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DeviceLog");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(32768));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramModelName;

    /**
    * Getter method of ModelName.
    * @return _ModelName
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamModelName() {
        return paramModelName;
    }

    /**
    * Create the parameter ModelName
    * @return ModelName
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createModelName() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ModelName");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    * nullIf NTP or equivalent is not available, this parameter, if 
    * SHOULD be set to the Unknown Time value. 
    */
    private com.francetelecom.admindm.model.Parameter paramFirstUseDate;

    /**
    * Getter method of FirstUseDate.
    * @return _FirstUseDate
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamFirstUseDate() {
        return paramFirstUseDate;
    }

    /**
    * Create the parameter FirstUseDate
    * @return FirstUseDate
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createFirstUseDate() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "FirstUseDate");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.DATE);
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }

    /**
    * nullThis value MUST remain fixed over the lifetime of the device, 
    * across firmware updates. 
    */
    private com.francetelecom.admindm.model.Parameter paramProductClass;

    /**
    * Getter method of ProductClass.
    * @return _ProductClass
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamProductClass() {
        return paramProductClass;
    }

    /**
    * Create the parameter ProductClass
    * @return ProductClass
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createProductClass() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ProductClass");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    * A comma-separated list of any additional versions. Represents any 
    * software version information the vendor might wish to supply. 
    */
    private com.francetelecom.admindm.model.Parameter paramAdditionalSoftwareVersion;

    /**
    * Getter method of AdditionalSoftwareVersion.
    * @return _AdditionalSoftwareVersion
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamAdditionalSoftwareVersion() {
        return paramAdditionalSoftwareVersion;
    }

    /**
    * Create the parameter AdditionalSoftwareVersion
    * @return AdditionalSoftwareVersion
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createAdditionalSoftwareVersion() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "AdditionalSoftwareVersion");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDeviceStatus;

    /**
    * Getter method of DeviceStatus.
    * @return _DeviceStatus
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDeviceStatus() {
        return paramDeviceStatus;
    }

    /**
    * Create the parameter DeviceStatus
    * @return DeviceStatus
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDeviceStatus() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DeviceStatus");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.setValue("");
	    String[] values = {
	                       "Up",
	                       "Initializing",
	                       "Error",
	                       "Disabled",
                          };
		param.addCheck(new CheckEnum(values));
        param.setWritable(false);
        return param;
        }


}