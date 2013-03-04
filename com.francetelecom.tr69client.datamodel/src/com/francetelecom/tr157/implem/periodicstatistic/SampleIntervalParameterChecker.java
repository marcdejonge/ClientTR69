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

import com.francetelecom.admindm.api.CheckCallBack;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author mpcy8647
 * 
 */
public class SampleIntervalParameterChecker implements CheckCallBack {

	/** The Constant MIN_SAMPLE_INTERVAL_PATH. */
	private static final String MIN_SAMPLE_INTERVAL_PATH = "PeriodicStatistics.MinSampleInterval";

	/** The parameter data. */
	private final IParameterData parameterData;

	/**
	 * Instantiates a new sample interval parameter checker.
	 * 
	 * @param pParameterData
	 *            the parameter data
	 */
	public SampleIntervalParameterChecker(IParameterData pParameterData) {
		parameterData = pParameterData;
	}

	/**
	 * <p>
	 * Check.
	 * </p>
	 * <p>
	 * The new value is rejected if it is less than the
	 * .PeriodicStatistics.MinSampleInterval.
	 * </p>
	 * 
	 * 
	 * @param arg0
	 *            the arg0
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.admindm.api.CheckCallBack#check(java.lang.Object)
	 */
	public void check(Object arg0) throws Fault {
		Long newValue = (Long) arg0;

		// get the value of the MinSampleInterval
		Parameter minSampleInterval = parameterData.getParameter(parameterData
				.getRoot()
				+ MIN_SAMPLE_INTERVAL_PATH);
		if (minSampleInterval != null) {
			Long minValue = (Long) minSampleInterval.getValue();
			if (newValue.longValue() < minValue.longValue()) {
				throw new Fault(
						9007,
						"The new value of SampleInterval is less than the value of the MinSampleInterval.");
			}

		} else {
			throw new Fault(9007,
					"Unable to get the MinSampleInterval parameter.");
		}

	}

}
