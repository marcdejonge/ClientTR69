/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : TR69ClientAPI
 *
 * Copyright © 2011 France Telecom
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */ 
package com.francetelecom.admindm.soap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.model.ParameterType;
/**
 * The Class Soap.
 */
public final class Soap {
    /**
     * Hide Soap constructor.
     */
    private Soap() {
    }
    protected static String namespace(String name, String value) {
        StringBuffer buffer = new StringBuffer(name);
        buffer.append("=");
        buffer.append('"');
        buffer.append(value);
        buffer.append('"');
        return buffer.toString();
    }
    /**
     * Gets the CWMP name space.
     * @return the CWMP name space
     */
    public static String getCWMPNameSpace() {
        // return namespace("cwmp", "urn:dslforum-org:cwmp-1-1");
        return "urn:dslforum-org:cwmp-1-1";
    }
    static private String cwmpPrefix= "cwmp";
    static private String soapEnvPrefix= "soapEnv";
    static private String soapEncPrefix= "soapEnc";
    
    public static void setCWMPNameSpacePrefix(final String value) {
        cwmpPrefix = value;
    }

    public static void setSoapEnvNameSpacePrefix(final String value) {
        soapEnvPrefix = value;
    }

    public static void setSoapEncNameSpacePrefix(String value) {
        soapEncPrefix = value;
    }
 
    public static String getCWMPNameSpacePrefix() {
        return cwmpPrefix;
    }

    public static String getSoapEnvNameSpacePrefix() {
        return soapEnvPrefix;
    }

