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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import com.francetelecom.admindm.api.CheckCallBack;
import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.ICom;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.persist.IPersist;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class ParameterData.
 */
public final class ParameterData extends Observable implements IParameterData,
        Observer {
    /** The device id. */
    private DeviceIdStruct deviceId;
    /** The list of parameters. */
    private List<Parameter> lsParameters;
    /** The list of events. */
    private List<EventStruct> lsEvents;
    /** The list of outgoing request. */
    private List<RPCMethod> lsOutgoingRequest;
    /** The event file. */
    private File eventFile;
    /** root of the data model. */
    private String root = "InternetGatewayDevice.";
    /** The persist. */
    private IPersist persist;
    /**
     * Gets the persist.
     * @return the persist
     */
    public IPersist getPersist() {
        return persist;
    }
    /**
     * Sets the persist.
     * @param persist the new persist
     */
    public void setPersist(IPersist persist) {
        Iterator<Parameter> it = lsParameters.iterator();
        while (it.hasNext()) {
            Parameter param = it.next();
            param.setPersist(persist);
        }
        this.persist = persist;
    }
    /**
     * Sets the event file.
     * @param pEventFile the event file
     */
    public void setEventFile(final File pEventFile) {
        this.eventFile = pEventFile;
    }
    /**
     * Gets the list of outgoing request.
     * @return the list of outgoing request
     */
    public List<RPCMethod> getLsOutgoingRequest() {
        return lsOutgoingRequest;
    }
    /** The parameter key. */
    private static String parameterKey;
    /**
     * Instantiates a new parameter data.
     */
    public ParameterData() {
        deviceId = new DeviceIdStruct(this);
        lsParameters = Collections.synchronizedList(new ArrayList<Parameter>());
        lsEvents = Collections.synchronizedList(new ArrayList<EventStruct>());
        lsOutgoingRequest = Collections.synchronizedList(new ArrayList<RPCMethod>());
    }
    /**
     * Gets the parameter iterator.
     * @return the parameter iterator
     */
    public Iterator<Parameter> getParameterIterator() {
        return lsParameters.iterator();
    }
    /** The is model loaded. */
    private boolean isModelLoaded = false;
    /**
     * Checks if is model loaded.
     * @return true, if checks if is model loaded
     */
    public boolean isModelLoaded() {
        return isModelLoaded;
    }
    /**
     * Sets the model loaded.
     * @param pIsModelLoaded the is model loaded
     */
    public void setModelLoaded(final boolean pIsModelLoaded) {
        if (isModelLoaded != pIsModelLoaded) {
            Log.info("##############################");
            Log.info("#    Data Model LOADED       #");
            Log.info("##############################");
            this.isModelLoaded = pIsModelLoaded;
        }
    }
    /**
     * Gets the parameter.
     * @param value the value
     * @return the parameter
     */
    public Parameter getParameter(final String value) {
        Parameter result = null;
        Object[] objs = lsParameters.toArray();
        if (value != null) {
            for (int i = 0; i < objs.length && result == null; i++) {
                Parameter temp = (Parameter) objs[i];
                if (value.equals(temp.getName())) {
                    result = temp;
                }
            }
        }
        /*
         * if (result == null) { Log.warn("try to get unknown Parameter \"" +
         * value+"\""); }
         */
        return result;
    }
    /**
     * Value param.
     * @param key the key
     * @param value the value
     * @throws Fault the fault java.lang.String)
     */
    // public void valueParam(final String key, final String value) throws Fault
    // {
    // Parameter param = createOrRetrieveParameter(key);
    // param.setValue(value);
    // param.notifyObservers();
    // }
    /**
     * Creates the or retrieve parameter.
     * @param key the key
     * @return the parameter
     * @throws Fault the fault
     */
    public Parameter createOrRetrieveParameter(final String key) throws Fault {
        Parameter param = getParameter(key);
        if (param == null) {
            param = new Parameter();
            param.setName(key);
            param.setStorageMode(StorageMode.COMPUTED);
            param.addObserver(this);
            param.setPersist(persist);
            lsParameters.add(param);
        }
        return param;
    }
    /**
     * Adds the or update parameter.
     * @param param the param
     * @param updater the updater
     * @throws Fault the fault
     */
    public void addOrUpdateParameter(final Parameter param,
            final String updater) throws Fault {
        Parameter parameter = createOrRetrieveParameter(param.getName());
        parameter.setAccessList(param.getAccessList());
        parameter.setActiveNotificationDenied(param
                .isActiveNotificationDenied());
        parameter.setBackValue(param.getBackValue());
        parameter.setImmediateChanges(param.isImmediateChanges());
        parameter.setMandatoryNotification(param.isMandatoryNotification());
        List<CheckCallBack> ls = parameter.getLsCheckCallBack();
        ls.clear();
        Iterator<CheckCallBack> it = ls.iterator();
        while (it.hasNext()) {
            parameter.addCheck(it.next());
        }
        parameter.setNotification(param.getNotification());
        parameter.setState(param.getState());
        parameter.setStorageMode(param.getStorageMode());
        parameter.setType(param.getType());
        parameter.setUpdateMode(param.getUpdateMode());
        parameter.setValueWithout(param.getValue());
        parameter.setWritable(param.isWritable());
    }
    /**
     * Extract parameter list.
     * @param searchName the names
     * @return the list
     * @throws Fault the fault
     */
    public List<Parameter> extractParameterList(final String searchName) throws Fault {
        List<Parameter> lsParam = new ArrayList<Parameter>();
        String name;
        Parameter param = getParameter(searchName);
        if (param == null && !"".equals(searchName)) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9005);
            error.append(": '");
            error.append(searchName);
            error.append("' doesn't belong to data model.");
            throw new Fault(FaultUtil.FAULT_9005, error.toString());
        }
        if ("".equals(searchName)|(searchName != null && searchName.endsWith("."))) {
            Iterator<Parameter> it = lsParameters.iterator();
            while (it.hasNext()) {
                param = it.next();
                name = param.getName();
                if (name != null && name.startsWith(searchName)) {
                    lsParam.add(param);
                }
            }
        } else {
            lsParam.add(param);
        }
        return lsParam;
    }
    /**
     * Extract parameter list.
     * @param parameterNames the parameter names
     * @return the list
     * @throws Fault the fault
     */
    public List<Parameter> extractParameterList(final String[] parameterNames)
            throws Fault {
        List<Parameter> result = new ArrayList<Parameter>();
        List<Parameter> resultPartial;
        for (int i = 0; i < parameterNames.length; i++) {
            resultPartial = extractParameterList(parameterNames[i]);
            while (!resultPartial.isEmpty()) {
                result.add(resultPartial.remove(0));
            }
        }
        return result;
    }
    /**
     * Delete param.
     * @param param the param
     */
    public void deleteParam(final Parameter param) {
        lsParameters.remove(param);
    }
    /**
     * Adds the event.
     * @param eventStruct the event struct
     */
    public void addEvent(final EventStruct eventStruct) {
        lsEvents.add(eventStruct);
        storeEvent();
    }
    /**
     * Restore events.
     */
    protected void restoreEvents() {
        FileInputStream inputstream = null;
        ObjectInputStream p = null;
        if (eventFile != null) {
            if (eventFile.exists()) {
                try {
                    inputstream = new FileInputStream(eventFile);
                    p = new ObjectInputStream(inputstream);
                    int nb = p.readInt();
                    lsEvents = new ArrayList<EventStruct>();
                    for (int i = 0; i < nb; i++) {
                        lsEvents.add((EventStruct) p.readObject());
                    }
                } catch (ClassNotFoundException e) {
                    StringBuffer error = new StringBuffer(
                            "Unable to restore Event List");
                    error.append(e.getLocalizedMessage());
                    Log.error(error.toString());
                } catch (IOException e) {
                    StringBuffer error = new StringBuffer(
                            "Unable to restore Event List ");
                    e.printStackTrace();
                    error.append(e.getMessage());
                    Log.error(error.toString());
                } finally {
                    if (p != null) {
                        try {
                            p.close();
                        } catch (IOException e) {
                            Log.error("Exception :", e);
                        }
                    }
                    if (inputstream != null) {
                        try {
                            inputstream.close();
                        } catch (IOException e) {
                            Log.error("Exception :", e);
                        }
                    }
                }
            } else {
                Log.info("No persist Events file found");
            }
        } else {
            Log.error("Unable to store event");
        }
    }
    /**
     * Store data will persist the list of Event.
     */
    protected void storeEvent() {
        // TODO keep only the event that must be restore
        FileOutputStream ostream = null;
        ObjectOutputStream p = null;
        if (eventFile != null) {
            try {
                eventFile.createNewFile();
                ostream = new FileOutputStream(eventFile);
                p = new ObjectOutputStream(ostream);
                Object[] objects = lsEvents.toArray();
                p.writeInt(objects.length);
                for (int i = 0; i < objects.length; i++) {
                    p.writeObject(objects[i]);
                }
            } catch (IOException e) {
                StringBuffer error = new StringBuffer(
                        "Unable to store Event List");
                error.append(e.getLocalizedMessage());
                Log.error(error.toString());
            } finally {
                if (p != null) {
                    try {
                        p.close();
                    } catch (IOException e) {
                        Log.error("Exception :", e);
                    }
                }
                if (ostream != null) {
                    try {
                        ostream.close();
                    } catch (IOException e) {
                        Log.error("Exception :", e);
                    }
                }
            }
        } else {
            Log.error("Unable to store event");
        }
    }
    /**
     * Delete event.
     * @param eventStruct the event struct
     */
    public void deleteEvent(final EventStruct eventStruct) {
        lsEvents.remove(eventStruct);
        storeEvent();
    }
    /**
     * Gets the parameters array.
     * @return the parameters array
     */
    public Object[] getParametersArray() {
        return lsParameters.toArray();
    }
    /**
     * Gets the device id.
     * @return the device id
     */
    public DeviceIdStruct getDeviceId() {
        return deviceId;
    }
    /**
     * Gets the event iterator.
     * @return the event iterator
     */
    public Iterator<EventStruct> getEventIterator() {
        return lsEvents.iterator();
    }
    /**
     * Sets the parameter key.
     * @param pParameterKey the parameter key
     */
    public void setParameterKey(final String pParameterKey) {
        ParameterData.parameterKey = pParameterKey;
    }
    /**
     * Gets the parameter key.
     * @return the parameter key
     */
    public String getParameterKey() {
        return parameterKey;
    }
    /**
     * Adds the outgoing request.
     * @param rpcMethod the rpc method
     */
    public void addOutgoingRequest(final RPCMethod rpcMethod) {
        lsOutgoingRequest.add(rpcMethod);
        if (com != null) {
            com.requestNewSession();
        }
    }
    /** The com. */
    private ICom com = null;
    /**
     * Gets the com.
     * @return the com
     */
    public ICom getCom() {
        return com;
    }
    /**
     * Sets the com.
     * @param pCom the com
     */
    public void setCom(final ICom pCom) {
        this.com = pCom;
    }
    /**
     * Removes the outgoing request.
     * @param rpcMethod the rpc method
     */
    public void removeOutgoingRequest(final RPCMethod rpcMethod) {
        lsOutgoingRequest.remove(rpcMethod);
    }
    /**
     * Gets the events array.
     * @return the events array
     * @see com.francetelecom.admindm.model.IParameterData#getEventsArray()
     */
    public Object[] getEventsArray() {
        return lsEvents.toArray();
    }
    /**
     * Gets the root of the data model.
     * @return the root
     */
    public String getRoot() {
        return this.root;
    }
    /**
     * Sets the root.
     * @param pRoot the root
     */
    public void setRoot(final String pRoot) {
        this.root = pRoot;
    }
    /**
     * Update.
     * @param arg0 the arg0
     * @param arg1 the arg1
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(final Observable arg0, final Object arg1) {
        if (arg0 instanceof Parameter) {
            Parameter param = (Parameter) arg0;
            if (param.getNotification() != 0) {
                setParamChanged.add(param);
                addEvent(new EventStruct(EventCode.VALUE_CHANGE, ""));
            }            
            if (param.getNotification() == 2 && com != null) {
                com.requestNewSession();
            }
        }
    }
    private Set<Parameter> setParamChanged = new HashSet<Parameter>();
    
    /**
     * To string (very useful for debug).
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        Iterator<Parameter> it = lsParameters.iterator();
        StringBuffer buffer = new StringBuffer("");
        while (it.hasNext()) {
            Parameter param = it.next();
            buffer.append(param.getName());
            if (param.getType()!=ParameterType.ANY){
            for (int i = param.getName().length(); i < 70; i++) {
                buffer.append(".");
            }
            
            buffer.append("> ");
            String value = param.getTextValue("");
            if (value != null && value.length() > 20) {
                value = value.substring(0, 19) + "...";
            }
            buffer.append(value);
            buffer.append("");
            }
            buffer.append(System.getProperty("line.separator"));
        }
        return buffer.toString();
    }
    
    public Set<Parameter> getSetParamChanged() {
        return setParamChanged;
    }
}
