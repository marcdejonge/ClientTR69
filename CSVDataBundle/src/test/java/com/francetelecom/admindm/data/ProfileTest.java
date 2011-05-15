package com.francetelecom.admindm.data;

import org.ow2.odis.test.TestUtil;
import junit.framework.TestCase;

public class ProfileTest extends TestCase {
    public void testEqualsObject() {
        TestUtil.TRACE(this);
        Profile profile = new Profile("test",4);
        Profile profile2 = new Profile("test",4);
        Profile profile2bis = new Profile("test:4");
        Profile profile3 = new Profile("test2",4);
        Profile profile4 = new Profile("test",5);
        assertEquals(profile, profile2);
        assertEquals(profile, profile2bis);
        assertFalse(profile.equals(profile3));
        assertFalse(profile.equals(profile4));
    }
    public void testNotFailedEvenWithBadString() {
        TestUtil.TRACE(this);
        new Profile("test");
        new Profile("test:5");
        new Profile("test:5.1");
        new Profile("test:5,1");
    }
}
