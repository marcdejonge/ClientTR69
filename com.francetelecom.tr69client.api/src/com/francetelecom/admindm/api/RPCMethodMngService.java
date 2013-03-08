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
    List<String> getRPCMethods();
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
