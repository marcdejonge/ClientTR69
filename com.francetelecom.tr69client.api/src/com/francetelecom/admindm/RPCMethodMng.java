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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.francetelecom.admindm.api.EventBehavior;
import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.inform.InformResponse;
import com.francetelecom.admindm.soap.FaultDecoder;
import com.francetelecom.admindm.soap.FaultEncoder;
import com.francetelecom.admindm.soap.SetParamValuesFaultEncoder;
/**
 * The Class RPCMethodMng.
 */
public final class RPCMethodMng implements RPCMethodMngService {
    /** The instance. */
    private static RPCMethodMng instance = new RPCMethodMng();
    /**
     * Gets the single instance of RPCMethodMng.
     * @return single instance of RPCMethodMng
     */
    public static RPCMethodMng getInstance() {
        return instance;
    }
    /** The map eventname event behavior. */
    private Map mapEventnameEventBehavior = new HashMap();
    /** The map name rpc encoder. */
    private Map mapNameRPCEncoder = new HashMap();
    /** The map name rpc decoder. */
    private Map mapNameRPCDecoder = new HashMap();
    /** The ls rpc methods. */
    private List lsRPCMethods = new ArrayList();
    /**
     * Instantiates a new rPC method mng.
     */
    private RPCMethodMng() {
        registerRPCEncoder("Fault", new FaultEncoder());
        registerRPCEncoder("SetParamValuesFault",
                new SetParamValuesFaultEncoder());
        registerRPCDecoder("Fault", new FaultDecoder());
        registerEventBehavior(EventCode.BOOTSTRAP, new EventBehavior(true,
                EventCode.DISCARD_OTHER_EVENTS, InformResponse.NAME));
        registerEventBehavior(EventCode.BOOT, new EventBehavior(true,
                EventCode.RETRY_UNTIL_REBOOT, InformResponse.NAME));
        registerEventBehavior(EventCode.PERIODIC, new EventBehavior(true,
                EventCode.ALWAYS_RETRY, InformResponse.NAME));
        registerEventBehavior(EventCode.SCHEDULED, new EventBehavior(true,
                EventCode.ALWAYS_RETRY, InformResponse.NAME));
        registerEventBehavior(EventCode.VALUE_CHANGE, new EventBehavior(true,
                EventCode.RETRY_UNTIL_REBOOT, InformResponse.NAME));
        // personal choice EventCode.Kicked never retry
        registerEventBehavior(EventCode.KICKED, new EventBehavior(true,
                EventCode.NEVER_RETRY, "KickedResponse"));
        registerEventBehavior(EventCode.CONNECTION_REQUEST, new EventBehavior(
                true, EventCode.NEVER_RETRY, InformResponse.NAME));
        registerEventBehavior(EventCode.DIAGNOSTICS_COMPLETE,
                new EventBehavior(true, EventCode.RETRY_UNTIL_REBOOT,
                        InformResponse.NAME));
        // personal choice EventCode.RequestDownload never retry
        registerEventBehavior(EventCode.REQUEST_DOWNLOAD, new EventBehavior(
                true, EventCode.NEVER_RETRY, "RequestDownloadResponse"));
        // AUTONOMOUS_TRANSFER_COMPLETE is optional
        // registerEventBehavior(EventCode.AUTONOMOUS_TRANSFER_COMPLETE, new
        // EventBehavior(true,EventCode.ALWAYS_RETRY,
        // "AutonomousTransferCompleteResponse"));
        registerEventBehavior(EventCode.M_REBOOT, new EventBehavior(false,
                EventCode.ALWAYS_RETRY, InformResponse.NAME));
        registerEventBehavior(EventCode.M_SCHEDULE_INFORM, new EventBehavior(
                false, EventCode.ALWAYS_RETRY, InformResponse.NAME));
        // M_UPLOAD is optional
        // registerEventBehavior(EventCode.M_UPLOAD, new
        // EventBehavior(false,EventCode.ALWAYS_RETRY));
    }
    /**
     * Register rpc method.
     * @param name the name
     */
    public void registerRPCMethod(final String name) {
        StringBuffer buffer = new StringBuffer("Register RPCMethod ");
        buffer.append(name);
        if (lsRPCMethods.contains(name)) {
            buffer.append(" failed: cause it was already registred");
            Log.warn(buffer.toString());
        } else {
            Log.debug(buffer.toString());
            lsRPCMethods.add(name);
        }
    }
    /**
     * Unregister rpc method.
     * @param name the name
     */
    public void unregisterRPCMethod(final String name) {
        StringBuffer buffer = new StringBuffer("Unregister RPCMethod ");
        buffer.append(name);
        if (lsRPCMethods.contains(name)) {
            Log.debug(buffer.toString());
            lsRPCMethods.remove(name);
        } else {
            buffer.append(" failed: cause it wasn't registred");
            Log.warn(buffer.toString());
        }
    }
    /**
     * Register rpc encoder.
     * @param name the name
     * @param encoder the encoder
     */
    public void registerRPCEncoder(final String name, final RPCEncoder encoder) {
        StringBuffer buffer = new StringBuffer("Register Encoder ");
        buffer.append(name);
        Log.debug(buffer.toString());
        mapNameRPCEncoder.put(name, encoder);
    }
    /**
     * Unregister rpc encoder.
     * @param name the name
     */
    public void unregisterRPCEncoder(final String name) {
        StringBuffer buffer = new StringBuffer("Unregister Encoder ");
        buffer.append(name);
        Log.debug(buffer.toString());
        mapNameRPCEncoder.remove(name);
    }
    /**
     * Register rpc decoder.
     * @param name the name
     * @param decoder the decoder
     */
    public void registerRPCDecoder(final String name, final RPCDecoder decoder) {
        StringBuffer buffer = new StringBuffer("Register Decoder ");
        buffer.append(name);
        Log.debug(buffer.toString());
        mapNameRPCDecoder.put(name, decoder);
    }
    /**
     * Unregister rpc decoder.
     * @param name the name
     */
    public void unregisterRPCDecoder(final String name) {
        StringBuffer buffer = new StringBuffer("Unregister Decoder ");
        buffer.append(name);
        Log.debug(buffer.toString());
        mapNameRPCDecoder.remove(name);
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
        result = (RPCEncoder) instance.mapNameRPCEncoder.get(value);
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
        result = (RPCDecoder) instance.mapNameRPCDecoder.get(value);
        if (result == null) {
            Log.error("unable to find decoder for: " + value);
        }
        return result;
    }
    /**
     * Gets the rpc method.
     * @return the RPC method
     */
    public List getRPCMethod() {
        return lsRPCMethods;
    }
    /**
     * Register event behavior.
     * @param name the name
     * @param eventBehavior the event behavior
     */
    public void registerEventBehavior(final String name,
            final EventBehavior eventBehavior) {
        StringBuffer buffer = new StringBuffer("Register EventBehavior ");
        buffer.append(name);
        Log.debug(buffer.toString());
        mapEventnameEventBehavior.put(name, eventBehavior);
    }
    /**
     * Gets the map eventname event behavior.
     * @return the map eventname event behavior
     */
    public static Map getMapEventnameEventBehavior() {
        Set entry = instance.mapEventnameEventBehavior.keySet();
        Iterator it = entry.iterator();
        String key;
        EventBehavior eb;
        while (it.hasNext()) {
            key = (String) it.next();
            eb = (EventBehavior) instance.mapEventnameEventBehavior.get(key);
            eb.setCount(0);
        }
        return instance.mapEventnameEventBehavior;
    }
    /**
     * Unregister event behavior.
     * @param name the name
     */
    public void unregisterEventBehavior(final String name) {
        instance.mapEventnameEventBehavior.remove(name);
    }
}
