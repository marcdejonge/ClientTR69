package com.francetelecom.admindm.getRPCMethods;

import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;

/**
 * The Class GetRPCMethodsDecoder.
 */
public final class GetRPCMethodsDecoder implements RPCDecoder {
    
    /** The mng. */
    private final RPCMethodMngService mng;
    
    /**
     * The Constructor.
     * @param pMng the mng
     */
    protected GetRPCMethodsDecoder(final RPCMethodMngService pMng) {
        this.mng = pMng;
    }
    
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     */
    public RPCMethod decode(final Element element) {
        return new GetRPCMethods(mng);
    }
}
