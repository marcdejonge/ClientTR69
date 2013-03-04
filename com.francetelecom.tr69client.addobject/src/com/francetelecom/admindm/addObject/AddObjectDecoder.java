/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : AddObjectBundle
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
 * Author : Orange Labs R&D O.Beyler
 */
package com.francetelecom.admindm.addObject;
import java.io.File;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.XMLUtil;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class AddObjectDecoder.
 */
public class AddObjectDecoder implements RPCDecoder {
    /** The Constant saveFile. */
    private final File saveFile;
    /**
     * Instantiates a new adds the object decoder.
     * @param pSaveFile the save file
     */
    public AddObjectDecoder(final File pSaveFile) {
        this.saveFile = pSaveFile;
    }
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     * @throws Fault the fault
     */
    public final RPCMethod decode(final Element element) throws Fault {
        String objectName = XMLUtil.extractValue(element, "ObjectName");
        String parameterKey = XMLUtil.extractValue(element, "ParameterKey");
        AddObject result = new AddObject(saveFile, objectName, parameterKey);
        return result;
    }
}
