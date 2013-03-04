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

/**
 * The Class SetParameterAttributesStruct.
 */
public final class SetParameterAttributesStruct {
    
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
     * Checks if is notification change.
     * @return true, if is notification change
     */
    public boolean isNotificationChange() {
        return notificationChange;
    }
    
    /**
     * Sets the notification change.
     * @param pNotificationChange the new notification change
     */
    public void setNotificationChange(final boolean pNotificationChange) {
        this.notificationChange = pNotificationChange;
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
    
    /** The name. */
    private String name;
    
    /** The notification change. */
    private boolean notificationChange;
    
    /** The notification. */
    private int notification;
    
    /** The access list. */
    private String[] accessList;
    
    /** The access list change. */
    private boolean accessListChange;
    
    /**
     * Checks if is access list change.
     * @return true, if is access list change
     */
    public boolean isAccessListChange() {        
        return accessListChange;
    }
    
    /**
     * Sets the access list change.
     * @param value the new access list change
     */
    public void setAccessListChange(boolean value) {        
        accessListChange = value;
    }
}
