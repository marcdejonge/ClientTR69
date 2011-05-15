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
/**
 * Very light HTTP connection server class.
 * Valid status (HTTP/1.1 200) can only be reach in case of
 * the HTTPRequest use:<br/>
 * <li>method GET</li>
 * <li>on specific path</li>
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;
import com.francetelecom.admindm.api.ICom;
import com.francetelecom.admindm.api.Log;
/**
 * The Class HttpServerConnection.
 */
public class HttpServerConnection extends Thread {
    /** The random path. */
    private String randomPath;
    /** The connected client. */
    private Socket connectedClient = null;
    /** The com. */
    private final ICom com;
    /**
     * Instantiates a new http server connection.
     * @param pClient the client
     * @param pRandomPath the random path
     */
    public HttpServerConnection(final Socket pClient,
            final String pRandomPath, final ICom pCom) {
        connectedClient = pClient;
        this.randomPath = pRandomPath;
        this.com = pCom;
    }
    /** The msg ok. */
    private static String msgOK = "" + "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/plain\r\n" + "Server: CPE_HTTPServer\r\n"
            + "\r\n";
    /**
     * Run.
     * @see java.lang.Thread#run()
     */
    public final void run() {
        BufferedReader inFromClient = null;
        try {
            Log.info("The Client " + connectedClient.getInetAddress() + ":"
                    + connectedClient.getPort() + " is connected");
            inFromClient = new BufferedReader(new InputStreamReader(
                    connectedClient.getInputStream()));
            String requestString = inFromClient.readLine();            
            String headerLine = requestString;
            StringTokenizer tokenizer = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();
            String httpQueryString = tokenizer.nextToken();
            StringBuffer responseBuffer = new StringBuffer();
            while (inFromClient.ready()) {
                // Read the HTTP complete HTTP Query
                responseBuffer.append(requestString);
                responseBuffer.append("<BR>");
                requestString = inFromClient.readLine();
            }
            if (httpMethod.equals("GET")) {
                if (httpQueryString.equals(randomPath)) {
                    OutputStream out = connectedClient.getOutputStream();
                    out.write(msgOK.getBytes());
                    out.close();
                    com.requestNewSessionByHTTPConnection();
                }
                inFromClient.close();
            }
            
        } catch (Exception e) {
            Log.error(e.getLocalizedMessage());                        
        } finally {
            try {
                connectedClient.close();
            } catch (IOException e) {
                Log.error(e.getLocalizedMessage());
            }
        }
    }
}
