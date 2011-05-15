package com.francetelecom.admindm.data;
import com.francetelecom.acse.modus.util.StringUtil;
import com.francetelecom.admindm.api.Log;
/**
 * The Class ObjectVersion.
 */
public final class ObjectVersion {
    /** The major. */
    private int major = 1;
    /** The minor. */
    private int minor = 0;
    /**
     * Hash code.
     * @return the int
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + major;
        result = prime * result + minor;
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
        ObjectVersion other = (ObjectVersion) obj;
        if (major != other.major) {
            return false;
        }
        if (minor != other.minor) {
            return false;
        }
        return true;
    }
    /**
     * Instantiates a new object version.
     * @param pMajor the major
     * @param pMinor the minor
     */
    public ObjectVersion(final int pMajor, final int pMinor) {
        major = pMajor;
        minor = pMinor;
    }
    /**
     * Instantiates a new object version.
     * @param parse the parse
     */
    public ObjectVersion(final String parse) {
        String[] tokens;
        try {
            tokens = StringUtil.simpleSplit(parse, ".");
            if (tokens[0] != null) {
                major = Integer.parseInt(tokens[0]);
            }
            if (tokens[1] != null) {
                minor = Integer.parseInt(tokens[1]);
            }
        } catch (Exception e) {
            Log.error("Unable to decode ObjectVersion", e);
        }
    }
    /**
     * Redefine <code>toString</code> to be human readable.
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(major);
        buffer.append(".");
        buffer.append(minor);
        return buffer.toString();
    }
}
