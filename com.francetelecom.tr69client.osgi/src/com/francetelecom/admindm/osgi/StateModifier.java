/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : OSGiBundle
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

package com.francetelecom.admindm.osgi;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import com.francetelecom.admindm.api.CheckCallBack;
import com.francetelecom.admindm.api.Getter;
import com.francetelecom.admindm.api.Setter;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class OSGISetState.
 */
public final class StateModifier implements Setter,
        Getter, CheckCallBack {
    /** The bundle. */
    private final Bundle bundle;
    /** The state param. */
    private final Parameter stateParam;
    /**
     * Instantiates a new oSGI set state.
     * @param data the data
     * @param pBundle the bundle
     * @param pParam the state parameter
     */
    public StateModifier(final IParameterData data, final Bundle pBundle,
            final Parameter pParam) {
        this.bundle = pBundle;
        this.stateParam = pParam;
    }
    /**
     * Bundle changed.
     * @param event the event
     */
    public void bundleChanged(final BundleEvent event) {
        ///super.bundleChanged(event);
        stateParam.setDirectValue(get("SessionId"));
    }
    /**
     * Sets the parameter with the Object.
     * @param param the param
     * @param obj the obj
     * @throws Fault the fault
     */
    public void set(final Parameter param, final Object obj) throws Fault {
        try {
            if ("START".equals(obj)) {
                bundle.start();
            } else if ("STOP".equals(obj)) {
                bundle.stop();
            } else {
                // the check should have been performed before.
                check(obj);
            }
        } catch (BundleException e) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9001);
            error.append(": ");
            error.append(e.getLocalizedMessage());
            throw new Fault(FaultUtil.FAULT_9001, error.toString(), e);
        }
    }
    /**
     * Check.
     * @param value the value
     * @throws Fault the fault
     */
    public void check(final Object value) throws Fault {
        boolean result = false;
        result = "START".equals(value) | "STOP".equals(value);
        if (!result) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": START , STOP are only allowed values.");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
    }
    
    /**
     * Gets the Object.
     * @param sessionId the session id
     * @return the object
     */
    public Object get(final String sessionId) {
        String result;
        switch (bundle.getState()) {
        case Bundle.ACTIVE:
            result = "ACTIVE";
            break;
        case Bundle.INSTALLED:
            result = "INSTALLED";
            break;
        case Bundle.RESOLVED:
            result = "RESOLVED";
            break;
        case Bundle.STARTING:
            result = "STARTING";
            break;
        case Bundle.STOPPING:
            result = "STOPPING";
            break;
        case Bundle.UNINSTALLED:
            result = "UNINSTALLED";
            break;
        default:
            result = "UNKNOWN";
            break;
        }
        return result;
    }
}
