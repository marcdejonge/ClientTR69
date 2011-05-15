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
package com.francetelecom.admindm.api;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParserException;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Interface RPCDecoder.
 */
public interface RPCDecoder {
    /**
     * Decode.
     * @param element the element
     * @return the rPC method
     * @throws Fault the decoder exception
     * @throws XmlPullParserException the xml pull parser exception
     */
    RPCMethod decode(Element element) throws Fault, XmlPullParserException;
}
