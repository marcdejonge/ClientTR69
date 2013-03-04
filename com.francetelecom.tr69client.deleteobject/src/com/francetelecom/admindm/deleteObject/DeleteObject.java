/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DeleteObjectBundle
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
package com.francetelecom.admindm.deleteObject;
import java.util.Iterator;
import java.util.List;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class DeleteObject.
 */
public final class DeleteObject implements RPCMethod {
    /**
     * Instantiates a new delete object.
     * @param pObjectName the object name
     * @param pParameterKey the parameter key
     */
    public DeleteObject(final String pObjectName, final String pParameterKey) {
        super();
        this.objectName = pObjectName;
        this.parameterKey = pParameterKey;
    }
    /** The Constant name. */
    private static final String NAME = "DeleteObject";
    /** The object name. */
    private final String objectName;
    /** The parameter key. */
    private final String parameterKey;
    /**
     * Gets the name.
     * @return the name
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
        IParameterData parameterData = session.getParameterData();
        // verify that the ObjectName is valid
        Parameter param = parameterData.getParameter(objectName);
        if (param == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9005);
            error.append(": unable to find this Object '");
            error.append(objectName);
            error.append("' into the model.");
            throw new Fault(FaultUtil.FAULT_9005, error.toString());
        }
        if (!objectName.endsWith(".")) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9005);
            error.append(": this parameter '");
            error.append(objectName);
            error.append("' does not end with a dot.");
            throw new Fault(FaultUtil.FAULT_9005, error.toString());
        }
        if (!param.isWritable()) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9005);
            error.append(": this parameter '");
            error.append(objectName);
            error.append("' is read only.");
            throw new Fault(FaultUtil.FAULT_9005, error.toString());
        }
        List lsParam = parameterData.extractParameterList(objectName);
        Iterator it = lsParam.iterator();
        while (it.hasNext()) {
            Parameter subParameter = (Parameter) it.next();
            parameterData.deleteParam(subParameter);
        }
        parameterData.deleteParam(param);
        parameterData.setParameterKey(parameterKey);
        DeleteObjectResponse response = new DeleteObjectResponse(0);
        session.doASoapResponse(response);
    }
    /**
     * Gets the object name.
     * @return the object name
     */
    public String getObjectName() {
        return objectName;
    }
    /**
     * Gets the parameter key.
     * @return the parameter key
     */
    public String getParameterKey() {
        return parameterKey;
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
