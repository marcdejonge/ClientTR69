package com.francetelecom.admindm.data;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
/**
 * The Class DeviceSummaryTest.
 */
public class DeviceSummaryTest extends TestCase {
    
    protected void setUp() throws Exception {    
        super.setUp();
    }
    /** The device summary. */
    private String deviceSummary;
    /**
     * Test1.
     */
    public void test1() {
        TestUtil.COMMENT("Simple device supporting ABCService");
        TestUtil.TRACE(this);
        deviceSummary = "Device:1.0[](Baseline:1), ABCService:1.0[1](Baseline:1)";
        DeviceSummary parser = new DeviceSummary(deviceSummary);
        ObjectVersion version = new ObjectVersion(1, 0);
        Profile profile = new Profile("Baseline", 1);
        List profiles = new ArrayList();
        profiles.add(profile);
        ProfileList lsProfiles = new ProfileList(profiles);
        RootObject root = new RootObject("Device", version, lsProfiles);
        ServiceObject service;
        service = new ServiceObject("ABCService", version, 1, lsProfiles);
        assertEquals(root, parser.getRootObject());
        List lsServices = new ArrayList();
        lsServices.add(service);
        ServiceObjectList serviceObjectList = new ServiceObjectList(lsServices);
        assertEquals(serviceObjectList, parser.getServiceObjectList());
    }
    /**
     * Test2.
     */
    public void test2() {
        TestUtil.COMMENT("Device supporting both ABCService ");
        TestUtil.COMMENT("and XYZService Service");
        TestUtil.TRACE(this);
        deviceSummary = "Device:1.0[](Baseline:1), ABCService:1.0[1](Baseline:1), XYZService:1.0[1](Baseline:1)";
        DeviceSummary parser = new DeviceSummary(deviceSummary);
        ObjectVersion version = new ObjectVersion(1, 0);
        Profile profile = new Profile("Baseline", 1);
        List profiles = new ArrayList();
        profiles.add(profile);
        ProfileList lsProfiles = new ProfileList(profiles);
        RootObject root = new RootObject("Device", version, lsProfiles);
        ServiceObject service1;
        ServiceObject service2;
        service1 = new ServiceObject("ABCService", version, 1, lsProfiles);
        service2 = new ServiceObject("XYZService", version, 1, lsProfiles);
        assertEquals(root, parser.getRootObject());
        List lsServices = new ArrayList();
        lsServices.add(service1);
        lsServices.add(service2);
        ServiceObjectList serviceObjectList = new ServiceObjectList(lsServices);
        assertEquals(serviceObjectList, parser.getServiceObjectList());
    }
    /**
     * Test3.
     */
    public void test3() {
        TestUtil.COMMENT("InternetGatewayDevice that also supports ");
        TestUtil.COMMENT("two Services");
        TestUtil.TRACE(this);
        deviceSummary = "InternetGatewayDevice:1.0[](Baseline:1), ABCService:1.0[1](Baseline:1),XYZService:1.0[1](Baseline:1)";
        DeviceSummary parser = new DeviceSummary(deviceSummary);
        ObjectVersion version = new ObjectVersion(1, 0);
        Profile profile = new Profile("Baseline", 1);
        List profiles = new ArrayList();
        profiles.add(profile);
        ProfileList lsProfiles = new ProfileList(profiles);
        RootObject root = new RootObject("InternetGatewayDevice", version, lsProfiles);
        ServiceObject service1;
        ServiceObject service2;
        service1 = new ServiceObject("ABCService", version, 1, lsProfiles);
        service2 = new ServiceObject("XYZService", version, 1, lsProfiles);
        assertEquals(root, parser.getRootObject());
        List lsServices = new ArrayList();
        lsServices.add(service1);
        lsServices.add(service2);
        ServiceObjectList serviceObjectList = new ServiceObjectList(lsServices);
        assertEquals(serviceObjectList, parser.getServiceObjectList());
    }
    /**
     * Test4.
     */
    public void test4() {
        TestUtil.COMMENT("Device supporting a service and proxying for 2 ");
        TestUtil.COMMENT("devices Supporting the functionality of ");
        TestUtil.COMMENT("the XYZService");
        TestUtil.TRACE(this);
        deviceSummary = "Device:1.0[](Baseline:1), ABCService:2.17[1](Baseline:1), XYZService:1.2[1](Baseline:2), XYZService:1.2[2](Baseline:2, AnotherProfile:3)";
        DeviceSummary parser = new DeviceSummary(deviceSummary);
        System.out.println(deviceSummary);
        System.out.println(parser.toString());
        assertEquals(deviceSummary, parser.toString());
           
    }
    /**
     * Test5.
     */
    public void test5() {
        TestUtil.COMMENT("Internet Gateway Device also serving as ");
        TestUtil.COMMENT("a management proxy for three devices supporting ");
        TestUtil.COMMENT("the functionality of the ABCService Service Object");
        TestUtil.TRACE(this);
        // :
        deviceSummary = "InternetGatewayDevice:1.0[](Baseline:1), ABCService:1.0[1](Baseline:1), ABCService:1.0[2](Baseline:1), ABCService:1.0[3](Baseline:1, AnotherProfile:1)";
        DeviceSummary parser = new DeviceSummary(deviceSummary);
        System.out.println(deviceSummary);
        System.out.println(parser.toString());
        assertEquals(deviceSummary, parser.toString());

    }
    /**
     * Test6.
     */
    public void test6() {
        TestUtil.COMMENT("Version 1.0 Internet Gateway Device ");
        TestUtil.COMMENT("with no additional service objects supported");
        TestUtil.TRACE(this);
        // :
        deviceSummary = "InternetGatewayDevice:1.0[](Baseline:1)";
        DeviceSummary parser = new DeviceSummary(deviceSummary);
        System.out.println(deviceSummary);
        System.out.println(parser.toString());
        assertEquals(deviceSummary, parser.toString());

    }
    /**
     * Test7.
     */
    public void test7() {
        TestUtil.COMMENT("Device supporting the ability to proxy ");
        TestUtil.COMMENT("for devices supporting the functionality ");
        TestUtil.COMMENT("of the ABCService Service Object, but with ");
        TestUtil.COMMENT("no current instances of that object");
        TestUtil.TRACE(this);
        deviceSummary = "Device:1.0[](Baseline:1), ABCService:2.17[]()";
        DeviceSummary parser = new DeviceSummary(deviceSummary);
        System.out.println(deviceSummary);
        System.out.println(parser.toString());
        assertEquals(deviceSummary, parser.toString());

    }
    /**
     * Test8.
     */
    public void test8() {
        TestUtil.COMMENT("Device supporting the ABCService Service Object ");
        TestUtil.COMMENT("with the baseline and a vendor-specific profile");
        TestUtil.TRACE(this);
        // :
        deviceSummary = "Device:1.0[](Baseline:1), ABCService:2.17[1](Baseline:1, X_EXAMPLE-COM_MyProfile:2)";
        DeviceSummary parser = new DeviceSummary(deviceSummary);
        System.out.println(deviceSummary);
        System.out.println(parser.toString());
        assertEquals(deviceSummary, parser.toString());

    }
    /**
     * Test9.
     */
    public void test9() {
        TestUtil.COMMENT("Data Model Template for TR-069-Enabled ");
        TestUtil.COMMENT("Devices TR-106 Amendment 2 November 2008 © ");
        TestUtil.COMMENT("The Broadband Forum. All rights reserved. 46 ");
        TestUtil.COMMENT("Device supporting the ABCService Service Object");
        TestUtil.COMMENT(", but with no profiles");
        TestUtil.TRACE(this);
        // :
        deviceSummary = "Device:1.0[](Baseline:1), ABCService:2.17[1]()";
        DeviceSummary parser = new DeviceSummary(deviceSummary);
        System.out.println(deviceSummary);
        System.out.println(parser.toString());
        assertEquals(deviceSummary, parser.toString());

    }
}
