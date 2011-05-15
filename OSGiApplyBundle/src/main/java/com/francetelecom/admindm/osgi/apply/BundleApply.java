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
