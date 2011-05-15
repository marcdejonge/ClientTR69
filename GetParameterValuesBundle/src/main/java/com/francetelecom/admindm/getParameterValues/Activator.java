package com.francetelecom.admindm.getParameterValues;
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
            rpcMng.registerRPCMethod("GetParameterValues");
            rpcMng.registerRPCEncoder("GetParameterValuesResponse",
                    new GetParameterValuesResponseEncoder());
            rpcMng.registerRPCDecoder("GetParameterValues",
                    new GetParameterValuesDecoder());
            Log.info("Start RPC Method GetParameterNames");
        } else {
            Log.error("Unable tp start GetParameterValues: "
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
            rpcMng.unregisterRPCMethod("GetParameterValues");
            rpcMng.unregisterRPCEncoder("GetParameterValuesResponse");
            rpcMng.unregisterRPCDecoder("GetParameterValues");
        }
        context.ungetService(rpcMethodMngServiceRef);
    }
}
