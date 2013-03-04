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
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.inform.InformResponseDecoder;
import com.francetelecom.admindm.model.ParameterData;
/**
 * The Class Activator.
 */
public final class Activator implements BundleActivator {
    /** The data. */
    private final ParameterData data;
    /**
     * Instantiates a new activator.
     */
    public Activator() {
        super();
        data = new ParameterData();
    }
    /**
     * Start.
     * @param context the context
     * @throws Exception the exception
     */
    public void start(final BundleContext context) throws Exception {
        context.addServiceListener(new Scheduler(context, data));
        data.setEventFile(context.getDataFile("Events.data"));
        context.registerService(RPCMethodMngService.class.getName(),
                RPCMethodMng.getInstance(), null);
        RPCMethodMng.getInstance().registerRPCMethod("Inform");
        RPCMethodMng.getInstance().registerRPCDecoder("InformResponse",
                new InformResponseDecoder());
    }
    /**
     * Stop.
     * @param context the context
     * @throws Exception the exception
     */
    public void stop(final BundleContext context) throws Exception {
        RPCMethodMng.getInstance().unregisterRPCMethod("Inform");
        RPCMethodMng.getInstance().unregisterRPCDecoder("InformResponse");
    }
}
