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
import org.osgi.framework.BundleException;

import com.francetelecom.admindm.api.Log;

/**
 * @author mpcy8647
 * 
 */
public class BundleInfo {

	/** The bundle. */
	private final Bundle bundle;

	/** The saved state. */
	private final int savedState;

	/**
	 * Instantiates a new bundle info.
	 * 
	 * @param pBundle
	 *            the bundle
	 */
	public BundleInfo(Bundle pBundle) {
		bundle = pBundle;
		savedState = bundle.getState();
	}

	/**
	 * Start bundle.
	 * 
	 * @throws BundleException the bundle exception
	 */
	public void startBundle() throws BundleException {
		bundle.start();
	}

	/**
	 * Restore bundle state.
	 */
	public void restoreBundleState() {
		if (savedState != Bundle.ACTIVE) {
			try {
				bundle.stop();
			} catch (BundleException e) {
				Log.error("unable to put the bundle in the saved state", e);
			}
		}
	}
	
	/**
	 * Gets the bundle location.
	 * 
	 * @return the bundle location
	 */
	public String getBundleLocation() {
		return bundle.getLocation();
	}

}
