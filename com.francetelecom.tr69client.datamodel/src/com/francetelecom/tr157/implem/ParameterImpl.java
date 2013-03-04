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

import java.util.Observable;
import java.util.Observer;

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Parameter;

/**
 * @author mpcy8647
 * 
 */
public class ParameterImpl extends Parameter implements Observer {

	/** The Constant IN_RANGE. */
	private static final int IN_RANGE = 0;
	
	/** The Constant OUT_OF_RANGE. */
	private static final int OUT_OF_RANGE = 1;
	
	/** The Constant OUT_OF_RANGE_LOW. */
	private static final int OUT_OF_RANGE_LOW = 2;
	
	/** The Constant OUT_OF_RANGE_HIGH. */
	private static final int OUT_OF_RANGE_HIGH = 3;
	
	/** The observed parameter. */
	private com.francetelecom.admindm.model.Parameter observedParameter = null;
	
	public ParameterImpl(IParameterData pData, String pBasePath) {
		super(pData, pBasePath);
		
	}

	public void initialize() throws Fault {
		super.initialize();

		getParamCalculationMode().setValue("Latest");
		getParamSampleMode().setValue("Current");
		getParamValues().addObserver(this);
	}

	public void update(Observable arg0, Object arg1) {
		if (arg0.equals(getParamValues())) {
			processThreshold();
		}

	}
	
	private com.francetelecom.admindm.model.Parameter getObserverParameter() {
		if ((!"".equals(getParamReference().getValue()) && (observedParameter == null))) {
			observedParameter = getData().getParameter(
					(String) getParamReference().getValue());
		}
		
		return observedParameter;
	}

	private void processThreshold() {
		if (isThresholdApplicable()) {
			// check the last value
			String value = (String) getParamValues().getValue();
			String[] splittedString = value.split(";");
			String lastValue = null;
			String previousLastValue = null;
			if (splittedString != null) {
				if (splittedString.length > 0) {
					lastValue = splittedString[splittedString.length - 1];
				}
				if (splittedString.length > 1) {
					previousLastValue = splittedString[splittedString.length - 2];
				}
			}
			if (!checkThreshold(lastValue, previousLastValue)) {
				long failure = ((Long) getParamFailures().getValue()).longValue();
				failure++;
				try {
					getParamFailures().setValue(new Long(failure));
				} catch (Fault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		}
	}

	private boolean isThresholdApplicable() {
		if (getParamLowThreshold().getValue().equals(
				getParamHighThreshold().getValue())) {
			return false;
		}

		if (getObserverParameter() == null) {
			return false;
		}
		switch (getObserverParameter().getType()) {
		case ParameterType.INT:
		case ParameterType.UINT:
		case ParameterType.BASE64:
		case ParameterType.LONG:
			return true;
		default:
			return false;
		}

	}


	/**
	 * Check threslhold.
	 * 
	 * @return true, if the value isn't is a suspect value (that is to say that
	 *         this value is greater than the high threshold or is less than the
	 *         low threshold).
	 */
	private boolean checkThreshold(String lastValue, String previousLastValue) {
		if (lastValue != null) {
			int lastValueState = -1;
			int previousLastValueState = -1;
			switch (observedParameter.getType()) {
			case ParameterType.INT:
				Integer lastInt = new Integer(lastValue);
				Integer previouslastInt = null;
				if (previousLastValue != null) {
					previouslastInt = new Integer(previousLastValue);
				}
				Integer lowThreshold = (Integer) getParamLowThreshold().getValue();
				Integer highThreshold = (Integer) getParamHighThreshold().getValue();
				
				// compute state of the last value
				if ((lastInt.compareTo(lowThreshold) > 0) && (lastInt.compareTo(highThreshold) < 0)) {
					lastValueState = IN_RANGE;
				} else if (lastInt.compareTo(lowThreshold) <= 0) {
					lastValueState = OUT_OF_RANGE_LOW;
				} else if (lastInt.compareTo(highThreshold) >= 0) {
					lastValueState = OUT_OF_RANGE_HIGH;
				}
				
				// compute the state of the previous last value
				if (previouslastInt != null) {
					if ((previouslastInt.compareTo(lowThreshold) > 0) && (previouslastInt.compareTo(highThreshold) < 0)) {
						previousLastValueState = IN_RANGE;
					} else if (previouslastInt.compareTo(lowThreshold) <= 0) {
						previousLastValueState = OUT_OF_RANGE_LOW;
					} else if (previouslastInt.compareTo(highThreshold) >= 0) {
						previousLastValueState = OUT_OF_RANGE_HIGH;
					}
				} else {
					previousLastValueState = IN_RANGE;
				}
				break;
			case ParameterType.UINT:
			case ParameterType.LONG:
				Long lastLong = new Long(lastValue);
				Long previouslastLong = null;
				if (previousLastValue != null) {
					previouslastLong = new Long(previousLastValue);
				}
				Long lowThresholdLong = (Long) getParamLowThreshold().getValue();
				Long highThresholdLong = (Long) getParamHighThreshold().getValue();
				
				// compute state of the last value
				if ((lastLong.compareTo(lowThresholdLong) > 0) && (lastLong.compareTo(highThresholdLong) < 0)) {
					lastValueState = IN_RANGE;
				} else if (lastLong.compareTo(lowThresholdLong) <= 0) {
					lastValueState = OUT_OF_RANGE_LOW;
				} else if (lastLong.compareTo(highThresholdLong) >= 0) {
					lastValueState = OUT_OF_RANGE_HIGH;
				}
				
				// compute the state of the previous last value
				if (previouslastLong != null) {
					if ((previouslastLong.compareTo(lowThresholdLong) > 0) && (previouslastLong.compareTo(highThresholdLong) < 0)) {
						previousLastValueState = IN_RANGE;
					} else if (previouslastLong.compareTo(lowThresholdLong) <= 0) {
						previousLastValueState = OUT_OF_RANGE_LOW;
					} else if (previouslastLong.compareTo(highThresholdLong) >= 0) {
						previousLastValueState = OUT_OF_RANGE_HIGH;
					}
				} else {
					previousLastValueState = IN_RANGE;
				}
				
				break;
			case ParameterType.BASE64:
				break;
			
			}
			
			if ((previousLastValueState == IN_RANGE) && (lastValueState > previousLastValueState)) {
				return false;
			}
			if ((previousLastValueState > OUT_OF_RANGE) && (lastValueState > OUT_OF_RANGE) && (previousLastValueState != lastValueState)) {
				return false;
			}
		}
		
		return true;
	}
}
