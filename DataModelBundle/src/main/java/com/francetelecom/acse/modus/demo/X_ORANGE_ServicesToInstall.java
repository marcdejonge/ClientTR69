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
 */
package com.francetelecom.acse.modus.demo;

import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author mpcy8647
 * 
 */
public class X_ORANGE_ServicesToInstall {

	/** The Services to install. */
	private Parameter servicesToInstall;

	/** The base path. */
	private final String basePath;

	/** The parameter data. */
	private final IParameterData parameterData;

	public X_ORANGE_ServicesToInstall(IParameterData pParameterData,
			String pBasePath) {
		parameterData = pParameterData;
		basePath = pBasePath;
	}

	/**
	 * Initialize.
	 * 
	 * @throws Fault
	 *             the fault
	 */
	public void initialize() throws Fault {
		createServicesToInstall();
	}

	/**
	 * Gets the services to install.
	 * 
	 * @return the services to install
	 */
	public Parameter getServicesToInstall() {
		return servicesToInstall;
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 */
	public IParameterData getData() {
		return parameterData;
	}

	/**
	 * Creates the services to install.
	 * 
	 * @throws Fault
	 *             the fault
	 */
	private void createServicesToInstall() throws Fault {
		servicesToInstall = parameterData
				.createOrRetrieveParameter(parameterData.getRoot()
						+ "X_ORANGE_ServicesToInstall");
		servicesToInstall.setType(ParameterType.STRING);
		servicesToInstall.setValue("");
		servicesToInstall.setStorageMode(StorageMode.DM_ONLY);
		servicesToInstall.setWritable(true);
		servicesToInstall.setNotification(2);
		servicesToInstall.setActiveNotificationDenied(false);

	}

}
