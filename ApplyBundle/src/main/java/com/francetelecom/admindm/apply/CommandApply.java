/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : ApplyBundle
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
package com.francetelecom.admindm.apply;
import com.francetelecom.admindm.api.ThreadProcessReader;
import com.francetelecom.admindm.download.api.IApplyAction;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
public class CommandApply implements IApplyAction {
    public boolean apply(String name) throws Fault {        
        Process p;
        try {
            StringBuffer result = new StringBuffer();
            String[] args = { "/bin/sh", "-c", name };
            p = Runtime.getRuntime().exec(args);
            ThreadProcessReader t1;
            ThreadProcessReader t2;            
            t1 = new ThreadProcessReader(p.getInputStream());
            t2 = new ThreadProcessReader(p.getErrorStream());            
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            
            if (!"".equals(t1.result.toString())) {
                result.append(t1.result);
            } else if (!"".equals(t2.result.toString())) {
                result.append(t2.result);
            }
            p.waitFor();
        } catch (Exception e) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9010);
            error.append(": unable to apply this cmd.");
            throw new Fault(FaultUtil.FAULT_9010, error.toString(), e);
        }  
        return (p.exitValue()>=0);
    }
    public boolean isApplicable(String fileType) {
        return "X_ORANGE_SCRIPT".equals(fileType);
    }
}
