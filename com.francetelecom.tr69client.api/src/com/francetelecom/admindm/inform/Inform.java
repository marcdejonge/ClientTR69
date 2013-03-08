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
package com.francetelecom.admindm.inform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.francetelecom.admindm.RPCMethodMng;
import com.francetelecom.admindm.api.EventBehavior;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.DeviceIdStruct;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterValueStruct;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class Inform.
 * @author Olivier Beyler - OrangeLabs -
 */
public final class Inform implements RPCMethod {
    /** The name. */
    private static final String NAME = "Inform";
    /** The device id. */
    private DeviceIdStruct deviceId;
    /** The event. */
    private List<EventStruct> event = new ArrayList<EventStruct>();
    /** The max envelopes. */
    private long maxEnvelopes = 1;
    /** The inform encoder. */
    private static InformEncoder informEncoder = new InformEncoder();
    /**
     * Gets the device id.
     * @return the device id
     */
    public DeviceIdStruct getDeviceId() {
        return deviceId;
    }
    /**
     * Sets the device id.
     * @param pDeviceId the new device id
     */
    public void setDeviceId(final DeviceIdStruct pDeviceId) {
        this.deviceId = pDeviceId;
    }
    /**
     * Gets the event.
     * @return the event
     */
    public List<EventStruct> getEvent() {
        return event;
    }
    /**
     * Sets the event.
     * @param pEvent the new event
     */
    public void setEvent(final List<EventStruct> pEvent) {
        this.event = pEvent;
    }
    /**
     * Gets the max envelopes.
     * @return the max envelopes
     */
    public long getMaxEnvelopes() {
        return maxEnvelopes;
    }
    /**
     * Sets the max envelopes.
     * @param pMaxEnvelopes the new max envelopes
     */
    public void setMaxEnvelopes(final long pMaxEnvelopes) {
        this.maxEnvelopes = pMaxEnvelopes;
    }
    /**
     * Gets the current time.
     * @return the current time
     */
    public long getCurrentTime() {
        return currentTime;
    }
    /**
     * Sets the current time.
     * @param pCurrentTime the new current time
     */
    public void setCurrentTime(final long pCurrentTime) {
        this.currentTime = pCurrentTime;
    }
    /**
     * Gets the retry count.
     * @return the retry count
     */
    public long getRetryCount() {
        return retryCount;
    }
    /**
     * Gets the parameter list.
     * @return the parameter list
     */
    public List<ParameterValueStruct> getParameterList() {
        return parameterList;
    }
    /**
     * Sets the parameter list.
     * @param pParameterList the new parameter list
     */
    public void setParameterList(final List<ParameterValueStruct> pParameterList) {
        this.parameterList = pParameterList;
    }
    /** The current time. */
    private long currentTime;
    /** The retry count. */
    private final long retryCount;
    /** The parameter list. */
    private List<ParameterValueStruct> parameterList = new ArrayList<ParameterValueStruct>();
    /** The parameter data. */
    private final IParameterData parameterData;
    /**
     * Instantiates a new inform.
     * @param pParameterData the parameter data
     * @param retry the retry
     */
    public Inform(final IParameterData pParameterData, final long retry) {
        parameterData = pParameterData;
        deviceId = new DeviceIdStruct(pParameterData);
        retryCount = retry;
    }
    /**
     * construct the events list for an inform. As some Event code must be
     * unique the duplicate some event are remove.
     */
    private void constructEvents() {
        Map<String, EventBehavior> mapEventnameEventBehavior = RPCMethodMng
                .getMapEventnameEventBehavior();
        List<EventStruct> eventToBeRemove = new ArrayList<EventStruct>();
        EventStruct[] events = parameterData.getEventsArray();
        EventStruct evt;
        EventBehavior eb;
        for (int i = 0; i < events.length; i++) {
            evt = events[i];
            eb = mapEventnameEventBehavior.get(evt.getEventCode());
            if (eb == null || (eb.isMustBeSingle() && eb.getCount() == 0) || !eb.isMustBeSingle()) {
                event.add(evt);
                if (eb != null) {
                    eb.setCount(eb.getCount() + 1);
                }
            } else {
                eventToBeRemove.add(evt);
            }
        }
        // auto remove the duplicate event.
        Iterator<EventStruct> it = eventToBeRemove.iterator();
        while (it.hasNext()) {
            parameterData.deleteEvent(it.next());
        }
    }
    /**
     * Construct parameter.
     * @param sessionId the session id
     */
    private void constructParameter(final String sessionId) {
        Parameter[] obs = parameterData.getParametersArray();
        for (int i = 0; i < obs.length; i++) {
            Parameter param = (Parameter) obs[i];
            if (param.isMandatoryNotification()) {
                parameterData.getSetParamChanged().add(param);
            }
        }
        obs = parameterData.getSetParamChanged().toArray(new Parameter[0]);
        for (int i = 0; i < obs.length; i++) {
            Parameter param = obs[i];
            param.getType();
            ParameterValueStruct pvs = new ParameterValueStruct(param.getName(), param.getTextValue(sessionId), param.getType());
            parameterList.add(pvs);
        }
    };
    /**
     * Gets the name.
     * @return RPCMethod's name
     */
    public String getName() {
        return NAME;
    }
    /**
     * Perform.
     * @param session the session
     * @throws Fault the fault
     * @see RPCMethod#perform(Session)
     */
    public void perform(final Session session) throws Fault {
        constructEvents();
        constructParameter(session.getSessionId());
        session.doSoapRequest(informEncoder.encode(this),id);
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
