package com.francetelecom.admindm.setParameterAttributes;

import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.Parameter;

/**
 * The Class SaveTest.
 */
public class SaveTest extends TestCase {
    
    /**
     * Test restore.
     */
    public void testRestore() {
        TestUtil.TRACE(this);
        Parameter param = new Parameter();
        String[] accessList = {"Subscriber"};
        String[] accessList2 = {"Subscriber", "test"};
        param.setAccessList(accessList);
        param.setNotification(1);
        
        Save save = new Save(param);
        param.setNotification(4);
        param.setAccessList(accessList2);
        save.restore();
        assertEquals(param.getNotification(), 1);
        assertEquals(param.getAccessList(), accessList);
    }
}
