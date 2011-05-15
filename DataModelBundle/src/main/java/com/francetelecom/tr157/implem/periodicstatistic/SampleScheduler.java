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
public class SampleScheduler implements Runnable, Observer {

	/** The parameter data. */
	private final IParameterData parameterData;

	/** The base path of the SampleSet. */
	private final String basePath;

	/** The sample interval parameter. */
	private Parameter sampleIntervalParameter;

	/** The report samples parameter. */
	private Parameter reportSamplesParameter;

	/** The fetch samples parameter. */
	private Parameter fetchSamplesParameter;

	/** The status parameter. */
	private Parameter statusParameter;

	/** The thread. */
	private Thread thread;

	/** The stop. */
	private boolean stop = false;

	/** The report sample. */
	private long reportSample;

	/**
	 * Instantiates a new sample scheduler.
	 * 
	 * @param pParameterData
	 *            the parameter data
	 * @param pBasePath
	 *            the base path
	 */
	public SampleScheduler(IParameterData pParameterData, String pBasePath) {
		parameterData = pParameterData;
		basePath = pBasePath;

		Log.debug("SampleScheduler constructor time = " + System.currentTimeMillis());
		// get parameters
		reportSamplesParameter = parameterData.getParameter(basePath
				+ "ReportSamples");
		sampleIntervalParameter = parameterData.getParameter(basePath
				+ "SampleInterval");
		fetchSamplesParameter = parameterData.getParameter(basePath
				+ "FetchSamples");
		statusParameter = parameterData.getParameter(basePath + "Status");
		Log.debug("ReportSamplesParameter = " + reportSamplesParameter); 
		Log.debug("SampleIntervalParameter = " + sampleIntervalParameter); 
		Log.debug("FetchSamplesParameter = " + fetchSamplesParameter); 
		Log.debug("StatusParameter = " + statusParameter);
	}

	/**
	 * <p>
	 * Start
	 * </p>
	 * <p>
	 * The following actions are performed:
	 * <ul>
	 * <li>adds observers on the parameters.</li>
	 * <li>start a new thread.</li>
	 * </ul>
	 * </p>
	 */
	public void start(long pReportSample) {
		reportSample = pReportSample;

		// adds observers
		sampleIntervalParameter.addObserver(this);
		reportSamplesParameter.addObserver(this);
		fetchSamplesParameter.addObserver(this);

		stop = false;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		if (!stop) {
			stop = true;
			thread.interrupt();
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sampleIntervalParameter.deleteObserver(this);
			reportSamplesParameter.deleteObserver(this);
			fetchSamplesParameter.deleteObserver(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		if (reportSample == -1) {
			reportSample = ((Long) reportSamplesParameter.getValue())
					.longValue();
		}
		long sampleInterval = ((Long) sampleIntervalParameter.getValue())
				.longValue();
		try {
			statusParameter.setValue("Enabled");

			for (long i = 0; i < reportSample; i++) {
				Log.debug("start " + i + "th sampleTask " + basePath);
				SampleTask sampleTask = new SampleTask(parameterData, basePath,
						i + 1);
				sampleTask.start();

				try {
					Thread.sleep(sampleInterval * 1000);
				} catch (InterruptedException e) {
				}

				sampleTask.stop();
				sampleTask = null;
				if (stop) {
					return;
				}
			}

			statusParameter.setValue("Disabled");
		} catch (Fault e1) {
			e1.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable arg0, Object arg1) {
		Parameter param = (Parameter) arg0;
		if (param.equals(reportSamplesParameter)) {
			onChangeReportSamplesParameter(param);
		} else if (param.equals(sampleIntervalParameter)) {
			onChangeSampleIntervalParameter(param);
		} else if (param.equals(fetchSamplesParameter)) {
			onChangeFetchSamplesParameter(param);
		}

	}

	/**
	 * <p>
	 * Discard all values.
	 * </p>
	 * <p>
	 * The following actions are performed:
	 * <ul>
	 * <li>set empty the value of the ReportStartTime parameter.</li>
	 * <li>set empty the value of the ReportEndTime parameter.</li>
	 * <li>set empty the value of the SampleSeconds parameter.</li>
	 * <li>for each .Parameter.{i}. hosted by the SampleSet:
	 * <ul>
	 * <li>set empty the value of the SampleSeconds parameter.</li>
	 * <li>set empty the value of the SuspectData parameter.</li>
	 * <li>set empty the value of the Values parameter.</li>
	 * <li>set 0 the value of the Failures parameter.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * </p>
	 * 
	 */
	private void discardAllValues() {
		// ReportStartTime parameter
		Parameter reportStartTimeParameter = parameterData
				.getParameter(basePath + "ReportStartTime");
		reportStartTimeParameter.setValueWithout("");

		// ReportEndTime parameter
		Parameter reportEndTimeParameter = parameterData.getParameter(basePath
				+ "ReportEndTime");
		reportEndTimeParameter.setValueWithout("");

		// SampleSecondsParameter
		Parameter sampleSecondsParameter = parameterData.getParameter(basePath
				+ "SampleSeconds");
		sampleSecondsParameter.setValueWithout("");

		// get ParameterNumberOfEntries parameter value
		Parameter parameterNumberOfEntriesParameter = parameterData
				.getParameter(basePath + "ParameterNumberOfEntries");
		long parameterNumberOfEntries = ((Long) parameterNumberOfEntriesParameter
				.getValue()).longValue();
		for (long i = 1; i <= parameterNumberOfEntries; i++) {
			Parameter sampleSeconds = parameterData.getParameter(basePath
					+ "Parameter." + i + ".SampleSeconds");
			sampleSeconds.setValueWithout("");
			Parameter suspectData = parameterData.getParameter(basePath
					+ "Parameter." + i + ".SuspectData");
			suspectData.setValueWithout("");
			Parameter valuesParameter = parameterData.getParameter(basePath
					+ "Parameter." + i + ".Values");
			valuesParameter.setValueWithout("");
			Parameter failuresParameter = parameterData.getParameter(basePath
					+ "Parameter." + i + ".Failures");
			failuresParameter.setValueWithout(new Long(0));
		}

	}

	/**
	 * On change fetch samples parameter.
	 * 
	 * @param param
	 *            the param
	 */
	private void onChangeFetchSamplesParameter(Parameter param) {
	}

	/**
	 * On change sample interval parameter.
	 * 
	 * @param param
	 *            the param
	 */
	private void onChangeSampleIntervalParameter(Parameter param) {
		String status = (String) statusParameter.getValue();
		if ("Enabled".equals(status)) {
			stop();
			discardAllValues();
			start(-1);
		}

	}

	/**
	 * On change report samples parameter.
	 * 
	 * @param param
	 *            the param
	 */
	private void onChangeReportSamplesParameter(Parameter param) {
		// check the status
		String status = (String) statusParameter.getValue();
		if ("Enabled".equals(status)) {
			long newReportSample = ((Long) param.getValue()).longValue();
			reportSample = newReportSample;

		}

	}

}
