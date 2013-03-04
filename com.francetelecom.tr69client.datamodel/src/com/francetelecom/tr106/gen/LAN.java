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
* Class LAN.
* @author OrangeLabs R&D
*/
public class LAN  {
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
     public LAN(
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

        paramMACAddressOverride = createMACAddressOverride();
        paramDefaultGateway = createDefaultGateway();
        paramSubnetMask = createSubnetMask();
        paramDHCPOption = createDHCPOption();
        paramMACAddress = createMACAddress();
        paramAddressingType = createAddressingType();
        paramIPAddress = createIPAddress();
        paramDNSServers = createDNSServers();
        paramDHCPOptionNumberOfEntries = createDHCPOptionNumberOfEntries();
    }


    /**
     * Serial ID use for serialize.
     */
     private static final long serialVersionUID = 1L;
    /**
    * Whether the value of MACAddress parameter can be overridden. * When 
    * {{param|MACAddress}} is writable. * When {{false}}, 
    * is not writable, and the default MAC address assigned by the device 
    * be restored. 
    */
    private com.francetelecom.admindm.model.Parameter paramMACAddressOverride;

    /**
    * Getter method of MACAddressOverride.
    * @return _MACAddressOverride
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamMACAddressOverride() {
        return paramMACAddressOverride;
    }

    /**
    * Create the parameter MACAddressOverride
    * @return MACAddressOverride
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createMACAddressOverride() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "MACAddressOverride");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.BOOLEAN);
        param.addCheck(CheckBoolean.getInstance());
        param.setValue(Boolean.FALSE);
        param.setWritable(true);
        return param;
        }

    /**
    * The IP address of the current default gateway for this interface. 
    * ability to modify this parameter is OPTIONAL, and this parameter 
    * be modified if the {{param|AddressingType}} is 
    * 
    */
    private com.francetelecom.admindm.model.Parameter paramDefaultGateway;

    /**
    * Getter method of DefaultGateway.
    * @return _DefaultGateway
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDefaultGateway() {
        return paramDefaultGateway;
    }

    /**
    * Create the parameter DefaultGateway
    * @return DefaultGateway
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDefaultGateway() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DefaultGateway");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    * The current subnet mask. The ability to modify this parameter is 
    * and this parameter cannot be modified if the 
    * is {{enum|DHCP|AddressingType}}. 
    */
    private com.francetelecom.admindm.model.Parameter paramSubnetMask;

    /**
    * Getter method of SubnetMask.
    * @return _SubnetMask
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamSubnetMask() {
        return paramSubnetMask;
    }

    /**
    * Create the parameter SubnetMask
    * @return SubnetMask
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createSubnetMask() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "SubnetMask");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(45));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramDHCPOption;

    /**
    * Getter method of DHCPOption.
    * @return _DHCPOption
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDHCPOption() {
        return paramDHCPOption;
    }

    /**
    * Create the parameter DHCPOption
    * @return DHCPOption
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDHCPOption() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DHCPOption");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.ANY);
        param.setWritable(false);
        return param;
        }

    /**
    *  
    */
    private com.francetelecom.admindm.model.Parameter paramMACAddress;

    /**
    * Getter method of MACAddress.
    * @return _MACAddress
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamMACAddress() {
        return paramMACAddress;
    }

    /**
    * Create the parameter MACAddress
    * @return MACAddress
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createMACAddress() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "MACAddress");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(17));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    * The method used to assign an address to this interface. {{enum}} The 
    * to modify this parameter is OPTIONAL. 
    */
    private com.francetelecom.admindm.model.Parameter paramAddressingType;

    /**
    * Getter method of AddressingType.
    * @return _AddressingType
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamAddressingType() {
        return paramAddressingType;
    }

    /**
    * Create the parameter AddressingType
    * @return AddressingType
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createAddressingType() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "AddressingType");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.setValue("");
	    String[] values = {
	                       "DHCP",
	                       "Static",
                          };
		param.addCheck(new CheckEnum(values));
        param.setWritable(true);
        return param;
        }

    /**
    * The current IP address assigned to this interface. The ability to 
    * this parameter is OPTIONAL, and this parameter cannot be modified if 
    * {{param|AddressingType}} is {{enum|DHCP|AddressingType}}. 
    */
    private com.francetelecom.admindm.model.Parameter paramIPAddress;

    /**
    * Getter method of IPAddress.
    * @return _IPAddress
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamIPAddress() {
        return paramIPAddress;
    }

    /**
    * Create the parameter IPAddress
    * @return IPAddress
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createIPAddress() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "IPAddress");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setMandatoryNotification(true);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(45));
        param.setValue("");
        param.setWritable(true);
        return param;
        }

    /**
    * Comma-separated list of IP address of the DNS servers for this 
    * The ability to modify this parameter is OPTIONAL, and this parameter 
    * be modified if the {{param|AddressingType}} is 
    * If this parameter is modifiable, the device MAY ignore any DNS 
    * beyond the first two in the list. 
    */
    private com.francetelecom.admindm.model.Parameter paramDNSServers;

    /**
    * Getter method of DNSServers.
    * @return _DNSServers
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDNSServers() {
        return paramDNSServers;
    }

    /**
    * Create the parameter DNSServers
    * @return DNSServers
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDNSServers() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DNSServers");
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
    private com.francetelecom.admindm.model.Parameter paramDHCPOptionNumberOfEntries;

    /**
    * Getter method of DHCPOptionNumberOfEntries.
    * @return _DHCPOptionNumberOfEntries
    */
    public final com.francetelecom.admindm.model.Parameter
                     getParamDHCPOptionNumberOfEntries() {
        return paramDHCPOptionNumberOfEntries;
    }

    /**
    * Create the parameter DHCPOptionNumberOfEntries
    * @return DHCPOptionNumberOfEntries
    * @throws Fault exception
    */  
    public final com.francetelecom.admindm.model.Parameter
                     createDHCPOptionNumberOfEntries() throws Fault {
    	com.francetelecom.admindm.model.Parameter param;
        param = data.createOrRetrieveParameter(basePath + "DHCPOptionNumberOfEntries");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.setWritable(false);
        return param;
        }


}