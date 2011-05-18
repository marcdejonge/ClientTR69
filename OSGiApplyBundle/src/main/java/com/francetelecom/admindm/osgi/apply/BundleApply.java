/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : OSGiApplyBundle
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
package com.francetelecom.admindm.osgi.apply;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.download.api.IApplyAction;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class BundleApply.
 */
public final class BundleApply extends AbstractApply {
    /**
     * The Constructor.
     * @param pContext the context
     */
    public BundleApply(final BundleContext pContext) {
        super();
        this.context = pContext;
    }
    /** The context. */
    private BundleContext context;
    /**
     * Apply the bundle consist in install the bundle.
     * @param name the name
     * @return true if need to reboot
     * @throws Fault the fault
     */
    public boolean apply(final String name) throws Fault {
        String location = "file:" + name;
        boolean needReboot = false;
        try {
            boolean isUpdate = false;
            if (!location.equals(context.getBundle().getLocation())) {
                Bundle[] bundles = context.getBundles();
                if (bundles != null) {
                    for (int i = 0; i < bundles.length; i++) {
                        if (location.equals(bundles[i].getLocation())) {
                            bundles[i].stop();
                            bundles[i].update();
                            isUpdate = true;
                        }
                    }
                }
                if (!isUpdate) {
                    context.installBundle(location);
                }
            } else {
                String pathRestart = getRestartBundleName(context);
                Bundle restart = context.installBundle(pathRestart);
                restart.getHeaders().put("location", location);
                restart.stop();
                restart.start();
            }
            return needReboot;
        } catch (BundleException e) {
            Log.error("Bundle install ",e);
            throw rejectBadFile(name, e);
        }
    }
    /**
     * Gets the restart bundle name to be compliant with the download bundle
     * version.
     * @param pContext the context
     * @return the restart bundle path
     */
    protected String getRestartBundleName(final BundleContext pContext) {
        StringBuffer result = new StringBuffer("file:RestartBundle");
        String location = pContext.getBundle().getLocation();
        int index = location.indexOf('-');
        result.append(location.substring(index));
        return result.toString();
    }
    /**
     * Checks if is applicable.
     * @param name the name
     * @return true, if checks if is applicable
     */
    public boolean isApplicable(final String name) {
        return ("1 Firmware Upgrade Image".equals(name));
    }
}
