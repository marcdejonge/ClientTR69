package com.francetelecom.admindm.data;
/**
 * The Class RootObject.
 */
public final class RootObject {
    /** The object name. */
    private String objectName;
    /** The object version. */
    private ObjectVersion objectVersion;
    /** The profile list. */
    private ProfileList profileList;
    /**
     * Instantiates a new root object.
     * @param pObjectName the object name
     * @param pObjectVersion the object version
     * @param pProfileList the profile list
     */
    public RootObject(final String pObjectName,
            final ObjectVersion pObjectVersion,
            final ProfileList pProfileList) {
        objectName = pObjectName;
        objectVersion = pObjectVersion;
        profileList = pProfileList;
    }
    /**
     * Instantiates a new root object.
     * @param parse the parse
     */
    public RootObject(final String parse) {
        int index = parse.indexOf(':');
        int openAcco = parse.indexOf('[');
        int openPar = parse.indexOf('(');
        int closePar = parse.indexOf(')');
        objectName = parse.substring(0, index);
        objectVersion = new ObjectVersion(parse.substring(index + 1, openAcco));
        profileList = new ProfileList(parse.substring(openPar + 1, closePar));
    }
    /**
     * Redefine <code>toString</code> to be human readable.
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(objectName);
        buffer.append(":");
        buffer.append(objectVersion);
        buffer.append("[]");
        buffer.append(profileList);
        return buffer.toString();
    }
    /**
     * Gets the object name.
     * @return the object name
     */
    public String getObjectName() {
        return objectName;
    }
    /**
     * Gets the object version.
     * @return the object version
     */
    public ObjectVersion getObjectVersion() {
        return objectVersion;
    }
    /**
     * Gets the profile list.
     * @return the profile list
     */
    public ProfileList getProfileList() {
        return profileList;
    }
    /**
     * Hash code.
     * @return the int
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (objectName != null) {
            result += objectName.hashCode();
        }
        result = prime * result;
        if (objectVersion != null) {
            result += objectVersion.hashCode();
        }
        result = prime * result;
        if (profileList != null) {
            result += profileList.hashCode();
        }
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
        RootObject other = (RootObject) obj;
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
        if (profileList == null) {
            if (other.profileList != null) {
                return false;
            }
        } else if (!profileList.equals(other.profileList)) {
            return false;
        }
        return true;
    }
}
