/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DataModelBundle
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
 * Generated : 21 oct. 2009 by GenModel
 */

package com.francetelecom.tr157.gen;
import com.francetelecom.admindm.model.*;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.soap.Fault;
/**
* Class Process.
* @author OrangeLabs R&D
*/
public class Process  {
    /** The data. */
    private final IParameterData data;
    /** The base path. */
    private final String basePath;
     /**
     * Default constructor.
     * @param pData data model
     * @param pBasePath base path of attribute
     * @param pPersist persistence
     */
     public Process(
            final IParameterData pData,
            final String pBasePath) {
        super();
        this.data = pData;
        this.basePath = pBasePath;
    }
    /**
     * Get the data.
     * @return the data
     */
    public final IParameterData getData() {
        return data;
    }

    /**
     * Get the basePath.
     * @return the basePath
     */
    public final String getBasePath() {
        return basePath;
    }

     /**
     * Initialiser.
     */ 
     public void initialize() throws Fault {
        com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath);
        param.setType(ParameterType.ANY);

        paramCommand = createCommand();
        paramState = createState();
        paramPID = createPID();
        paramCPUTime = createCPUTime();
        paramPriority = createPriority();
        paramSize = createSize();
    }


    /**
     * Serial ID use for serialize.
     */
     private static final long serialVersionUID = 1L;
    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramCommand;

    /**
    * Getter method of Command.
    * @return _Command
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamCommand() {
        return paramCommand;
    }

    /**
    * Create the parameter Command
    * @return Command
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createCommand() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Command");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramState;

    /**
    * Getter method of State.
    * @return _State
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamState() {
        return paramState;
    }

    /**
    * Create the parameter State
    * @return State
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createState() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "State");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.STRING);
        param.setValue("");
	    String[] values = {
	                       "Running",
	                       "Sleeping",
	                       "Stopped",
	                       "Idle",
	                       "Uninterruptible",
	                       "Zombie",
                          };
		param.addCheck(new CheckEnum(values));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramPID;

    /**
    * Getter method of PID.
    * @return _PID
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamPID() {
        return paramPID;
    }

    /**
    * Create the parameter PID
    * @return PID
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createPID() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "PID");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramCPUTime;

    /**
    * Getter method of CPUTime.
    * @return _CPUTime
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamCPUTime() {
        return paramCPUTime;
    }

    /**
    * Create the parameter CPUTime
    * @return CPUTime
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createCPUTime() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "CPUTime");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramPriority;

    /**
    * Getter method of Priority.
    * @return _Priority
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamPriority() {
        return paramPriority;
    }

    /**
    * Create the parameter Priority
    * @return Priority
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createPriority() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Priority");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.addCheck(new CheckMaximum(99));
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramSize;

    /**
    * Getter method of Size.
    * @return _Size
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamSize() {
        return paramSize;
    }

    /**
    * Create the parameter Size
    * @return Size
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createSize() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Size");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }


}