package com.francetelecom.admindm.reboot;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethodMngService;
/**
 * The Class Activator.
 */
public final class Activator implements BundleActivator {
    /** The RPC method mng service ref. */
    private ServiceReference rpcMethodMngServiceRef = null;
    /** The rpc mng. */
    private RPCMethodMngService rpcMng;
    /**
     * Start.
     * @param context the context
     * @throws Exception the exception
     */
    public void start(final BundleContext context) throws Exception {
        rpcMethodMngServiceRef = context
                .getServiceReference(RPCMethodMngService.class.getName());
        if (rpcMethodMngServiceRef != null) {
            rpcMng = (RPCMethodMngService) context
                    .getService(rpcMethodMngServiceRef);
            rpcMng.registerRPCMethod("Reboot");
            rpcMng.registerRPCEncoder("RebootResponse",
                    new RebootResponseEncoder());
            rpcMng.registerRPCDecoder("Reboot", new RebootDecoder());
            Log.info("Start RPC Method Reboot");
        } else {
            Log.error("Unable tp start Reboot: "
                    + "RPCMethodMngService is missing");
        }
    }
    /**
     * Stop.
     * @param context the context
     * @throws Exception the exception
     */
    public void stop(final BundleContext context) throws Exception {
        if (rpcMng != null) {
            rpcMng.unregisterRPCMethod("Reboot");
            rpcMng.unregisterRPCEncoder("RebootResponse");
            rpcMng.unregisterRPCDecoder("Reboot");
        }
        context.ungetService(rpcMethodMngServiceRef);
    }
}
