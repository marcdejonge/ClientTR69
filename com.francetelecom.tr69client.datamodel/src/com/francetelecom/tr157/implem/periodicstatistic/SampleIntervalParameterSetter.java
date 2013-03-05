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
package com.francetelecom.tr157.implem.periodicstatistic;

import com.francetelecom.admindm.api.Setter;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author mpcy8647
 * 
 */
public class SampleIntervalParameterSetter implements Setter {

	/** The parameter data. */
	private final IParameterData parameterData;

	/** The base path. */
	private final String basePath;

	public SampleIntervalParameterSetter(IParameterData pParameterData,
			String pBasePath) {
		parameterData = pParameterData;
		basePath = pBasePath;
	}

	/**
	 * <p>
	 * Sets the parameter.
	 * </p>
	 * <p>
	 * The following actions are performed:
	 * <ul>
	 * <li>discards any stored samples if the Enable parameter equals true.</li>
	 * <li>starts the first sample interval if the Enable parameter equals true.</li>
	 * </ul>
	 * </p>
	 * 
	 * 
	 * 
	 * @param parameter
	 *            the parameter
	 * @param value
	 *            the value
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.admindm.api.Setter#set(com.francetelecom.admindm.model.Parameter,
	 *      java.lang.Object)
	 */
	public void set(Parameter parameter, Object value) throws Fault {
		Parameter enableParameter = parameterData.getParameter(basePath
				+ "Enable");
		if (enableParameter != null) {
			Boolean enableValue = (Boolean) enableParameter.getValue();
			if (Boolean.TRUE.equals(enableValue)) {
				// discard any stored samples
				// ReportStartTime parameter
				
				
				// start the first sample
			}
		}
	}

}
