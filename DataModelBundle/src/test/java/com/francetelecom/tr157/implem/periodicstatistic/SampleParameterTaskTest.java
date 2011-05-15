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

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Parameter;
import com.francetelecom.tr157.implem.ParameterImpl;
import com.francetelecom.tr157.implem.PeriodicStatisticsImpl;
import com.francetelecom.tr157.implem.SampleSetImpl;

import junit.framework.TestCase;

public class SampleParameterTaskTest extends TestCase {

	/** The Constant ROOT. */
	public static final String ROOT = "Device.";

	/** The parameter data. */
	private IParameterData parameterData;

	/** The sample parameter task. */
	private SampleParameterTask sampleParameterTask;
	
	/** The observed parameter. */
	private com.francetelecom.admindm.model.Parameter observedParameter;
	
	/** The parameter. */
	private Parameter parameter;

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		parameterData = new ParameterData();
		parameterData.setRoot(ROOT);
		observedParameter = parameterData.createOrRetrieveParameter("Device.Test");
		observedParameter.setType(ParameterType.INT);
		observedParameter.setValue(new Integer(10));
		System.out.println("observed parameter = " + observedParameter.getName());
		
		new PeriodicStatisticsImpl(parameterData, parameterData.getRoot() + "PeriodicStatistics.").initialize();
		SampleSetImpl sampleSet = new SampleSetImpl(parameterData, parameterData.getRoot() + "PeriodicStatistics.SampleSet.1.");
		sampleSet.initialize();
		parameter = sampleSet.addParameter();
//		parameter = new ParameterImpl(parameterData, parameterData.getRoot() + "PeriodicStatistics.SampleSet.1.Parameter.1.");
//		parameter.initialize();
		System.out.println(parameter.getParamReference());
		parameter.getParamReference().setValue(observedParameter.getName());
		
		
	}

	/**
	 * Test create_ simple.
	 */
	public void testCreate_Simple_Current_Latest() {
		sampleParameterTask = new SampleParameterTask(parameterData,
				parameterData.getRoot()
						+ "PeriodicStatistics.SampleSet.1.Parameter.1.");
		assertTrue(parameter.getParamFailures().getValue().equals(new Long(0)));
		sampleParameterTask.start();
		sampleParameterTask.stop();
		assertTrue(new Integer(10).equals(sampleParameterTask.getValue()));
		assertTrue(parameter.getParamFailures().getValue().equals(new Long(0)));
	}
	
	/**
	 * Test create_ advanced_ change_ maximum.
	 */
	public void testCreate_Advanced_Change_Maximum() {
		try {
			parameter.getParamSampleMode().setValue("Change");
			parameter.getParamCalculationMode().setValue("Maximum");
		} catch (Fault e) {
		}
		assertTrue(parameter.getParamFailures().getValue().equals(new Long(0)));
		sampleParameterTask = new SampleParameterTask(parameterData,
				parameterData.getRoot()
						+ "PeriodicStatistics.SampleSet.1.Parameter.1.");
		sampleParameterTask.start();
		
		try {
			observedParameter.setValue(new Integer(9));
			observedParameter.setValue(new Integer(8));
			observedParameter.setValue(new Integer(7));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sampleParameterTask.stop();
		assertTrue(new Integer(9).equals(sampleParameterTask.getValue()));
		assertTrue(parameter.getParamFailures().getValue().equals(new Long(0)));
		System.out.println(sampleParameterTask.getValue());
	}
	
	public void testCreate_Current_Maximum() {
		try {
			parameter.getParamSampleMode().setValue("Current");
			parameter.getParamCalculationMode().setValue("Maximum");
		} catch (Fault e) {
		}
		sampleParameterTask = new SampleParameterTask(parameterData,
				parameterData.getRoot()
						+ "PeriodicStatistics.SampleSet.1.Parameter.1.");
		sampleParameterTask.start();
		
		try {
			observedParameter.setValue(new Integer(9));
			observedParameter.setValue(new Integer(8));
			observedParameter.setValue(new Integer(7));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sampleParameterTask.stop();
		assertTrue(new Integer(7).equals(sampleParameterTask.getValue()));
		System.out.println(sampleParameterTask.getValue());
	}
	
	/**
	 * Test create_ advanced_ change_ minimum.
	 */
	public void testCreate_Advanced_Change_Minimum() {
		try {
			parameter.getParamSampleMode().setValue("Change");
			parameter.getParamCalculationMode().setValue("Minimum");
		} catch (Fault e) {
		}
		sampleParameterTask = new SampleParameterTask(parameterData,
				parameterData.getRoot()
						+ "PeriodicStatistics.SampleSet.1.Parameter.1.");
		sampleParameterTask.start();
		
		try {
			observedParameter.setValue(new Integer(9));
			observedParameter.setValue(new Integer(8));
			observedParameter.setValue(new Integer(7));
			observedParameter.setValue(new Integer(9));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sampleParameterTask.stop();
		assertTrue(new Integer(7).equals(sampleParameterTask.getValue()));
		System.out.println(sampleParameterTask.getValue());
	}
	
	/**
	 * Test create_ advanced_ change_ average.
	 */
	public void testCreate_Advanced_Change_Average() {
		try {
			parameter.getParamSampleMode().setValue("Change");
			parameter.getParamCalculationMode().setValue("Average");
		} catch (Fault e) {
		}
		sampleParameterTask = new SampleParameterTask(parameterData,
				parameterData.getRoot()
						+ "PeriodicStatistics.SampleSet.1.Parameter.1.");
		sampleParameterTask.start();
		
		try {
			observedParameter.setValue(new Integer(2));
			observedParameter.setValue(new Integer(6));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sampleParameterTask.stop();
		assertTrue(new Integer(4).equals(sampleParameterTask.getValue()));
		System.out.println(sampleParameterTask.getValue());
	}
	

}
