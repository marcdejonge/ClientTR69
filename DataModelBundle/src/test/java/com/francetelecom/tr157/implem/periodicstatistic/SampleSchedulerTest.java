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

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Parameter;
import com.francetelecom.tr157.implem.PeriodicStatisticsImpl;
import com.francetelecom.tr157.implem.SampleSetImpl;

import junit.framework.TestCase;

public class SampleSchedulerTest extends TestCase implements Observer {

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
	private SampleScheduler sampleScheduler;
	
	/** The count. */
	private int count = 0;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		count = 0;
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
		sampleSet.getParamSampleInterval().setValue(new Long(1));
		sampleSet.getParamReportSamples().setValue(new Long(10));
		
		parameter = sampleSet.addParameter();
		parameter.getParamReference().setValue(observedParameter.getName());
	}
	
	public void testCreate() {
		sampleScheduler = new SampleScheduler(parameterData, sampleSet.getBasePath());
		sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(sampleSet.getParamStatus().getValue()));
		
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(sampleSet.getParamStatus().getValue()));
		assertTrue("10;10;10;10;10;10;10;10;10;10".equals(parameter.getParamValues().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(sampleSet.getParamSampleSeconds().getValue()));
		
	}
	
	public void testCreate_ForceSample() {
		try {
			sampleSet.getParamReportSamples().setValue(new Long(1));
			sampleSet.getParamSampleInterval().setValue(new Long(10));
		} catch (Fault e2) {
			e2.printStackTrace();
		}

		sampleScheduler = new SampleScheduler(parameterData, sampleSet.getBasePath());
		sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue("Enabled".equals(sampleSet.getParamStatus().getValue()));
		
		try {
			observedParameter.setValue(new Integer(0));
			sampleSet.getParamForceSample().setValue(Boolean.TRUE);

		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("0".equals(parameter.getParamValues().getValue()));
		try {
			observedParameter.setValue(new Integer(10));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		assertTrue("Disabled".equals(sampleSet.getParamStatus().getValue()));
		assertTrue("10".equals(parameter.getParamValues().getValue()));
		assertTrue("10".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue("10".equals(sampleSet.getParamSampleSeconds().getValue()));
		
	}
	
	public void testCreate_Threshold_WithForceSample() {
		try {
			parameter.getParamLowThreshold().setValue(new Integer(4));
			parameter.getParamHighThreshold().setValue(new Integer(6));
//			parameter.getParamSampleMode().setValue("Change");
			
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sampleScheduler = new SampleScheduler(parameterData, sampleSet.getBasePath());
		sampleScheduler.start(-1);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		try {
			observedParameter.setValue(new Integer(4));
			sampleSet.getParamForceSample().setValue(Boolean.TRUE);
			System.out.println(parameter.getParamValues().getValue());
			Thread.sleep(1000);
			
			observedParameter.setValue(new Integer(5));
			sampleSet.getParamForceSample().setValue(Boolean.TRUE);
			System.out.println(parameter.getParamValues().getValue());
			Thread.sleep(1000);
			
			observedParameter.setValue(new Integer(4));
			sampleSet.getParamForceSample().setValue(Boolean.TRUE);
			System.out.println(parameter.getParamValues().getValue());
			Thread.sleep(1000);
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
		}
		sampleScheduler.stop();
//		assertTrue("1".equals(sampleSet.getParamSampleSeconds().getValue()));
//		assertTrue("4".equals(parameter.getParamValues().getValue()));
//		assertTrue("1".equals(parameter.getParamSampleSeconds().getValue()));
//		assertTrue(!"".equals(sampleSet.getParamReportStartTime().getValue()));
//		assertTrue(!"".equals(sampleSet.getParamReportEndTime().getValue()));
		System.out.println(parameter.getParamFailures().getValue());
		System.out.println(parameter.getParamValues().getValue());
		assertTrue(new Long(3).equals(parameter.getParamFailures().getValue()));
	}
	
	/**
	 * Test create_ fetch sample_5.
	 */
	public void testCreate_FetchSample_5() {
		
		try {
			sampleSet.getParamFetchSamples().setValue(new Long(5));
			sampleSet.getParamStatus().addObserver(this);
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sampleScheduler = new SampleScheduler(parameterData, sampleSet.getBasePath());
		sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(sampleSet.getParamStatus().getValue()));
		
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(sampleSet.getParamStatus().getValue()));
		assertTrue("10;10;10;10;10;10;10;10;10;10".equals(parameter.getParamValues().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(sampleSet.getParamSampleSeconds().getValue()));
		assertTrue(count == 4);
		
	}
	
	/**
	 * Test create_ fetch sample_0.
	 */
	public void testCreate_FetchSample_0() {
		
		try {
			sampleSet.getParamFetchSamples().setValue(new Long(0));
			sampleSet.getParamStatus().addObserver(this);
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sampleScheduler = new SampleScheduler(parameterData, sampleSet.getBasePath());
		sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(sampleSet.getParamStatus().getValue()));
		
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(sampleSet.getParamStatus().getValue()));
		assertTrue("10;10;10;10;10;10;10;10;10;10".equals(parameter.getParamValues().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(sampleSet.getParamSampleSeconds().getValue()));
		assertTrue(count == 2);
		
	}
	
	/**
	 * Test create_ fetch sample_15.
	 */
	public void testCreate_FetchSample_15() {
		
		try {
			sampleSet.getParamFetchSamples().setValue(new Long(15));
			sampleSet.getParamStatus().addObserver(this);
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sampleScheduler = new SampleScheduler(parameterData, sampleSet.getBasePath());
		sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(sampleSet.getParamStatus().getValue()));
		
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(sampleSet.getParamStatus().getValue()));
		assertTrue("10;10;10;10;10;10;10;10;10;10".equals(parameter.getParamValues().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(sampleSet.getParamSampleSeconds().getValue()));
		assertTrue(count == 2);
		
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		com.francetelecom.admindm.model.Parameter parameter = (com.francetelecom.admindm.model.Parameter) o;
		System.out.println("parameter name = " + parameter.getName() + ", value = " + parameter.getValue());
		count++;
		
	}
	
	/**
	 * Test change sample interval.
	 */
	public void testChangeSampleInterval() {
		try {
			sampleSet.getParamReportSamples().setValue(new Long(5));
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sampleScheduler = new SampleScheduler(parameterData, sampleSet.getBasePath());
		sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(sampleSet.getParamStatus().getValue()));
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue("Enabled".equals(sampleSet.getParamStatus().getValue()));
		System.out.println(parameter.getParamValues().getValue());
		
		try {
			sampleSet.getParamSampleInterval().setValue(new Long(2));
			observedParameter.setValue(new Integer(2));
		} catch (Fault e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Status = " + sampleSet.getParamStatus().getValue());
		System.out.println("Values = " + parameter.getParamValues().getValue());
		
		assertTrue("Disabled".equals(sampleSet.getParamStatus().getValue()));
		assertTrue("2;2;2;2;2".equals(parameter.getParamValues().getValue()));
		assertTrue("2;2;2;2;2".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue("2;2;2;2;2".equals(sampleSet.getParamSampleSeconds().getValue()));
		
	}
	
	public void testChangeReportSample_GreaterValue() {
		try {
			sampleSet.getParamReportSamples().setValue(new Long(3));
			observedParameter.setValue(new Integer(2));
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sampleScheduler = new SampleScheduler(parameterData, sampleSet.getBasePath());
		sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(sampleSet.getParamStatus().getValue()));
		
		try {
			sampleSet.getParamReportSamples().setValue(new Long(5));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(sampleSet.getParamStatus().getValue()));
		assertTrue("2;2;2;2;2".equals(parameter.getParamValues().getValue()));
		assertTrue("1;1;1;1;1".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1;1;1".equals(sampleSet.getParamSampleSeconds().getValue()));
		
		
	}
	
	public void testChangeReportSample_LessValue() {
		try {
			sampleSet.getParamReportSamples().setValue(new Long(5));
			observedParameter.setValue(new Integer(2));
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sampleScheduler = new SampleScheduler(parameterData, sampleSet.getBasePath());
		sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(sampleSet.getParamStatus().getValue()));
		
		try {
			sampleSet.getParamReportSamples().setValue(new Long(3));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(sampleSet.getParamStatus().getValue()));
		assertTrue("2;2;2".equals(parameter.getParamValues().getValue()));
		assertTrue("1;1;1".equals(parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1".equals(sampleSet.getParamSampleSeconds().getValue()));
		
		
	}

}
