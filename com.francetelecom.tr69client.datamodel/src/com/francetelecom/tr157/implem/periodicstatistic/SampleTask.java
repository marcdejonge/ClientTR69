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
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author mpcy8647
 * 
 */
public class SampleTask implements Observer {

	/** The parameter data. */
	private final IParameterData parameterData;

	/** The base path. */
	private final String basePath;

	/** The parameter number of entries parameter. */
	private Parameter parameterNumberOfEntriesParameter;

	/** The sample number. */
	private final long sampleNumber;

	/** The sample parameter task list. */
	private final List sampleParameterTaskList;

	/** The report start time parameter. */
	private Parameter reportStartTimeParameter;

	/** The report end time parameter. */
	private Parameter reportEndTimeParameter;

	/** The fetch samples parameter. */
	private Parameter fetchSamplesParameter;

	/** The status parameter. */
	private Parameter statusParameter;
	
	private Parameter forceSampleParameter;

	/** The start time. */
	private long startTime;

	/** The end time. */
	private long endTime;

	public SampleTask(IParameterData pParameterData, String pBasePath,
			long pSampleNumber) {
		parameterData = pParameterData;
		basePath = pBasePath;
		sampleNumber = pSampleNumber;
		sampleParameterTaskList = new ArrayList();

		parameterNumberOfEntriesParameter = parameterData.getParameter(basePath
				+ "ParameterNumberOfEntries");
		reportStartTimeParameter = parameterData.getParameter(basePath
				+ "ReportStartTime");
		reportEndTimeParameter = parameterData.getParameter(basePath
				+ "ReportEndTime");
		fetchSamplesParameter = parameterData.getParameter(basePath
				+ "FetchSamples");
		statusParameter = parameterData.getParameter(basePath + "Status");
		forceSampleParameter = parameterData.getParameter(basePath + "ForceSample");
		
	}

	/**
	 * <p>
	 * Start.
	 * </p>
	 * <p>
	 * Create a SampleParameterTask for each Parameter.
	 * </p>
	 */
	public void start() {

		forceSampleParameter.addObserver(this);
		long numberOfEntries = ((Long) parameterNumberOfEntriesParameter
				.getValue()).longValue();
		for (long i = 1; i <= numberOfEntries; i++) {
			String parameterBasePath = basePath + "Parameter." + i + ".";

			// create a SampleParameterTask
			Log.debug("create a SampleParameterTask for " + basePath);
			SampleParameterTask sampleParameterTask = new SampleParameterTask(
					parameterData, parameterBasePath);
			sampleParameterTaskList.add(sampleParameterTask);
		}

		startTime = System.currentTimeMillis();

		if (sampleNumber == 1) {
			try {
				reportStartTimeParameter.setValue(new Long(startTime));
			} catch (Fault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (Iterator it = sampleParameterTaskList.iterator(); it.hasNext();) {
			SampleParameterTask task = (SampleParameterTask) it.next();
			task.start();
		}
	}

	/**
	 * Stop.
	 */
	public void stop() {
		forceSampleParameter.deleteObserver(this);
		for (Iterator it = sampleParameterTaskList.iterator(); it.hasNext();) {
			SampleParameterTask task = (SampleParameterTask) it.next();
			task.stop();
		}
		storeSampleValues();
		fetchSample();

	}

	/**
	 * Force sample.
	 */
	private void forceSample() {
		storeSampleValues();
	}

	/**
	 * Store sample values.
	 */
	private void storeSampleValues() {
		endTime = System.currentTimeMillis();
		setCSVValue(basePath + "SampleSeconds", getIntervalTime());
		try {
			reportEndTimeParameter.setValue(new Long(endTime));
		} catch (Fault e) {
			e.printStackTrace();
		}

		for (Iterator it = sampleParameterTaskList.iterator(); it.hasNext();) {
			SampleParameterTask task = (SampleParameterTask) it.next();
			Object value = task.getValue();
			setCSVValue(task.getBasePath() + "Values", value);
			setCSVValue(task.getBasePath() + "SampleSeconds", getIntervalTime());
			setCSVValue(task.getBasePath() + "SuspectData", "0");
		}
	}

	/**
	 * Fetch sample.
	 */
	private void fetchSample() {
		if (isFetchApplicable()) {
			try {
				statusParameter.setValue("Trigger");
				statusParameter.setValueWithout("Enabled");
			} catch (Fault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if is fetch applicable.
	 * 
	 * @return true, if is fetch applicable
	 */
	private boolean isFetchApplicable() {
		long fetch = ((Long) fetchSamplesParameter.getValue()).longValue();
		if (fetch == 0) {
			return false;
		}
		if (sampleNumber%fetch == 0) {
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * Sets the value of the Values parameter.
	 * </p>
	 * <p>
	 * The current value of this parameter is parsed in order to add the new
	 * value in the csv form.
	 * </p>
	 * 
	 * @param parameterName
	 *            the parameter name
	 * @param value
	 *            the value
	 */
	private void setCSVValue(String parameterName, Object value) {
		Parameter valuesParameter = parameterData.getParameter(parameterName);
		String oldValue = (String) valuesParameter.getValue();
		//String[] splittedResult = oldValue.split(";");
		String[] splittedResult;
		try {
			splittedResult = oldValue.split(";");
		} catch (Exception e1) {
			splittedResult = null;
		}
		if (splittedResult != null) {
			if (splittedResult.length == sampleNumber) {
				// forceSample was set to true
				if (sampleNumber != 1) {
					oldValue = splittedResult[0];
					for (int i = 1; i < sampleNumber - 1; i++) {
						oldValue = oldValue + ";" + splittedResult[i];
					}
					oldValue = oldValue + ";" + value.toString();
				} else {
					oldValue = value.toString();
				}
	
			} else {
				if ("".equals(oldValue)) {
					oldValue = value.toString();
				} else {
					oldValue = oldValue + ";" + value.toString();
				}
			}
			try {
				valuesParameter.setValue(oldValue);
			} catch (Fault e) {
				Log.error("Unable to set the new value of " + parameterName, e);
			}
		}
		
	}

	/**
	 * Gets the interval time in seconds.
	 * 
	 * @return the interval time
	 */
	private String getIntervalTime() {
		return Long.toString((endTime - startTime) / 1000);
	}

	public void update(Observable o, Object arg) {
		if (forceSampleParameter.equals(o)) {
			forceSample();
		}

	}

}
