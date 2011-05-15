package com.francetelecom.admindm.getRPCMethods;
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.api.Session;
/**
 * The Class GetRPCMethodsResponse.
 */
public final class GetRPCMethodsResponse implements RPCMethod {
    /** The mng. */
    private final RPCMethodMngService mng;
    /**
     * The Constructor.
     * @param pMng the mng
     */
    protected GetRPCMethodsResponse(final RPCMethodMngService pMng) {
        this.mng = pMng;
    }
    /** The Constant NAME. */
    private static final String NAME = "GetRPCMethodsResponse";
    /**
     * Gets the name.
     * @return the name
     * @see com.francetelecom.admindm.api.RPCMethod#getName()
     */
    public String getName() {
        return NAME;
    }
    /**
     * Gets the ls rpc methods.
     * @return the ls rpc methods
     */
    public List getLsRPCMethods() {
        List result;
        if (mng != null) {
            result = mng.getRPCMethod();
        } else {
            result = new ArrayList();
        }
        return result;
    }
    /**
     * Perform.
     * @param session the session
     */
    public void perform(final Session session) {
    }
    /**
     * id of the RPCMethod Request by ACS.
     */
    private String id= null;
    /**
     * Gets the id.
     * @return the Id.
     */
	public String getId() { 
		return id;
	}
	/**
	 * Setter the Id.
	 */
	public void setId(String id) {
		this.id=id;		
	}

}
