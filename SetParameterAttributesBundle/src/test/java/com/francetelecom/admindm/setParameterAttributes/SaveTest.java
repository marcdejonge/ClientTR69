/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : SetParameterAttributesBundle
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

package com.francetelecom.admindm.setParameterAttributes;

import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.Parameter;

/**
 * The Class SaveTest.
 */
public class SaveTest extends TestCase {
    
    /**
     * Test restore.
     */
    public void testRestore() {
        TestUtil.TRACE(this);
        Parameter param = new Parameter();
        String[] accessList = {"Subscriber"};
        String[] accessList2 = {"Subscriber", "test"};
        param.setAccessList(accessList);
        param.setNotification(1);
        
        Save save = new Save(param);
        param.setNotification(4);
        param.setAccessList(accessList2);
        save.restore();
        assertEquals(param.getNotification(), 1);
        assertEquals(param.getAccessList(), accessList);
    }
}
