package com.francetelecom.admindm.data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.francetelecom.acse.modus.util.StringUtil;
import com.francetelecom.admindm.api.Log;
/**
 * The Class ServiceObjectList.
 */
public final class ServiceObjectList {
    /** The services. */
    private List services = new ArrayList();
    /**
     * Gets the services.
     * @return the services
     */
    public List getServices() {
        return services;
    }
    /**
     * Instantiates a new service object list.
     * @param summary the summary
     */
    public ServiceObjectList(final String summary) {
        try {
            String[] tokens = StringUtil.simpleSplit(summary, ",");
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
                int y = i;
                while (tokens[i].indexOf(')') < 0) {
                    y++;
                    tokens[i] = tokens[i] + "," + tokens[y];
                    tokens[y] = "";
                }
                i = y;
            }
            for (int i = 0; i < tokens.length; i++) {
                ServiceObject service;
                if (!"".equals(tokens[i])) {
                    service = new ServiceObject(tokens[i]);
                    services.add(service);
                }
            }
        } catch (Exception e) {
            Log.error("unable to create ServiceObjectList", e);
        }
    }
    /**
     * Instantiates a new service object list.
     * @param lsServices the ls services
     */
    public ServiceObjectList(final List lsServices) {
        services = lsServices;
    }
    /**
     * Hash code.
     * @return the int
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((services == null) ? 0 : services.hashCode());
        return result;
    }
    /**
     * Equals.
     * @param obj the obj
     * @return true, if equals
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ServiceObjectList other = (ServiceObjectList) obj;
        if (services == null) {
            if (other.services != null) {
                return false;
            }
        } else if (!services.equals(other.services)) {
            return false;
        }
        return true;
    }
    /**
     * Redefined <code>toString</code> to be human readable.
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer("");
        Iterator it = services.iterator();
        while (it.hasNext()) {
            buffer.append(it.next());
            if (it.hasNext()) {
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }
}
