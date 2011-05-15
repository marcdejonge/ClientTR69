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
package com.francetelecom.admindm.model;
import org.kxml2.kdom.Element;
/**
 * The Class DeviceIdStruct.
 */
public final class DeviceIdStruct {
    /** The parameter data. */
    private final IParameterData data;
    /**
     * The Constructor.
     * @param pData the parameter data
     */
    public DeviceIdStruct(final IParameterData pData) {
        data = pData;
    }
    /** The Constant PATH. */
    private static final String PATH = "DeviceInfo.";
    /**
     * Encoded.
     * @return the element
     */
    public Element encoded() {
        Element eDeviceId = new Element();
        eDeviceId.setName("DeviceId");
        Element eManufacturer = new Element();
        eManufacturer.setName("Manufacturer");
        if (getManufacturer() != null) {
            eManufacturer.addChild(Element.TEXT, getManufacturer());
        }
        Element eOui = new Element();
        eOui.setName("OUI");
        if (getManufacturerOui() != null) {
            eOui.addChild(Element.TEXT, getManufacturerOui());
        }
        Element eProductClass = new Element();
        eProductClass.setName("ProductClass");
        if (getProductClass() != null) {
            eProductClass.addChild(Element.TEXT, getProductClass());
        }
        Element eSerialNumber = new Element();
        eSerialNumber.setName("SerialNumber");
        if (getSerialNumber() != null) {
            eSerialNumber.addChild(Element.TEXT, getSerialNumber());
        }
        eDeviceId.addChild(Element.ELEMENT, eManufacturer);
        eDeviceId.addChild(Element.ELEMENT, eOui);
        eDeviceId.addChild(Element.ELEMENT, eProductClass);
        eDeviceId.addChild(Element.ELEMENT, eSerialNumber);
        return eDeviceId;
    }
    /**
     * Gets the attribute string value.
     * @param key the key
     * @return the attribute string value or null if the parameter doesn't
     *         exist.
     */
    private String getAttributeStringValue(final String key) {
        String result = null;
        Parameter param;
        param = data.getParameter(data.getRoot() + PATH + key);
        if (param != null) {
            result = param.getTextValue("");
        }
        return result;
    }
    /**
     * Gets the manufacturer.
     * @return the manufacturer
     */
    public String getManufacturer() {
        return getAttributeStringValue("Manufacturer");
    }
    /**
     * Gets the manufacturer oui.
     * @return the manufacturer oui
     */
    public String getManufacturerOui() {
        return getAttributeStringValue("ManufacturerOUI");
    }
    /**
     * Gets the serial number.
     * @return the serial number
     */
    public String getSerialNumber() {
        return getAttributeStringValue("SerialNumber");
    }
    /**
     * Gets the product class.
     * @return the product class
     */
    public String getProductClass() {
        return getAttributeStringValue("ProductClass");
    }
}
