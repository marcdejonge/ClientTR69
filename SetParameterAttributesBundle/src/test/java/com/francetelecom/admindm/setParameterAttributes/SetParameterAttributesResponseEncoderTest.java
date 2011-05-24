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
public class SetParameterAttributesResponseEncoderTest extends TestCase {
    /** The encoder. */
    private SetParameterAttributesResponseEncoder encoder;
    /**
     * Test encode nominal case.
     */
    public void testEncodeNominalCase() {
        TestUtil.TRACE(this);
        encoder = new SetParameterAttributesResponseEncoder();
        SetParameterAttributesResponse param = new SetParameterAttributesResponse();
        encoder.encode(param);
    }
}
