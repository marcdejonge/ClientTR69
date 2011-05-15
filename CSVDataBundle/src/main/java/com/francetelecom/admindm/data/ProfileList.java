package com.francetelecom.admindm.data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.francetelecom.acse.modus.util.StringUtil;
import com.francetelecom.admindm.api.Log;
/**
 * The Class ProfileList.
 */
public final class ProfileList {
    /** The profiles. */
    private List profiles = new ArrayList();
    /**
     * Instantiates a new profile list.
     * @param pProfiles the profiles
     */
    public ProfileList(final List pProfiles) {
        profiles = pProfiles;
    }
    /**
     * Instantiates a new profile list.
     * @param substring the substring
     */
    public ProfileList(final String substring) {
        try {
            String[] tokens = StringUtil.simpleSplit(substring, ",");
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].trim();
                if (!"".equals(tokens[i])) {
                    profiles.add(new Profile(tokens[i]));
                }
            }
        } catch (Exception e) {
            Log.error("Failed to create ProfileList", e);
        }
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
        if (profiles != null) {
            result += profiles.hashCode();
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
        ProfileList other = (ProfileList) obj;
        if (profiles == null) {
            if (other.profiles != null) {
                return false;
            }
        } else if (!profiles.equals(other.profiles)) {
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
        Iterator it = profiles.iterator();
        StringBuffer buffer = new StringBuffer("(");
        while (it.hasNext()) {
            buffer.append(it.next());
            if (it.hasNext()) {
                buffer.append(", ");
            }
        }
        buffer.append(")");
        return buffer.toString();
    }
}
