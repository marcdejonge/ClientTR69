package com.francetelecom.admindm.getParameterNames;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class GetParameterNamesResponseEncoder.
 */
public class GetParameterNamesResponseEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     * @throws Fault the fault
     */
    public final Element encode(final RPCMethod method) throws Fault {
        if (method == null) {
            throw new Fault(FaultUtil.FAULT_9002, "encode null pointer");
        }
        GetParameterNamesResponse gpnr = (GetParameterNamesResponse) method;
        Element result = new Element();
        result.setName(gpnr.getName());
        result.setNamespace(Soap.getCWMPNameSpace());
        Element eParameterList = new Element();
        eParameterList.setName("ParameterList");
        result.addChild(Element.ELEMENT, eParameterList);
        StringBuffer value = new StringBuffer("xsd:string[");
        value.append(gpnr.getParameterList().length);
        value.append("]");
        for (int i = 0; i < gpnr.getParameterList().length; i++) {
            eParameterList.addChild(Element.ELEMENT,
                    gpnr.getParameterList()[i].encode());
        }
        return result;
    }
}
