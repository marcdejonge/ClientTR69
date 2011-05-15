package com.francetelecom.admindm.getRPCMethods;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class GetRPCMethods.
 */
public final class GetRPCMethods implements RPCMethod {
    /** The Constant NAME. */
    private static final String NAME = "GetRPCMethods";
    /** The mng. */
    private final RPCMethodMngService mng;
    
    /**
     * The Constructor.
     * @param pMng the mng
     */
    protected GetRPCMethods(final RPCMethodMngService pMng) {
        this.mng = pMng;
    }
    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return NAME;
    }
    /**
     * Perform.
     * @param session the session
     * @throws Fault the exception
     */
    public void perform(final Session session) throws Fault {
        session.doASoapResponse(new GetRPCMethodsResponse(mng));
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
