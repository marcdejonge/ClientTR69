/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DataModelBundle
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
package com.francetelecom.model;
import java.util.Hashtable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.upnp.UPnPDevice;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.francetelecom.admindm.api.IModel;
/**
 * The Class Activator.
 */
public class Activator implements BundleActivator, ServiceTrackerCustomizer {
    /** The model. */
    private final Model model = new Model();
    
    /** The bundle context. */
    private BundleContext bundleContext = null;
    
    /** The tracker. */
    private ServiceTracker upnpTracker = null;
    /**
     * Start.
     * @param context the context
     * @throws Exception the exception
     */
    public void start(BundleContext context) throws Exception {
    	
    	// save context
    	this.bundleContext = context;
    	
        Hashtable table = new Hashtable();
        table.put("ROOT_MODEL", Boolean.TRUE);
        context.registerService(IModel.class.getName(), model, table);
        
        // create and open tracker
        upnpTracker = new ServiceTracker(context,UPnPDevice.class.getName(), this);
        upnpTracker.open();
    }
    /**
     * Stop.
     * @param context the context
     * @throws Exception the exception
     */
    public void stop(BundleContext context) throws Exception {
    	if (upnpTracker != null) {
    		upnpTracker.close();
    	}
    }
    
	/**
	 * Adding service.
	 * 
	 * @param reference the reference
	 * 
	 * @return the object
	 * 
	 * @see org.osgi.util.tracker.ServiceTrackerCustomizer#addingService(org.osgi.framework.ServiceReference)
	 */
	public Object addingService(ServiceReference reference) {
		// get service object
		UPnPDevice upnpDevice = (UPnPDevice) bundleContext.getService(reference);
		
		// add it to the model object
		model.addUPnPDevice(upnpDevice);
		
		return upnpDevice;
	}
	
	/**
	 * Modified service.
	 * 
	 * @param reference the reference
	 * @param service the service
	 * 
	 * @see org.osgi.util.tracker.ServiceTrackerCustomizer#modifiedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	public void modifiedService(ServiceReference reference, Object service) {
	}
	
	/**
	 * Removed service.
	 * 
	 * @param reference the reference
	 * @param service the service
	 * 
	 * @see org.osgi.util.tracker.ServiceTrackerCustomizer#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	public void removedService(ServiceReference reference, Object service) {
		UPnPDevice upnpDevice = (UPnPDevice) service;
		
		model.removeUPnPDevice(upnpDevice);
		
	}
}
