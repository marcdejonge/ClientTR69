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

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Capabilities;

/**
 * The Class CapabilitiesImp.
 */
public class CapabilitiesImp extends Capabilities {

	/**
	 * Instantiates a new capabilities imp.
	 * 
	 * @param data
	 *            the data
	 * @param basePath
	 *            the base path
	 */
	public CapabilitiesImp(IParameterData data, String basePath) {
		super(data, basePath);
	}

	/**
	 * Initialize.
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.tr157.gen.Capabilities#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();
		// set default values for Capabilities parameters.
		getParamUPnPArchitecture().setValue(new Long(0));
		getParamUPnPMediaServer().setValue(new Long(0));
		getParamUPnPMediaRenderer().setValue(new Long(0));
		getParamUPnPWLANAccessPoint().setValue(new Long(0));
		getParamUPnPBasicDevice().setValue(new Long(0));
		getParamUPnPQoSDevice().setValue(new Long(0));
		getParamUPnPQoSPolicyHolder().setValue(new Long(0));
		getParamUPnPIGD().setValue(new Long(0));
	}
}
