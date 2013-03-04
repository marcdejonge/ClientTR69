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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;

/**
 * @author mpcy8647
 * 
 */
public class SampleParameterTask implements Observer {

	/** The Constant CURRENT. */
	private static final String CURRENT = "Current";

	/** The Constant CHANGE. */
	private static final String CHANGE = "Change";

	/** The Constant LATEST. */
	private static final String LATEST = "Latest";

	/** The Constant MINIMUM. */
	private static final String MINIMUM = "Minimum";

	/** The Constant MAXIMUM. */
	private static final String MAXIMUM = "Maximum";

	/** The Constant AVERAGE. */
	private static final String AVERAGE = "Average";

	/** The base path. */
	private final String basePath;

	/** The parameter data. */
	private final IParameterData parameterData;

	/** The observed parameter. */
	private final Parameter observedParameter;

	/** The calculation mode parameter. */
	private final Parameter calculationModeParameter;

	/** The sample mode parameter. */
	private final Parameter sampleModeParameter;

	/** The values. */
	private final List values;

	/**
	 * Instantiates a new sample parameter task.
	 * 
	 * @param pParameterData
	 *            the parameter data
	 * @param pBasePath
	 *            the base path
	 */
	public SampleParameterTask(IParameterData pParameterData, String pBasePath) {
		basePath = pBasePath;
		parameterData = pParameterData;
		values = new ArrayList();

		calculationModeParameter = parameterData.getParameter(basePath
				+ "CalculationMode");
		sampleModeParameter = parameterData.getParameter(basePath
				+ "SampleMode");

		Parameter referenceParameter = parameterData.getParameter(basePath
				+ "Reference");
		String referenceValue = (String) referenceParameter.getValue();
		observedParameter = parameterData.getParameter(referenceValue);

	}

	/**
	 * Start.
	 */
	public void start() {
		// add observer
		String sampleMode = CURRENT;
		if (sampleModeParameter != null) {
			sampleMode = (String) sampleModeParameter.getValue();
		}

		if ((observedParameter != null) && (CHANGE.equals(sampleMode))) {
			observedParameter.addObserver(this);
		}

	}

	/**
	 * Stop.
	 */
	public void stop() {
		if (observedParameter != null) {
			// remove observer
			observedParameter.deleteObserver(this);
		}
	}

	/**
	 * <p>
	 * Gets the value.
	 * </p>
	 * <p>
	 * Two cases:
	 * <ul>
	 * <li>the values list is empty, the current value of the observed
	 * parameter is returned.</li>
	 * <li>the values list isn't empty, then the method returns the computed
	 * value according to the value of the CalculationMode parameter.</li>
	 * </ul>
	 * </p>
	 * 
	 * @return the value
	 */
	public Object getValue() {
		if (values.isEmpty()) {
			return observedParameter.getValue();
		} else {
			// depending on the value of CalculationMode
			String calculationMode = (String) calculationModeParameter
					.getValue();
			if (LATEST.equals(calculationMode)) {
				return values.get(values.size() - 1);
			} else if (MINIMUM.equals(calculationMode)) {
				return Collections.min(values);
			} else if (MAXIMUM.equals(calculationMode)) {
				return Collections.max(values);
			} else if (AVERAGE.equals(calculationMode)) {
				Class objectClass = values.get(0).getClass();
				if (Integer.class.equals(objectClass)) {
					int average = 0;
					for (Iterator it = values.iterator(); it.hasNext();) {
						average = average + ((Integer) it.next()).intValue();
					}
					return new Integer(average / values.size());
				} else if (Long.class.equals(objectClass)) {
					long average = 0;
					for (Iterator it = values.iterator(); it.hasNext();) {
						average = average + ((Long) it.next()).longValue();
					}
					return new Long(average / values.size());
				} else if (Float.class.equals(objectClass)) {
					float average = 0;
					for (Iterator it = values.iterator(); it.hasNext();) {
						average = average + ((Float) it.next()).floatValue();
					}
					return new Float(average / values.size());
				} else if (Double.class.equals(objectClass)) {
					double average = 0;
					for (Iterator it = values.iterator(); it.hasNext();) {
						average = average + ((Double) it.next()).doubleValue();
					}
					return new Double(average / values.size());
				}

			}
		}
		return null;
	}

	/**
	 * <p>
	 * Update.
	 * </p>
	 * <p>
	 * The new value is added in the values list.
	 * </p>
	 * 
	 * @param parameter
	 *            the parameter
	 * @param value
	 *            the value
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable observable, Object value) {
		Parameter param = (Parameter) observable;
		values.add(param.getValue());
	}
	
	public String getBasePath() {
		return basePath;
	}

}
