/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : see inside manifest file
 * Module Name : IpersistBundle
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
package com.francetelecom.admindm.persist;


/**
  * This interface define the basic functionnality who need to be implemented 
 * to realize the persistence of a parameter.
 * To do this, it has to be able to persist the parameter and also to be able to
 * restore it.
 * Please note that all restore function will only be use when the CPE start, 
 * but the persist will be invocated at each modification of a saved parameter,
 * so implement it with attention.
 *
 * @author Olivier Beyler
 */
public interface IPersist {
    
    /**
     * Persist the parameter will all his information.
     * @param key the key
     * @param subscribers the subscribers
     * @param notification the notification
     * @param value the value
     * @param type the type
     */
    void persist(String key, String[] subscribers, int notification,
            Object value, int type);
    
    /**
     * Restore parameter value.
     * @param name the name
     * @param type the type
     * @return the object
     */
    Object restoreParameterValue(String name, int type);
    
    /**
     * Restore parameter subscriber.
     * @param name the name
     * @return the string[]
     */
    String[] restoreParameterSubscriber(String name);
    
    /**
     * Restore parameter notification.
     * @param name the name
     * @return the int
     */
    int restoreParameterNotification(String name);
}
