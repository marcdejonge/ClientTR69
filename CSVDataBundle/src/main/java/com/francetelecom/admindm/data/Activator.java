package com.francetelecom.admindm.data;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import com.francetelecom.admindm.api.ICSV;
/**
 * The Class Activator.
 */
public final class Activator implements BundleActivator {
    /**
     * Start.
     * @param context the context
     * @throws BundleException the bundle exception
     */
    public void start(final BundleContext context) throws BundleException {
        context.registerService(ICSV.class.getName(), new Start(), null);
    }
    /**
     * Stop.
     * @param context the context
     */
    public void stop(final BundleContext context) {
    }
}
