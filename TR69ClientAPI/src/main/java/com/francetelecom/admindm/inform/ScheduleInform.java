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
package com.francetelecom.admindm.inform;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class ScheduleInform. The aim of this class is to request opening a new
 * session periodically. Period is defined into data model.
 */
public final class ScheduleInform implements Observer {
    /** The Constant DEFAULT_TIME. */
    private static final long DEFAULT_TIME = 1246912000000L;;
    /** The Constant PATH. */
    private static final String PATH = "ManagementServer.";
    /** The is periodic. */
    private Parameter isPeriodic;
    /** The periodic interval. */
    private Parameter periodicInterval;
    /** The periodic time. */
    private Parameter periodicTime;
    /** The current task. */
    private NextSession currentTask = null;
    /** The parameter data. */
    private final IParameterData parameterData;
    /**
     * Stop scheduling.
     */
    public synchronized void stopScheduling() {
        if (currentTask != null) {
            currentTask.isObsolete = true;
        }
        currentTask = null;
    }
    /**
     * Instantiates a new schedule inform.
     * @param pParameterData the parameter data
     */
    public ScheduleInform(final IParameterData pParameterData) {
        parameterData = pParameterData;
        try {
            isPeriodic = parameterData.createOrRetrieveParameter(parameterData
                    .getRoot()
                    + PATH + "PeriodicInformEnable");
            periodicInterval = parameterData
                    .createOrRetrieveParameter(parameterData.getRoot() + PATH
                            + "PeriodicInformInterval");
            periodicTime = parameterData
                    .createOrRetrieveParameter(parameterData.getRoot() + PATH
                            + "PeriodicInformTime");
            periodicTime = parameterData
                    .createOrRetrieveParameter(parameterData.getRoot() + PATH
                            + "PeriodicInformTime");
        } catch (Fault fault) {
            Log.error(fault.getFaultstring());
        }
    }
    /**
     * Declare as observer of the different parameter.
     */
    public void initParameterSource() {
        isPeriodic.addObserver(this);
        periodicInterval.addObserver(this);
        periodicTime.addObserver(this);
    }
    /**
     * Default reference time in case of use of undefined Time.
     */
    public final class NextSession extends TimerTask {
        /**
         * The isObsolete Flag. When this flag is set the task will bypass the
         * action
         */
        private boolean isObsolete = false;
        /**
         * Run.
         * @see java.util.TimerTask#run()
         */
        public void run() {
            Boolean enable = (Boolean) isPeriodic.getValue();
            if (enable == null) {
                enable = Boolean.TRUE;
            }
            if (!isObsolete && enable.booleanValue()) {
                parameterData
                        .addEvent(new EventStruct(EventCode.PERIODIC, ""));
                if (parameterData.getCom() != null) {
                    parameterData.getCom().requestNewSession();
                }
                System.out.println("TRY CREATE TASK2");
                createTask();
            }
        }
    }
    /**
     * Update.
     * @param arg0 the observable
     * @param arg1 the arg1
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(final Observable arg0, final Object arg1) {
        if (arg0.equals(isPeriodic) | arg0.equals(periodicInterval)
                | arg0.equals(periodicTime)) {
            if (currentTask != null) {
                currentTask.isObsolete = true;
            }
            if (Boolean.TRUE.equals(isPeriodic.getValue())) {
                System.out.println("TRY CREATE TASK");
                createTask();
            }
        } else {
            Log.error("unexpected notify");
        }
    }
    /** The Constant nbMilliSecond. */
    private static final int NB_MS_BY_S = 1000;
    /**
     * Compute next occurrence delay.
     * @param currentTime the current time
     * @return the long
     */
    protected long computeNextOccurenceDelay(final long currentTime) {
        long initialTime;
        long next;
        try {
            Long periode = (Long) periodicInterval.getValue();
            Long pt = (Long) periodicTime.getValue();
            if (periode != null && pt != null) {
                next = periode.longValue() * NB_MS_BY_S;
                initialTime = pt.longValue();
            } else {
                return -1;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
        Log.debug("initialTime = " + initialTime);
        Log.debug("next = " + next);
        Log.debug("currentTime = " + currentTime);
        
        // case of unspecified reference time
        if (initialTime == 0) {
            initialTime = DEFAULT_TIME;
        }
        if (initialTime < currentTime) {
        	Log.debug("initialTime < currentTime");
            long t = ((currentTime - initialTime) / next);
            initialTime += next * (t + 1);
        }
        if (initialTime - next > currentTime) {
            long t = ((initialTime - currentTime ) / next);
            initialTime -= next * t;
        }
        return initialTime - currentTime;
    }
    /**
     * Creates the task.
     */
    public synchronized void createTask() {
        currentTask = new NextSession();
        long delay = computeNextOccurenceDelay(System.currentTimeMillis());
        Log.info("CREATE TASK delay is " + delay);
        if (delay >= 0) {
            new Timer().schedule(currentTask, delay);
        }
    }
}
