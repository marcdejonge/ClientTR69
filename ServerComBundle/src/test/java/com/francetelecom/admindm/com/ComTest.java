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
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import org.ow2.odis.test.TestUtil;
import junit.framework.TestCase;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterData;
public class ComTest extends TestCase {
    public void testTestUrl() {
        TestUtil.TRACE(this);
//        IParameterData data = new ParameterData();
//        System.out.println("test");
//        
//         Properties prop = System.getProperties();
//                
//         prop.put("socksProxyHost", "");
//         prop.put("socksProxyPort", "");
//         prop.put("http.proxySet", "true");
//         prop.put("http.proxyHost", "");
//         prop.put("http.proxyPort", "");
//         String address = "";                
//         try {
//         InetSocketAddress ads = new InetSocketAddress("xxx.xxx.xxx.xxx",80);
//                    
//         Socket socket = new Socket();
//         System.out.println("fi");
//         
//         socket.connect(ads, 20);
//         System.out.println("fin");
//         System.out.println(socket.getLocalAddress().getHostAddress());
//         } catch (UnknownHostException e) {
//         e.printStackTrace();
//         } catch (IOException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//         }
    }
    
    public void testRetry(int retry,long min,long max) {
        long oldValue= 0;
        for (int i=0;i<5000;i++){            
            long value = Com.getSleepingTimeBeforeRetry(retry);
            assertTrue(""+i+"retry delay"+retry+"  "+value+" "+oldValue,value!=oldValue);
            assertTrue("retry delay "+retry+"  "+value, value>=min );
            assertTrue("retry delay "+retry+"  "+value, value<=max);
            oldValue = value;
        }
    }
    public void testRetry(){
        TestUtil.TRACE(this);        
        testRetry(1,5000,10000);
        testRetry(2,10000,20000);
        testRetry(3,20000,40000);
        testRetry(4,40000,80000);
        testRetry(5,80000 ,160000);
        testRetry(6,160000,320000);
        testRetry(7,320000,640000);
        testRetry(8,640000,1280000);
        testRetry(9,1280000, 2560000);
        testRetry(10,2560000, 5120000);
    }
    
}
