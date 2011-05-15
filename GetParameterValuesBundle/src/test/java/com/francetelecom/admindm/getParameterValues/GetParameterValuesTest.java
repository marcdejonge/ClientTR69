package com.francetelecom.admindm.getParameterValues;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterAttributeStruct;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class GetParameterValuesTest.
 */
public class GetParameterValuesTest extends TestCase {
    /**
     * Test perform with null.
     * @throws Exception the exception
     */
    public void testPerformWithNull() throws Exception {
        TestUtil.TRACE(this);
        GetParameterValues getParameterValues = new GetParameterValues();
        MockSession session = new MockSession();
        try {
            getParameterValues.perform(session);
            fail("should throw a fault.");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
        }
    }
    /**
     * Test perform with over max array size.
     * @throws Exception the exception
     */
    public void testPerformWithOverMaxArraySize() throws Exception {
        TestUtil.TRACE(this);
        GetParameterValues getParameterValues = new GetParameterValues();
        String[] test = new String[20000];
        getParameterValues.setParameterNames(test);
        MockSession session = new MockSession();
        try {
            getParameterValues.perform(session);
            fail("should throw a fault.");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
        }
    }
    /**
     * Test perform with bad parameter.
     * @throws Exception the exception
     */
    public void testPerformWithBadParameter() throws Exception {
        TestUtil.TRACE(this);
        GetParameterValues getParameterValues = new GetParameterValues();
        String[] test = new String[1];
        test[0] = "test";
        getParameterValues.setParameterNames(test);
        MockSession session = new MockSession();
        try {
            getParameterValues.perform(session);
            fail("should throw a fault.");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
        }
    }
    /**
     * Test perform nominal case.
     * @throws Exception the exception
     */
    public void testPerformNominalCaseDirectName() throws Exception {
        TestUtil.TRACE(this);
        GetParameterValues getParameterValues = new GetParameterValues();
        String[] test = new String[1];
        test[0] = "InternetGateway.test";              
        getParameterValues.setParameterNames(test);
        MockSession session = new MockSession();
        IParameterData data = session.getParameterData();
        Parameter param1 = data.createOrRetrieveParameter(test[0]);
        param1.setType(ParameterType.STRING);
        param1.setValue(null);
        try {
            getParameterValues.perform(session);
            fail("should throw a fault param not initailized");
        } catch (Fault e) {
            System.out.println("Ok");
          }
        param1.setValue("test");
        try {            
            getParameterValues.perform(session);
            GetParameterValuesResponse response;
            response = (GetParameterValuesResponse) session.soapResponse;
            assertEquals(1,response.getParameterList().length);
            assertEquals(test[0],response.getParameterList()[0].getName());
            assertEquals("test",response.getParameterList()[0].getValue());
        } catch (Fault e) {
            fail("should not throw a fault.");
        }
        
    }
    
    /**
     * Test perform nominal case.
     * @throws Exception the exception
     */
    public void testPerformNominalCaseDirectNameButHiddenParameter()
        throws Exception {
        TestUtil.TRACE(this);
        GetParameterValues getParameterValues = new GetParameterValues();
        String[] test = new String[1];
        test[0] = "InternetGateway.Password";              
        getParameterValues.setParameterNames(test);
        MockSession session = new MockSession();
        IParameterData data = session.getParameterData();
        Parameter param1 = data.createOrRetrieveParameter(test[0]);
        param1.setType(ParameterType.STRING);
        param1.setValue("toto");
        param1.setHidden(true);        
        try {            
            getParameterValues.perform(session);
            GetParameterValuesResponse response;
            response = (GetParameterValuesResponse) session.soapResponse;
            assertEquals(1,response.getParameterList().length);
            assertEquals(test[0],response.getParameterList()[0].getName());
            assertEquals("",response.getParameterList()[0].getValue());
        } catch (Fault e) {
            fail("should not throw a fault.");
        }
        
    }
    
    /**
     * Test perform nominal case.
     * @throws Exception the exception
     */
    public void testPerformNominalCaseIndirectName() throws Exception {
        TestUtil.TRACE(this);
        GetParameterValues getParameterValues = new GetParameterValues();
        String[] test = new String[1];
        test[0] = "InternetGateway.";
        getParameterValues.setParameterNames(test);
        MockSession session = new MockSession();
        IParameterData data = session.getParameterData();
        Parameter param1 = data.createOrRetrieveParameter(test[0]);
        param1.setType(ParameterType.ANY);
        Parameter param2 = data.createOrRetrieveParameter("InternetGateway.test");
        param2.setType(ParameterType.STRING);
        param2.setValue("value2");
        Parameter param3 = data.createOrRetrieveParameter("InternetGateway.test2");
        param3.setType(ParameterType.STRING);
        param3.setValue("value3");
        Parameter param4 = data.createOrRetrieveParameter("InternetGateway.test3.");
        param4.setType(ParameterType.ANY);
        param4.setValue(null);
        Parameter param5 = data.createOrRetrieveParameter("InternetGateway.test3.aa");
        param5.setType(ParameterType.INT);
        param5.setValue(Integer.valueOf(45));
        //---------
        // verification des resultats qui doivent juste representer les parametres 
        try {
            getParameterValues.perform(session);
            GetParameterValuesResponse response;
            response = (GetParameterValuesResponse) session.soapResponse;
            assertEquals(3,response.getParameterList().length);    
            assertEquals("InternetGateway.test",response.getParameterList()[0].getName());
            assertEquals("value2",response.getParameterList()[0].getValue());
            assertEquals("InternetGateway.test2",response.getParameterList()[1].getName());
            assertEquals("value3",response.getParameterList()[1].getValue());
            assertEquals("InternetGateway.test3.aa",response.getParameterList()[2].getName());
            assertEquals("45",response.getParameterList()[2].getValue());
            System.out.println("OK");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            fail("should not throw a fault.");
        }
    }
}
