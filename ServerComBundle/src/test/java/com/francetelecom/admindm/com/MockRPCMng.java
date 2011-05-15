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

import java.util.List;
import com.francetelecom.admindm.api.EventBehavior;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;

public class MockRPCMng implements RPCMethodMngService {
    public RPCDecoder findRPCMethodDecoder(String value) {
        System.out.println("call findRPCMethodDecoder"+ value);
        return null;
    }
    public RPCEncoder findRPCMethodEncoder(RPCMethod method) {
        System.out.println("call findRPCMethodEncoder"+ method.getName());
        return null;
    }
    public RPCEncoder findRPCMethodEncoder(String name) {
        System.out.println("call findRPCMethodEncoder"+ name);
        return null;
    }
    public List getRPCMethod() {
        
        return null;
    }
    public void registerEventBehavior(String name, EventBehavior eventBehavior) {
        
    }
    public void registerRPCDecoder(String name, RPCDecoder decoder) {
        
    }
    public void registerRPCEncoder(String name, RPCEncoder encoder) {
        
    }
    public void registerRPCMethod(String name) {
        
    }
    public void unregisterEventBehavior(String name) {
        
    }
    public void unregisterRPCDecoder(String name) {
        
    }
    public void unregisterRPCEncoder(String name) {
        
    }
    public void unregisterRPCMethod(String name) {
        
    }
}
