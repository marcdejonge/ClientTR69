package com.francetelecom.admindm.reboot;

import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.XMLUtil;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class RebootDecoder.
 */
public class RebootDecoder implements RPCDecoder {
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     * @throws Fault the fault
     */
    public final RPCMethod decode(final Element element) throws Fault {
        Reboot reboot = new Reboot(XMLUtil.extractValue(element, "CommandKey"));
        return reboot;
    }
}
