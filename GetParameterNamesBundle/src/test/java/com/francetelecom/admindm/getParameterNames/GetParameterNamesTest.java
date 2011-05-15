package com.francetelecom.admindm.getParameterNames;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.model.ParameterInfoStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class GetParameterNamesTest.
 */
public class GetParameterNamesTest extends TestCase {
    /**
     * Test is selectable.
     */
    public void testIsSelectableWithNextLevelFalse() {
        TestUtil.TRACE(this);
        String initial;
        String path="InternetGatewayDevice.LANDevice.1.Hosts.";
        
        initial="InternetGatewayDevice.LANDevice.1.Hosts.";
        assertTrue(GetParameterNames.isSelectable(initial, path, false));
        
        initial="InternetGatewayDevice.LANDevice.1.Hosts.HostNumberOfEntries";
        assertTrue(GetParameterNames.isSelectable(initial, path, false));
        
        initial="InternetGatewayDevice.LANDevice.1.Hosts.";
        assertTrue(GetParameterNames.isSelectable(initial, path, false));
        initial="InternetGatewayDevice.LANDevice.1.Hosts.1.";
        assertTrue(GetParameterNames.isSelectable(initial, path, false));
        initial="InternetGatewayDevice.LANDevice.1.Hosts.1.IPAddress";
        assertTrue(GetParameterNames.isSelectable(initial, path, false));        
    }
    
    /**
     * Test is selectable.
     */
    public void testIsSelectableWithNextLevel2True() {
        TestUtil.TRACE(this);
        String initial;
        String path="InternetGatewayDevice.LANDevice.1.Hosts.Host.1.";
        
        initial="InternetGatewayDevice.LANDevice.1.Hosts.Host.";
        assertTrue(GetParameterNames.isSelectable(path, initial,  true));
                
    }
 
    
    
    public void testIsSelectableWithNextLevelTrue() {
        TestUtil.TRACE(this);
        String initial;
        String path="InternetGatewayDevice.LANDevice.1.Hosts.";
        
        initial="InternetGatewayDevice.LANDevice.1.Hosts.";
        assertFalse(GetParameterNames.isSelectable(initial, path, true));
        
        initial="InternetGatewayDevice.LANDevice.1.Hosts.HostNumberOfEntries";
        assertTrue(GetParameterNames.isSelectable(initial, path, true));
              
        initial="InternetGatewayDevice.LANDevice.1.Hosts.1.";
        assertTrue(GetParameterNames.isSelectable(initial, path, true));
        initial="InternetGatewayDevice.LANDevice.1.Hosts.1.IPAddress";
        assertFalse(GetParameterNames.isSelectable(initial, path, true));        
    }
    
    public void testIsSelectableWithEmpty() {
        TestUtil.TRACE(this);
        String initial;
        String path="";
        
        initial="InternetGatewayDevice.LANDevice.1.Hosts.";
        assertTrue(GetParameterNames.isSelectable(initial, path, false));
        assertFalse(GetParameterNames.isSelectable(initial, path, true));
                
    }
    /**
     * Test throws fault when not found.
     */
    public void testThrowsFaultWhenNotFound() {
        TestUtil.TRACE(this);
        String initial;
        initial = "InternetGatewayDeviceTartanpion";
        GetParameterNames gpn = new GetParameterNames();
        gpn.setParameterPath(initial);
        gpn.setNextLevel(false);
        try {
            gpn.perform(new MockSession(null));
            fail("Should throws a fault");
        } catch (Fault e) {
            assertTrue("Bad fault code",
                    e.getFaultcode() == FaultUtil.FAULT_9005);
            System.out.println("OK");
        }
    }
    /**
     * Test get array of parameter info.
     * @throws Exception the exception
     */
    public void testGetArrayOfParameterInfo() throws Exception {
        TestUtil.TRACE(this);        
        String path="InternetGatewayDevice.LANDevice.1.Hosts.";
        ParameterData data = new ParameterData();
        data.createOrRetrieveParameter("InternetGateway.tartempion");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.HostNumberOfEntries");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.Host.");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.Host.1.");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.Host.1.IPAddress");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.Host.1.AddressSource");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.Host.1.LeaseTimeRemaining");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.Host.1.MACAddress");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.Host.1.HostName");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.Host.1.InterfaceType");
        data.createOrRetrieveParameter("InternetGatewayDevice.LANDevice.1.Hosts.Host.1.Active");
        
        ParameterInfoStruct[] result;
        result = GetParameterNames.getArrayOfParameterInfo(path, false, data);
        System.out.println("result with false");
        for (int i= 0;i< result.length;i++) {
            System.out.println(result[i].getParameterName());
        }
        assertEquals( 11 ,result.length);
        
        result = GetParameterNames.getArrayOfParameterInfo(path, true, data);
        System.out.println("result with true");
        for (int i= 0;i< result.length;i++) {
            System.out.println(result[i].getParameterName());
        }
        assertEquals(2, result.length );
        
    }
    /**
     * Test throws fault when not found.
     */
    public void testNoFaultWhenFound() {
        TestUtil.TRACE(this);
        String initial;
        initial = "InternetGatewayDevice.Tartampion";
        GetParameterNames gpn = new GetParameterNames();
        gpn.setParameterPath(initial);
        gpn.setNextLevel(false);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        try {
            data.createOrRetrieveParameter(initial);
            data.createOrRetrieveParameter(initial + ".");
            System.out.println(data.getParameter(initial).getName());
            gpn.perform(session);
            GetParameterNamesResponse response ; 
            response = (GetParameterNamesResponse) session.methodResponse;
            assertTrue(response.getParameterList().length==2);            
        } catch (Fault e) {
            e.printStackTrace();
            fail("Should not throws a fault" + e.getFaultstring());
        }
    }
}