    public static String getSoapEncNameSpacePrefix() {
        return soapEncPrefix;
    }
    /**
     * Gets the soap env name space.
     * @return the soap env name space
     */
    public static String getSoapEnvNameSpace() {
        // return namespace("soapenv",
        // "http://schemas.xmlsoap.org/soap/envelope/");
        return "http://schemas.xmlsoap.org/soap/envelope/";
    }
    /**
     * Gets the xsd name space.
     * @return the xsd name space
     */
    public static String getXsdNameSpace() {
        // return namespace("xsd","http://www.w3.org/2001/XMLSchema");
        return "http://www.w3.org/2001/XMLSchema";
    }
    /**
     * Gets the xsi name space.
     * @return the xsi name space
     */
    public static String getXsiNameSpace() {
        // return namespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
        return "http://www.w3.org/2001/XMLSchema-instance";
    }
    /**
     * Gets the soap enc name space.
     * @return the soap enc name space
     */
    public static String getSoapEncNameSpace() {
        // return namespace("soapenc",
        // "http://schemas.xmlsoap.org/soap/encoding/");
        return "http://schemas.xmlsoap.org/soap/encoding/";
    }
    /**
     * Gets the soap encoding name space.
     * @return the soap encoding name space
     */
    public static String getSoapEncodingNameSpace() {
        // return namespace("encodingStyle",
        // "http://schemas.xmlsoap.org/soap/encoding/");
        return "http://schemas.xmlsoap.org/soap/encoding/";
    }
    /**
     * Gets the document.
     * @param eResponse the e response
     * @param id the id
     * @return the document
     */
    public static Document getDocument(final Element eResponse, final String id) {
        Document doc = new Document();
        StringBuffer namespaces = new StringBuffer(getSoapEnvNameSpace());
        namespaces.append(" ").append(getSoapEncNameSpace());
        namespaces.append(" ").append(getCWMPNameSpace());
        namespaces.append(" ").append(getXsiNameSpace());
        namespaces.append(" ").append(getXsdNameSpace());
        Element eEnveloppe = new Element();
        eEnveloppe.setName("Envelope");
        eEnveloppe.setNamespace(getSoapEnvNameSpace());
        eEnveloppe.setPrefix(getSoapEnvNameSpacePrefix(),
                getSoapEnvNameSpace());
        eEnveloppe.setPrefix(getSoapEncNameSpacePrefix(),
                getSoapEncNameSpace());
        eEnveloppe.setPrefix("cwmp", "urn:dslforum-org:cwmp-1-1");
        eEnveloppe.setPrefix("xsi",
                "http://www.w3.org/2001/XMLSchema-instance");
        eEnveloppe.setPrefix("xsd", "http://www.w3.org/2001/XMLSchema");
        doc.addChild(Element.ELEMENT, eEnveloppe);
        if (eResponse != null) {
            Element eHeader;
            eHeader = eEnveloppe
                    .createElement(getSoapEnvNameSpace(), "Header");
            eEnveloppe.addChild(Element.ELEMENT, eHeader);
            
            Element eId = eHeader.createElement(getCWMPNameSpace(), "ID");           
            eId.setAttribute(getSoapEnvNameSpace(), "mustUnderstand", "1");            
            if (id != null) {
                eId.addChild(Element.TEXT, id);
                eHeader.addChild(Element.ELEMENT, eId);
            } else {
                throw new RuntimeException("");
                //eId.addChild(Element.TEXT, "");
                //eHeader.addChild(Element.ELEMENT, eId);
            }

            Element eBody;
            eBody = eEnveloppe.createElement(getSoapEnvNameSpace(), "Body");
            eBody.addChild(Element.ELEMENT, eResponse);
            eEnveloppe.addChild(Element.ELEMENT, eBody);
        }
        return doc;
    }
    /** The Constant _unknown_time. */
    private static final String UNKNOWN_TIME = "0001-01-01T00:00:00Z";
    /**
     * Convert date2 string.
     * @param value the value
     * @return the string
     */
    public static String convertDate2String(final long value) {
        String result = UNKNOWN_TIME;
        if (value > 0) {
            Date date = new Date(value);
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'");
            result = format.format(date);
        }
        return result;
    }
    /**
     * Convert date2 long.
     * @param value the value
     * @return the long
     * @throws Fault the fault
     */
    public static long convertDate2Long(final String value) throws Fault {
        long result;
        if (value == null | "".equals(value) | UNKNOWN_TIME.equals(value)) {
            result = 0;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date d;
            try {
                d = format.parse(value);
            } catch (ParseException e) {
                throw new Fault(FaultUtil.FAULT_9003, FaultUtil.STR_FAULT_9003);
            }
            result = d.getTime();
        }
        return result;
    }
    /**
     * Returns an type Attribute object whose the value is computed based on the
     * attributeValueClass argument.
     * @param attributeTypeValue attribute type
     * @return Attribute (name=xsi:type; value=computed from the
     *         attributeValueClass parameter else "string")
     */
    public static String getTypeAttribute(int attributeTypeValue) {
        StringBuffer typeAttribute = new StringBuffer();
        if (ParameterType.INT == attributeTypeValue) {
            typeAttribute.append("xsd:int");
        } else if (ParameterType.BOOLEAN == attributeTypeValue) {
            typeAttribute.append("xsd:boolean");
        } else if (ParameterType.LONG == attributeTypeValue) {
            typeAttribute.append("xsd:long");
        } else if (ParameterType.STRING == attributeTypeValue) {
            typeAttribute.append("xsd:string");
        } else if (ParameterType.DATE == attributeTypeValue) {
            typeAttribute.append("xsd:dateTime");
        } else if (ParameterType.UINT == attributeTypeValue) {
            typeAttribute.append("xsd:unsignedInt");
        } else if (ParameterType.BASE64 == attributeTypeValue) {
            typeAttribute.append("xsd:base64Binary");
        } else if (ParameterType.ANY == attributeTypeValue) {
            typeAttribute.append("xsd:anySimpleType");
        } else if (ParameterType.UNDEFINED == attributeTypeValue) {
            // by default, put string
            // TODO consider revision
            typeAttribute.append("xsd:string");
        }        
        return typeAttribute.toString();
    }
}
