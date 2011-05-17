package com.francetelecom.admindm.getParameterNames;
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
            rpcMng.registerRPCMethod("GetParameterNames");
            rpcMng.registerRPCEncoder("GetParameterNamesResponse",
                    new GetParameterNamesResponseEncoder());
            rpcMng.registerRPCDecoder("GetParameterNames",
                    new GetParameterNamesDecoder());
            Log.info("Start RPC Method GetParameterNames");
        } else {
            Log.error("Unable to start GetParameterNames: "
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
            rpcMng.unregisterRPCMethod("GetParameterNames");
            rpcMng.unregisterRPCEncoder("GetParameterNamesResponse");
            rpcMng.unregisterRPCDecoder("GetParameterNames");
        }
        context.ungetService(rpcMethodMngServiceRef);
    }
}