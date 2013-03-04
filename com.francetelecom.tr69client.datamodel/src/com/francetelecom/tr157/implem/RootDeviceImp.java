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
 */
package com.francetelecom.tr157.implem;

import java.lang.reflect.Array;

import org.osgi.service.upnp.UPnPDevice;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.CheckLength;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.RootDevice;

// TODO: Auto-generated Javadoc
/**
 * The Class RootDeviceImp.
 */
public class RootDeviceImp extends RootDevice {

	/** The Constant LEASE_ACTIVE. */
	public static final String LEASE_ACTIVE = "LeaseActive";
	
	/** The Constant BYE_BYE. */
	public static final String BYE_BYE = "ByeByeReceived";

	/** The Constant UUID_PREFIX. */
	private static final String UUID_PREFIX = "uuid:";

	/** The upnp device. */
	private UPnPDevice upnpDevice = null;
	
	/** The manufacturer. */
	private Parameter manufacturerParameter;
	
	/** The friendly name parameter. */
	private Parameter friendlyNameParameter;
	
	/** The model name parameter. */
	private Parameter modelNameParameter;
	
	/** The model number parameter. */
	private Parameter modelNumberParameter;

	/**
	 * Instantiates a new root device imp.
	 *
	 * @param data the data
	 * @param basePath the base path
	 * @param pUPnPDevice the u pn p device
	 */
	public RootDeviceImp(IParameterData data, String basePath,
			UPnPDevice pUPnPDevice) {
		super(data, basePath);
		this.upnpDevice = pUPnPDevice;
		Log.info("create a new UPnP Root device " + basePath);
	}

	/**
	 * Initialize.
	 * <p>
	 * The following actions are performed:
	 * <ul>
	 * <li>set the Status parameter to LeaseActive.</li>
	 * <li>set the UUID parameter using the UDN value.</li>
	 * <li>set the USN parameter using the UDN value and the Type value.</li>
	 * </ul>
	 * The parameters LeaseTime, Location, Server abd Host cann't be set.
	 * </p>
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.tr157.gen.RootDevice#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();

		// status
		getParamStatus().setValue(LEASE_ACTIVE);
		getParamStatus().setStorageMode(StorageMode.COMPUTED);

		// UUID
		String uuid = (String) upnpDevice.getDescriptions(null).get(
				UPnPDevice.UDN);
		// remove uuid: prefix string
		uuid = uuid.substring(UUID_PREFIX.length());
		getParamUUID().setValue(uuid);
		getParamUUID().setStorageMode(StorageMode.COMPUTED);

		// USN
		String usn = (String) upnpDevice.getDescriptions(null).get(
				UPnPDevice.UDN) + "::" 
				+ getDeviceType(upnpDevice);
		getParamUSN().setValue(usn);
		getParamUSN().setStorageMode(StorageMode.COMPUTED);
		
		Parameter deviceSummaryParameter = getData().getParameter(getData().getRoot() + "DeviceSummary");
		String deviceSummary="";
		if (deviceSummaryParameter != null) {
			deviceSummary = (String) deviceSummaryParameter.getValue();
		}
		if (deviceSummary.indexOf("X_ORANGE-COM_UPnP") != -1) {
		
			// manufacturer
			createX_ORANGE_COM_Manufacturer();
			getManufacturerParameter().setValue(upnpDevice.getDescriptions(null).get(UPnPDevice.MANUFACTURER));
			
			// friendlyName
			createX_ORANGE_COM_FriendlyName();
			getFriendlyNameParameter().setValue(upnpDevice.getDescriptions(null).get(UPnPDevice.FRIENDLY_NAME));
			
			// modelName
			createX_ORANGE_COM_ModelName();
			getModelNameParameter().setValue(upnpDevice.getDescriptions(null).get(UPnPDevice.MODEL_NAME));
			
			// modelNumber 
			createX_ORANGE_COM_ModelNumber();
			String modelNumber = (String) upnpDevice.getDescriptions(null).get(UPnPDevice.MODEL_NUMBER);
			if (modelNumber == null) {
				modelNumber = "";
			}
			getModelNumberParameter().setValue(modelNumber);
		}
			

	}
	
	/**
	 * Sets the status.
	 * 
	 * @param status the new status
	 */
	public void setStatus(String status) {
		try {
			getParamStatus().setValue(status);
		} catch (Fault e) {
			Log.error("unable to set status of " + getParamUUID().getValue() + " to " + status);
		}
	}
	
