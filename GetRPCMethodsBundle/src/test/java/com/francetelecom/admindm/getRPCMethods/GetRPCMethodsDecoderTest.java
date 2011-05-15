package com.francetelecom.admindm.getRPCMethods;
import java.io.File;
import java.io.FileReader;
import junit.framework.TestCase;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class GetRPCMethodsDecoderTest.
 */
public class GetRPCMethodsDecoderTest extends TestCase {
    /**
     * Test decode.
     * @throws Exception the exception
     */
    public void testDecode() throws Exception {
        TestUtil.TRACE(this);
        RPCDecoder decoder = new GetRPCMethodsDecoder(null);
        
        File file = new File("./src/test/resources/getRPCMethod.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);

        try {
            GetRPCMethods param = null;
            param = (GetRPCMethods) decoder.decode(doc.getRootElement());
            assertNotNull(param);
        } catch (Fault e) {
            fail("Should not throw a Fault");
        }
    }
}
