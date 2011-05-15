/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : IFilePersitBundle
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
package com.francetelecom.admindm.persist.file;

import java.io.Serializable;

public class PersistElement implements Serializable {
    private final String key;
    private final String[] subscribers; 
    private final int notification;
    private final Object value;
    
    public String getKey() {
        return key;
    }

    public String[] getSubscribers() {
        return subscribers;
    }

    public int getNotification() {
        return notification;
    }

    public Object getValue() {
        return value;
    }
    
    public PersistElement(String key, String[] subscribers, int notification,
            Object value) {
        this.key=key;
        this.subscribers=subscribers; 
        this.notification=notification;
        this.value=value;
    }
}
