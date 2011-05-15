package com.francetelecom.admindm.data;
import com.francetelecom.admindm.api.Log;
/**
 * The Class Profile.
 */
public final class Profile {
    /** The profile name. */
    private String profileName;
    /** The version. */
    private int version;
    /**
     * Instantiates a new profile.
     * @param parse the parse
     */
    public Profile(final String parse) {
        int index = parse.indexOf(':');
        if (index < 0) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Parsing profile failed, it should be like:");
            buffer.append("\"profile:1\"");
            buffer.append(" instead of :");
            buffer.append(parse);
            Log.error(buffer.toString());
            profileName = parse;
            version = 0;
            return;
        }
        profileName = parse.substring(0, index);
        try {
            version = Integer.parseInt(parse.substring(index + 1).trim());
        } catch (NumberFormatException e) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Parsing profile failed, ");
            buffer.append("invalid format for version:");
            buffer.append(parse);
            Log.error(buffer.toString());
            version = 0;
        }
    }
    /**
     * Instantiates a new profile.
     * @param string the string
     * @param i the i
     */
    public Profile(final String string, final int i) {
        profileName = string;
        version = i;
    }
    /**
     * Create hash code.
     * @return the int
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (profileName != null) {
            result += profileName.hashCode();
        }
        result = prime * result + version;
        return result;
    }
    /**
     * Checks if equals.
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
        Profile other = (Profile) obj;
        if (profileName == null) {
            if (other.profileName != null) {
                return false;
            }
        } else if (!profileName.equals(other.profileName)) {
            return false;
        }
        if (version != other.version) {
            return false;
        }
        return true;
    }
    /**
     * Redefine <code>toString</code> to be human readable.
     * @return the string
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return profileName + ":" + version;
    }
}
