package com.francetelecom.admindm.getParameterValues;
import java.util.ArrayList;
import java.util.List;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class GetParameterValuesDecoder.
 */
public class GetParameterValuesDecoder implements RPCDecoder {
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     * @throws Fault the fault
     */
    public final RPCMethod decode(final Element element) throws Fault {
        GetParameterValues result = new GetParameterValues();
        Element eParameterName;
        eParameterName = element.getElement("","ParameterNames");
        if (eParameterName == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": ParameterNames tags is missing.");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        List eString = new ArrayList();
        for (int index=0;index<eParameterName.getChildCount();index++){
            if ((eParameterName.getType(index)==Element.ELEMENT)&&
                    "string".equals(eParameterName.getElement(index).getName())){
                eString.add(eParameterName.getChild(index));
            }
        }        
        int size = eString.size();
        result.setParameterNames(new String[size]);
        for (int i = 0; i < size; i++) {
            Element temp = (Element) eString.get(i);
            if (temp.getChildCount()>0){
                result.getParameterNames()[i] = temp.getText(0);
            } else  {
                result.getParameterNames()[i] = "";
            }
        }
        return result;
    }
}
