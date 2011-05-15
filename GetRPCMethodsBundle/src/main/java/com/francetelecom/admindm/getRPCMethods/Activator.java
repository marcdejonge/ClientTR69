package com.francetelecom.admindm.getRPCMethods;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethodMngService;
/**
 * The Class Activator.
 */
public final class Activator implements BundleActivator {
    /** The rpc method mng service ref. */
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
            rpcMng.registerRPCMethod("GetRPCMethods");
            RPCDecoder decoder = new GetRPCMethodsDecoder(rpcMng);
            rpcMng.registerRPCDecoder("GetRPCMethods", decoder);
            RPCEncoder encoder = new GetRPCMethodsResponseEncoder();
            rpcMng.registerRPCEncoder("GetRPCMethodsResponse", encoder);
            Log.debug("Enabling RPCMethod Inform");
        } else {
            Log.error("Unable tp start GetRpcMethods:"
                    + " RPCMethodMngService is missing");
        }
    }
    /**
     * Stop.
     * @param context the context
     * @throws Exception the exception
     */
    public void stop(final BundleContext context) throws Exception {
        Log.debug("Disabling RPCMethod Inform");
        if (rpcMng != null) {
            rpcMng.unregisterRPCMethod("GetRPCMethods");
            rpcMng.unregisterRPCEncoder("GetRPCMethodsResponse");
            rpcMng.unregisterRPCDecoder("GetRPCMethods");
        }
        context.ungetService(rpcMethodMngServiceRef);
    }
}
