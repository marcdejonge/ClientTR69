/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : TR69ClientAPI
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
 */ 
package com.francetelecom.admindm.model;
import java.util.Iterator;
import java.util.List;
import java.util.Observer;
import java.util.Set;
import com.francetelecom.admindm.api.ICom;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Interface IParameterData.
 * @author Olivier Beyler - OrangeLabs -
 */
public interface IParameterData {
    /**
     * Gets the root of data model.
     * @return root
     */
    String getRoot();
    /**
     * Sets the root of the data model.
     * @param root root
     */
    void setRoot(String root);
    /**
     * Gets the list of outgoing request.
     * @return the list of outgoing request
     */
    List getLsOutgoingRequest();
    /**
     * Gets the parameter iterator.
     * @return the parameter iterator
     */
    Iterator getParameterIterator();
    /**
     * Checks if is model loaded.
     * @return true, if is model loaded
     */
    boolean isModelLoaded();
    /**
     * Sets the model loaded.
     * @param isModelLoaded the new model loaded
     */
    void setModelLoaded(final boolean isModelLoaded);
    /**
     * Gets the parameter.
     * @param value the value
     * @return a Parameter
     */
    Parameter getParameter(final String value);
    /**
     * Creates the or retrieve parameter.
     * @param key the key
     * @return the parameter
     * @throws Fault the fault
     */
    Parameter createOrRetrieveParameter(final String key) throws Fault;
    /**
     * Adds the or update parameter.
     * @param param the parameter
     * @param updater the updater
     * @throws Fault the fault
     */
    void addOrUpdateParameter(final Parameter param, final String updater)
            throws Fault;
    /**
     * Adds the observer.
     * @param observer the observer
     */
    void addObserver(Observer observer);
    /**
     * Extract parameter list.
     * @param filter the filter
     * @return the list
     * @throws Fault the fault
     */
    List extractParameterList(final String filter) throws Fault;
    /**
     * Extract a list of parameter corresponding to the parameterName criteria.
     * @param filters string array of full or partial path of a parameter
     * @return List of parameter
     * @throws Fault in case of parameterName'array contains unknown parameter
     *             or unknown partial path.
     */
    List extractParameterList(final String[] filters) throws Fault;
    /**
     * Delete parameter.
     * @param param the parameter
     */
    void deleteParam(final Parameter param);
    /**
     * Adds the event.
     * @param eventStruct the event structure
     */
    void addEvent(final EventStruct eventStruct);
    /**
     * Delete event.
     * @param eventStruct the event structure
     */
    void deleteEvent(final EventStruct eventStruct);
    /**
     * Gets the parameters array.
     * @return the parameters array
     */
    Object[] getParametersArray();
    /**
     * Sets the com.
     * @param pCom the com
     */
    void setCom(final ICom pCom);
    /**
     * Gets the com.
     * @return the com
     */
    ICom getCom();
    /**
     * Gets the events array.
     * @return the events array
     */
    Object[] getEventsArray();
    /**
     * Gets the device id.
     * @return the device id
     */
    DeviceIdStruct getDeviceId();
    /**
     * Sets the parameter key.
     * @param pParameterKey the parameter key
     */
    void setParameterKey(final String pParameterKey);
    /**
     * Gets the parameter key.
     * @return the parameter key
     */
    String getParameterKey();
    /**
     * Adds the outgoing request and ask for creating a new session.
     * @param rpcMethod the method
     */
    void addOutgoingRequest(final RPCMethod rpcMethod);
    /**
     * Remove the outgoing request and ask for creating a new session.
     * @param rpcMethod the method
     */
    void removeOutgoingRequest(final RPCMethod rpcMethod);
    /**
     * Gets the sets the param changed.
     * @return the sets the param changed
     */
    Set getSetParamChanged();
}
