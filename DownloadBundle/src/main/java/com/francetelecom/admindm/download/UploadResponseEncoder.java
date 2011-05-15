/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DownloadBundle
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
package com.francetelecom.admindm.download;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.download.api.UploadResponse;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class DownloadResponseEncoder.
 */
public final class UploadResponseEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     */
    public Element encode(final RPCMethod method) {
        UploadResponse dr = (UploadResponse) method;
        Element eDownloadResponse = new Element();
        eDownloadResponse.setName(dr.getName());
        eDownloadResponse.setNamespace(Soap.getCWMPNameSpace());
        Element eStatus = new Element();
        eStatus.setName("Status");
        eStatus.addChild(Element.TEXT, "" + dr.getStatus());
        Element eStartTime = new Element();
        eStartTime.setName("StartTime");
        eStartTime.addChild(Element.TEXT, 
                Soap.convertDate2String(dr.getStartTime()));
        Element eCompleteTime = new Element();
        eCompleteTime.setName("CompleteTime");
        eCompleteTime.addChild(Element.TEXT,
                Soap.convertDate2String(dr.getCompleteTime()));
        eDownloadResponse.addChild(Element.ELEMENT,eStatus);
        eDownloadResponse.addChild(Element.ELEMENT,eStartTime);
        eDownloadResponse.addChild(Element.ELEMENT,eCompleteTime);
        return eDownloadResponse;
    }
}
