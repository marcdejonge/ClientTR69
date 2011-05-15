package com.francetelecom.admindm.getParameterAttributes;

import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Soap;

/**
 * The Class GetParameterAttributesResponseEncoder.
 */
public class GetParameterAttributesResponseEncoder implements RPCEncoder {
	/**
	 * Encode.
	 * 
	 * @param method
	 *            the method
	 * @return the element
	 */
	public final Element encode(final RPCMethod method) {
		GetParameterAttributesResponse gpar;
		gpar = (GetParameterAttributesResponse) method;
		Element result = new Element();
		result.setName(gpar.getName());
		result.setNamespace(Soap.getCWMPNameSpace());
		Element eParameterList = new Element();
		eParameterList.setName("ParameterList");
		result.addChild(Element.ELEMENT, eParameterList);
		StringBuffer value = new StringBuffer("cwmp:ParameterAttributeStruct[");
		value.append(gpar.getParameterList().length);
		value.append("]");
		eParameterList.setAttribute(Soap.getSoapEncNameSpace(), "arrayType",
				value.toString());
		for (int i = 0; i < gpar.getParameterList().length; i++) {
			eParameterList.addChild(Element.ELEMENT, gpar.getParameterList()[i]
					.encoded());
		}
		return result;
	}
}
