/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : ServerComBundle
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
package com.francetelecom.admindm.com;

import org.ow2.odis.test.TestUtil;
import junit.framework.TestCase;

public class HttpServerTest extends TestCase {
    public void testGetURL() {
        TestUtil.TRACE(this);
        HttpServer.setBestNI(null);
        System.out.println(HttpServer.getURL());
        HttpServer.setBestNI("tap0");
        System.out.println(HttpServer.getURL());
        HttpServer.setBestNI("eth0");
        System.out.println(HttpServer.getURL());
    }
}
