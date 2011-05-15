package com.francetelecom.admindm.reboot;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class RebootResponseEncoder.
 */
public class RebootResponseEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     */
    public final Element encode(final RPCMethod method) {
        Element result = new Element();
        result.setName(method.getName());
        result.setNamespace(Soap.getCWMPNameSpace());
        return result;
    }
}
