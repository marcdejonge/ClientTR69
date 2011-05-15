package com.francetelecom.admindm.getParameterValues;

import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class GetParameterValuesResponseEncoder.
 */
public class GetParameterValuesResponseEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     */
    public final Element encode(final RPCMethod method) {
        GetParameterValuesResponse gpvr = (GetParameterValuesResponse) method;
        Element result = new Element();
        result.setName(gpvr.getName());
        result.setNamespace(Soap.getCWMPNameSpace());
        Element eParameterList = new Element();
        eParameterList.setName("ParameterList");
        result.addChild(Element.ELEMENT,eParameterList);
        StringBuffer value = new StringBuffer("cwmp:ParameterValueStruct[");
        int count = 0; 
        if (gpvr.getParameterList()!=null){
            count = gpvr.getParameterList().length;
        }
        value.append(count);
        value.append("]");
        eParameterList.setAttribute(Soap.getSoapEncNameSpace(),
                "arrayType", value.toString());
        for (int i = 0; i < count; i++) {
            eParameterList.addChild(Element.ELEMENT,
                    gpvr.getParameterList()[i].encoded());
        }
        return result;
    }
}
