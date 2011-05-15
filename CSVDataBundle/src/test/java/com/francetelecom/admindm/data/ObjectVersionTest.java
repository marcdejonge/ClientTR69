package com.francetelecom.admindm.data;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;

/**
 * The Class ObjectVersionTest.
 */
public class ObjectVersionTest extends TestCase {
    
    /**
     * Test object version string.
     */
    public void testObjectVersionString() {
        TestUtil.TRACE(this);
        ObjectVersion expected = new ObjectVersion(2, 1);
        ObjectVersion object = new ObjectVersion("2.1");
        assertEquals(expected, object);
    }
}
