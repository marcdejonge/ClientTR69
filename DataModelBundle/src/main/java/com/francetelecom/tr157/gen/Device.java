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
* Class Device.
* @author OrangeLabs R&D
*/
public class Device  {
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
     public Device(
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

        paramUPnPMediaRenderer = createUPnPMediaRenderer();
        paramUPnPIGD = createUPnPIGD();
        paramHost = createHost();
        paramUUID = createUUID();
        paramUPnPWLANAccessPoint = createUPnPWLANAccessPoint();
        paramProductID = createProductID();
        paramIsSuspended = createIsSuspended();
        paramDeviceSubClass = createDeviceSubClass();
        paramSerialNumber = createSerialNumber();
        paramIsSelfPowered = createIsSelfPowered();
        paramManufacturer = createManufacturer();
        paramVendorID = createVendorID();
        paramUSBVersion = createUSBVersion();
        paramRate = createRate();
        paramDeviceNumber = createDeviceNumber();
        paramUSN = createUSN();
        paramUPnPMediaServer = createUPnPMediaServer();
        paramParent = createParent();
        paramUPnPQoSDevice  = createUPnPQoSDevice ();
        paramDeviceProtocol = createDeviceProtocol();
        paramConfigurationNumberOfEntries = createConfigurationNumberOfEntries();
        paramLocation = createLocation();
        paramServer = createServer();
        paramStatus = createStatus();
        paramMaxChildren = createMaxChildren();
        paramPort = createPort();
        paramDeviceVersion = createDeviceVersion();
        paramEnable = createEnable();
        paramDeviceClass = createDeviceClass();
        paramProductClass = createProductClass();
        paramLeaseTime = createLeaseTime();
        paramUPnPQoSPolicyHolder = createUPnPQoSPolicyHolder();
    }


    /**
     * Serial ID use for serialize.
     */
     private static final long serialVersionUID = 1L;
    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramUPnPMediaRenderer;

