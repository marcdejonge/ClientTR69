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
import java.util.ArrayList;
import java.util.List;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.download.api.IApplyAction;
import com.francetelecom.admindm.download.api.IDownloadProtocol;
import com.francetelecom.admindm.download.api.IEngine;
import com.francetelecom.admindm.model.IParameterData;
/**
 * The listener interface for receiving applyService events. The class that is
 * interested in processing a applyService event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addApplyServiceListener<code> method. When
 * the applyService event occurs, that object's appropriate
 * method is invoked.
 * @see ApplyServiceEvent
 */
public final class ApplyServiceListener implements ServiceListener {
    /** The engine. */
    final IEngine engine;
    /** The context. */
    final BundleContext context;
    /** The ls service refs. */
    private List lsServiceRefs = new ArrayList();
    /**
     * Instantiates a new apply service listener. Add all IApply Action.
     * @param pEngine the engine
     * @param pContext the context
     */
    public ApplyServiceListener(final IEngine pEngine,
            final BundleContext pContext) {
        engine = pEngine;
        context = pContext;
        ServiceReference[] services;
        IApplyAction action;
        IDownloadProtocol protocol;
        String applyActionName = IApplyAction.class.getName();
        String downloadProtocolName = IDownloadProtocol.class.getName();
        try {
            services = context.getServiceReferences(applyActionName, null);
            if (services != null) {
                for (int i = 0; i < services.length; i++) {
                    action = (IApplyAction) context.getService(services[i]);
                    if (action != null) {
                        engine.addApplyAction(action);
                        lsServiceRefs.add(services[i]);
                    }
                }
            }
            services = context
                    .getServiceReferences(downloadProtocolName, null);
            if (services != null) {
                for (int i = 0; i < services.length; i++) {
                    protocol = (IDownloadProtocol) context
                            .getService(services[i]);
                    if (protocol != null) {
                        engine.addDownloadProtocol(protocol);
                        lsServiceRefs.add(services[i]);
                    }
                }
            }
        } catch (InvalidSyntaxException e) {
            Log.error("Internal Error", e);
        } catch (ClassCastException e) {
            Log.error("Internal Error", e);
        }
    }
    /**
     * One service changed.
     * @param event the event
     */
    public void serviceChanged(final ServiceEvent event) {
        ServiceReference ref = event.getServiceReference();
        Object service =context.getService(ref);
        if ( service instanceof IParameterData) {
            parameterDataChange(event, (IParameterData)service);
        }
        if (context.getService(ref) instanceof IApplyAction) {
            applyActionServiceChanged(event, ref);
        }
        if (context.getService(ref) instanceof IDownloadProtocol) {
            downloadProtocolServiceChanged(event, ref);
        }
    }
    private void parameterDataChange(ServiceEvent event, IParameterData data) {
        switch (event.getType()) {
        case ServiceEvent.REGISTERED:            
            engine.setData(data);
            break;
        case ServiceEvent.UNREGISTERING:
            engine.setData(null);
            break;
        default:
            // nothing to do
            break;
        }        
    }
    /**
     * Apply action service changed.
     * @param event the event
     * @param ref the ref
     */
    private void applyActionServiceChanged(final ServiceEvent event,
            final ServiceReference ref) {
        IApplyAction action = (IApplyAction) context.getService(ref);
        switch (event.getType()) {
        case ServiceEvent.REGISTERED:
            lsServiceRefs.add(ref);
            engine.addApplyAction(action);
            break;
        case ServiceEvent.UNREGISTERING:
            engine.removeApplyAction(action);
            lsServiceRefs.remove(ref);
            break;
        default:
            // nothing to do
            break;
        }
    }
    /**
     * Download protocol service changed.
     * @param event the event
     * @param ref the ref
     */
    private void downloadProtocolServiceChanged(final ServiceEvent event,
            final ServiceReference ref) {
        IDownloadProtocol protocol = (IDownloadProtocol) context
                .getService(ref);
        switch (event.getType()) {
        case ServiceEvent.REGISTERED:
            lsServiceRefs.add(ref);
            engine.addDownloadProtocol(protocol);
            break;
        case ServiceEvent.UNREGISTERING:
            engine.removeDownloadProtocol(protocol);
            lsServiceRefs.remove(ref);
            break;
        default:
            // nothing to do
            break;
        }
    }
    /**
     * Stop the listener properly.
     */
    public void stop() {
        engine.removeAllApplyActions();
        engine.removeAllDownloadProtocols();
        while (!lsServiceRefs.isEmpty()) {
            context.ungetService((ServiceReference) lsServiceRefs.remove(0));
        }
    }
}
