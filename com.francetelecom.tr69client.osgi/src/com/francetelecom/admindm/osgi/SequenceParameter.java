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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.Setter;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author mpcy8647
 * 
 */
public class SequenceParameter implements Setter {

	/** The bundle context. */
	private final BundleContext bundleContext;

	/** The parameter data. */
	private final IParameterData parameterData;

	public SequenceParameter(final BundleContext pBundleContext,
			final IParameterData pParameterData) {
		bundleContext = pBundleContext;
		parameterData = pParameterData;

		try {
			Parameter startSequenceParameter = parameterData
					.createOrRetrieveParameter(parameterData.getRoot()
							+ OSGIBundleListener.osgiPath + "StartSequence");
			startSequenceParameter.setType(ParameterType.STRING);
			startSequenceParameter.setValue("");
			startSequenceParameter.setWritable(true);
			startSequenceParameter.setStorageMode(StorageMode.COMPUTED);
			startSequenceParameter.setSetter(this);

			Parameter stopSequenceParameter = parameterData
					.createOrRetrieveParameter(parameterData.getRoot()
							+ OSGIBundleListener.osgiPath + "StopSequence");
			stopSequenceParameter.setType(ParameterType.STRING);
			stopSequenceParameter.setValue("");
			stopSequenceParameter.setWritable(true);
			stopSequenceParameter.setStorageMode(StorageMode.COMPUTED);
			stopSequenceParameter.setSetter(this);

			Parameter uninstallSequenceParameter = parameterData
					.createOrRetrieveParameter(parameterData.getRoot()
							+ OSGIBundleListener.osgiPath + "UninstallSequence");
			uninstallSequenceParameter.setType(ParameterType.STRING);
			uninstallSequenceParameter.setValue("");
			uninstallSequenceParameter.setWritable(true);
			uninstallSequenceParameter.setStorageMode(StorageMode.COMPUTED);
			uninstallSequenceParameter.setSetter(this);

		} catch (Fault e) {
			Log.error(
					"Unable to create and initialize the *Sequence parameter",
					e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.francetelecom.admindm.api.Setter#set(com.francetelecom.admindm.model.Parameter,
	 *      java.lang.Object)
	 */
	public void set(Parameter param, Object obj) throws Fault {
		if (param.getName().endsWith("StartSequence")) {
			processStart(obj);
		} else if (param.getName().endsWith("StopSequence")) {
			processStop(obj);
		} else if (param.getName().endsWith("UninstallSequence")) {
			processUninstall(obj);
		}
	}

	/**
	 * <p>
	 * Process uninstall.
	 * </p>
	 * <p>
	 * The following actions are performed:
	 * <ul>
	 * <li>get the bundle locations by splitting the obj string using the
	 * character ";".</li>
	 * <li>for each bundle location, gets the bundle object and uninstalls it.</li>
	 * </ul>
	 * </p>
	 * 
	 * 
	 * @param obj
	 *            the obj (CSV list of bundle locations)
	 */
	private void processUninstall(Object obj) {
		// get a splitted array of bundle location
		String value = (String) obj;
		String[] locations = value.split(";");
		if (locations != null) {
			// check locations
			for (int i = 0; i < locations.length; i++) {
				// get the bundle object
				Bundle bundle = getBundle(locations[i]);
				if (bundle != null) {
					try {
						bundle.uninstall();
					} catch (BundleException e) {
						// nothing to do
						Log.info("unable to uninstall the bundle "
								+ bundle.getBundleId() + " - "
								+ bundle.getLocation());
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * Process stop.
	 * </p>
	 * <p>
	 * The following actions are performed:
	 * <ul>
	 * <li>get the bundle locations by splitting the obj string using the
	 * character ";".</li>
	 * <li>for each bundle location, gets the bundle object and stops it.</li>
	 * </ul>
	 * </p>
	 * 
	 * 
	 * @param obj
	 *            the obj (CSV list of bundle locations)
	 */
	private void processStop(Object obj) {
		// get a splitted array of bundle location
		String value = (String) obj;
		String[] locations = value.split(";");
		if (locations != null) {
			// check locations
			for (int i = 0; i < locations.length; i++) {
				// get the bundle object
				Bundle bundle = getBundle(locations[i]);
				if (bundle != null) {
					try {
						bundle.stop();
					} catch (BundleException e) {
						// nothing to do
						Log.info("unable to stop the bundle "
								+ bundle.getBundleId() + " - "
								+ bundle.getLocation());
					}
				}
			}
		}

	}

	private void processStart(Object obj) throws Fault {
		// get a splitted array of bundle location
		String value = (String) obj;
		String[] locations = value.split(";");
		List bundleInfoList = new ArrayList();
		if (locations != null) {
			// check locations
			for (int i = 0; i < locations.length; i++) {
				// get the bundle object
				Bundle bundle = getBundle(locations[i]);
				if (bundle != null) {
					BundleInfo bundleInfo = new BundleInfo(bundle);
					bundleInfoList.add(bundleInfo);
				} else {
					throw new Fault(9007, "The location " + locations[i]
							+ " doesn't match with a bundle !!!");
				}
			}

			// start the bundles
			List changedStateBundleInfoList = new ArrayList();
			for (Iterator it = bundleInfoList.iterator(); it.hasNext();) {
				BundleInfo bundleInfo = (BundleInfo) it.next();
				try {
					// start the bundle
					bundleInfo.startBundle();

					// add the bundleInfo to the changedStateList
					changedStateBundleInfoList.add(bundleInfo);

					// remove from the bundleInfoList the bundleInfo
					it.remove();
				} catch (BundleException e) {
					String error = "Unable to start the bundle "
							+ bundleInfo.getBundleLocation();
					restoreState(changedStateBundleInfoList);
					throw new Fault(9007, error);
				}
			}
		}
	}

	/**
	 * Restore state.
	 * 
	 * @param changedStateBundleInfoList
	 *            the changed state bundle info list
	 */
	private void restoreState(final List changedStateBundleInfoList) {
		for (Iterator it = changedStateBundleInfoList.iterator(); it.hasNext();) {
			BundleInfo bundleInfo = (BundleInfo) it.next();
			bundleInfo.restoreBundleState();
		}
	}

	/**
	 * Gets the bundle.
	 * 
	 * @param location
	 *            the location
	 * 
	 * @return the bundle or null if the location doesn't match with a bundle.
	 */
	private Bundle getBundle(final String location) {
		Bundle[] bundles = bundleContext.getBundles();
		String extentedLocation = "file:" + location;
		for (int i = 0; i < bundles.length; i++) {
			Bundle bundle = bundles[i];
			if (bundle.getLocation().equals(extentedLocation)) {
				return bundle;
			}
		}
		return null;
	}

}
