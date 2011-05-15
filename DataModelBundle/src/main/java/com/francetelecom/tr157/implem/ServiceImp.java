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

import org.osgi.service.upnp.UPnPService;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Service;

public class ServiceImp extends Service {

	/** The Constant LEASE_ACTIVE. */
	public static final String LEASE_ACTIVE = "LeaseActive";

	/** The Constant BYE_BYE. */
	public static final String BYE_BYE = "ByeByeReceived";

	/** The upnp service. */
	private final UPnPService upnpService;

	/** The device uuid. */
	private final String deviceUuid;

	/**
	 * Instantiates a new service imp.
	 * 
	 * @param data
	 *            the data
	 * @param basePath
	 *            the base path
	 * @param pUPnPService
	 *            the u pn p service
	 * @param pDeviceUuid
	 *            the device uuid
	 */
	public ServiceImp(IParameterData data, String basePath,
			UPnPService pUPnPService, String pDeviceUuid) {
		super(data, basePath);
		this.upnpService = pUPnPService;
		this.deviceUuid = pDeviceUuid;
	}

	/**
	 * Initialize.
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.tr157.gen.Service#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();

		// status
		getParamStatus().setValue(LEASE_ACTIVE);
		getParamStatus().setStorageMode(StorageMode.COMPUTED);

		// usn
		String usn = this.deviceUuid + "::" + upnpService.getType();
		getParamUSN().setValue(usn);
		getParamUSN().setStorageMode(StorageMode.COMPUTED);
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
			Log.error("unable to set the status parameter of "
					+ getParamUSN().getValue() + " to " + status, e);
		}
	}

}
