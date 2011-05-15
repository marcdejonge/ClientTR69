package com.francetelecom.admindm.data;
import java.util.ArrayList;
import java.util.List;
import org.ow2.odis.test.TestUtil;
import junit.framework.TestCase;
/**
 * The Class ServiceObjectTest.
 */
public class ServiceObjectTest extends TestCase {
    /**
     * Test service object string.
     */
    public void testServiceObjectString() {
        TestUtil.COMMENT("ABCService with one profile");
        TestUtil.TRACE(this);
        String chaine = "ABCService:1.0[1](Baseline:1)";
        ServiceObject parser = new ServiceObject(chaine);
        ObjectVersion version = new ObjectVersion(1, 0);
        Profile profile = new Profile("Baseline", 1);
        List profiles = new ArrayList();
        profiles.add(profile);
        ProfileList lsProfiles = new ProfileList(profiles);
        ServiceObject service;
        service = new ServiceObject("ABCService", version, 1, lsProfiles);
        assertEquals(service, parser);
    }
}
