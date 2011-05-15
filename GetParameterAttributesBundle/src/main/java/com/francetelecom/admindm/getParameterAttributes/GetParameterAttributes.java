package com.francetelecom.admindm.getParameterAttributes;
import java.util.List;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterAttributeStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class GetParameterAttributes.
 */
public final class GetParameterAttributes implements RPCMethod {
    /** The Constant name. */
    private static final String NAME = "GetParameterAttributes";
    /** The parameter names. */
    private String[] parameterNames;

    /**
     * Gets the parameter names.
     * @return the parameter names
     */
    public String[] getParameterNames() {
        return parameterNames;
    }

    /**
     * Sets the parameter names.
     * @param pParameterNames the parameter names
     */
    public void setParameterNames(final String[] pParameterNames) {
        this.parameterNames = pParameterNames;
    }
    /**
     * Gets the name.
     * @return the name
     * @see com.francetelecom.admindm.api.RPCMethod#getName()
     */
    public String getName() {
        return NAME;
    }
    /** The Constant MAX_PARAMETER_NAME. */
    private static final int MAX_PARAMETER_NAME = 256;
    /**
     * Perform.
     * @param session the session
     * @throws Fault the exception
     */
    public void perform(final Session session) throws Fault {
        if (parameterNames.length > MAX_PARAMETER_NAME) {
            throw new Fault(FaultUtil.FAULT_9004, FaultUtil.STR_FAULT_9004);
        }
        IParameterData parameterData = session.getParameterData();
        List lsParameter = parameterData.extractParameterList(parameterNames);
        GetParameterAttributesResponse response;
        response = new GetParameterAttributesResponse();
        int size = lsParameter.size();
        response.setParameterList(new ParameterAttributeStruct[size]);
        ParameterAttributeStruct pvs;
        for (int i = 0; i < size; i++) {
            Parameter param = (Parameter) lsParameter.get(i);
            pvs = new ParameterAttributeStruct(param.getName(), param
                    .getAccessList(), param.getNotification());
            response.getParameterList()[i] = pvs;
        }
        session.doASoapResponse(response);
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
