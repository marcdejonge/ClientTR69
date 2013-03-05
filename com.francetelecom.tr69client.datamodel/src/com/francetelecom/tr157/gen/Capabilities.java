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
* Class Capabilities.
* @author OrangeLabs R&D
*/
public class Capabilities  {
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
     public Capabilities(
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

        paramUPnPMediaServer = createUPnPMediaServer();
        paramUPnPMediaRenderer = createUPnPMediaRenderer();
        paramUPnPBasicDevice = createUPnPBasicDevice();
        paramUPnPIGD = createUPnPIGD();
        paramMediaCollectionProfileID = createMediaCollectionProfileID();
        paramAVClassProfileID = createAVClassProfileID();
        paramUPnPWLANAccessPoint = createUPnPWLANAccessPoint();
        paramHIDDeviceClass = createHIDDeviceClass();
        paramDeviceCapability = createDeviceCapability();
        paramUPnPArchitecture = createUPnPArchitecture();
        paramUPnPQoSDevice = createUPnPQoSDevice();
        paramHNDDeviceClass = createHNDDeviceClass();
        paramAudioClassProfileID = createAudioClassProfileID();
        paramPrinterClassProfileID = createPrinterClassProfileID();
        paramUPnPQoSPolicyHolder = createUPnPQoSPolicyHolder();
        paramImageClassProfileID = createImageClassProfileID();
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
    private com.francetelecom.admindm.model.Parameter paramUPnPBasicDevice;

    /**
    * Getter method of UPnPBasicDevice.
    * @return _UPnPBasicDevice
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUPnPBasicDevice() {
        return paramUPnPBasicDevice;
    }

    /**
    * Create the parameter UPnPBasicDevice
    * @return UPnPBasicDevice
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUPnPBasicDevice() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UPnPBasicDevice");
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
    private com.francetelecom.admindm.model.Parameter paramMediaCollectionProfileID;

    /**
    * Getter method of MediaCollectionProfileID.
    * @return _MediaCollectionProfileID
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamMediaCollectionProfileID() {
        return paramMediaCollectionProfileID;
    }

    /**
    * Create the parameter MediaCollectionProfileID
    * @return MediaCollectionProfileID
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createMediaCollectionProfileID() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "MediaCollectionProfileID");
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
    private com.francetelecom.admindm.model.Parameter paramAVClassProfileID;

    /**
    * Getter method of AVClassProfileID.
    * @return _AVClassProfileID
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamAVClassProfileID() {
        return paramAVClassProfileID;
    }

    /**
    * Create the parameter AVClassProfileID
    * @return AVClassProfileID
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createAVClassProfileID() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "AVClassProfileID");
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
    private com.francetelecom.admindm.model.Parameter paramHIDDeviceClass;

    /**
    * Getter method of HIDDeviceClass.
    * @return _HIDDeviceClass
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamHIDDeviceClass() {
        return paramHIDDeviceClass;
    }

    /**
    * Create the parameter HIDDeviceClass
    * @return HIDDeviceClass
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createHIDDeviceClass() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "HIDDeviceClass");
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
    private com.francetelecom.admindm.model.Parameter paramDeviceCapability;

    /**
    * Getter method of DeviceCapability.
    * @return _DeviceCapability
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDeviceCapability() {
        return paramDeviceCapability;
    }

    /**
    * Create the parameter DeviceCapability
    * @return DeviceCapability
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDeviceCapability() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DeviceCapability");
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
    private com.francetelecom.admindm.model.Parameter paramUPnPArchitecture;

    /**
    * Getter method of UPnPArchitecture.
    * @return _UPnPArchitecture
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUPnPArchitecture() {
        return paramUPnPArchitecture;
    }

    /**
    * Create the parameter UPnPArchitecture
    * @return UPnPArchitecture
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUPnPArchitecture() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UPnPArchitecture");
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
    private com.francetelecom.admindm.model.Parameter paramUPnPQoSDevice;

    /**
    * Getter method of UPnPQoSDevice.
    * @return _UPnPQoSDevice
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUPnPQoSDevice() {
        return paramUPnPQoSDevice;
    }

    /**
    * Create the parameter UPnPQoSDevice
    * @return UPnPQoSDevice
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUPnPQoSDevice() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UPnPQoSDevice");
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
    private com.francetelecom.admindm.model.Parameter paramHNDDeviceClass;

    /**
    * Getter method of HNDDeviceClass.
    * @return _HNDDeviceClass
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamHNDDeviceClass() {
        return paramHNDDeviceClass;
    }

    /**
    * Create the parameter HNDDeviceClass
    * @return HNDDeviceClass
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createHNDDeviceClass() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "HNDDeviceClass");
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
    private com.francetelecom.admindm.model.Parameter paramAudioClassProfileID;

    /**
    * Getter method of AudioClassProfileID.
    * @return _AudioClassProfileID
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamAudioClassProfileID() {
        return paramAudioClassProfileID;
    }

    /**
    * Create the parameter AudioClassProfileID
    * @return AudioClassProfileID
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createAudioClassProfileID() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "AudioClassProfileID");
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
    private com.francetelecom.admindm.model.Parameter paramPrinterClassProfileID;

    /**
    * Getter method of PrinterClassProfileID.
    * @return _PrinterClassProfileID
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamPrinterClassProfileID() {
        return paramPrinterClassProfileID;
    }

    /**
    * Create the parameter PrinterClassProfileID
    * @return PrinterClassProfileID
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createPrinterClassProfileID() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "PrinterClassProfileID");
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
    private com.francetelecom.admindm.model.Parameter paramImageClassProfileID;

    /**
    * Getter method of ImageClassProfileID.
    * @return _ImageClassProfileID
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamImageClassProfileID() {
        return paramImageClassProfileID;
    }

    /**
    * Create the parameter ImageClassProfileID
    * @return ImageClassProfileID
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createImageClassProfileID() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ImageClassProfileID");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(false);
        return param;
        }


}