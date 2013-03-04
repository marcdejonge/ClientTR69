/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : GetParameterAttributesBundle
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

package com.francetelecom.admindm.getParameterAttributes;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethodMngService;
/**
 * The Class Activator.
 */
public final class Activator implements BundleActivator {
    /** The RPC method mng service ref. */
    private ServiceReference rpcMethodMngServiceRef = null;
    /** The rpc mng. */
    private RPCMethodMngService rpcMng;
    /**
     * Start.
     * @param context the context
     * @throws Exception the exception
     */
    public void start(final BundleContext context) throws Exception {
        rpcMethodMngServiceRef = context
                .getServiceReference(RPCMethodMngService.class.getName());
        if (rpcMethodMngServiceRef != null) {
            rpcMng = (RPCMethodMngService) context
                    .getService(rpcMethodMngServiceRef);
            rpcMng.registerRPCMethod("GetParameterAttributes");
            rpcMng.registerRPCEncoder("GetParameterAttributesResponse",
                    new GetParameterAttributesResponseEncoder());
            rpcMng.registerRPCDecoder("GetParameterAttributes",
                    new GetParameterAttributesDecoder());
            Log.info("Start RPC Method GetParameterAttributes");
        } else {
            Log.error("Unable tp start GetParameterAttributes: "
                    + "RPCMethodMngService is missing");
        }
    }
    /**
     * Stop.
     * @param context the context
     * @throws Exception the exception
     */
    public void stop(final BundleContext context) throws Exception {
        if (rpcMng != null) {
            rpcMng.unregisterRPCMethod("GetParameterAttribute");
            rpcMng.unregisterRPCEncoder("GetParameterAttributeResponse");
            rpcMng.unregisterRPCDecoder("GetParameterAttribute");
        }
        context.ungetService(rpcMethodMngServiceRef);
    }
}
