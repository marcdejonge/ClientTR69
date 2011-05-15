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
 * The Class ParameterValueStruct.
 */
public final class ParameterValueStruct {

    /** The name. */
    private final String name;

    /** The value. */
    private final String value;
    
    /** The type of the value (ParameterType syntax) */
    private final int type;

    /**
     * Instantiates a new parameter value structure.
     * @param pName the name
     * @param pValue the value
     * @param pType type associated to the value
     */
    public ParameterValueStruct(final String pName, final String pValue, final int pType) {
        super();
        this.name = pName;
        this.value = pValue;
        this.type = pType;
    }
    
    /**
     * Instantiates a new parameter value structure.
     * @param pName the name
     * @param pValue the value
     */
    public ParameterValueStruct(final String pName, final String pValue) {
    	this(pName, pValue, ParameterType.UNDEFINED);
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value.
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Encoded.
     * @return the element
     */
    public Element encoded() {
        Element result = new Element();
        result.setName("ParameterValueStruct");
        Element eName = new Element();
        eName.setName("Name");
        if (name==null){
            eName.addChild(Element.TEXT, "");
        } else {
            eName.addChild(Element.TEXT, name);
        }
        Element eValue = new Element();
        eValue.setName("Value");
        eValue.setAttribute(Soap.getXsiNameSpace(), "type", 
                Soap.getTypeAttribute(this.type));
        if (value == null){
            eValue.addChild(Element.TEXT,"");   
        } else {
            eValue.addChild(Element.TEXT,value);
        }
        result.addChild(Element.ELEMENT, eName);
        result.addChild(Element.ELEMENT,eValue);
        return result;
    }
}
