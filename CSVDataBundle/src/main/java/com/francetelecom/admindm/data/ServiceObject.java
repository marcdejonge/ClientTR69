package com.francetelecom.admindm.data;
/**
 * The Class ServiceObject.
 */
public final class ServiceObject {
    /** The object name. */
    private String objectName;
    /** The instance. */
    private int instance = -1;
    /** The object version. */
    private ObjectVersion objectVersion;
    /** The ls profile list. */
    private ProfileList lsProfileList;
    /**
     * Instantiates a new service object.
     * @param parse the parse
     */
    public ServiceObject(final String parse) {
        int index = parse.indexOf(':');
        int openAcco = parse.indexOf('[');
        int closeAcco = parse.indexOf(']');
        int openPar = parse.indexOf('(');
        int closePar = parse.indexOf(')');
        objectName = parse.substring(0, index);
        objectVersion = new ObjectVersion(parse.substring(index + 1, openAcco));
        String str = parse.substring(openAcco + 1, closeAcco).trim();
        if (!"".equals(str)) {
            instance = Integer.parseInt(str);
        }
        lsProfileList = new ProfileList(parse.substring(openPar + 1, closePar));
    }
    /**
     * Instantiates a new service object.
     * @param pObjectName the object name
     * @param pObjectVersion the object version
     * @param pInstance the instance
     * @param profiles the profiles
     */
    public ServiceObject(final String pObjectName,
            final ObjectVersion pObjectVersion,
            final int pInstance,
            final ProfileList profiles) {
        objectName = pObjectName;
        objectVersion = pObjectVersion;
        instance = pInstance;
        lsProfileList = profiles;
    }
    /**
     * Redefine <code>toString</code> to be human readable.
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer(objectName);
        buffer.append(":");
        buffer.append(objectVersion);
        buffer.append("[");
        if (instance > 0) {
            buffer.append(instance);
        }
        buffer.append("]");
        buffer.append(lsProfileList);
        return buffer.toString();
    }
    /**
     * Hash code.
     * @return the int
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + instance;
        result = prime * result
                + ((lsProfileList == null) ? 0 : lsProfileList.hashCode());
        result = prime * result
                + ((objectName == null) ? 0 : objectName.hashCode());
        result = prime * result
                + ((objectVersion == null) ? 0 : objectVersion.hashCode());
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
        ServiceObject other = (ServiceObject) obj;
        if (instance != other.instance) {
            return false;
        }
        if (lsProfileList == null) {
            if (other.lsProfileList != null) {
                return false;
            }
        } else if (!lsProfileList.equals(other.lsProfileList)) {
            return false;
        }
        if (objectName == null) {
            if (other.objectName != null) {
                return false;
            }
        } else if (!objectName.equals(other.objectName)) {
            return false;
        }
        if (objectVersion == null) {
            if (other.objectVersion != null) {
                return false;
            }
        } else if (!objectVersion.equals(other.objectVersion)) {
            return false;
        }
        return true;
    }
}
