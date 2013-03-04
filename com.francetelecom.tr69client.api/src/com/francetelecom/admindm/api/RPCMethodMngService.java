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
package com.francetelecom.admindm.api;
import java.util.List;
/**
 * The Interface RPCMethodMngService.
 */
public interface RPCMethodMngService {
    /**
     * Gets the rPC method.
     * @return the rPC method
     */
    List getRPCMethod();
    /**
     * Register rpc method.
     * @param name the name
     */
    void registerRPCMethod(String name);
    /**
     * Register rpc encoder.
     * @param name the name
     * @param encoder the encoder
     */
    void registerRPCEncoder(String name, RPCEncoder encoder);
    /**
     * Register rpc decoder.
     * @param name the name
     * @param decoder the decoder
     */
    void registerRPCDecoder(String name, RPCDecoder decoder);
    /**
     * Unregister rpc method.
     * @param name the name
     */
    void unregisterRPCMethod(String name);
    /**
     * Unregister rpc encoder.
     * @param name the name
     */
    void unregisterRPCEncoder(String name);
    /**
     * Unregister rpc decoder.
     * @param name the name
     */
    void unregisterRPCDecoder(String name);
    /**
     * Register event behavior.
     * @param name the name
     * @param eventBehavior the event behavior
     */
    void registerEventBehavior(String name, EventBehavior eventBehavior);
    /**
     * Unregister event behavior.
     * @param name the name
     */
    void unregisterEventBehavior(String name);
    /**
     * Find rpc method encoder.
     * @param method the method
     * @return the RPC encoder
     */
    RPCEncoder findRPCMethodEncoder(final RPCMethod method);
    /**
     * Find rpc method encoder.
     * @param name the name
     * @return the RPC encoder
     */
    RPCEncoder findRPCMethodEncoder(final String name);
    /**
     * Find rpc method decoder.
     * @param value the value
     * @return the rPC decoder
     */
    RPCDecoder findRPCMethodDecoder(final String value);
}
