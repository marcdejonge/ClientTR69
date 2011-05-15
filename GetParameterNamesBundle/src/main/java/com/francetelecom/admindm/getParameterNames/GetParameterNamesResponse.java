package com.francetelecom.admindm.getParameterNames;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.ParameterInfoStruct;
/**
 * The Class GetParameterNamesResponse.
 */
public final class GetParameterNamesResponse implements RPCMethod {
    /** The Constant NAME. */
    static final String NAME = "GetParameterNamesResponse";
    /**
     * Array of structures, each containing the name and other information for a
     * ParameterList Parameter or object.<br/>
     * When NextLevel is false, this list MUST contain the Parameter or object
     * whose name exactly matches the ParameterPath argument, plus all
     * Parameters and objects that are descendents of the object given by the
     * ParameterPath argument, if any (all levels below the specified object in
     * the object hierarchy). If the ParameterPath argument is an empty string,
     * names of all objects and Parameters accessible on the particular CPE are
     * returned.<br/>
     * When NextLevel is true, this list MUST contain all Parameters and object
     * that are next-level children of the object given by the ParameterPath
     * argument, if any.<br/>
     * For a Parameter, the Name returned in this structure MUST be a full path
     * name, ending with the name of the Parameter element. For an object, the
     * Name returned in this structure MUST be a partial path, ending with a
     * dot. This list MUST include any objects that are currently empty. An
     * empty object is one that contains no instances (for a multi-instance
     * object), no child objects, and no child Parameters.<br/>
     * If NextLevel is true and ParameterPath refers to an object that is empty,
     * this array MUST contain zero entries.<br/>
     * The ParameterList MUST include only Parameters and objects that are
     * actually implemented by the CPE. If a Parameter is listed, this implies
     * that a GetParameterValues for this Parameter would be expected to
     * succeed.<br/>
     */
    private final ParameterInfoStruct[] parameterList;
    /**
     * Instantiates a new gets the parameter names response.
     * @param pParameterList the parameter list
     */
    public GetParameterNamesResponse(
            final ParameterInfoStruct[] pParameterList) {
        super();
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
     */
    public void perform(final Session session) {
        // NOTHING TO DO
    }
    /**
     * Gets the parameter list.
     * @return the parameter list
     */
    public ParameterInfoStruct[] getParameterList() {
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
