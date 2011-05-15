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
import com.francetelecom.tr157.implem.PeriodicStatisticsImpl;
import com.francetelecom.tr157.implem.SampleSetImpl;

import junit.framework.TestCase;

public class SampleTaskTest extends TestCase {

	/** The Constant ROOT. */
	public static final String ROOT = "Device.";

	/** The parameter data. */
	private IParameterData parameterData;

	/** The observed parameter. */
	private com.francetelecom.admindm.model.Parameter observedParameter;

	/** The parameter. */
	private Parameter parameter;

	/** The sample set. */
	private SampleSetImpl sampleSet;

	/** The sample task. */
	private SampleTask sampleTask;

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
		observedParameter = parameterData
				.createOrRetrieveParameter("Device.Test");
		observedParameter.setType(ParameterType.INT);
		observedParameter.setValue(new Integer(10));

		new PeriodicStatisticsImpl(parameterData, parameterData.getRoot()
				+ "PeriodicStatistics.").initialize();
		sampleSet = new SampleSetImpl(parameterData, parameterData.getRoot()
				+ "PeriodicStatistics.SampleSet.1.");
		sampleSet.initialize();
		parameter = sampleSet.addParameter();
		parameter.getParamReference().setValue(observedParameter.getName());
	}

	/**
	 * Test create.
	 */
	public void testCreate() {
		sampleTask = new SampleTask(parameterData, sampleSet.getBasePath(), 1);

		sampleTask.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		sampleTask.stop();
		assertTrue("1".equals(sampleSet.getParamSampleSeconds().getValue()));
		assertTrue("10".equals(parameter.getParamValues().getValue()));
		assertTrue("1".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue(!"".equals(sampleSet.getParamReportStartTime().getValue()));
		assertTrue(!"".equals(sampleSet.getParamReportEndTime().getValue()));
	}

	/**
	 * Test create_ with2 parameters.
	 */
	public void testCreate_With2Parameters() {
		com.francetelecom.admindm.model.Parameter observedParameter2 = null;
		try {
			observedParameter2 = parameterData
					.createOrRetrieveParameter("Device.Test2");
			observedParameter2.setType(ParameterType.INT);
			observedParameter2.setValue(new Integer(5));
		} catch (Fault e) {
			e.printStackTrace();
		}
		
		Parameter parameter2 = sampleSet.addParameter();
		try {
			parameter2.getParamReference().setValue(observedParameter2.getName());
		} catch (Fault e) {
			e.printStackTrace();
		}
		
		sampleTask = new SampleTask(parameterData, sampleSet.getBasePath(), 1);

		sampleTask.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		sampleTask.stop();
		assertTrue("1".equals(sampleSet.getParamSampleSeconds().getValue()));
		assertTrue("10".equals(parameter.getParamValues().getValue()));
		assertTrue("1".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue("5".equals(parameter2.getParamValues().getValue()));
		assertTrue("1".equals(parameter2.getParamSampleSeconds().getValue()));
		assertTrue(!"".equals(sampleSet.getParamReportStartTime().getValue()));
		assertTrue(!"".equals(sampleSet.getParamReportEndTime().getValue()));
		
		
	}
	
	public void testCreate_Threshold() {
		try {
			parameter.getParamLowThreshold().setValue(new Integer(4));
			parameter.getParamHighThreshold().setValue(new Integer(6));
			parameter.getParamSampleMode().setValue("Change");
			
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sampleTask = new SampleTask(parameterData, sampleSet.getBasePath(), 1);

		sampleTask.start();
		try {
			observedParameter.setValue(new Integer(4));
			observedParameter.setValue(new Integer(5));
			observedParameter.setValue(new Integer(4));
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		sampleTask.stop();
		assertTrue("1".equals(sampleSet.getParamSampleSeconds().getValue()));
		assertTrue("4".equals(parameter.getParamValues().getValue()));
		assertTrue("1".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue(!"".equals(sampleSet.getParamReportStartTime().getValue()));
		assertTrue(!"".equals(sampleSet.getParamReportEndTime().getValue()));
		assertTrue(new Long(1).equals(parameter.getParamFailures().getValue()));
	}
	
	
}
