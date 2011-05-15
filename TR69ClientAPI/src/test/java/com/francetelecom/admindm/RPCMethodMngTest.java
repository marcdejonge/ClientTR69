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
package com.francetelecom.admindm;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.inform.Inform;
import com.francetelecom.admindm.model.ParameterData;
/**
 * The Class RPCMethodMngTest.
 */
public final class RPCMethodMngTest extends TestCase {
    /**
     * Test register rpc method.
     */
    public void testRegisterRPCMethod() {
        TestUtil.TRACE(this);
        RPCMethodMng mng = RPCMethodMng.getInstance();
        mng.registerRPCMethod("test");
        // try to register rpcmethod twice
        mng.registerRPCMethod("test");
    }
    /**
     * Test unregister rpc method.
     */
    public void testUnregisterRPCMethod() {
        TestUtil.TRACE(this);
        RPCMethodMng mng = RPCMethodMng.getInstance();
        mng.unregisterRPCMethod("test");
        mng.unregisterRPCMethod("test");
    }
    /**
     * Test register rpc encoder.
     */
    public void testRegisterRPCEncoder() {
        TestUtil.TRACE(this);
        RPCMethodMng mng = RPCMethodMng.getInstance();
        mng.registerRPCEncoder("test", null);
        // try to register encoder twice
        mng.registerRPCEncoder("test", null);
    }
    /**
     * Test unregister rpc encoder.
     */
    public void testUnregisterRPCEncoder() {
        TestUtil.TRACE(this);
        RPCMethodMng mng = RPCMethodMng.getInstance();
        mng.unregisterRPCEncoder("test");
        // try to register encoder twice
        mng.unregisterRPCEncoder("test");
    }
    /**
     * Test register rpc decoder.
     */
    public void testRegisterRPCDecoder() {
        TestUtil.TRACE(this);
        RPCMethodMng mng = RPCMethodMng.getInstance();
        mng.registerRPCDecoder("test", null);
        // try to register encoder twice
        mng.registerRPCDecoder("test", null);
    }
    /**
     * Test unregister rpc decoder.
     */
    public void testUnregisterRPCDecoder() {
        TestUtil.TRACE(this);
        RPCMethodMng mng = RPCMethodMng.getInstance();
        mng.unregisterRPCDecoder("test");
        // try to register decoder twice
        mng.unregisterRPCDecoder("test");
    }
    /**
     * Test get rpc method.
     */
    public void testGetRPCMethod() {
        TestUtil.TRACE(this);
        RPCMethodMng mng = RPCMethodMng.getInstance();
        mng.getRPCMethod();
    }
    /**
     * Test register event behavior.
     */
    public void testRegisterEventBehavior() {
        TestUtil.TRACE(this);
        RPCMethodMng mng = RPCMethodMng.getInstance();
        mng.registerEventBehavior("test", null);
        mng.registerEventBehavior("test", null);
    }
    /**
     * Test unregister event behavior.
     */
    public void testUnregisterEventBehavior() {
        TestUtil.TRACE(this);
        RPCMethodMng mng = RPCMethodMng.getInstance();
        mng.unregisterEventBehavior("test");
        mng.unregisterEventBehavior("test");
    }
    /**
     * Test find methods.
     */
    public void testFinds() {
        TestUtil.TRACE(this);
        RPCMethodMng mng = RPCMethodMng.getInstance();
        mng.findRPCMethodDecoder("test_");
        mng.findRPCMethodEncoder("test_");
        mng.findRPCMethodEncoder((RPCMethod) null);
        mng.findRPCMethodEncoder(new Inform(new ParameterData(),1));
    }
}
