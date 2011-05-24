/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : SetParameterValuesBundle
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

package com.francetelecom.admindm.setParameterValues;
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.model.ParameterValueStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
import com.francetelecom.admindm.soap.SetParamValuesFault;
/**
 * The Class SetParameterValues.
 */
public final class SetParameterValues implements RPCMethod {
    /**
     * The Constructor.
     * @param pParameterList the parameter list
     * @param pParameterKey the parameter key
     */
    public SetParameterValues(final ParameterValueStruct[] pParameterList,
            final String pParameterKey) {
        super();
        this.parameterList = pParameterList;
        this.parameterKey = pParameterKey;
    }
    /** The Constant name. */
    private static final String NAME = "SetParameterValues";
    /** The parameter list. */
    private final ParameterValueStruct[] parameterList;
    /** The parameter key. */
    private final String parameterKey;
    /**
     * Gets the parameter list.
     * @return the parameter list
     */
    public ParameterValueStruct[] getParameterList() {
        return parameterList;
    }
    /**
     * Gets the parameter key.
     * @return the parameter key
     */
    public String getParameterKey() {
        return parameterKey;
    }
    /**
     * Gets the name.
     * @return the name
     * @see com.francetelecom.admindm.api.RPCMethod#getName()
     */
    public String getName() {
        return NAME;
    }
    /**
     * Perform.
     * @param pSession the session
     * @throws Fault the exception
     */
    public void perform(final Session pSession) throws Fault {
        RPCMethod response;
        List lsFault = new ArrayList();
        IParameterData parameterData = pSession.getParameterData();
        for (int i = 0; i < parameterList.length; i++) {
            try {
                ParameterValueStruct pvs = parameterList[i];
                for (int u = 0; u < i; u++) {
                    if (pvs.getName().equals(parameterList[u].getName())) {
                        StringBuffer error;
                        error = new StringBuffer(FaultUtil.STR_FAULT_9003);
                        error.append(": is present more than one time "
                                + "and it's forbidden");
                        throw new SetParamValuesFault(pvs.getName(),
                                FaultUtil.FAULT_9003, error.toString());
                    }
                }
                Parameter param = parameterData.getParameter(pvs.getName());
                // unable to find the parameter
                if (param == null) {
                    StringBuffer error;
                    error = new StringBuffer(FaultUtil.STR_FAULT_9005);
                    error.append(": Unable to find this parameter "
                            + "in the data model");
                    throw new SetParamValuesFault(pvs.getName(),
                            FaultUtil.FAULT_9005, error.toString());
                }
                checkIfSetIsPossible(param);
                checkIfSetIsApplicable(param, pvs.getValue());
            } catch (SetParamValuesFault fault) {
                lsFault.add(fault);
            }
        }
        if (!lsFault.isEmpty()) {
            // create a global fault with all detected fault
            throw new Fault(FaultUtil.FAULT_9003, FaultUtil.STR_FAULT_9003,
                    lsFault);
        }
        boolean isAllApplied = true;
        String value;
        Parameter param;
        Object[] oldValues = storeInitialValue(parameterList, parameterData);
        for (int i = 0; i < parameterList.length; i++) {
            value = parameterList[i].getValue();
            param = parameterData.getParameter(parameterList[i].getName());
            try {
                param.setValue(Parameter.getValue(param.getName(),
                        value, param.getType()));
            } catch (Fault e) {
                StringBuffer error;
                error = new StringBuffer(FaultUtil.STR_FAULT_9003);
                error.append(": Unable to find this parameter "
                        + "in the data model");
                lsFault.add(new SetParamValuesFault(param.getName(), e
                        .getFaultcode(), e.getFaultstring()));
            }
            param.notifyObservers("ACS");
            isAllApplied &= param.isImmediateChanges();
        }
        if (!lsFault.isEmpty()) {
            restoreInitialValue(parameterList, oldValues, parameterData);
            // create a global fault with all detected fault
            throw new Fault(FaultUtil.FAULT_9003, FaultUtil.STR_FAULT_9003,
                    lsFault);
        }
        if (isAllApplied) {
            response = new SetParameterValuesResponse(0);
        } else {
            response = new SetParameterValuesResponse(1);
        }
        parameterData.setParameterKey(parameterKey);
        pSession.doASoapResponse(response);
    }
    /**
     * Store initial value.
     * @param values the values
     * @param parameterData the parameter data
     * @return the object[]
     */
    private Object[] storeInitialValue(final ParameterValueStruct[] values,
            final IParameterData parameterData) {
        Object[] result = new Object[values.length];
        Parameter param;
        for (int i = 0; i < parameterList.length; i++) {
            param = parameterData.getParameter(parameterList[i].getName());
            result[i] = param.getValue();
        }
        return result;
    }
    /**
     * Restore initial value.
     * @param values the values
     * @param oldValues the old values
     * @param parameterData the parameter data
     */
    private void restoreInitialValue(final ParameterValueStruct[] values,
            final Object[] oldValues, final IParameterData parameterData) {
        Parameter param;
        for (int i = 0; i < parameterList.length; i++) {
            param = parameterData.getParameter(parameterList[i].getName());
            param.setDirectValue(oldValues[i]);
        }
    }
    /**
     * Check if set is possible.
     * @param param the parameter
     * @throws SetParamValuesFault the set parameter values fault
     */
    private void checkIfSetIsPossible(final Parameter param)
            throws SetParamValuesFault {
        if (!param.isWritable()) {
            throw new SetParamValuesFault(param.getName(),
                    FaultUtil.FAULT_9008, FaultUtil.STR_FAULT_9008);
        }
    }
    /**
     * Check if the parameter is compliance with the request.
     * @param param parameter
     * @param value new value
     * @throws SetParamValuesFault in case of error.
     */
    private void checkIfSetIsApplicable(final Parameter param,
            final String value) throws SetParamValuesFault {
        try {
            param.check(value);
        } catch (Fault exp) {
            throw new SetParamValuesFault(param.getName(), exp.getFaultcode(),
                    exp.getFaultstring(), exp);
        }
    }
    /**
     * id of the RPCMethod Request by ACS.
     */
    private String id= null;
    /**
     * Gets the id.
     * @return the Id.
     */
	public String getId() { 
		return id;
	}
	/**
	 * Setter the Id.
	 */
	public void setId(String id) {
		this.id=id;		
	}
}
