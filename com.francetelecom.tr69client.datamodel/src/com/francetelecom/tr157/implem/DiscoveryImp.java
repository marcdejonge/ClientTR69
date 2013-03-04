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
 */
package com.francetelecom.tr157.implem;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.upnp.UPnPDevice;
import org.osgi.service.upnp.UPnPService;

import com.francetelecom.admindm.api.GetterForList;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.CheckMaximum;
import com.francetelecom.admindm.model.CheckMinimum;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Discovery;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscoveryImp.
 */
public class DiscoveryImp extends Discovery {

	private static final Long ROOT_DEVICE_MAXIMUM_NUMBER_OF_ENTRIES_VALUE = new Long(
			100);

	/** The Constant UUID. */
	private static final String UUID = "uuid:";

	/** The Constant ROOTDEVICE_PATH. */
	private static final String ROOTDEVICE_PATH = "Services.UPnP.Discovery.RootDevice.";

	/** The Constant DEVICE_PATH. */
	private static final String DEVICE_PATH = "Services.UPnP.Discovery.Device.";

	/** The Constant SERVICE_PATH. */
	private static final String SERVICE_PATH = "Services.UPnP.Discovery.Service.";

	/** The Constant ROOT_DEVICE_MAXIMUM_NUMBER_OF_ENTRIES. */
	private static final String ROOT_DEVICE_MAXIMUM_NUMBER_OF_ENTRIES = "Services.UPnP.Discovery.X_ORANGE_RootDeviceMaximumNumberOfEntries";

	/** The root device map. */
	private final Map rootDeviceMap = new HashMap();

	/** The device map. */
	private final Map deviceMap = new HashMap();

	/** The service map. */
	private final Map serviceMap = new HashMap();

	/** The parameter data. */
	private final IParameterData data;

	private Parameter rootDeviceMaximumNumberOfEntries;

	/**
	 * Instantiates a new discovery imp.
	 * 
	 * @param basePath
	 *            the base path
	 * @param pData
	 *            the data
	 */
	public DiscoveryImp(IParameterData pData, String basePath) {
		super(pData, basePath);
		this.data = pData;
	}

	/**
	 * <p>
	 * Initialize.
	 * </p>
	 * <p>
	 * Set storage mode to COMPUTED for:
	 * <ul>
	 * <li>DeviceNumberOfEntries.</li>
	 * <li>RootDeviceNumberOfEntries.</li>
	 * <li>ServiceNumberOfEntries.</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Set getter (GetterFoList) for:
	 * <ul>
	 * <li>DeviceNumberOfEntries.</li>
	 * <li>RootDeviceNumberOfEntries.</li>
	 * <li>ServiceNumberOfEntries.</li>
	 * </ul>
	 * </p>
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.tr157.gen.Discovery#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();

		// X_ORANGE_RootDeviceMaximumNumberOfEntries
		rootDeviceMaximumNumberOfEntries = getData().createOrRetrieveParameter(
				getData().getRoot() + ROOT_DEVICE_MAXIMUM_NUMBER_OF_ENTRIES);
		rootDeviceMaximumNumberOfEntries.setStorageMode(StorageMode.COMPUTED);
		rootDeviceMaximumNumberOfEntries.setNotification(0);
		rootDeviceMaximumNumberOfEntries.setType(ParameterType.UINT);
		rootDeviceMaximumNumberOfEntries.addCheck(new CheckMinimum(0));
		rootDeviceMaximumNumberOfEntries
				.addCheck(new CheckMaximum(4294967295L));
		rootDeviceMaximumNumberOfEntries
				.setValue(ROOT_DEVICE_MAXIMUM_NUMBER_OF_ENTRIES_VALUE);
		rootDeviceMaximumNumberOfEntries.setWritable(false);

		getParamDeviceNumberOfEntries().setStorageMode(StorageMode.COMPUTED);
		getParamRootDeviceNumberOfEntries()
				.setStorageMode(StorageMode.COMPUTED);
		getParamServiceNumberOfEntries().setStorageMode(StorageMode.COMPUTED);
		getParamDeviceNumberOfEntries().setGetter(
				new GetterForList(deviceMap.values(), ParameterType.UINT));
		getParamRootDeviceNumberOfEntries().setGetter(
				new GetterForList(rootDeviceMap.values(), ParameterType.UINT));
		getParamServiceNumberOfEntries().setGetter(
				new GetterForList(serviceMap.values(), ParameterType.UINT));

	}

	/**
	 * <p>
	 * Adds the upnp device.
	 * </p>
	 * <p>
	 * Two cases (either for RootDevice or Embedded Device:
	 * <ul>
	 * <li>a branch exists, the status of the Device and the related services
	 * is updated to LeaseActive.
	 * </p>
	 * <li>no branch exists, a new one is created.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param device
	 *            the device
	 */
	public void addUPnPDevice(UPnPDevice device) {
		// get the parent udn parameter
		String parentUDN = (String) device.getDescriptions(null).get(
				UPnPDevice.PARENT_UDN);
		String uuid = DiscoveryImp.computeUUID(device);

		if (parentUDN != null) {
			// embedded device
			Log.info("discover an embedded upnp device, uuid = " + uuid);
			if (deviceMap.containsKey(uuid)) {
				// update
				Log
						.debug("update status of " + uuid
								+ ", status = LeaseActive");
				Log.debug("update embedded services status");
			} else {
				// create new branch
				Log.debug("create datamodel branch for " + uuid);
				Log.debug("create embedded services branch");
			}
		} else {
			// root device
			Log.info("discover a root upnp device, uuid = " + uuid);
			if (rootDeviceMap.containsKey(uuid)) {
				// existing root device - update status (root device + services)
				Log
						.debug("update status of " + uuid
								+ ", status = LeaseActive");
				RootDeviceImp rootDevice = (RootDeviceImp) rootDeviceMap
						.get(uuid);
				rootDevice.setStatus(RootDeviceImp.LEASE_ACTIVE);

				UPnPService[] services = device.getServices();
				if (services != null) {
					for (int i = 0; i < services.length; i++) {
						UPnPService service = services[i];
						String usn = computeUSN(device, service);
						Log.debug("update status of the service " + usn
								+ " to LeaseActive");
						ServiceImp serviceImp = (ServiceImp) serviceMap
								.get(usn);
						if (serviceImp != null) {
							serviceImp.setStatus(ServiceImp.LEASE_ACTIVE);
						} else {
							Log.error("unable to get the service " + usn);
						}

					}
				}

			} else {
				// new root device
				// add a new root device if and only if the
				// RootDeviceNumberOfEntries
				// parameter value is less than the
				// RootDeviceMaximumnNumberOfEntries
				long rootDeviceNumberOfEntries = ((Long) getParamRootDeviceNumberOfEntries()
						.getValue()).longValue();
				long rootDeviceMaximumNumberOfEntries = ((Long) this.rootDeviceMaximumNumberOfEntries
						.getValue()).longValue();
				if (rootDeviceNumberOfEntries < rootDeviceMaximumNumberOfEntries) {

					Log.debug("create datamodel branch for the root device "
							+ uuid);
					RootDeviceImp rootDevice = new RootDeviceImp(data,
							getNextRootDevicePath(), device);
					try {
						rootDevice.initialize();
					} catch (Fault e1) {
						Log.error("unable to initialise the root device "
								+ uuid, e1);
						return;
					}

					// add it to the rootDeviceMap
					rootDeviceMap.put(uuid, rootDevice);
					
					// set RootDeviceNumberOfEntries parameter (for notification purpose only)
					try {
						getParamRootDeviceNumberOfEntries().setValue(new Long((((Long) getParamRootDeviceNumberOfEntries().getValue()).longValue() + 1L)));
					} catch (Fault e1) {
						Log.error("unable to set value for parameter RootDeviceNumberOfEntries", e1);
					}

					// create a new service branch for each embedded services
					UPnPService[] services = device.getServices();
					if (services != null) {
						for (int i = 0; i < services.length; i++) {
							UPnPService service = services[i];
							String usn = computeUSN(device, service);
							Log.debug("create service branch for " + usn);
							ServiceImp serviceImp = new ServiceImp(data,
									getNextServicePath(), service, UUID + uuid);
							try {
								serviceImp.initialize();
							} catch (Fault e) {
								Log.error("unable to initialise the service "
										+ usn, e);
								return;
							}
							serviceMap.put(usn, serviceImp);
						}
					}

				} else {
					Log.debug("number of root device exceeded");
				}
			}

		}

	}

