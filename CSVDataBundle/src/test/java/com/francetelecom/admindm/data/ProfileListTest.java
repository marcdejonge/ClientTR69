package com.francetelecom.admindm.data;
import java.util.ArrayList;
import java.util.List;
import org.ow2.odis.test.TestUtil;
import junit.framework.TestCase;
/**
 * The Class ProfileListTest.
 */
public class ProfileListTest extends TestCase {
    /**
     * Test profile list empty.
     */
    public void testProfileListEmpty() {
        TestUtil.TRACE(this);
        ProfileList pl;
        List profiles = new ArrayList();
        ProfileList expected = new ProfileList(profiles);
        pl = new ProfileList("");
        assertEquals(expected, pl);
    }
    /**
     * Test profile list1 profile.
     */
    public void testProfileList1Profile() {
        TestUtil.TRACE(this);
        Profile p1 = new Profile("BaseLine", 1);
        ProfileList pl;
        List profiles = new ArrayList();
        profiles.add(p1);
        ProfileList expected = new ProfileList(profiles);
        pl = new ProfileList("BaseLine:1");
        assertEquals(expected, pl);
    }
    /**
     * Test profile list2 profile.
     */
    public void testProfileList2Profile() {
        TestUtil.TRACE(this);
        Profile p1 = new Profile("BaseLine", 1);
        Profile p2 = new Profile("X_EXAMPLE-COM_MyProfile", 2);
        ProfileList pl;
        List profiles = new ArrayList();
        profiles.add(p1);
        profiles.add(p2);
        ProfileList expected = new ProfileList(profiles);
        pl = new ProfileList("BaseLine:1, X_EXAMPLE-COM_MyProfile:2");
        assertEquals(expected, pl);
    }
}
