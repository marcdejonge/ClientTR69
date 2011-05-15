package com.francetelecom.admindm.getParameterAttributes;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.ParameterAttributeStruct;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class GetParameterAttributesResponse.
 */
public final class GetParameterAttributesResponse implements RPCMethod {
    /** The Constant NAME. */
    static final String NAME = "GetParameterAttributesResponse";
    /** The parameter list. */
    private ParameterAttributeStruct[] parameterList;
    /**
     * Gets the parameter list.
     * @return the parameter list
     */
    public ParameterAttributeStruct[] getParameterList() {
        return parameterList;
    }
    /**
     * Sets the parameter list.
     * @param pParameterList the new parameter list
     */
    public void setParameterList(
            final ParameterAttributeStruct[] pParameterList) {
        this.parameterList = pParameterList;
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
     * @throws Fault the exception
     */
    public void perform(final Session session) throws Fault {
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
