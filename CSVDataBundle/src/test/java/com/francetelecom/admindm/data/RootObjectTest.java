package com.francetelecom.admindm.data;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
/**
 * The Class RootObjectTest.
 */
public class RootObjectTest extends TestCase {
    /**
     * Test equals object.
     */
    public void testEqualsObject() {
        TestUtil.TRACE(this);
        RootObject root = new RootObject("test", null, null);
        RootObject root2 = new RootObject("test", null, null);
        assertEquals(root, root2);
    }
    /**
     * Test equals object with object version.
     */
    public void testEqualsObjectWithObjectVersion() {
        TestUtil.TRACE(this);
        ObjectVersion version = new ObjectVersion(12, 45);
        RootObject root = new RootObject("test", version, null);
        ObjectVersion version2 = new ObjectVersion(12, 45);
        RootObject root2 = new RootObject("test", version2, null);
        assertEquals(root, root2);
    }
    /**
     * Test equals object with object profile list.
     */
    public void testEqualsObjectWithObjectProfileList() {
        TestUtil.TRACE(this);
        ObjectVersion version = new ObjectVersion(12, 45);
        List profiles = new ArrayList();
        Profile profile = new Profile("toto", 5);
        profiles.add(profile);
        ProfileList profileList = new ProfileList(profiles);
        RootObject root = new RootObject("test", version, profileList);
        ObjectVersion version2 = new ObjectVersion(12, 45);
        List profiles2 = new ArrayList();
        Profile profile2 = new Profile("toto", 5);
        profiles2.add(profile2);
        ProfileList profileList2 = new ProfileList(profiles2);
        RootObject root2 = new RootObject("test", version2, profileList2);
        assertEquals(root, root2);
    }
}
