package com.francetelecom.admindm.setParameterValues;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.soap.Fault;

/**
 * The Class SetParameterValuesResponseEncoderTest.
 */
public final class SetParameterValuesResponseEncoderTest extends TestCase {
    /** The encoder. */
    private SetParameterValuesResponseEncoder encoder;
    /**
     * Test encode with null.
     */
    public void testEncodeWithNull() {
        TestUtil.COMMENT("The aim of this test is to test the failure");
        TestUtil.COMMENT("Have several Fault exception "
                + "is the normal behavior.");
        TestUtil.TRACE(this);
        encoder = new SetParameterValuesResponseEncoder();
        try {
            encoder.encode(null);
            fail("Should not encode without Fault Exception");
        } catch (Fault ex) {
            System.out.println("OK");
        }
    }

    /**
     * Test encode nominal case.
     */
    public void testEncodeNominalCase() {
        TestUtil.TRACE(this);
        encoder = new SetParameterValuesResponseEncoder();
        SetParameterValuesResponse param = new SetParameterValuesResponse(2);
        try {
            encoder.encode(param);
        } catch (Fault e) {
            fail("Should not encode without Fault Exception");
        }
    }
}
