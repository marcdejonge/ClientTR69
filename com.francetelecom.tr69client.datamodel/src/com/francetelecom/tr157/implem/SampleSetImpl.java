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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.francetelecom.admindm.api.GetterForList;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Parameter;
import com.francetelecom.tr157.gen.SampleSet;
import com.francetelecom.tr157.implem.periodicstatistic.EnableParameterSetter;
import com.francetelecom.tr157.implem.periodicstatistic.ForceSampleGetter;
import com.francetelecom.tr157.implem.periodicstatistic.ForceSampleSetter;
import com.francetelecom.tr157.implem.periodicstatistic.SampleIntervalParameterChecker;
import com.francetelecom.tr157.implem.periodicstatistic.SampleScheduler;

/**
 * @author mpcy8647
 * 
 */
public class SampleSetImpl extends SampleSet implements Observer {

	/** The Constant DISABLED. */
	public static final String DISABLED = "Disabled";

	/** The Constant ENABLED. */
	public static final String ENABLED = "Enabled";

	/** The Constant TRIGGER. */
	public static final String TRIGGER = "Trigger";

	/** The parameter. */
	public List parameterList;
	
	/** The sample scheduler. */
	private SampleScheduler sampleScheduler;

	/**
	 * Instantiates a new sample set impl.
	 * 
	 * @param pData
	 *            the data
	 * @param pBasePath
	 *            the base path
	 */
	public SampleSetImpl(IParameterData pData, String pBasePath) {
		super(pData, pBasePath);
		parameterList = new ArrayList();
		
	}

	/**
	 * <p>
	 * Initialize.
	 * </p>
	 * <p>
	 * The following actions are performed :
	 * <ul>
	 * <li>adds a specific setter on the Enable parameter.</li>
	 * <li>sets default value for the Status parameter.</li>
	 * <li>adds a checker for the Status parameter.</li>
	 * <li>adds a checker for the SampleInterval parameter.</li>
	 * <li>adds a checker for the ReportSamples parameter.</li>
	 * <li>sets the getter for the ForceSample parameter.</li>
	 * <li>sets the setter for the ForceSample parameter.</li>
	 * <li>add an Observer on the Enable parameter.</li>
	 * </ul>
	 * </p>
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.tr157.gen.SampleSet#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();
		Log.debug("SampleSetImpl initialise time = " + System.currentTimeMillis());
		
		com.francetelecom.admindm.model.Parameter root = getData().createOrRetrieveParameter(getBasePath());
		root.setWritable(true);

		getParamName().setMandatoryNotification(true);
		
		// adds a setter to the Enable parameter
		getParamEnable().setSetter(new EnableParameterSetter());

		// sets Status parameter default value
		getParamStatus().setValueWithout(DISABLED);

		// adds checker to the SampleInterval parameter
		getParamSampleInterval().addCheck(
				new SampleIntervalParameterChecker(getData()));
		// set the default value of the SampleInterval parameter
		getParamSampleInterval().setValue(new Long(3600));

		// adds checker to the ReportSamples parameter
		getParamReportSamples().setDirectValue(new Long(24));

		// add a GetterForList for ParameterNumberOfEntries
		getParamParameterNumberOfEntries().setGetter(
				new GetterForList(parameterList, ParameterType.UINT));

		// sets getter for ForceSample
		getParamForceSample().setGetter(new ForceSampleGetter());
		getParamForceSample().setSetter(new ForceSampleSetter());

		// set -1 to time reference
		getParamTimeReference().setValue(new Long(-1));
		
		sampleScheduler = new SampleScheduler(getData(), getBasePath());
		
		// add observer on the Enable parameter
		getParamEnable().addObserver(this);
		

		

	}

	/**
	 * Adds the parameter.
	 * 
	 * TODO : add a parameter via the AddObject method.
	 */
	public ParameterImpl addParameter() {
		ParameterImpl parameter = new ParameterImpl(getData(), getBasePath()
				+ "Parameter." + (parameterList.size() + 1) + ".");
		try {
			parameter.initialize();
			parameterList.add(parameter);
		} catch (Fault e) {
			e.printStackTrace();
			parameter = null;
		}
		return parameter;

	}

	/**
	 * <p>
	 * Discard all samples.
	 * </p>
	 * <p>
	 * The following actions are performed:
	 * <ul>
	 * <li>discards the value of the ReportStartTime parameter.</li>
	 * <li>discards the value of the ReportEndTime parameter.</li>
	 * <li>discards the value of the SampleSeconds parameter.</li>
	 * <li>discards the value of the .Parameter.{i}.SampleSeconds.</li>
	 * <li>discards the value of the .Parameter.{i}.SuspectData.</li>
	 * <li>discards the value of the .Parameter.{i}.Values.</li>
	 * <li>discards the value of the .Parameter.{i}.Failures.</li>
	 * </ul>
	 * </p>
	 * 
	 */
	public void discardAllSamples() throws Fault {
		getParamReportStartTime().setValue("");
		getParamReportEndTime().setValue("");
		getParamSampleSeconds().setValue("");
		for(Iterator it = parameterList.iterator(); it.hasNext();) {
			ParameterImpl parameterImpl = (ParameterImpl) it.next();
			parameterImpl.getParamSampleSeconds().setValue("");
			parameterImpl.getParamSuspectData().setValue("");
			parameterImpl.getParamValues().setValue("");
			parameterImpl.getParamFailures().setValue(new Long(0));
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable arg0, Object arg1) {
		com.francetelecom.admindm.model.Parameter parameter = (com.francetelecom.admindm.model.Parameter) arg0;
		if (getParamEnable().equals(parameter)) {
			if (Boolean.TRUE.equals(getParamEnable().getValue())) {
				Log.info("Enable SampleSet " + getBasePath());
				startSampleScheduler();
			} else {
				Log.info("Disable SampleSet " + getBasePath());
				stopSampleScheduler();
			}
		}
		
	}
	
	/**
	 * Start sample scheduler.
	 */
	private void startSampleScheduler() {
		// TODO synchronize with reference time parameter
		sampleScheduler.start(-1);
	}
	
	/**
	 * Stop sample scheduler.
	 */
	private void stopSampleScheduler() {
		sampleScheduler.stop();
	}

}
