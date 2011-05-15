/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.impl;
import java.util.Observable;
import java.util.Observer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.stunclient.ISTUNCLient;
/**
 * <p>
 * Activator of the STUN client.
 * </p>
 * 
 * @author mpcy8647
 * 
 */
public class Activator implements BundleActivator, Observer,
        ServiceTrackerCustomizer {
    /** STUNEnable parameter name */
    private static final String STUN_ENABLE = "ManagementServer.STUNEnable";
    /** stun client object registered as an service */
    private STUNClient stunClient = null;
    /** The RPC method mng service ref. */
    private ServiceReference dataServiceRef = null;
    /** STUN client service registration */
    private ServiceRegistration stunClientServiceReg = null;
    /** STUNEnabled parameter */
    private Parameter stunEnableParameter = null;
    /** bundle context */
    private BundleContext context = null;
    /** data model */
    private IParameterData data = null;
    /** The tracker of IParameterData. */
    private ServiceTracker tracker;
    /** is the STUN client in use */
    private boolean isInUse = false;
    /**
     * <p>
     * This method registers a {@link STUNClient} service in the registry.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>saves the context object</li>
     * <li>creates a tracker on {@link IParameterData} services.</li>
     * <li>opens it.</li>
     * </ul>
     * </p>
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        Log.debug("Activator start for the STUNClient bundle");
        // save context
        this.context = context;
        // create a ServiceTracker
        tracker =
                new ServiceTracker(context, IParameterData.class.getName(),
                        this);
        // open the tracker
        tracker.open();
    }
    /**
     * <p>
     * This method unregisters the {@link STUNClient} service registered in
     * the start method.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>close the tracker.</li>
     * </ul>
     * </p>
     * 
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        tracker.close();
        unregisterSTUNClient();
        // if (dataServiceRef != null) {
        // context.ungetService(dataServiceRef);
        // dataServiceRef = null;
        // }
    }
    /**
     * <p>
     * This method is invoked when a change is detected on the STUNEnable
     * parameter.
     * </p>
     * <p>
     * If the value of this parameter is equal TRUE, the STUN client service
     * is registered. Else it is unregistered.
     * </p>
     */
    public void update(Observable arg0, Object arg1) {
        // check the value
        if ((Boolean.TRUE.equals(stunEnableParameter.getValue()))
            && (!isInUse)) {
            registerSTUNClient();
        } else if ((Boolean.FALSE.equals(stunEnableParameter.getValue()))
                   && (isInUse)) {
            unregisterSTUNClient();
        }
    }
    /**
     * Gets the data provider.
     * 
     * @param context the context
     * @return the data provider
     * @throws BundleException the bundle exception
     */
    private IParameterData getDataProvider(final BundleContext context)
            throws BundleException {
        dataServiceRef =
                context.getServiceReference(IParameterData.class.getName());
        if (dataServiceRef == null) {
            throw new BundleException(IParameterData.class.getName());
        }
        return (IParameterData) context.getService(dataServiceRef);
    }
    /**
     * <p>
     * Register the STUN client as a service.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>set the isInUse state boolean.</li>
     * <li>create a new {@link STUNClient} object.</li>
     * <li>register it as a service.</li>
     * </ul>
     * </p>
     */
    private void registerSTUNClient() {
        // set in use
        isInUse = true;
        // create the stun client
        stunClient = new STUNClient(data);
        // register as a service
        stunClientServiceReg =
                context.registerService(ISTUNCLient.class.getName(),
                        stunClient, null);
    }
    /**
     * <p>
     * Unregister the STUN client service.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>unregister the service using its {@link ServiceRegistration}
     * object.</li>
     * <li>stop the STUN client.</li>
     * </ul>
     * </p>
     */
    private void unregisterSTUNClient() {
        // set in use to false
        isInUse = false;
        if (stunClientServiceReg != null) {
            stunClientServiceReg.unregister();
            stunClientServiceReg = null;
        }
        if (stunClient != null) {
            stunClient.stop();
            stunClient = null;
        }
    }
    /**
     * Adding service.
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>gets the IParameterData service object.</li>
     * <li>if the STUNEnable parameter is set to TRUE, register the STUN
     * client.</li>
     * </ul>
     * </p>
     * 
     * @param reference the reference
     * 
     * @return the object
     * 
     * @see org.osgi.util.tracker.ServiceTrackerCustomizer#addingService(org.osgi.framework.ServiceReference)
     */
    public Object addingService(ServiceReference reference) {
        Log.debug("STUNClient - add IParameterData service");
        // get the data model object
        try {
            data = getDataProvider(context);
        } catch (BundleException e1) {
        }
        if (data != null) {
            // get the STUNEnabled parameter
            stunEnableParameter =
                    data.getParameter(data.getRoot() + STUN_ENABLE);
            if (stunEnableParameter != null) {
                stunEnableParameter.addObserver(this);
                Log.info("STUNEnable = " + stunEnableParameter.getTextValue(null));
                if (Boolean.TRUE.equals(stunEnableParameter.getValue())) {
                    registerSTUNClient();
                }
            }
        }
        return data;
    }
    /**
     * Modified service.
     * 
     * @param reference the reference
     * @param service the service
     * 
     * @see org.osgi.util.tracker.ServiceTrackerCustomizer#modifiedService(org.osgi.framework.ServiceReference,
     *      java.lang.Object)
     */
    public void modifiedService(ServiceReference reference, Object service) {
        // TODO Auto-generated method stub
    }
    /**
     * Removed service. Unregisters the STUN client.
     * 
     * @param reference the reference
     * @param service the service
     * 
     * @see org.osgi.util.tracker.ServiceTrackerCustomizer#removedService(org.osgi.framework.ServiceReference,
     *      java.lang.Object)
     */
    public void removedService(ServiceReference reference, Object service) {
        // unregister the STUN client
        unregisterSTUNClient();
    }
}
