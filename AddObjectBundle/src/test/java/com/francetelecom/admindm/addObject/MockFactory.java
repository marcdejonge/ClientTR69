/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : AddObjectBundle
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
package com.francetelecom.admindm.addObject;
import com.francetelecom.admindm.api.Factory;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
public class MockFactory implements Factory {
    
    private final String base;
    public MockFactory(String base){
        this.base= base;
    }
    
    public int createObjectInstance(long instanceId,IParameterData data) throws Fault {
         
        String path = base + instanceId + ".";        
        Parameter param= data.createOrRetrieveParameter(path);
        System.out.println("create " +path);
        param.setName(path);
        param.setType(ParameterType.ANY);
        
        path= path+"isCreated";
        System.out.println("create " +path);
        param= data.createOrRetrieveParameter(path);        
        param.setType(ParameterType.BOOLEAN);
        param.setValue(Boolean.TRUE);
        return 0;
    }
}
