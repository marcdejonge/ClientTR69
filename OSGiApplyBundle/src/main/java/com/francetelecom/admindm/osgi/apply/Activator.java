/*
 * 
 */
package com.francetelecom.admindm.osgi.apply;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.francetelecom.admindm.download.api.IApplyAction;
/**
 * The Class Activator.
 */
public final class Activator implements BundleActivator {
	
	
    /**
     * Start.
     * @param context the context
     * @throws Exception the exception
     */
    public void start(final BundleContext context) throws Exception {
        IApplyAction bundleApply = new BundleApply(context);
        String serviceName = IApplyAction.class.getName();
        context.registerService(serviceName, bundleApply, null);
    }
    
    /**
     * Stop.
     * @param context the context
     */
    public void stop(final BundleContext context) {
    }

}
