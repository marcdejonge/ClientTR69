package com.francetelecom.admindm.data;

import org.ow2.odis.test.TestUtil;
import junit.framework.TestCase;
import com.francetelecom.admindm.api.FileUtil;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterData;

/**
 * The Class StartTest.
 */
public class StartTest extends TestCase {
    
    /**
     * Test start without initial data.
     */
    public void testStartWithoutInitialData() throws Exception {
        TestUtil.TRACE(this);
        ParameterData data = new ParameterData();
        Start start = new Start();
        FileUtil.CONFIG_FILE = "./src/test/resources/CSV_withoutdata.txt";
        start.setData(data);
        start.readStructComplement();
        start.putDefaultParameter();        
        Object[] events = data.getEventsArray();
        System.out.println(events.length);
        for (int i=0;i<events.length;i++){
            System.out.println(((EventStruct)events[i]).getEventCode());
        }
        assertEquals(2, events.length);
        assertEquals("0 BOOTSTRAP",((EventStruct)events[0]).getEventCode());
        assertEquals("1 BOOT",((EventStruct)events[1]).getEventCode());
        Object[] params = data.getParametersArray();
        System.out.print(data.toString());
        System.out.println(params.length);
        assertEquals(42,params.length);
        Parameter param = data.getParameter("InternetGatewayDevice.ManagementServer.URL");
        assertEquals("http://localhost:8180/ACS/CWMPServlet", param.getValue());
    }
    
    /**
     * Test start with initial data.
     * TestUtil.TRACE(this);
     */
    public void testStartWithInitialData() throws Exception {
        ParameterData data = new ParameterData();
        Start start = new Start();
        FileUtil.CONFIG_FILE = "./src/test/resources/CSV.txt";
        start.setData(data);
        start.readStructComplement();
        start.putDefaultParameter();               
        Object[] events = data.getEventsArray();
        System.out.println(events.length);
        for (int i=0;i<events.length;i++){
            System.out.println(((EventStruct)events[i]).getEventCode());
        }
        assertEquals(1, events.length);
        assertEquals("1 BOOT",((EventStruct)events[0]).getEventCode());
        Object[] params = data.getParametersArray();
        for (int i=0;i<params.length;i++){
            System.out.println(((Parameter)params[i]).getName());
        }
        System.out.println(params.length);
        assertEquals(42,params.length);        
    }
}
