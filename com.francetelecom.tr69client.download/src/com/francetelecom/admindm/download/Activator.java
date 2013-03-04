/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DownloadBundle
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
package com.francetelecom.admindm.download;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import com.francetelecom.admindm.api.EventBehavior;
import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.download.api.Download;
import com.francetelecom.admindm.download.api.DownloadResponse;
import com.francetelecom.admindm.download.api.IEngine;
import com.francetelecom.admindm.download.api.IEngineService;
import com.francetelecom.admindm.download.api.TransferComplete;
import com.francetelecom.admindm.download.api.Upload;
import com.francetelecom.admindm.download.api.UploadResponse;
/**
 * The Class Activator.
 */
public final class Activator implements BundleActivator {
    /** The RPC method mng service ref. */
    private ServiceReference rpcMethodMngServiceRef = null;
    /** The RPC method mng service ref. */
    private ServiceReference dataServiceRef = null;
    /** The Constant EVT_BEHAVIOR_M_DOWNLOAD. */
    private static final EventBehavior EVT_BEHAVIOR_M_DOWNLOAD =
        new EventBehavior(
                false, EventCode.ALWAYS_RETRY, TransferCompleteResponse.NAME);
    /** The Constant EVT_BEHAVIOR_TRANSFER_COMPLETE. */
    private static final EventBehavior EVT_BEHAVIOR_TRANSFER_COMPLETE =
        new EventBehavior(
            true, EventCode.ALWAYS_RETRY, TransferCompleteResponse.NAME);
    /** The rpc mng. */
    private RPCMethodMngService rpcMng;
    /** The listener. */
    private ApplyServiceListener listener = null;
    /** The engine. */
    private IEngine engine;
    /**
     * Start.
     * @param context the context
     * @throws Exception the exception
     */
    public void start(final BundleContext context) throws Exception {        
        rpcMng = getRPCMng(context);
        // Register new type of event behavior to help the engine
        rpcMng.registerEventBehavior(EventCode.M_DOWNLOAD,
                EVT_BEHAVIOR_M_DOWNLOAD);
        rpcMng.registerEventBehavior(EventCode.TRANSFER_COMPLETE,
                EVT_BEHAVIOR_TRANSFER_COMPLETE);
        // Register new type of RPCMethod
        rpcMng.registerRPCMethod(Download.NAME);
        rpcMng.registerRPCMethod(Upload.NAME);
        rpcMng.registerRPCEncoder(UploadResponse.NAME,                
                new UploadResponseEncoder());
        rpcMng.registerRPCEncoder(DownloadResponse.NAME,
                new DownloadResponseEncoder());
        engine = new Engine();
        rpcMng.registerRPCDecoder(Upload.NAME, new UploadDecoder(engine));
        rpcMng.registerRPCDecoder(Download.NAME, new DownloadDecoder(engine));
        rpcMng.registerRPCEncoder(TransferComplete.NAME,
                new TransferCompleteEncoder());
        rpcMng.registerRPCDecoder(TransferCompleteResponse.NAME,
                new TransferCompleteResponseDecoder(engine));
        engine.setDatafile(context.getDataFile("download.data"));
        engine.restoreData();
        listener = new ApplyServiceListener(engine, context);
        context.registerService(IEngineService.class.getName(), engine, null);
        context.addServiceListener(listener);
        Log.info("Start RPC Method Download");
    }
    /**
     * Gets the rpc mng.
     * @param context the context
     * @return the RPC mng
     * @throws BundleException the bundle exception
     */
    private RPCMethodMngService getRPCMng(final BundleContext context)
            throws BundleException {
        rpcMethodMngServiceRef = context
                .getServiceReference(RPCMethodMngService.class.getName());
        if (rpcMethodMngServiceRef == null) {
            throw new BundleException(RPCMethodMngService.class.getName());
        }
        return (RPCMethodMngService) context
                .getService(rpcMethodMngServiceRef);
    }
    /**
     * Stop.
     * @param context the context
     * @throws Exception the exception
     */
    public void stop(final BundleContext context) throws Exception {
        listener.stop();
        context.removeServiceListener(listener);
        if (rpcMng != null) {
            rpcMng.unregisterRPCMethod(Download.NAME);
            rpcMng.unregisterRPCEncoder(DownloadResponse.NAME);
            rpcMng.unregisterRPCDecoder(Download.NAME);
            rpcMng.unregisterRPCEncoder(TransferComplete.NAME);
            rpcMng.unregisterRPCDecoder(TransferCompleteResponse.NAME);
            rpcMng.unregisterEventBehavior(EventCode.M_DOWNLOAD);
            rpcMng.unregisterEventBehavior(EventCode.TRANSFER_COMPLETE);
        }
        context.ungetService(rpcMethodMngServiceRef);
        context.ungetService(dataServiceRef);
    }
}
