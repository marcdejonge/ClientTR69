package com.francetelecom.admindm.getParameterValues;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.ParameterValueStruct;
/**
 * The Class GetParameterValuesResponse.
 */
public final class GetParameterValuesResponse implements RPCMethod {
    /** The Constant NAME. */
    static final String NAME = "GetParameterValuesResponse";
    /** The parameter list. */
    private ParameterValueStruct[] parameterList = null;
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
     * Sets the parameter list.
     * @param pParameterList the new parameter list
     */
    public void setParameterList(final ParameterValueStruct[] pParameterList) {
        this.parameterList = pParameterList;
    }
    /**
     * Gets the parameter list.
     * @return the parameter list
     */
    public ParameterValueStruct[] getParameterList() {
        return parameterList;
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
