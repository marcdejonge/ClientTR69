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
package com.francetelecom.admindm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

import com.francetelecom.admindm.api.EventBehavior;
import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.inform.InformResponse;
/**
 * The Class RPCMethodMng.
 */
@Component
public final class RPCMethodMng implements RPCMethodMngService {
	private static final String PROPERTY_KEY = "name";

	private static RPCMethodMng instance;
	
    /** The map eventname event behavior. */
    private Map<String, EventBehavior> mapEventnameEventBehavior = new HashMap<String, EventBehavior>();
    /** The map name rpc encoder. */
    private Map<String, RPCEncoder> mapNameRPCEncoder = new HashMap<String, RPCEncoder>();
    /** The map name rpc decoder. */
    private Map<String, RPCDecoder> mapNameRPCDecoder = new HashMap<String, RPCDecoder>();
    /**
     * Instantiates a new rPC method mng.
     */
    public RPCMethodMng() {
    	instance = this;
    	
    	addEventBehavior(new EventBehavior(true, EventCode.DISCARD_OTHER_EVENTS, InformResponse.NAME), 
    	                 Collections.singletonMap(PROPERTY_KEY, EventCode.BOOTSTRAP));
    	addEventBehavior(new EventBehavior(true, EventCode.RETRY_UNTIL_REBOOT, InformResponse.NAME), 
    			Collections.singletonMap(PROPERTY_KEY, EventCode.BOOT));
    	addEventBehavior(new EventBehavior(true, EventCode.ALWAYS_RETRY, InformResponse.NAME), 
    			Collections.singletonMap(PROPERTY_KEY, EventCode.PERIODIC));
    	addEventBehavior(new EventBehavior(true, EventCode.ALWAYS_RETRY, InformResponse.NAME), 
    			Collections.singletonMap(PROPERTY_KEY, EventCode.SCHEDULED));
    	addEventBehavior(new EventBehavior(true, EventCode.RETRY_UNTIL_REBOOT, InformResponse.NAME), 
    			Collections.singletonMap(PROPERTY_KEY, EventCode.VALUE_CHANGE));
    	addEventBehavior(new EventBehavior(true, EventCode.NEVER_RETRY, "KickedResponse"), 
    			Collections.singletonMap(PROPERTY_KEY, EventCode.KICKED));
    	addEventBehavior(new EventBehavior(true, EventCode.NEVER_RETRY, InformResponse.NAME), 
    			Collections.singletonMap(PROPERTY_KEY, EventCode.CONNECTION_REQUEST));
    	addEventBehavior(new EventBehavior(true, EventCode.RETRY_UNTIL_REBOOT, InformResponse.NAME), 
    			Collections.singletonMap(PROPERTY_KEY, EventCode.DIAGNOSTICS_COMPLETE));
    	addEventBehavior(new EventBehavior(true, EventCode.NEVER_RETRY, "RequestDownloadResponse"), 
    			Collections.singletonMap(PROPERTY_KEY, EventCode.REQUEST_DOWNLOAD));
    	addEventBehavior(new EventBehavior(false, EventCode.ALWAYS_RETRY, InformResponse.NAME), 
    			Collections.singletonMap(PROPERTY_KEY, EventCode.M_REBOOT));
    	addEventBehavior(new EventBehavior(false, EventCode.ALWAYS_RETRY, InformResponse.NAME), 
    			Collections.singletonMap(PROPERTY_KEY, EventCode.M_SCHEDULE_INFORM));
    }
    
    @Reference(dynamic=true, multiple=true, optional=true)
    public void addRPCEncoder(RPCEncoder encoder, Map<String, String> properties) {
    	if(properties.containsKey(PROPERTY_KEY) && !mapNameRPCEncoder.containsKey(properties.get(PROPERTY_KEY))) {
    		mapNameRPCEncoder.put(properties.get(PROPERTY_KEY), encoder);
    	} else {
    		Log.error("Registered a RPCEncoder without name property : " + encoder.getClass().getCanonicalName());
    	}
    }
    
    public void removeRPCEncoder(RPCEncoder encoder, Map<String, String> properties) {
    	mapNameRPCEncoder.remove(properties.get(PROPERTY_KEY));
    }
    
    @Reference(dynamic=true, multiple=true, optional=true)
    public void addRPCDecoder(RPCDecoder decoder, Map<String, String> properties) {
    	if(properties.containsKey(PROPERTY_KEY) && !mapNameRPCDecoder.containsKey(properties.get(PROPERTY_KEY))) {
    		mapNameRPCDecoder.put(properties.get(PROPERTY_KEY), decoder);
    	} else {
    		Log.error("Registered a RPCDecoder without name property : " + decoder.getClass().getCanonicalName());
    	}
    }
    
    public void removeRPCDecoder(RPCDecoder decoder, Map<String, String> properties) {
    	mapNameRPCDecoder.remove(properties.get(PROPERTY_KEY));
    }
    
    @Reference(dynamic=true, multiple=true, optional=true)
    public void addEventBehavior(EventBehavior eventBehavior, Map<String, String> properties) {
    	if(properties.containsKey(PROPERTY_KEY) && !mapEventnameEventBehavior.containsKey(properties.get(PROPERTY_KEY))) {
    		mapEventnameEventBehavior.put(properties.get(PROPERTY_KEY), eventBehavior);
    	} else {
    		Log.error("Registered a EventBehavior without name property : " + eventBehavior.getClass().getCanonicalName());
    	}
    }
    
    public void removeEventBehavior(EventBehavior eventBehavior, Map<String, String> properties) {
    	mapNameRPCDecoder.remove(properties.get(PROPERTY_KEY));
    }
    
    /**
     * Find rpc method encoder.
     * @param method the method
     * @return the rPC encoder
     */
    public RPCEncoder findRPCMethodEncoder(final RPCMethod method) {
        RPCEncoder result = null;
        if (method != null) {
            result = findRPCMethodEncoder(method.getName());
        }
        return result;
    }
    /**
     * Find rpc method encoder.
     * @param value the value
     * @return the rPC encoder
     */
    public RPCEncoder findRPCMethodEncoder(final String value) {
        RPCEncoder result;
        result = mapNameRPCEncoder.get(value);
        if (result == null) {
            Log.error("unable to find encoder for: " + value);
        }
        return result;
    };
    /**
     * Find rpc method decoder.
     * @param value the value
     * @return the rPC decoder
     */
    public RPCDecoder findRPCMethodDecoder(final String value) {
        RPCDecoder result;
        result = mapNameRPCDecoder.get(value);
        if (result == null) {
            Log.error("unable to find decoder for: " + value);
        }
        return result;
    }
    
    public List<String> getRPCMethods() {
    	List<String> result = new ArrayList<String>();
    	for(String name : mapNameRPCDecoder.keySet()) {
    		if(!name.endsWith("Response")){
    			result.add(name);
    		}
    	}
    	for(String name : mapNameRPCEncoder.keySet()) {
    		if(!name.endsWith("Response")){
    			result.add(name);
    		}
    	}
    	return result;
    }

    /**
     * Gets the map eventname event behavior.
     * @return the map eventname event behavior
     */
    public static Map<String, EventBehavior> getMapEventnameEventBehavior() {
        Set<String> entry = instance.mapEventnameEventBehavior.keySet();
        Iterator<String> it = entry.iterator();
        String key;
        EventBehavior eb;
        while (it.hasNext()) {
            key = it.next();
            eb = instance.mapEventnameEventBehavior.get(key);
            eb.setCount(0);
        }
        return instance.mapEventnameEventBehavior;
    }
}
