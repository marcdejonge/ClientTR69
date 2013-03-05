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
* Class LocalDisplay.
* @author OrangeLabs R&D
*/
public class LocalDisplay  {
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
     public LocalDisplay(
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

        paramDisplayHeight = createDisplayHeight();
        paramPosY = createPosY();
        paramPosX = createPosX();
        paramHeight = createHeight();
        paramWidth = createWidth();
        paramResizable = createResizable();
        paramDisplayWidth = createDisplayWidth();
        paramMovable = createMovable();
    }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDisplayHeight;

    /**
    * Getter method of DisplayHeight.
    * @return _DisplayHeight
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDisplayHeight() {
        return paramDisplayHeight;
    }

    /**
    * Create the parameter DisplayHeight
    * @return DisplayHeight
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDisplayHeight() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DisplayHeight");
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
    private com.francetelecom.admindm.model.Parameter paramPosY;

    /**
    * Getter method of PosY.
    * @return _PosY
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamPosY() {
        return paramPosY;
    }

    /**
    * Create the parameter PosY
    * @return PosY
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createPosY() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "PosY");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.INT);
        param.setValue(new Integer(0));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramPosX;

    /**
    * Getter method of PosX.
    * @return _PosX
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamPosX() {
        return paramPosX;
    }

    /**
    * Create the parameter PosX
    * @return PosX
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createPosX() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "PosX");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.INT);
        param.setValue(new Integer(0));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramHeight;

    /**
    * Getter method of Height.
    * @return _Height
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamHeight() {
        return paramHeight;
    }

    /**
    * Create the parameter Height
    * @return Height
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createHeight() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Height");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramWidth;

    /**
    * Getter method of Width.
    * @return _Width
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamWidth() {
        return paramWidth;
    }

    /**
    * Create the parameter Width
    * @return Width
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createWidth() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Width");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setActiveNotificationDenied(true);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramResizable;

    /**
    * Getter method of Resizable.
    * @return _Resizable
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamResizable() {
        return paramResizable;
    }

    /**
    * Create the parameter Resizable
    * @return Resizable
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createResizable() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Resizable");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.BOOLEAN);
        param.addCheck(CheckBoolean.getInstance());
        param.setValue(Boolean.FALSE);
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDisplayWidth;

    /**
    * Getter method of DisplayWidth.
    * @return _DisplayWidth
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDisplayWidth() {
        return paramDisplayWidth;
    }

    /**
    * Create the parameter DisplayWidth
    * @return DisplayWidth
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDisplayWidth() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DisplayWidth");
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
    private com.francetelecom.admindm.model.Parameter paramMovable;

    /**
    * Getter method of Movable.
    * @return _Movable
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamMovable() {
        return paramMovable;
    }

    /**
    * Create the parameter Movable
    * @return Movable
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createMovable() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "Movable");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.BOOLEAN);
        param.addCheck(CheckBoolean.getInstance());
        param.setValue(Boolean.FALSE);
        param.setWritable(true);
        return param;
        }


}