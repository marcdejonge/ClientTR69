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

import java.io.Serializable;
import org.kxml2.kdom.Element;


/**
 * The Class EventStruct.
 * @author Olivier Beyler - OrangeLabs -
 */
public final class EventStruct implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The event code. */
    private String eventCode;

    /**
     * Gets the event code.
     * @return the event code
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     * Sets the event code.
     * @param pEventCode the new event code
     */
    public void setEventCode(final String pEventCode) {
        this.eventCode = pEventCode;
    }

    /**
     * Gets the command key.
     * @return the command key
     */
    public String getCommandKey() {
        return commandKey;
    }

    /**
     * Sets the command key.
     * @param pCommandKey the new command key
     */
    public void setCommandKey(final String pCommandKey) {
        this.commandKey = pCommandKey;
    }

    /** The command key. */
    private String commandKey;

    /**
     * Creates a new event structure.
     * @param ec An event code
     * @param ck A command key
     */
    public EventStruct(final String ec, final String ck) {
        eventCode = ec;
        commandKey = ck;

    }

    /**
     * Encoded.
     * @return element
     */
    public Element encoded() {
        Element result = new Element();
        result.setName("EventStruct");
        Element eEventCode = new Element();
        eEventCode.setName("EventCode");
        eEventCode.addChild(Element.TEXT, eventCode);
        Element eCommandKey = new Element();
        eCommandKey.setName("CommandKey");
        eCommandKey.addChild(Element.TEXT, commandKey);
        result.addChild(Element.ELEMENT, eEventCode);
        result.addChild(Element.ELEMENT,eCommandKey);
        return result;
    }

    /**
     * Hash code.
     * @return the int
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (commandKey != null) {
            result += commandKey.hashCode();
        }
        result = prime * result;
        if (eventCode != null) {
            result+= eventCode.hashCode();
        }
        return result;
    }

    /**
     * Equals.
     * @param obj the obj
     * @return true, if equals
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EventStruct other = (EventStruct) obj;
        if (commandKey == null) {
            if (other.commandKey != null) {
                return false;
            }
        } else if (!commandKey.equals(other.commandKey)) {
            return false;
        }
        if (eventCode == null) {
            if (other.eventCode != null) {
                return false;
            }
        } else if (!eventCode.equals(other.eventCode)) {
            return false;
        }
        return true;
    }
}
