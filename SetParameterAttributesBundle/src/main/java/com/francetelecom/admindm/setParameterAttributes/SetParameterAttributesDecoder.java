package com.francetelecom.admindm.setParameterAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.XMLUtil;
import com.francetelecom.admindm.model.SetParameterAttributesStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class SetParameterAttributesDecoder.
 */
public class SetParameterAttributesDecoder implements RPCDecoder {
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     * @throws Fault the fault
     */
    public final RPCMethod decode(final Element element) throws Fault {
        SetParameterAttributes result = new SetParameterAttributes();
        Element eParameterList = element.getElement("", "ParameterList");
        int index = eParameterList.indexOf("", "SetParameterAttributesStruct",
                0);
        List lsESPAS = new ArrayList();
        while (index >= 0) {
            System.out.println("index" + index);
            lsESPAS.add(eParameterList.getChild(index));
            index = eParameterList.indexOf("", "SetParameterAttributesStruct",
                    index + 1);
        }
        SetParameterAttributesStruct[] tab;
        tab = new SetParameterAttributesStruct[lsESPAS.size()];
        result.setParameterList(tab);
        Iterator it = lsESPAS.iterator();
        SetParameterAttributesStruct sPAS;
        Element eSPA;
        int i = 0;
        while (it.hasNext()) {
            eSPA = (Element) it.next();
            sPAS = new SetParameterAttributesStruct();
            sPAS.setName(XMLUtil.extractValue(eSPA, "Name"));
            String value;
            value = XMLUtil.extractValue(eSPA, "NotificationChange");
            sPAS.setNotificationChange("true".equals(value)
                    || "Y".equals(value) || "1".equals(value));
            value = XMLUtil.extractValue(eSPA, "AccessListChange");
            sPAS.setAccessListChange("true".equals(value) || "Y".equals(value)
                    || "1".equals(value));
            value = XMLUtil.extractValue(eSPA, "Notification");
            sPAS.setNotification(Integer.parseInt(value));
            Element eAccessList = eSPA.getElement("", "AccessList");
            if (eAccessList == null) {
                StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
                error.append(": AccessList miss.");
                throw new Fault(FaultUtil.FAULT_9003, error.toString());
            }
            List eString = new ArrayList();
            index = eAccessList.indexOf("", "SetParameterAttributesStruct", 0);
            while (index >= 0) {
                eString.add(eAccessList.getChild(index));
                index = eAccessList.indexOf("",
                        "SetParameterAttributesStruct", index + 1);
            }
            int size = eString.size();
            sPAS.setAccessList(new String[size]);
            for (int u = 0; u < size; u++) {
                Element temp = (Element) eString.get(u);
                sPAS.getAccessList()[u] = temp.getText(0);
            }
            tab[i++] = sPAS;
        }
        return result;
    }
}
