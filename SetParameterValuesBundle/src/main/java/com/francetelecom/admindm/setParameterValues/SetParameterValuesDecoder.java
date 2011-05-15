package com.francetelecom.admindm.setParameterValues;
import java.util.ArrayList;
import java.util.List;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.XMLUtil;
import com.francetelecom.admindm.model.ParameterValueStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class SetParameterValuesDecoder.
 */
public final class SetParameterValuesDecoder implements RPCDecoder {
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     * @throws Fault the fault
     */
    public RPCMethod decode(final Element element) throws Fault {
        if (element == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": unable to decode null SetParameterValues.");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        SetParameterValues result;
        int index = element.indexOf("", "ParameterList", 0);
        if (index < 0) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": Missing xml tag ParameterList");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        Element eParameterList = element.getElement(index);
        List lsParameters = new ArrayList();
        index = eParameterList.indexOf("", "ParameterValueStruct", 0);
        while (index >= 0) {
            lsParameters.add(eParameterList.getElement(index));
            index = eParameterList.indexOf("", "ParameterValueStruct",
                    index + 1);
        }
        ParameterValueStruct[] pvs;
        pvs = new ParameterValueStruct[lsParameters.size()];
        for (int i = 0; i < lsParameters.size(); i++) {
            pvs[i] = decodeParameterStruct((Element) lsParameters.get(i));
        }
        String parameterKey = XMLUtil.extractValue(element, "ParameterKey");
        if (parameterKey.length() > 32) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": '");
            error.append(parameterKey);
            error.append(": ' is too long.");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        result = new SetParameterValues(pvs, parameterKey);
        return result;
    }
    /**
     * Decode parameter struct.
     * @param element the element
     * @return the parameter value struct
     * @throws Fault the fault
     */
    protected ParameterValueStruct decodeParameterStruct(final Element element)
            throws Fault {
        String name = XMLUtil.extractValue(element, "Name");
        String value = XMLUtil.extractValue(element, "Value");
        return new ParameterValueStruct(name, value);
    }
}
