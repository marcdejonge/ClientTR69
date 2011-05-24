/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : SetParameterValuesBundle
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

package com.francetelecom.admindm.setParameterValues;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.soap.Fault;

/**
 * The Class SetParameterValuesResponseEncoderTest.
 */
public final class SetParameterValuesResponseEncoderTest extends TestCase {
    /** The encoder. */
    private SetParameterValuesResponseEncoder encoder;
    /**
     * Test encode with null.
     */
    public void testEncodeWithNull() {
        TestUtil.COMMENT("The aim of this test is to test the failure");
        TestUtil.COMMENT("Have several Fault exception "
                + "is the normal behavior.");
        TestUtil.TRACE(this);
        encoder = new SetParameterValuesResponseEncoder();
        try {
            encoder.encode(null);
            fail("Should not encode without Fault Exception");
        } catch (Fault ex) {
            System.out.println("OK");
        }
    }

    /**
     * Test encode nominal case.
     */
    public void testEncodeNominalCase() {
        TestUtil.TRACE(this);
        encoder = new SetParameterValuesResponseEncoder();
        SetParameterValuesResponse param = new SetParameterValuesResponse(2);
        try {
            encoder.encode(param);
        } catch (Fault e) {
            fail("Should not encode without Fault Exception");
        }
    }
}
