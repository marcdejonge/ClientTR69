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
import java.util.Iterator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import com.francetelecom.admindm.api.ICSV;
import com.francetelecom.admindm.api.ICom;
import com.francetelecom.admindm.api.IModel;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.inform.ScheduleInform;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.persist.IPersist;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class Scheduler.
 */
public final class Scheduler implements ServiceListener {
    /** The context. */
    private final BundleContext context;
    /** The Constant IPERSIST. */
    private static final String IPERSIST = IPersist.class.getName();
    /** The Constant ICOM. */
    private static final String ICOM = ICom.class.getName();
    /** The persist. */
    private IPersist persist = null;
    /** The com. */
    private ICom com = null;
    /** The model. */
    private IModel model = null;
    /** The csv. */
    private ICSV csv = null;
    /** The data. */
    private final IParameterData data;
    /**
     * Instantiates a new scheduler.
     * @param pContext the context
     * @param pData the data
     */
    public Scheduler(final BundleContext pContext, final IParameterData pData) {
        this.context = pContext;
        this.data = pData;
        ServiceReference persistRef;
        persistRef = context.getServiceReference(IPERSIST);
        if (persistRef != null) {
            persist = (IPersist) context.getService(persistRef);
        }
        ServiceReference icomRef;
        icomRef = context.getServiceReference(ICOM);
        if (icomRef != null) {
            com = (ICom) context.getService(icomRef);
        }
        ServiceReference logServiceRef;
        logServiceRef = context
                .getServiceReference(LogService.class.getName());
        if (logServiceRef != null) {
            Log.setLogService((LogService) context.getService(logServiceRef));
        }
    }
    /**
     * Service changed.
     * @param event the event
     */
    public void serviceChanged(final ServiceEvent event) {
        ServiceReference ref = event.getServiceReference();
        Object service = context.getService(ref);
        if (service instanceof IPersist) {
            onIPersistChange((IPersist) service, event);
            startTR69();
        } else if (service instanceof ICom) {
            onIComChange((ICom) service, event);
            startTR69();
        } else if (service instanceof IModel) {
            onIModelChange((IModel) service, event);
            startTR69();
        } else if (service instanceof ICSV) {
            onICSVChange((ICSV) service, event);
            startTR69();
        } else if (service instanceof LogService) {
            onLogChange((LogService) service, event);
        }
    }
    /**
     * On icsv change.
     * @param service the service
     * @param event the event
     */
    private void onICSVChange(final ICSV service, final ServiceEvent event) {
        switch (event.getType()) {
        case ServiceEvent.REGISTERED:
            csv = service;
            break;
        case ServiceEvent.UNREGISTERING:
            csv = null;
            break;
        default:
            break;
        }
    }
    /**
     * On i model change.
     * @param service the service
     * @param event the event
     */
    private void onIModelChange(final IModel service,
            final ServiceEvent event) {
        switch (event.getType()) {
        case ServiceEvent.REGISTERED:
            model = service;
            break;
        case ServiceEvent.UNREGISTERING:
            model = null;
            break;
        default:
            break;
        }
    }
    /**
     * On log change.
     * @param service the service
     * @param event the event
     */
    private void onLogChange(final LogService service,
            final ServiceEvent event) {
        switch (event.getType()) {
        case ServiceEvent.REGISTERED:
            Log.setLogService(service);
            break;
        case ServiceEvent.UNREGISTERING:
            Log.setLogService(null);
            break;
        default:
            break;
        }
    }
    /**
     * Start t r69.
     */
    private void startTR69() {
        if (checkState()) {
            Log.info("TR69Client is starting");
            // allow discovers root of datamodel
            csv.setData(data);
            try {
                csv.discoverModelRoot();
            } catch (Fault e) {
                Log.error(e.getFaultstring());
                return;
            }
            Log.info("Root is " + data.getRoot());
            // put the data model structure
            model.setData(data);
            Log.debug("=======================");
            Log.debug("Model is ");
            Log.debug("=======================");
            Log.debug(data.toString());
            // complete the data model structure with csv content
            csv.readStructComplement();
            Log.debug("=======================");
            Log.debug("Model is after add csv");
            Log.debug("=======================");
            Log.debug(data.toString());
            csv.putDefaultParameter();
            Log.info("===========================");
            Log.debug("Model is after put default");
            Log.info("===========================");
            Log.debug(data.toString());
            // put data into data model
            Iterator it = data.getParameterIterator();
            Parameter p;
            Object value;
            while (it.hasNext()) {
                p = (Parameter) it.next();
                persist.restoreParameterNotification(p.getName());
                persist.restoreParameterSubscriber(p.getName());
                value = persist
                        .restoreParameterValue(p.getName(), p.getType());
                if (value != null) {
                    p.setValueWithout(value);
                }
            }
            Log.info("===========================");
            Log.info("Model is after restore data");
            Log.info("===========================");
            Log.info(data.toString());
            com.setParameterData(data);
            com.setRPCMng(RPCMethodMng.getInstance());
            it = data.getParameterIterator();
            // save data model
            while (it.hasNext()) {
                p = (Parameter) it.next();
                persist.persist(p.getName(), p.getAccessList(), p
                        .getNotification(), p.getValue(), p.getType());
            }
            context
                    .registerService(IParameterData.class.getName(), data,
                            null);
            com.setRunning(true);
            new Thread(com, "Com Server").start();
            ScheduleInform si = new ScheduleInform(data);
            si.initParameterSource();
            si.createTask();
        } else {
            if (com != null) {
                // stop the com to be clean when is restart
                com.setRunning(false);
            }
        }
    }
    /**
     * On i com change.
     * @param service the service
     * @param event the event
     */
    private void onIComChange(final ICom service, final ServiceEvent event) {
        switch (event.getType()) {
        case ServiceEvent.REGISTERED:
            com = service;
            break;
        case ServiceEvent.UNREGISTERING:
            com = null;
            break;
        default:
            break;
        }
        data.setCom(com);
    }
    /**
     * On i persist change.
     * @param service the service
     * @param event the event
     */
    private void onIPersistChange(final IPersist service,
            final ServiceEvent event) {
        switch (event.getType()) {
        case ServiceEvent.REGISTERED:
            persist = service;
            break;
        case ServiceEvent.UNREGISTERING:
            persist = null;
            break;
        default:
            break;
        }
    }
    /**
     * Check state.
     * @return true, if successful
     */
    boolean checkState() {
        return (persist != null && com != null && csv != null && model != null);
    }
}
