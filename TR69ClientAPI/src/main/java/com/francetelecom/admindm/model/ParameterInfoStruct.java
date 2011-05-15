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
 * The Class ParameterInfoStruct.
 */
public final class ParameterInfoStruct {
    /** This is the full path name of a Parameter or a partial path. Name */
    private final String parameterName;
    /**
     * Whether or not the Parameter value can be overwritten Writable using the
     * SetParameterValues method. If Name is a partial path that refers to an
     * object, this indicates whether or not AddObject can be used to add new
     * instances of this object.<br/>
     * If Name is a partial path that refers to a particular instance of a
     * multi-instance object, this indicates whether or not DeleteObject can be
     * used to remove this particular instance.<br/>
     * This element MUST be true only if the corresponding Parameter or object
     * as implemented in this CPE is writable as described above. The value of
     * this element MUST reflect only the actual implementation rather than
     * whether or not the specification of the Parameter or object allows it to
     * be writable.<br/>
     */
    private final boolean writable;
    /**
     * Constructor.
     * @param pParameterName the parameter name
     * @param pWritable the writable
     */
    public ParameterInfoStruct(final String pParameterName,
            final boolean pWritable) {
        super();
        this.parameterName = pParameterName;
        this.writable = pWritable;
    }
    /**
     * Gets the parameter name.
     * @return the parameter name
     */
    public String getParameterName() {
        return parameterName;
    }
    /**
     * Checks if is writable.
     * @return true, if is writable
     */
    public boolean isWritable() {
        return writable;
    }
    /**
     * Encode.
     * @return the element
     */
    public Element encode() {
        Element result = new Element();
        result.setName("ParameterInfoStruct");
        Element eName = new Element();
        eName.setName("Name");
        if (parameterName!=null){
            eName.addChild(Element.TEXT,parameterName);
        } else {
            eName.addChild(Element.TEXT,"");
        }        
        result.addChild(Element.ELEMENT,eName);
        Element eWritable = new Element();
        eWritable.setName("Writable");
        if (writable) {
            eWritable.addChild(Element.TEXT,"true");
        } else {
            eWritable.addChild(Element.TEXT,"false");
        }
        result.addChild(Element.ELEMENT,eWritable);
        return result;
    }
}
