package com.francetelecom.admindm.setParameterValues;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;

/**
 * The Class SetParameterValuesResponse.
 */
public final class SetParameterValuesResponse implements RPCMethod {

    /** The Constant NAME. */
    private static final String NAME = "SetParameterValuesResponse";

    /** The status. */
    private int status;

    /**
     * Gets the status.
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Instantiates a new sets the parameter values response.
     * @param pStatus the status
     */
    public SetParameterValuesResponse(final int pStatus) {
        this.status = pStatus;
    }

    /**
     * Gets the name.
     * @return the name
     * @see com.francetelecom.admindm.api.RPCMethod#getName()
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
