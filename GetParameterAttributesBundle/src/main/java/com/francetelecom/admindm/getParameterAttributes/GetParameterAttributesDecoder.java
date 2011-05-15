package com.francetelecom.admindm.getParameterAttributes;
import java.util.ArrayList;
import java.util.List;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class GetParameterAttributesDecoder.
 */
public final class GetParameterAttributesDecoder implements RPCDecoder {
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     * @throws Fault the decoder exception
     */
    public RPCMethod decode(final Element element) throws Fault {
        GetParameterAttributes gpa = new GetParameterAttributes();
        Element eParameterName = element.getElement("","ParameterNames");
        List eString = new ArrayList();
        for (int index = 0;index<eParameterName.getChildCount();index++){
            Element tmp = eParameterName.getElement(index);
            int type = eParameterName.getType(index);
        if (tmp!=null && type ==Element.ELEMENT
                && "string".equals(tmp.getName())){
            eString.add(tmp);
             }
        }
        int size = eString.size();
        gpa.setParameterNames(new String[size]);
        for (int i = 0; i < size; i++) {
            Element temp = (Element) eString.get(i);
            if (temp.getChildCount()>0) {
                gpa.getParameterNames()[i] = temp.getText(0);
            } else{
                gpa.getParameterNames()[i] = "";
            }
        }        
        return gpa;
    }
}
