/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : GetParameterValuesBundle
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
package com.francetelecom.admindm.getParameterValues;
import java.util.Iterator;
import java.util.List;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.model.ParameterValueStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class GetParameterValues.
 */
public final class GetParameterValues implements RPCMethod {
    /** The Constant NAME. */
    private static final String NAME = "GetParameterValues";
    /** The Constant MAX_LENGTH. */
    private static final int MAX_LENGTH = 256;
    /** The parameter names. */
    private String[] parameterNames;
    /**
     * Gets the parameter names.
     * @return the parameter names
     */
    public String[] getParameterNames() {
        return parameterNames;
    }
    /**
     * Sets the parameter names.
     * @param pParameterNames the parameter names
     */
    public void setParameterNames(final String[] pParameterNames) {
        this.parameterNames = pParameterNames;
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
     * @param session the session
     * @throws Fault the exception
     */
    public void perform(final Session session) throws Fault {
        if (parameterNames == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": parameterNames array is null.");
            throw new Fault(FaultUtil.FAULT_9002, error.toString());
        }
        if (parameterNames.length > MAX_LENGTH) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": Max size for parameterNames array is ");
            error.append(MAX_LENGTH);
            error.append(".");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        IParameterData parameterData = session.getParameterData();
        List lsParameter = parameterData.extractParameterList(parameterNames);
        // count parameter with ANY as type
        int countParameterWithAnyType = 0; // 
        Iterator it = lsParameter.iterator();
        Parameter param;
        while (it.hasNext()) {
            param = (Parameter) it.next();
            if (param.getType() == ParameterType.ANY) {
                countParameterWithAnyType++;
            }
        }
        GetParameterValuesResponse response = new GetParameterValuesResponse();
        int size = lsParameter.size() - countParameterWithAnyType;
        if (size == 0) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": \"");
            for (int i = 0; i < parameterNames.length; i++) {
                error.append(parameterNames[i]);
                error.append(" ");
            }
            error.append("\" filter does'nt correspond to any parameter.");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        response.setParameterList(new ParameterValueStruct[size]);
        ParameterValueStruct pvs;
        int count =0;  
        // n'affecte que les parametres de type different d'ANY
        // utilise donc un compteur different de l'iterateur de parcour
        // pour peupler le tableau de resultat
        for (int i = 0; i < lsParameter.size(); i++) {
            param = (Parameter) lsParameter.get(i);
            if (param.getType() != ParameterType.ANY) {
                if (param.getValue(session.getSessionId()) == null) {
                    StringBuffer error = new StringBuffer(
                            FaultUtil.STR_FAULT_9002);
                    error.append(": ");
                    error.append(param.getName());
                    error.append(" is not initialized or getter return null.");
                    throw new Fault(FaultUtil.FAULT_9002, error.toString());
                }
                pvs = new ParameterValueStruct(param.getName(), param
                        .getTextValue(session.getSessionId()), param.getType());
                response.getParameterList()[count] = pvs;
                count++;
            }
        }
        session.doASoapResponse(response);      
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
