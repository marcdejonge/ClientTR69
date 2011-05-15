package com.francetelecom.admindm.getRPCMethods;
import java.util.Iterator;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class GetRPCMethodsResponseEncoder.
 */
public class GetRPCMethodsResponseEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     */
    public final Element encode(final RPCMethod method) {
        GetRPCMethodsResponse grpc = (GetRPCMethodsResponse) method;
        Element result = new Element();
        result.setName("GetRPCMethodsResponse");
        result.setNamespace(Soap.getCWMPNameSpace());
        Element eMethodList = new Element();
        eMethodList.setName("MethodList");
        result.addChild(Element.ELEMENT,eMethodList);
        StringBuffer value = new StringBuffer("xsd:string[");
        value.append(grpc.getLsRPCMethods().size());
        value.append("]");
        eMethodList.setAttribute(Soap.getSoapEncNameSpace(),
                "arrayType", value.toString());
        Iterator it = grpc.getLsRPCMethods().iterator();
        while (it.hasNext()) {
            Element e = new Element();
            e.setName("string");
            e.addChild(Element.TEXT,(String) it.next());
            eMethodList.addChild(Element.ELEMENT,e);
        }
        return result;
    }
}
