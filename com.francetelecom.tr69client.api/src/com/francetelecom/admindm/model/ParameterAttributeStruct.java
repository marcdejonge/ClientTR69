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
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class ParameterAttributeStruct.
 */
public final class ParameterAttributeStruct {
    /**
     * Instantiates a new parameter attribute structure.
     * @param pName the name
     * @param pAccessList the access list
     * @param pNotification the notification
     */
    public ParameterAttributeStruct(final String pName,
            final String[] pAccessList, final int pNotification) {
        super();
        this.accessList = pAccessList;
        this.name = pName;
        this.notification = pNotification;
    }
    /** Name of the parameter. */
    private String name;
    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name.
     * @param pName the new name
     */
    public void setName(final String pName) {
        this.name = pName;
    }
    /**
     * Gets the notification.
     * @return the notification
     */
    public int getNotification() {
        return notification;
    }
    /**
     * Sets the notification.
     * @param pNotification the new notification
     */
    public void setNotification(final int pNotification) {
        this.notification = pNotification;
    }
    /**
     * Gets the access list.
     * @return the access list
     */
    public String[] getAccessList() {
        return accessList;
    }
    /**
     * Sets the access list.
     * @param pAccessList the new access list
     */
    public void setAccessList(final String[] pAccessList) {
        this.accessList = pAccessList;
    }
    /** Notification. */
    private int notification;
    /** Access List. */
    private String[] accessList;
    /**
     * Encoded.
     * @return the element
     */
    public Element encoded() {
        Element result = new Element();
        result.setName("ParameterAttributeStruct");
        Element eName = new Element();
        eName.setName("Name");
        if (name == null) {
            eName.addChild(Element.TEXT, "");
        } else {
            eName.addChild(Element.TEXT, name);
        }
        result.addChild(Element.ELEMENT, eName);
        Element eNotification = new Element();
        eNotification.setName("Notification");
        eNotification.addChild(Element.TEXT, "" + notification);
        result.addChild(Element.ELEMENT, eNotification);
        Element eAccessList = new Element();
        eAccessList.setName("AccessList");
        StringBuffer value = new StringBuffer("xsd:string[");
        int accessListSize = 0;
        if (accessList != null) {
            accessListSize = accessList.length;
        }
        value.append(accessListSize);
        value.append("]");
        eAccessList.setAttribute(Soap.getXsiNameSpace(), "arrayType", value
                .toString());
        for (int i = 0; i < accessListSize; i++) {
            Element eValue = new Element();
            eValue.setName("string");
            if (accessList[i] == null) {
                eValue.addChild(Element.TEXT, "");
            } else {
                eValue.addChild(Element.TEXT, accessList[i]);
            }
            eAccessList.addChild(Element.ELEMENT, eValue);
        }
        result.addChild(Element.ELEMENT, eAccessList);
        return result;
    }
}
