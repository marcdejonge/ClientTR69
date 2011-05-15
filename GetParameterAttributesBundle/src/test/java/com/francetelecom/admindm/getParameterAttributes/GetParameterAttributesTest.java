package com.francetelecom.admindm.getParameterAttributes;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterAttributeStruct;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class GetParameterAttributesTest.
 */
public class GetParameterAttributesTest extends TestCase {
    /**
     * Test perform parameter names with null.
     */
    public void testPerformParameterNamesWithNull() {
        TestUtil.TRACE(this);
        GetParameterAttributes gpa = new GetParameterAttributes();
        gpa.setParameterNames(null);
    }
    /**
     * Test perform with unknow parameter.
     */
    public void testPerformWithUnknowParameter() {
        TestUtil.TRACE(this);
        GetParameterAttributes gpa = new GetParameterAttributes();
        String[] names = { "test", "tutu" };
        gpa.setParameterNames(names);
        MockSession session = new MockSession(null);
        try {
            gpa.perform(session);
            fail("Should throw a Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
        }
    }
    /**
     * Test perform with known parameter.
     * @throws Exception the exception
     */
    public void testPerformWithKnownParameter() throws Exception {
        TestUtil.TRACE(this);
        GetParameterAttributes gpa = new GetParameterAttributes();
        String[] names = { "test", "tutu" };
        gpa.setParameterNames(names);
        MockSession session = new MockSession(null);
        Parameter t1 = session.getParameterData().createOrRetrieveParameter(
                "test");
        String[] accessListEmpty = {};
        String[] accessListNotEmpty = { "Subscriber" };
        t1.setAccessList(accessListEmpty);
        t1.setNotification(0);
        Parameter t2 = session.getParameterData().createOrRetrieveParameter(
                "tutu");
        t2.setAccessList(accessListNotEmpty);
        t2.setNotification(1);
        gpa.perform(session);
        GetParameterAttributesResponse response;
        response = (GetParameterAttributesResponse) session.methodResponse;
        assertTrue(response.getParameterList().length == 2);
        ParameterAttributeStruct pas;
        pas = response.getParameterList()[0];
        assertEquals(pas.getAccessList().length, 0);
        assertEquals(pas.getNotification(), 0);
        pas = response.getParameterList()[1];
        assertEquals(pas.getAccessList()[0], "Subscriber");
        assertEquals(pas.getNotification(), 1);
    }
}