	/**
	 * Gets the manufacturer parameter.
	 *
	 * @return the manufacturer parameter
	 */
	public Parameter getManufacturerParameter() {
		return manufacturerParameter;
	}

	/**
	 * Gets the friendly name parameter.
	 *
	 * @return the friendly name parameter
	 */
	public Parameter getFriendlyNameParameter() {
		return friendlyNameParameter;
	}

	/**
	 * Gets the model name parameter.
	 *
	 * @return the model name parameter
	 */
	public Parameter getModelNameParameter() {
		return modelNameParameter;
	}

	/**
	 * Gets the model number parameter.
	 *
	 * @return the model number parameter
	 */
	public Parameter getModelNumberParameter() {
		return modelNumberParameter;
	}
	
	/**
	 * Creates the x_ orang e_ co m_ manufacturer.
	 *
	 * @throws Fault the fault
	 */
	private void createX_ORANGE_COM_Manufacturer() throws Fault {
        manufacturerParameter = getData().createOrRetrieveParameter(getBasePath() + "X_ORANGE-COM_Manufacturer");
        manufacturerParameter.setNotification(0);
        manufacturerParameter.setStorageMode(StorageMode.DM_ONLY);
        manufacturerParameter.setType(ParameterType.STRING);
        manufacturerParameter.addCheck(new CheckLength(256));
        manufacturerParameter.setValue("");
        manufacturerParameter.setWritable(false);
	}
	
	/**
	 * Creates the x_ orang e_ co m_ friendly name.
	 *
	 * @throws Fault the fault
	 */
	private void createX_ORANGE_COM_FriendlyName() throws Fault {
        friendlyNameParameter = getData().createOrRetrieveParameter(getBasePath() + "X_ORANGE-COM_FriendlyName");
        friendlyNameParameter.setNotification(0);
        friendlyNameParameter.setStorageMode(StorageMode.DM_ONLY);
        friendlyNameParameter.setType(ParameterType.STRING);
        friendlyNameParameter.addCheck(new CheckLength(256));
        friendlyNameParameter.setValue("");
        friendlyNameParameter.setWritable(false);
	}
	
	/**
	 * Creates the x_ orang e_ co m_ model name.
	 *
	 * @throws Fault the fault
	 */
	private void createX_ORANGE_COM_ModelName() throws Fault {
        modelNameParameter = getData().createOrRetrieveParameter(getBasePath() + "X_ORANGE-COM_ModelName");
        modelNameParameter.setNotification(0);
        modelNameParameter.setStorageMode(StorageMode.DM_ONLY);
        modelNameParameter.setType(ParameterType.STRING);
        modelNameParameter.addCheck(new CheckLength(256));
        modelNameParameter.setValue("");
        modelNameParameter.setWritable(false);
	}
	
	/**
	 * Creates the x_ orang e_ co m_ model number.
	 *
	 * @throws Fault the fault
	 */
	private void createX_ORANGE_COM_ModelNumber() throws Fault {
        modelNumberParameter = getData().createOrRetrieveParameter(getBasePath() + "X_ORANGE-COM_ModelNumber");
        modelNumberParameter.setNotification(0);
        modelNumberParameter.setStorageMode(StorageMode.DM_ONLY);
        modelNumberParameter.setType(ParameterType.STRING);
        modelNumberParameter.addCheck(new CheckLength(256));
        modelNumberParameter.setValue("");
        modelNumberParameter.setWritable(false);
	}
	
	
	private static String getDeviceType(final UPnPDevice device) {
		Object type = device.getDescriptions(null).get(UPnPDevice.TYPE);
		
		if (type != null) {
			if (type.getClass().isArray()) {
				String typeString = "";
				for(int i = 0; i < Array.getLength(type); i++) {
					Object arrayItem = Array.get(type, i);
					if (arrayItem != null) {
						typeString = typeString + arrayItem.toString();
					}
				}
				return typeString;
			} else {
				return type.toString();
			}
		} else {
			return "";
		}
	}
	
	

}
