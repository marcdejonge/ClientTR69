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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * The Class ThreadProcessReader.
 */
public class ThreadProcessReader extends Thread {
    /** The out. */
    private final InputStream out;
    /** The result. */
    public StringBuffer result = new StringBuffer();
    /**
     * Instantiates a new thread process reader.
     * @param out the out
     */
    public ThreadProcessReader(InputStream out) {
        this.out = out;
    }
    /**
     * Run.
     * @see java.lang.Thread#run()
     */
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(out);
            BufferedReader reader = new BufferedReader(isr);
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    result.append(System.getProperty("line.separator"));
                }
            } finally {
                reader.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