	/**
	 * Removes the upnp device.
	 * 
	 * @param device
	 *            the device
	 */
	public void removeUPnPDevice(UPnPDevice device) {

		String uuid = computeUUID(device);
		Log.info("UPnPDevice " + uuid + " disappears");
		String parentUDN = (String) device.getDescriptions(null).get(
				UPnPDevice.PARENT_UDN);
		Log.debug("update status of " + uuid + " to ByeByeReceived");
		if (parentUDN != null) {
			// embedded device

		} else {
			// root device
			RootDeviceImp rootDevice = (RootDeviceImp) rootDeviceMap.get(uuid);
			if (rootDevice != null) {
				rootDevice.setStatus(RootDeviceImp.BYE_BYE);
			} else {
				Log.error("unable to get the root device " + uuid);
			}
		}

		// update service status
		UPnPService[] services = device.getServices();
		if (services != null) {
			for (int i = 0; i < services.length; i++) {
				UPnPService service = services[i];
				String usn = computeUSN(device, service);

				ServiceImp serviceImp = (ServiceImp) serviceMap.get(usn);
				if (serviceImp != null) {
					serviceImp.setStatus(ServiceImp.BYE_BYE);
				} else {
					Log.error("unable to get the service " + usn);
				}
			}
		}
	}

	/**
	 * Compute uuid of a upnp device.
	 * 
	 * @param device
	 *            the device
	 * 
	 * @return the uuid
	 */
	public static String computeUUID(UPnPDevice device) {
		return ((String) device.getDescriptions(null).get(UPnPDevice.UDN))
				.substring(UUID.length());
	}

	/**
	 * Compute usn of a UPnPService.
	 * 
	 * @param service
	 *            the service
	 * @param upnpDevice
	 *            the upnp device
	 * 
	 * @return the usn of the service
	 */
	public static String computeUSN(UPnPDevice upnpDevice, UPnPService service) {
		return upnpDevice.getDescriptions(null).get(UPnPDevice.UDN) + "::"
				+ service.getType();
	}

	/**
	 * Gets the next root device path.
	 * 
	 * @return the next root device path
	 */
	private String getNextRootDevicePath() {
		return data.getRoot() + ROOTDEVICE_PATH + (rootDeviceMap.size() + 1)
				+ ".";
	}

	/**
	 * Gets the next device path.
	 * 
	 * @return the next device path
	 */
	private String getNextDevicePath() {
		return data.getRoot() + DEVICE_PATH + (deviceMap.size() + 1) + ".";
	}

	/**
	 * Gets the next service path.
	 * 
	 * @return the next service path
	 */
	private String getNextServicePath() {
		return data.getRoot() + SERVICE_PATH + (serviceMap.size() + 1) + ".";
	}

}
