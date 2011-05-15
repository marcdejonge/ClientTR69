package com.francetelecom.admindm.getRPCMethods;
import java.io.OutputStreamWriter;
import junit.framework.TestCase;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import org.xmlpull.v1.XmlSerializer;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class GetRPCMethodsResponseEncoderTest.
 */
public class GetRPCMethodsResponseEncoderTest extends TestCase {
    private GetRPCMethodsResponseEncoder encoder;
    /**
     * Test encode with an empty list.
     */
    public void testEncodeWithNull() throws Exception {
        TestUtil.TRACE(this);
        GetRPCMethodsResponse response = new GetRPCMethodsResponse(null);
        encoder = new GetRPCMethodsResponseEncoder();
        Document doc = Soap.getDocument(encoder.encode(response), "test");
        XmlSerializer serial = new KXmlSerializer();
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        serial.setOutput(writer);
        serial
        .setFeature(
                "http://xmlpull.org/v1/doc/features.html#indent-output",
                true);
        doc.write(serial);
        System.out.println("");
    }
    /**
     * Test encode.
     */
    public void testEncode() throws Exception {
        TestUtil.TRACE(this);
        GetRPCMethodsResponse response;
        RPCMethodMngService mng = new MockRPCMethodsManagement();
        response = new GetRPCMethodsResponse(mng);
        encoder = new GetRPCMethodsResponseEncoder();
        Document doc = Soap.getDocument(encoder.encode(response), "test");
        XmlSerializer serial = new KXmlSerializer();
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        serial.setOutput(writer);
        serial
        .setFeature(
                "http://xmlpull.org/v1/doc/features.html#indent-output",
                true);
        doc.write(serial);
        System.out.println("");
    }
}
