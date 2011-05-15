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
 */
package com.francetelecom.model;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.upnp.UPnPDevice;

import com.francetelecom.acse.modus.demo.X_ORANGE_ServicesToInstall;
import com.francetelecom.admindm.api.IModel;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr106.gen.LAN;
import com.francetelecom.tr106.implem.ConfigImp;
import com.francetelecom.tr106.implem.DeviceInfoImp;
import com.francetelecom.tr106.implem.IPingDiagnosticsImpl;
import com.francetelecom.tr106.implem.ManagementServerImp;
import com.francetelecom.tr106.implem.TimeImp;
import com.francetelecom.tr157.implem.DiscoveryImp;
import com.francetelecom.tr157.implem.ParameterImpl;
import com.francetelecom.tr157.implem.PeriodicStatisticsImpl;
import com.francetelecom.tr157.implem.SampleSetImpl;
import com.francetelecom.tr157.implem.UPnPImp;

public class Model implements IModel {

	/** The data. */
	private IParameterData data;

	/** The discovery. */
	private DiscoveryImp discovery;
	
	/** The periodic statistics. */
	private PeriodicStatisticsImpl periodicStatistics;

	public Model() {
	}

	private List profiles = new ArrayList();

	/**
	 * Sets the data.
	 * 
	 * @param pData
	 *            the data
	 * 
	 * @see com.francetelecom.admindm.api.IModel#setData(com.francetelecom.admindm.model.IParameterData)
	 */
	public void setData(IParameterData pData) {
		this.data = pData;

		String root = data.getRoot();

		try {
			data.createOrRetrieveParameter(root).setType(ParameterType.ANY);
			addProfilBaseline1(data);
			addProfilTime(data);
			addProfilUPnPDiscBasic1(data);
			addProfilPeriodicStatsBase1(data);
			addProfilX_ORANGE_Services(data);
			new ConfigImp(data, data.getRoot() + "Config.").initialize();

			new LAN(data, data.getRoot() + "LAN.").initialize();
			new IPingDiagnosticsImpl(data, data.getRoot() + "IPingDiagnostics.")
					.initialize();
			new UPnPImp(data, data.getRoot() + "Services.UPnP.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the profil baseline1.
	 * 
	 * @param data
	 *            the data
	 * 
	 * @throws Fault
	 *             the fault
	 */
	public void addProfilBaseline1(IParameterData data) throws Fault {
		String root = data.getRoot();
		new DeviceInfoImp(data, root + "DeviceInfo.").initialize();
		new ManagementServerImp(data, root + "ManagementServer.").initialize();
		profiles.add("Baseline:1");
	}

	/**
	 * Adds the profil time.
	 * 
	 * @param data
	 *            the data
	 * 
	 * @throws Fault
	 *             the fault
	 */
	public void addProfilTime(IParameterData data) throws Fault {
		String root = data.getRoot();
		data.createOrRetrieveParameter(root).setType(ParameterType.ANY);
		new TimeImp(data, root + "Time.").initialize();
		profiles.add("Time:1");
	}

	/**
	 * Adds the profil upnpdiscbasic1.
	 * 
	 * @param data
	 *            the data
	 * 
	 * @throws Fault
	 *             the fault
	 */
	public void addProfilUPnPDiscBasic1(IParameterData data) throws Fault {
		String root = data.getRoot();
		discovery = new DiscoveryImp(data, root + "Services.UPnP.Discovery.");
		discovery.initialize();

		profiles.add("UPnPDiscBasic:1");
	}
	
	/**
	 * Adds the profil periodic stats base1.
	 * 
	 * @param data the data
	 * 
	 * @throws Fault the fault
	 */
	public void addProfilPeriodicStatsBase1(IParameterData data) throws Fault {
		String root = data.getRoot();
		periodicStatistics = new PeriodicStatisticsImpl(data, root + "PeriodicStatistics.");
		periodicStatistics.initialize();
		
		// create a new SampleSet
		SampleSetImpl sampleSet = periodicStatistics.addSampleSet();
		sampleSet.getParamName().setValue("SampleSet1");
		
		// add to the set the UDPConnectionRequest parameter
		ParameterImpl parameter = sampleSet.addParameter();
		parameter.getParamReference().setValue(data.getRoot() + "ManagementServer.UDPConnectionRequestAddress");
		
		sampleSet.getParamEnable().setValue(Boolean.TRUE);
		
		
		profiles.add("PeriodicStatsBase:1");
		profiles.add("PeriodicStatsAdv:1");
	}
	
	/**
	 * Adds the profil x_ orang e_ services.
	 * 
	 * @param data the data
	 * 
	 * @throws Fault the fault
	 */
	public void addProfilX_ORANGE_Services(IParameterData data) throws Fault {
		new X_ORANGE_ServicesToInstall(data, null).initialize();
		profiles.add("X_ORANGE_Services");
	}

	/**
	 * Gets the list implemented profile.
	 * 
	 * @return the list implemented profile
	 * 
	 * @see com.francetelecom.admindm.api.IModel#getListImplementedProfile()
	 */
	public List getListImplementedProfile() {
		return profiles;
	}

	/**
	 * Adds the upnp device.
	 * 
	 * @param upnpDevice
	 *            the upnp device
	 */
	public void addUPnPDevice(UPnPDevice upnpDevice) {
		discovery.addUPnPDevice(upnpDevice);
	}

	/**
	 * Removes the upnp device.
	 * 
	 * @param upnpDevice
	 *            the upnp device
	 */
	public void removeUPnPDevice(UPnPDevice upnpDevice) {
		discovery.removeUPnPDevice(upnpDevice);
	}

}
