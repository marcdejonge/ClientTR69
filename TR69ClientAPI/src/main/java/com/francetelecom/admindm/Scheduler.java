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

import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.FileUtil;
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
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.persist.IPersist;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

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
        logServiceRef = context.getServiceReference(LogService.class.getName());
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
        } else if (service instanceof LogService) {
            onLogChange((LogService) service, event);
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
            File conf = FileUtil.getFileFromShortName(FileUtil.CONFIG);
            if (conf != null) {
                InputStream in = null;
                try {
                    Properties properties = new Properties();
                    // TODO ne pas passer par un fichier de properties
                    in = new FileInputStream(conf);
                    properties.load(in);
                    data.setRoot(properties.getProperty("root"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.info("Root is " + data.getRoot());            
            // put the data model structure
            model.setData(data);
            putDefaultParameter();
            Log.debug("=======================");
            Log.debug("Model is ");
            Log.debug("=======================");
            Log.debug(data.toString());
            // put data into data model
            Iterator it = data.getParameterIterator();
            Parameter p;
            Object value;
            while (it.hasNext()) {
                p = (Parameter) it.next();
                persist.restoreParameterNotification(p.getName());
                persist.restoreParameterSubscriber(p.getName());
                value = persist.restoreParameterValue(p.getName(), p.getType());
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
                persist.persist(p.getName(), p.getAccessList(), p.getNotification(), p.getValue(), p.getType());
            }
            context.registerService(IParameterData.class.getName(), data,
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
        return (persist != null && com != null && model != null);
    }

    /**
     * Put default parameter.
     * @see com.francetelecom.admindm.api.ICSV#putDefaultParameter()
     */
    public void putDefaultParameter() {
        File dataSave = FileUtil.getFileFromShortName(FileUtil.SAVE);
        if (dataSave == null) {
            StringBuffer error = new StringBuffer(FileUtil.SAVE);
            error.append(" is not defined : no persistance will be present.");
            Log.error(error.toString());
        }
        if (dataSave == null || !dataSave.exists()) {
            data.addEvent(new EventStruct(EventCode.BOOTSTRAP, ""));
        }
        data.addEvent(new EventStruct(EventCode.BOOT, ""));
        PropertiesReader reader;
        reader = new PropertiesReader(data);
        reader.read(FileUtil.getFileFromShortName(FileUtil.USINE));
    }
}
