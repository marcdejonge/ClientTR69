package com.francetelecom.admindm.data;

import org.ow2.odis.test.TestUtil;
import junit.framework.TestCase;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;

public class CSVReaderTest extends TestCase  {
    public void testParseLineBoolean() {
        TestUtil.TRACE(this);
        String line;
        Parameter param;        
        try {
            line ="InternetGatewayDevice.ManagementServer.PeriodicInformEnable;" +
            "3;0;1;0;0;0;0;Subscriber;true;0;0;";
            param = CSVReader.parseLine(line);
            assertTrue(param.getValue() == Boolean.TRUE);
            
            line ="InternetGatewayDevice.ManagementServer.PeriodicInformEnable;" +
            "3;0;1;0;0;0;0;Subscriber;TRUE;0;0;";
            param = CSVReader.parseLine(line);
            assertTrue(param.getValue() == Boolean.TRUE);
            
            line ="InternetGatewayDevice.ManagementServer.PeriodicInformEnable;" +
            "3;0;1;0;0;0;0;Subscriber;false;0;0;";            
            param = CSVReader.parseLine(line);           
            assertTrue(param.getValue() == Boolean.FALSE);
            
            line ="InternetGatewayDevice.ManagementServer.PeriodicInformEnable;" +
            "3;0;1;0;0;0;0;Subscriber;FALSE;0;0;";
            param = CSVReader.parseLine(line);
            assertTrue(param.getValue() == Boolean.FALSE);
            
            
            line = "InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1" +
            		".WANIPConnection.1.Name;STRING;2;1;0;2;0;0;;;0;0";
            param = CSVReader.parseLine(line);
            assertTrue(param.isWritable());
            
        } catch (Fault e) {
            fail("Should not throw a Fault"+e.getFaultstring());
        }        
    }
    public void test2()  {
        TestUtil.TRACE(this);
        String line;
        Parameter param;          
        line = "InternetGatewayDevice.ManagementServer.PeriodicInformTime;" +
        		"4;0;1;0;0;0;0;Subscriber;;0;0;";
        try {
            param = CSVReader.parseLine(line);
            System.out.println(param.getValue());
            Long l = (Long)param.getValue();
            assertTrue(l==null);            
        } catch (Fault e) {
            fail("Should not throw a Fault"+e.getFaultstring());
        }
        
    }
}
