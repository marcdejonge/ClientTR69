package com.francetelecom.admindm.setParameterAttributes;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
/**
 * The Class SetParameterAttributesResponse.
 */
public final class SetParameterAttributesResponse implements RPCMethod {
    /** The Constant NAME. */
    public static final String NAME = "SetParameterAttributesResponse";
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
