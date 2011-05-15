package com.francetelecom.admindm.getParameterNames;

import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.XMLUtil;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class GetParameterNamesDecoder.
 */
public class GetParameterNamesDecoder implements RPCDecoder {
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     * @throws Fault the fault
     */
    public final RPCMethod decode(final Element element) throws Fault {
        GetParameterNames result = new GetParameterNames();
        result.setParameterPath(XMLUtil.extractValue(element,
                        "ParameterPath"));
        String value = XMLUtil.extractValue(element, "NextLevel");
        result.setNextLevel("Y".equals(value) 
                | "true".equals(value) 
                | "1".equals(value));
        return result;
    }
}
