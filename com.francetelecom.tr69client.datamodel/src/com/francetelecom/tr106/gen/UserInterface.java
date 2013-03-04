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
package com.francetelecom.tr106.gen;
import com.francetelecom.admindm.model.*;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.soap.Fault;
/**
* Class UserInterface.
* @author OrangeLabs R&D
*/
public class UserInterface  {
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
     public UserInterface(
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

        paramAvailableLanguages = createAvailableLanguages();
        paramISPHelpDesk = createISPHelpDesk();
        paramUserUpdateServer = createUserUpdateServer();
        paramISPHomePage = createISPHomePage();
        paramTextColor = createTextColor();
        paramBackgroundColor = createBackgroundColor();
        paramISPNewsServer = createISPNewsServer();
        paramISPLogo = createISPLogo();
        paramAutoUpdateServer = createAutoUpdateServer();
        paramPasswordUserSelectable = createPasswordUserSelectable();
        paramButtonColor = createButtonColor();
        paramISPMailServer = createISPMailServer();
        paramPasswordRequired = createPasswordRequired();
        paramISPName = createISPName();
        paramButtonTextColor = createButtonTextColor();
        paramWarrantyDate = createWarrantyDate();
        paramCurrentLanguage = createCurrentLanguage();
        paramISPLogoSize = createISPLogoSize();
        paramISPHelpPage = createISPHelpPage();
        paramUpgradeAvailable = createUpgradeAvailable();
    }


    /**
     * Serial ID use for serialize.
     */
     private static final long serialVersionUID = 1L;
    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramAvailableLanguages;

    /**
    * Getter method of AvailableLanguages.
    * @return _AvailableLanguages
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamAvailableLanguages() {
        return paramAvailableLanguages;
    }

    /**
    * Create the parameter AvailableLanguages
    * @return AvailableLanguages
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createAvailableLanguages() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "AvailableLanguages");
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
    private com.francetelecom.admindm.model.Parameter paramISPHelpDesk;

    /**
    * Getter method of ISPHelpDesk.
    * @return _ISPHelpDesk
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamISPHelpDesk() {
        return paramISPHelpDesk;
    }

    /**
    * Create the parameter ISPHelpDesk
    * @return ISPHelpDesk
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createISPHelpDesk() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ISPHelpDesk");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(32));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    * The server where a user can check via a web browser if an update is 
    * for download to a PC. This MUST NOT be used by the CPE if the 
    * parameter is {{true}}. 
    */
    private com.francetelecom.admindm.model.Parameter paramUserUpdateServer;

    /**
    * Getter method of UserUpdateServer.
    * @return _UserUpdateServer
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUserUpdateServer() {
        return paramUserUpdateServer;
    }

    /**
    * Create the parameter UserUpdateServer
    * @return UserUpdateServer
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUserUpdateServer() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UserUpdateServer");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramISPHomePage;

    /**
    * Getter method of ISPHomePage.
    * @return _ISPHomePage
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamISPHomePage() {
        return paramISPHomePage;
    }

    /**
    * Create the parameter ISPHomePage
    * @return ISPHomePage
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createISPHomePage() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ISPHomePage");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramTextColor;

    /**
    * Getter method of TextColor.
    * @return _TextColor
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamTextColor() {
        return paramTextColor;
    }

    /**
    * Create the parameter TextColor
    * @return TextColor
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createTextColor() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "TextColor");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(6));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramBackgroundColor;

    /**
    * Getter method of BackgroundColor.
    * @return _BackgroundColor
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamBackgroundColor() {
        return paramBackgroundColor;
    }

    /**
    * Create the parameter BackgroundColor
    * @return BackgroundColor
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createBackgroundColor() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "BackgroundColor");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(6));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramISPNewsServer;

    /**
    * Getter method of ISPNewsServer.
    * @return _ISPNewsServer
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamISPNewsServer() {
        return paramISPNewsServer;
    }

    /**
    * Create the parameter ISPNewsServer
    * @return ISPNewsServer
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createISPNewsServer() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ISPNewsServer");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramISPLogo;

    /**
    * Getter method of ISPLogo.
    * @return _ISPLogo
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamISPLogo() {
        return paramISPLogo;
    }

    /**
    * Create the parameter ISPLogo
    * @return ISPLogo
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createISPLogo() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ISPLogo");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setWritable(true);
        return param;
        }

    /**
    * The server the CPE can check to see if an update is available for 
    * download to it. This MUST NOT be used by the CPE if the 
    * parameter is {{true}}. 
    */
    private com.francetelecom.admindm.model.Parameter paramAutoUpdateServer;

