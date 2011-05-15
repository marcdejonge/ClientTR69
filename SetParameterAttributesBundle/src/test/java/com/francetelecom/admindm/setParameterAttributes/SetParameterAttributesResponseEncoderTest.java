package com.francetelecom.admindm.setParameterAttributes;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
public class SetParameterAttributesResponseEncoderTest extends TestCase {
    /** The encoder. */
    private SetParameterAttributesResponseEncoder encoder;
    /**
     * Test encode nominal case.
     */
    public void testEncodeNominalCase() {
        TestUtil.TRACE(this);
        encoder = new SetParameterAttributesResponseEncoder();
        SetParameterAttributesResponse param = new SetParameterAttributesResponse();
        encoder.encode(param);
    }
}
