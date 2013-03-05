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
import java.util.Observable;
import java.util.Observer;
import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.Getter;
import com.francetelecom.admindm.api.ICom;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.stunclient.ISTUNCLient;
/**
 * The Class Com.
 * @author Olivier Beyler - OrangeLabs -
 */
public final class Com implements Runnable, Observer, ICom {
    /** The sem. */
    private Semaphore sem = new Semaphore();
    /** flag if is running. */
    private boolean isRunning = true;
    /**
     * Sets the running.
     * @param pIsRunning the is running
     */
    public void setRunning(final boolean pIsRunning) {
        this.isRunning = pIsRunning;
    }
    /** The Constant STATE_INIT. */
    static final int STATE_INIT = 0;
    /** The Constant STATE_STARTED. */
    static final int STATE_STARTED = 1;
    /** The state. */
    private int state = STATE_INIT;
    /** The server. */
    private HttpServer server;
    /** the udp server. */
    private UDPServer udpServer;
    /** The Constant CONNECTION_URL. */
    private static final String CONNECTION_URL = "ManagementServer.ConnectionRequestURL";
    /** The Constant URL. */
    private static final String URL = "ManagementServer.URL";
    /** The param url. */
    private Parameter paramURL;
    /** The parameter data. */
    private IParameterData parameterData;
    /** The rpc mng. */
    private RPCMethodMngService rpcMng;
    /**
     * Instantiates a new com.
     */
    public Com() { 
        udpServer = UDPServer.getInstance();
    }
    public void setRPCMng(final RPCMethodMngService pRpcMng) {
        this.rpcMng = pRpcMng;
    }
    public void setParameterData(final IParameterData pParameterData) {        
        parameterData = pParameterData;       
    }
    
    /**
     * Request new session.
     */
    public void requestNewSessionByHTTPConnection() {
        parameterData.addEvent(new EventStruct(EventCode.CONNECTION_REQUEST,
                ""));
        sem.release();
    }
    /**
     * Request new session.
     */
    public void requestNewSession() {
        sem.release();
    }
    /**
     * Run.
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            // set com
            parameterData.setCom(this);
            // tcp connection requests
            server = new HttpServer(this);
            // udp connection request
            udpServer.setParameterData(parameterData);
            udpServer.startUDPServer();
            // configure observers
            parameterData.addObserver(this);
            paramURL = parameterData.createOrRetrieveParameter(parameterData
                    .getRoot()
                    + URL);
            paramURL.addObserver(this);
            // start servers
            
        } catch (Exception e1) {
            Log.error("Exception while create Com", e1);
        }
        new Thread(server, "HTTP_Server").start();
        
        Log.debug("server COM is running...");
        Parameter param;
        TR69Session session;
        int retry = 0;
        getterUrl getter = new getterUrl();
        while (isRunning) {
            try {
                param = parameterData.createOrRetrieveParameter(parameterData
                        .getRoot() + CONNECTION_URL);
                param.setGetter(getter);
                session = new TR69Session(parameterData, rpcMng, retry);
                session.run();
                retry = 0;
                Log.debug("server COM is waiting...");
                sem.waiting();
            } catch (Fault e) {
                Log.error("Pb :" + e.getFaultstring(), e);
                try {
                    retry++;
                    Thread.sleep(getSleepingTimeBeforeRetry(retry));
                } catch (InterruptedException e1) {
                    Log.debug("Interrupt Sleep");
                }
            } catch (InterruptedException e) {
                Log.debug("Interrupt Wait");
            }
        }
    }
    class getterUrl implements Getter {
        public  Object get(String sessionId){
            return HttpServer.getURL();
        }
    }
    /** The old random. */
    private static long oldRandom = 0;
    /**
     * Gets the sleeping time before retry.
     * @param retry the retry
     * @return the sleeping time(unit ms) before retry
     */
    protected static long getSleepingTimeBeforeRetry(int retry) {
        long result;  
        double tmp = Math.random();
        switch (retry) {
        case 1:
            result = 5 ;
            break;
        case 2:
            result = 10;
            break;
        case 3:
            result = 20 ;
            break;
        case 4:
            result = 40;
            break;
        case 5:
            result = 80;
            break;
        case 6:
            result = 160;
            break;
        case 7:
            result = 320;
            break;
        case 8:
            result = 640;
            break;
        case 9:
            result = 1280;
            break;
        default:
            result = 2560;
            break;
        }
        result = Math.round(((tmp* result) + result) * 1000);
        if (result == oldRandom) {
        	result = getSleepingTimeBeforeRetry(retry);
        }
        oldRandom = result;
        return result;
    }
    /**
     * Update.
     * @param o the observable
     * @param arg the argument
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(final Observable o, final Object arg) {
        if (o.equals(paramURL) && state != STATE_INIT) {
            onURLUpdate();
        }
    }
    /**
     * <p>
     * Set the STUNclient.
     * </p>
     * <p>
     * This method is also used to unset the STUN client if it is not more
     * available (stunClient parameter set to null).
     * </p>
     * @param stunClient stun client object or null to unset
     */
    public void setSTUNClient(final ISTUNCLient stunClient) {
    	Log.debug("stunClient = " + stunClient
    			);
    	Log.debug("this.udpServer = " + this.udpServer);
        this.udpServer.setSTUNClient(stunClient);
    }
    /**
     * On url update.
     */
    private void onURLUpdate() {
        Log.info("ACS URL CHANGE new Inform session is mandatory");
        parameterData.addEvent(new EventStruct(EventCode.BOOTSTRAP, ""));
        sem.release();
    }
    public Object get(String sessionId) {
        // TODO Auto-generated method stub
        return null;
    }
}
