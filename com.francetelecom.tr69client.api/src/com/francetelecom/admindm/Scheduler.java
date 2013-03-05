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

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.FileUtil;
import java.util.Iterator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import com.francetelecom.admindm.api.ICom;
import com.francetelecom.admindm.api.IModel;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.inform.ScheduleInform;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.persist.IPersist;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Class Scheduler.
 */
@Component
public final class Scheduler {

	private ParameterData data = new ParameterData();
    private BundleContext context;
    
    public void activate(final BundleContext context) {
	    this.context = context;
	    data.setEventFile(context.getDataFile("Events.data"));
	    
	    startTR69();
	}

	private IPersist persist = null;
    
    @Reference
    public void setPersist(IPersist persist) {
		this.persist = persist;
	}
    
    public void unsetPersist(IPersist persist) {
    	this.persist = null;
    }
    
    private ICom com = null;
    
    @Reference
    public void setCom(ICom com) {
		this.com = com;
		com.setRunning(true);
		data.setCom(com);
	}

    public void unsetCom(ICom com) {
    	this.com = null;
    	com.setRunning(false);
    	data.setCom(null);
    }
    
    private IModel model = null;
    
    @Reference
    public void setModel(IModel model) {
		this.model = model;
	}
    
    @Reference
    public void setLogService(LogService logService) {
    	Log.setLogService(logService);
    }

    private RPCMethodMngService rpcMethodMng;
    
    @Reference
    public void setRpcMethodMng(RPCMethodMngService rpcMethodMng) {
		this.rpcMethodMng = rpcMethodMng;
	}
    
    /**
     * Start t r69.
     */
    private void startTR69() {
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
        Iterator<Parameter> it = data.getParameterIterator();
        Object value;
        while (it.hasNext()) {
            Parameter p = it.next();
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
        com.setRPCMng(rpcMethodMng);
        it = data.getParameterIterator();
        // save data model
        while (it.hasNext()) {
            Parameter p = it.next();
            persist.persist(p.getName(), p.getAccessList(), p.getNotification(), p.getValue(), p.getType());
        }
        context.registerService(IParameterData.class.getName(), data,
                null);
        com.setRunning(true);
        new Thread(com, "Com Server").start();
        ScheduleInform si = new ScheduleInform(data);
        si.initParameterSource();
        si.createTask();
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
