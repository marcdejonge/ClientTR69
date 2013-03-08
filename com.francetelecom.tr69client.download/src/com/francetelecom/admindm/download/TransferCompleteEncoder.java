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

import aQute.bnd.annotation.component.Component;

import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.download.api.TransferComplete;
import com.francetelecom.admindm.soap.Soap;

/**
 * The Class TransferCompleteEncoder.
 */
@Component(properties="name=TransferComplete")
public class TransferCompleteEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     */
    public final Element encode(final RPCMethod method) {
        TransferComplete transferComplete = (TransferComplete) method;
        Element eTransferComplete =
            new Element();
        eTransferComplete.setName(transferComplete.getName());
        eTransferComplete.setNamespace(Soap.getCWMPNameSpace());
        Element eCommandKey = new Element();
        eCommandKey.setName("CommandKey");
        eCommandKey.addChild(Element.TEXT,transferComplete.getCommandKey());
        Element eFaultStruct = transferComplete.getFaultStruct().encode();
        Element eStartTime =  new Element();
        eStartTime.setName("StartTime");
        eStartTime.addChild(Element.TEXT,
                Soap.convertDate2String(transferComplete.getStartTime()));
        Element eCompleteTime =  new Element();
        eCompleteTime.setName("CompleteTime");
        eCompleteTime.addChild(Element.TEXT,
                Soap.convertDate2String(transferComplete.getCompleteTime()));
        eTransferComplete.addChild(Element.ELEMENT,eCommandKey);
        eTransferComplete.addChild(Element.ELEMENT,eFaultStruct);
        eTransferComplete.addChild(Element.ELEMENT,eStartTime);
        eTransferComplete.addChild(Element.ELEMENT,eCompleteTime);
        return eTransferComplete;
    }
}
