package com.francetelecom.admindm.data;
/**
 * The Class DeviceSummary.
 */
public final class DeviceSummary {
    /** The summary. */
    private String summary;
    /** The root object. */
    private RootObject rootObject;
    /** The list of service object. */
    private ServiceObjectList serviceObjectList;
    /**
     * Instantiates a new device summary.
     * @param pDeviceSummary the device summary
     */
    public DeviceSummary(final String pDeviceSummary) {
        summary = pDeviceSummary;
        int index = summary.indexOf(',');
        if (index > 0) {
            rootObject = new RootObject(summary.substring(0, index));
            String services = summary.substring(index + 1);
            serviceObjectList = new ServiceObjectList(services);
        } else {
            // when no service is defined
            rootObject = new RootObject(summary);
            serviceObjectList = null;
        }
    }
    /**
     * Gets the root object.
     * @return the root object
     */
    public RootObject getRootObject() {
        return rootObject;
    }
    /**
     * Gets the list service object.
     * @return the list service object
     */
    public ServiceObjectList getServiceObjectList() {
        return serviceObjectList;
    }
    /**
     * Redefine <code>toString</code> to be human readable.
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(getRootObject().toString());
        if (serviceObjectList != null) {
            buffer.append(", ");
            buffer.append(serviceObjectList);
        }
        return buffer.toString();
    }
}