    /**
    * Getter method of AutoUpdateServer.
    * @return _AutoUpdateServer
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamAutoUpdateServer() {
        return paramAutoUpdateServer;
    }

    /**
    * Create the parameter AutoUpdateServer
    * @return AutoUpdateServer
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createAutoUpdateServer() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "AutoUpdateServer");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    * Present only if the CPE provides a password-protected LAN-side user 
    * and supports LAN-side Auto-Configuration. Indicates whether or not a 
    * to protect the local user interface of the CPE MAY be selected by 
    * user directly, or MUST be equal to the password used by the LAN-side 
    * protocol. 
    */
    private com.francetelecom.admindm.model.Parameter paramPasswordUserSelectable;

    /**
    * Getter method of PasswordUserSelectable.
    * @return _PasswordUserSelectable
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamPasswordUserSelectable() {
        return paramPasswordUserSelectable;
    }

    /**
    * Create the parameter PasswordUserSelectable
    * @return PasswordUserSelectable
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createPasswordUserSelectable() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "PasswordUserSelectable");
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
    private com.francetelecom.admindm.model.Parameter paramButtonColor;

    /**
    * Getter method of ButtonColor.
    * @return _ButtonColor
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamButtonColor() {
        return paramButtonColor;
    }

    /**
    * Create the parameter ButtonColor
    * @return ButtonColor
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createButtonColor() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ButtonColor");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(6));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramISPMailServer;

    /**
    * Getter method of ISPMailServer.
    * @return _ISPMailServer
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamISPMailServer() {
        return paramISPMailServer;
    }

    /**
    * Create the parameter ISPMailServer
    * @return ISPMailServer
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createISPMailServer() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ISPMailServer");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    * Present only if the CPE provides a password-protected LAN-side user 
    * Indicates whether or not the local user interface MUST require a 
    * to be chosen by the user. If {{false}}, the choice of whether or not 
    * password is used is left to the user. 
    */
    private com.francetelecom.admindm.model.Parameter paramPasswordRequired;

    /**
    * Getter method of PasswordRequired.
    * @return _PasswordRequired
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamPasswordRequired() {
        return paramPasswordRequired;
    }

    /**
    * Create the parameter PasswordRequired
    * @return PasswordRequired
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createPasswordRequired() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "PasswordRequired");
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
    private com.francetelecom.admindm.model.Parameter paramISPName;

    /**
    * Getter method of ISPName.
    * @return _ISPName
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamISPName() {
        return paramISPName;
    }

    /**
    * Create the parameter ISPName
    * @return ISPName
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createISPName() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ISPName");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(64));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramButtonTextColor;

    /**
    * Getter method of ButtonTextColor.
    * @return _ButtonTextColor
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamButtonTextColor() {
        return paramButtonTextColor;
    }

    /**
    * Create the parameter ButtonTextColor
    * @return ButtonTextColor
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createButtonTextColor() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ButtonTextColor");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(6));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramWarrantyDate;

    /**
    * Getter method of WarrantyDate.
    * @return _WarrantyDate
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamWarrantyDate() {
        return paramWarrantyDate;
    }

    /**
    * Create the parameter WarrantyDate
    * @return WarrantyDate
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createWarrantyDate() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "WarrantyDate");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.DATE);
        param.setValue(new Long(0));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramCurrentLanguage;

    /**
    * Getter method of CurrentLanguage.
    * @return _CurrentLanguage
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamCurrentLanguage() {
        return paramCurrentLanguage;
    }

    /**
    * Create the parameter CurrentLanguage
    * @return CurrentLanguage
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createCurrentLanguage() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "CurrentLanguage");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(16));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramISPLogoSize;

    /**
    * Getter method of ISPLogoSize.
    * @return _ISPLogoSize
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamISPLogoSize() {
        return paramISPLogoSize;
    }

    /**
    * Create the parameter ISPLogoSize
    * @return ISPLogoSize
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createISPLogoSize() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ISPLogoSize");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4095));
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramISPHelpPage;

    /**
    * Getter method of ISPHelpPage.
    * @return _ISPHelpPage
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamISPHelpPage() {
        return paramISPHelpPage;
    }

    /**
    * Create the parameter ISPHelpPage
    * @return ISPHelpPage
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createISPHelpPage() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "ISPHelpPage");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramUpgradeAvailable;

    /**
    * Getter method of UpgradeAvailable.
    * @return _UpgradeAvailable
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamUpgradeAvailable() {
        return paramUpgradeAvailable;
    }

    /**
    * Create the parameter UpgradeAvailable
    * @return UpgradeAvailable
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createUpgradeAvailable() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "UpgradeAvailable");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.BOOLEAN);
        param.addCheck(CheckBoolean.getInstance());
        param.setValue(Boolean.FALSE);
        param.setWritable(true);
        return param;
        }


}