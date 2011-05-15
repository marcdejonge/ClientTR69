package com.francetelecom.admindm.getParameterNames;

import java.io.IOException;
import java.io.OutputStreamWriter;
import junit.framework.TestCase;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import org.xmlpull.v1.XmlSerializer;
import com.francetelecom.admindm.model.ParameterInfoStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.Soap;

public class GetParameterNamesResponseEncoderTest extends TestCase {
    /** The encoder. */
    private GetParameterNamesResponseEncoder encoder;
    /**
     * Test encode with null.
     */
    public void testEncodeWithNull() {
        TestUtil.COMMENT("The aim of this test is to test the failure");
        TestUtil.COMMENT("Have several Fault exception "
                + "is the normal behavior.");
        TestUtil.TRACE(this);
        encoder = new GetParameterNamesResponseEncoder();
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
    public void testEncodeNominalCase() throws IOException{
        TestUtil.TRACE(this);
        encoder = new GetParameterNamesResponseEncoder();
        ParameterInfoStruct[] pis = new ParameterInfoStruct[1];
        pis[0] = new ParameterInfoStruct("toto",true); 
        GetParameterNamesResponse param = new GetParameterNamesResponse(pis);
        try {            
            Document doc = Soap.getDocument(encoder.encode(param), "test");
            XmlSerializer serial = new KXmlSerializer();
            OutputStreamWriter writer = new OutputStreamWriter(System.out);
            serial.setOutput(writer);
            serial
            .setFeature(
                    "http://xmlpull.org/v1/doc/features.html#indent-output",
                    true);
            doc.write(serial);
            System.out.println("");

        } catch (Fault e) {
            fail("Should not encode without Fault Exception");
        }
    }
}
