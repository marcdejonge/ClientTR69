package com.francetelecom.admindm.setParameterValues;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class SetParameterValuesResponseEncoder.
 */
public final class SetParameterValuesResponseEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     * @throws Fault the fault
     */
    public Element encode(final RPCMethod method) throws Fault {
        SetParameterValuesResponse stvr;
        if (method == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": try to encode "
                    + "null SetParameterValuesResponse.");
            throw new Fault(FaultUtil.FAULT_9002, error.toString());
        }
        Element result = null;
        if (method instanceof SetParameterValuesResponse) {
            stvr = (SetParameterValuesResponse) method;
            result = new Element();
            result.setName(stvr.getName());
            result.setNamespace(Soap.getCWMPNameSpace());
            Element eStatut = new Element();
            eStatut.setName("Status");
            eStatut.addChild(Element.TEXT, "" + stvr.getStatus());
            result.addChild(Element.ELEMENT, eStatut);
        } else {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": Invlid cast into ");
            error.append(this.getClass().getName());
            throw new Fault(FaultUtil.FAULT_9002, error.toString());
        }
        return result;
    }
}
