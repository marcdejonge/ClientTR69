/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : SetParameterAttributesBundle
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


package com.francetelecom.admindm.setParameterAttributes;
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.SetParameterAttributesStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class SetParameterAttributes.
 * The specification is issue from the 
 * <a href="http://www.broadband-forum.org/technical/download/TR-069Amendment2.pdf#page=51">
 * specification</a> of Brandband Forum document.  
 */
public final class SetParameterAttributes implements RPCMethod {
    /** The Constant NAME. */
    private static final String NAME = "SetParameterAttributes";
    /** The parameter list. */
    private SetParameterAttributesStruct[] parameterList;
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
     * @param session the session
     * @throws Fault the fault
     */
    public void perform(final Session session) throws Fault {
        SetParameterAttributesResponse response;
        response = new SetParameterAttributesResponse();
        IParameterData data = session.getParameterData();
        SetParameterAttributesStruct param;
        List<Save> lsSave = new ArrayList<Save>();
        List<Parameter> lsParameters;
        String name;
        if (parameterList == null || parameterList.length == 0) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": parameterList is empty.");
            throw new Fault(FaultUtil.FAULT_9003,error.toString());
        }
        try {
            for (int i = 0; i < parameterList.length; i++) {
                param = parameterList[i];
                name = param.getName();
                lsParameters = data.extractParameterList(name);
                if (lsParameters == null || lsParameters.isEmpty()) {
                    StringBuffer error = new StringBuffer(
                            FaultUtil.STR_FAULT_9005);
                    error.append(": ");
                    error.append(name);
                    error.append(" does'nt represent any attribute"
                            + " into current data model.");
                    throw new Fault(FaultUtil.FAULT_9005, error.toString());
                }
                while (!lsParameters.isEmpty()) {
                    Parameter p = lsParameters.remove(0);
                    lsSave.add(new Save(p));
                    setAttribute(p, param);
                }
            }
        } catch (Fault f) {
            restoreParam(lsSave);
            throw f;
        }
        session.doASoapResponse(response);
    }

    /**
     * In case of fault all parameters must be restore 
     * to their initial value to prevent the case 
     * of the parameter has been modify
     * more than one time. The save list must be use from the end 
     * to the end.
     * @param lsSave the ls save
     */
    protected static void restoreParam(List<Save> lsSave) {
        Save save;
        for (int i=lsSave.size()-1;i>= 0;i-- ) {
            save = lsSave.get(i);
            save.restore();
        }
    }
    /**
     * Sets the new value for attribute.
     * @param param the p
     * @param setParam the param
     * @throws Fault the fault
     */
    protected static void setAttribute(final Parameter param,
            final SetParameterAttributesStruct setParam)
            throws Fault {
        if (setParam.isNotificationChange()) {
            if (param.isActiveNotificationDenied() 
                    & setParam.getNotification() == 2) {
                StringBuffer error;
                error = new StringBuffer(FaultUtil.STR_FAULT_9009);
                error.append(": ");
                error.append(param.getName());
                error.append(" active notification is forbidden.");
                throw new Fault(FaultUtil.FAULT_9009, error.toString());
            }
            param.setNotification(setParam.getNotification());
        }
        if (setParam.isAccessListChange()){
            param.setAccessList(setParam.getAccessList());
        }
    }
    /**
     * Sets the parameter list.
     * @param pParameterList the new parameter list
     */
    public void setParameterList(
            final SetParameterAttributesStruct[] pParameterList) {
        this.parameterList = pParameterList;
    }
    /**
     * Gets the parameter list.
     * @return the parameter list
     */
    public SetParameterAttributesStruct[] getParameterList() {
        return parameterList;
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