    /**
    * Getter method of UPnPMediaRenderer.
    * @return _UPnPMediaRenderer
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUPnPMediaRenderer() {
        return paramUPnPMediaRenderer;
    }

    /**
    * Create the parameter UPnPMediaRenderer
    * @return UPnPMediaRenderer
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUPnPMediaRenderer() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UPnPMediaRenderer");
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
    private com.francetelecom.admindm.model.Parameter paramUPnPIGD;

    /**
    * Getter method of UPnPIGD.
    * @return _UPnPIGD
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUPnPIGD() {
        return paramUPnPIGD;
    }

    /**
    * Create the parameter UPnPIGD
    * @return UPnPIGD
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUPnPIGD() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UPnPIGD");
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
    private com.francetelecom.admindm.model.Parameter paramHost;

    /**
    * Getter method of Host.
    * @return _Host
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamHost() {
        return paramHost;
    }

    /**
    * Create the parameter Host
    * @return Host
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createHost() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Host");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(1024));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramUUID;

    /**
    * Getter method of UUID.
    * @return _UUID
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUUID() {
        return paramUUID;
    }

    /**
    * Create the parameter UUID
    * @return UUID
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUUID() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UUID");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(36));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramUPnPWLANAccessPoint;

    /**
    * Getter method of UPnPWLANAccessPoint.
    * @return _UPnPWLANAccessPoint
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUPnPWLANAccessPoint() {
        return paramUPnPWLANAccessPoint;
    }

    /**
    * Create the parameter UPnPWLANAccessPoint
    * @return UPnPWLANAccessPoint
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUPnPWLANAccessPoint() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UPnPWLANAccessPoint");
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
    private com.francetelecom.admindm.model.Parameter paramProductID;

    /**
    * Getter method of ProductID.
    * @return _ProductID
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamProductID() {
        return paramProductID;
    }

    /**
    * Create the parameter ProductID
    * @return ProductID
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createProductID() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ProductID");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.addCheck(new CheckMaximum(65535));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramIsSuspended;

    /**
    * Getter method of IsSuspended.
    * @return _IsSuspended
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamIsSuspended() {
        return paramIsSuspended;
    }

    /**
    * Create the parameter IsSuspended
    * @return IsSuspended
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createIsSuspended() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "IsSuspended");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.BOOLEAN);
        param.addCheck(CheckBoolean.getInstance());
        param.setValue(Boolean.FALSE);
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDeviceSubClass;

    /**
    * Getter method of DeviceSubClass.
    * @return _DeviceSubClass
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDeviceSubClass() {
        return paramDeviceSubClass;
    }

    /**
    * Create the parameter DeviceSubClass
    * @return DeviceSubClass
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDeviceSubClass() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DeviceSubClass");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setWritable(false);
        return param;
        }

    /**
    *  
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
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramIsSelfPowered;

    /**
    * Getter method of IsSelfPowered.
    * @return _IsSelfPowered
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamIsSelfPowered() {
        return paramIsSelfPowered;
    }

    /**
    * Create the parameter IsSelfPowered
    * @return IsSelfPowered
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createIsSelfPowered() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "IsSelfPowered");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.BOOLEAN);
        param.addCheck(CheckBoolean.getInstance());
        param.setValue(Boolean.FALSE);
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
    private com.francetelecom.admindm.model.Parameter paramVendorID;

    /**
    * Getter method of VendorID.
    * @return _VendorID
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamVendorID() {
        return paramVendorID;
    }

    /**
    * Create the parameter VendorID
    * @return VendorID
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createVendorID() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "VendorID");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.addCheck(new CheckMaximum(65535));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramUSBVersion;

    /**
    * Getter method of USBVersion.
    * @return _USBVersion
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUSBVersion() {
        return paramUSBVersion;
    }

    /**
    * Create the parameter USBVersion
    * @return USBVersion
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUSBVersion() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "USBVersion");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(4));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramRate;

    /**
    * Getter method of Rate.
    * @return _Rate
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamRate() {
        return paramRate;
    }

    /**
    * Create the parameter Rate
    * @return Rate
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createRate() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Rate");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.setValue("");
	    String[] values = {
	                       "Low",
	                       "Full",
	                       "High",
	                       "Super",
                          };
		param.addCheck(new CheckEnum(values));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDeviceNumber;

    /**
    * Getter method of DeviceNumber.
    * @return _DeviceNumber
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDeviceNumber() {
        return paramDeviceNumber;
    }

    /**
    * Create the parameter DeviceNumber
    * @return DeviceNumber
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDeviceNumber() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DeviceNumber");
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
    private com.francetelecom.admindm.model.Parameter paramUSN;

    /**
    * Getter method of USN.
    * @return _USN
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUSN() {
        return paramUSN;
    }

    /**
    * Create the parameter USN
    * @return USN
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUSN() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "USN");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramUPnPMediaServer;

    /**
    * Getter method of UPnPMediaServer.
    * @return _UPnPMediaServer
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUPnPMediaServer() {
        return paramUPnPMediaServer;
    }

    /**
    * Create the parameter UPnPMediaServer
    * @return UPnPMediaServer
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUPnPMediaServer() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UPnPMediaServer");
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
    private com.francetelecom.admindm.model.Parameter paramParent;

    /**
    * Getter method of Parent.
    * @return _Parent
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamParent() {
        return paramParent;
    }

    /**
    * Create the parameter Parent
    * @return Parent
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createParent() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Parent");
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
    private com.francetelecom.admindm.model.Parameter paramUPnPQoSDevice ;

    /**
    * Getter method of UPnPQoSDevice .
    * @return _UPnPQoSDevice 
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUPnPQoSDevice () {
        return paramUPnPQoSDevice ;
    }

    /**
    * Create the parameter UPnPQoSDevice 
    * @return UPnPQoSDevice 
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUPnPQoSDevice () throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UPnPQoSDevice ");
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
    private com.francetelecom.admindm.model.Parameter paramDeviceProtocol;

    /**
    * Getter method of DeviceProtocol.
    * @return _DeviceProtocol
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDeviceProtocol() {
        return paramDeviceProtocol;
    }

    /**
    * Create the parameter DeviceProtocol
    * @return DeviceProtocol
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDeviceProtocol() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DeviceProtocol");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramConfigurationNumberOfEntries;

    /**
    * Getter method of ConfigurationNumberOfEntries.
    * @return _ConfigurationNumberOfEntries
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamConfigurationNumberOfEntries() {
        return paramConfigurationNumberOfEntries;
    }

    /**
    * Create the parameter ConfigurationNumberOfEntries
    * @return ConfigurationNumberOfEntries
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createConfigurationNumberOfEntries() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ConfigurationNumberOfEntries");
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
    private com.francetelecom.admindm.model.Parameter paramLocation;

    /**
    * Getter method of Location.
    * @return _Location
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamLocation() {
        return paramLocation;
    }

    /**
    * Create the parameter Location
    * @return Location
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createLocation() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Location");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramServer;

    /**
    * Getter method of Server.
    * @return _Server
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamServer() {
        return paramServer;
    }

    /**
    * Create the parameter Server
    * @return Server
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createServer() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Server");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
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
        param.setType(ParameterType.STRING);
        param.setValue("");
	    String[] values = {
	                       "LeaseActive",
	                       "LeaseExpired",
	                       "ByebyeReceived",
                          };
		param.addCheck(new CheckEnum(values));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramMaxChildren;

    /**
    * Getter method of MaxChildren.
    * @return _MaxChildren
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamMaxChildren() {
        return paramMaxChildren;
    }

    /**
    * Create the parameter MaxChildren
    * @return MaxChildren
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createMaxChildren() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "MaxChildren");
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
        param.addCheck(new CheckMaximum(255));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDeviceVersion;

    /**
    * Getter method of DeviceVersion.
    * @return _DeviceVersion
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDeviceVersion() {
        return paramDeviceVersion;
    }

    /**
    * Create the parameter DeviceVersion
    * @return DeviceVersion
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDeviceVersion() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DeviceVersion");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.addCheck(new CheckMaximum(65535));
        param.setWritable(false);
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
    private com.francetelecom.admindm.model.Parameter paramDeviceClass;

    /**
    * Getter method of DeviceClass.
    * @return _DeviceClass
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDeviceClass() {
        return paramDeviceClass;
    }

    /**
    * Create the parameter DeviceClass
    * @return DeviceClass
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDeviceClass() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DeviceClass");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setWritable(false);
        return param;
        }

    /**
    *  
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
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramLeaseTime;

    /**
    * Getter method of LeaseTime.
    * @return _LeaseTime
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamLeaseTime() {
        return paramLeaseTime;
    }

    /**
    * Create the parameter LeaseTime
    * @return LeaseTime
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createLeaseTime() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "LeaseTime");
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
    private com.francetelecom.admindm.model.Parameter paramUPnPQoSPolicyHolder;

    /**
    * Getter method of UPnPQoSPolicyHolder.
    * @return _UPnPQoSPolicyHolder
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUPnPQoSPolicyHolder() {
        return paramUPnPQoSPolicyHolder;
    }

    /**
    * Create the parameter UPnPQoSPolicyHolder
    * @return UPnPQoSPolicyHolder
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUPnPQoSPolicyHolder() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UPnPQoSPolicyHolder");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.BOOLEAN);
        param.addCheck(CheckBoolean.getInstance());
        param.setValue(Boolean.FALSE);
        param.setWritable(true);
        return param;
        }


}