/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : GetParameterNamesBundle
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
package com.francetelecom.admindm.getParameterNames;
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterInfoStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * This method MAY be used by an ACS to discover the Parameters accessible on a
 * particular CPE. <a href=
 * "http://www.broadband-forum.org/technical/download/TR-069Amendment2.pdf#page=49"
 * > specification of BrandBandForum</a>
 * @author Olivier Beyler - OrangeLabs -
 */
public final class GetParameterNames implements RPCMethod {
    /** The Constant name. */
    static final String NAME = "GetParameterNames";
    /**
     * A string containing either a complete Parameter name, or a partial path
     * name ParameterPath representing a subset of the name hierarchy. An empty
     * string indicates the top of the name hierarchy. A partial path name MUST
     * end with a . (dot) after the last node name in the hierarchy.<br/>
     * Below is an example of a full Parameter name:<br/>
     * InternetGatewayDevice.DeviceInfo.SerialNumber<br/>
     * Below is an example of a partial path name:<br/>
     * InternetGatewayDevice.DeviceInfo.<br/>
     */
    private String parameterPath;
    /**
     * If false, the response MUST contain the Parameter or object whose name
     * exactly NextLevel matches the ParameterPath argument, plus all Parameters
     * and objects that are descendents of the object given by the ParameterPath
     * argument, if any (all levels below the specified object in the object
     * hierarchy). For example, if ParameterPath were
     * InternetGatewayDevice.LANDevice.1.Hosts., the response would include
     * the following (if there were a single instance of Host with instance
     * number 1):<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.HostNumberOfEntries<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.Host.<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.Host.1.<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.Host.1.IPAddress<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.Host.1.AddressSource<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.Host.1.LeaseTimeRemaining<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.Host.1.MACAddress<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.Host.1.HostName<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.Host.1.InterfaceType<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.Host.1.Active<br/>
     * If true, the response MUST contain all Parameters and objects that are
     * next-level children of the object given by the ParameterPath argument, if
     * any. For example, if ParameterPath were
     * InternetGatewayDevice.LANDevice.1.Hosts., the response would include
     * the following:<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.HostNumberOfEntries<br/>
     * InternetGatewayDevice.LANDevice.1.Hosts.Host.<br/>
     * Or, if ParameterPath were empty, with NextLevel equal true, the response
     * would list only InternetGatewayDevice. (if the CPE is an Internet
     * Gateway Device).<br/>
     */
    private boolean nextLevel;
    /**
     * Gets the parameter path.
     * @return the parameter path
     */
    public String getParameterPath() {
        return parameterPath;
    }
    /**
     * Sets the parameter path.
     * @param pParameterPath the new parameter path
     */
    public void setParameterPath(final String pParameterPath) {
        this.parameterPath = pParameterPath;
    }
    /**
     * Checks if is next level.
     * @return true, if is next level
     */
    public boolean isNextLevel() {
        return nextLevel;
    }
    /**
     * Sets the next level.
     * @param pNextLevel the new next level
     */
    public void setNextLevel(final boolean pNextLevel) {
        this.nextLevel = pNextLevel;
    }
    /**
     * Getter of Name.
     * @return the name
     */
    public String getName() {
        return NAME;
    }
    /**
     * If the fault is caused by an invalid ParameterPath value, the Invalid
     * Parameter Name fault code (9005) MUST be used instead of the more general
     * Invalid Arguments fault code (9003). A ParameterPath value MUST be
     * considered invalid if it is not an empty string and does not exactly
     * match a parameter or object name currently present in the CPE’s data
     * model. If NextLevel is true and ParameterPath is a Parameter name rather
     * than a partial path, the CPE MUST return a fault response with the
     * Invalid Arguments fault code (9003).
     * @param session the session
     * @throws Fault the exception
     */
    public void perform(final Session session) throws Fault {
        RPCMethod response;
        ParameterInfoStruct[] parameterList;
        parameterList = getArrayOfParameterInfo(parameterPath, nextLevel,
                session.getParameterData());
        if (parameterList == null || parameterList.length == 0) {            
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9005);
            error.append(": ");
            error.append(parameterPath);
            error.append(" doesn't correspond to any parameter.");
            throw new Fault(FaultUtil.FAULT_9005, error.toString());
        }
        response = new GetParameterNamesResponse(parameterList);
        session.doASoapResponse(response);
    }
    /**
     * Gets the array of parameter info.
     * @param name the name
     * @param nextLevel the next level
     * @param session the session
     * @return the array of parameter info
     */
    protected static ParameterInfoStruct[] getArrayOfParameterInfo(
            final String name, final boolean nextLevel,
            final IParameterData parameterData) {
        ParameterInfoStruct[] result = null;
        List<ParameterInfoStruct> lsParameter = new ArrayList<ParameterInfoStruct>();
        Object[] params = parameterData.getParametersArray();
        ParameterInfoStruct pis = null;
        Parameter temp = null;
        for (int i = 0; i < params.length; i++) {
            temp = (Parameter) params[i];
            if (isSelectable(temp.getName(), name, nextLevel)) {
                pis = new ParameterInfoStruct(temp.getName(), temp
                        .isWritable());
                lsParameter.add(pis);
            }
        }
        result = (ParameterInfoStruct[]) lsParameter
                .toArray(new ParameterInfoStruct[lsParameter.size()]);
        return result;
    }
    /**
     * Checks if is selectable.
     * @param initial the initial
     * @param name the name
     * @param nextLevel the next level
     * @return true, if is selectable
     */
    protected static boolean isSelectable(final String initial,
            final String name, final boolean nextLevel) {
        boolean result = false;
        if (initial.startsWith(name)) {
            if (nextLevel) {
                if (initial.equals(name)) { 
                    return false;
                }
                int dotPosition = initial.indexOf('.', name.length());
                result = (dotPosition == -1 || dotPosition == initial.length()-1);                
            } else {
                result = true;
            }
        }
        return result;
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
