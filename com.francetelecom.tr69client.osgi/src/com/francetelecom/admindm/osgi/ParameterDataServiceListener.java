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
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * The listener interface for receiving parameterDataService events. The class
 * that is interested in processing a parameterDataService event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addParameterDataServiceListener<code> method. When
 * the parameterDataService event occurs, that object's appropriate
 * method is invoked.
 * @see ParameterDataServiceEvent
 */
public class ParameterDataServiceListener implements ServiceListener {
	/** The Constant filter. */
	public static final String filter = "(objectclass="
			+ IParameterData.class.getName() + ")";

	/** The context. */
	private BundleContext context;

	/** The data. */
	private IParameterData data;

	/** The listener. */
	private OSGIBundleListener listener;

	/** The updater. */
	private BundleNumberOfEntriesUpdater updater;

	/**
	 * Instantiates a new parameter data service listener.
	 * 
	 * @param context
	 *            the context
	 */
	public ParameterDataServiceListener(final BundleContext context) {
		super();
		this.context = context;
	}

	/**
	 * 
	 */
	public void serviceChanged(ServiceEvent event) {
		ServiceReference sr = event.getServiceReference();
		Object service = context.getService(sr);
		if (service instanceof IParameterData) {
			data = (IParameterData) service;
			updater = new BundleNumberOfEntriesUpdater(context, data);
			Bundle[] bundles;
			switch (event.getType()) {
			case ServiceEvent.REGISTERED:
				try {
					createOSGIRootParameter(data);
					listener = new OSGIBundleListener(data, updater);
					new SequenceParameter(context, data);
					context.addBundleListener(listener);
					bundles = context.getBundles();
					for (int i = 0; i < bundles.length; i++) {
						listener.createOrUpdateDataModel(bundles[i]);
					}
				} catch (Fault e) {
					Log.error("unable to create the OSGI parameter");
					listener = null;
				}

				break;
			case ServiceEvent.UNREGISTERING:
				if (listener != null) {
					context.removeBundleListener(listener);
					listener = null;
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Creates the osgi root parameter.
	 * 
	 * @param parameterData
	 *            the parameter data
	 */
	private static void createOSGIRootParameter(IParameterData parameterData)
			throws Fault {
		Parameter osgi = parameterData.createOrRetrieveParameter(parameterData
				.getRoot()
				+ OSGIBundleListener.osgiPath);
		osgi.setType(ParameterType.ANY);
		osgi.setWritable(false);
		osgi.setValue("");
		osgi.setStorageMode(StorageMode.COMPUTED);
		
		Parameter bundle = parameterData.createOrRetrieveParameter(parameterData
				.getRoot()
				+ OSGIBundleListener.bundlePath);
		bundle.setType(ParameterType.ANY);
		bundle.setWritable(false);
		bundle.setValue("");
		bundle.setStorageMode(StorageMode.COMPUTED);
		
	}
}
