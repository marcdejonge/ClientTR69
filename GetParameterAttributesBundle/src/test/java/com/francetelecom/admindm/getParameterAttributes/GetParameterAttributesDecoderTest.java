package com.francetelecom.admindm.getParameterAttributes;
import java.io.File;
import java.io.FileReader;
import junit.framework.TestCase;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class GetParameterAttributesDecoderTest.
 */
public class GetParameterAttributesDecoderTest extends TestCase {
    /**
     * Test decode.
     * @throws Exception the exception
     */
    public void testDecode() throws Exception {
        TestUtil.TRACE(this);
        RPCDecoder decoder = new GetParameterAttributesDecoder();
        
        File file = new File("./src/test/resources/GetParameterAttributes.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        String [] tab = {"string","string2"};
        try {
            GetParameterAttributes method;
            method = (GetParameterAttributes)decoder.decode(doc.getRootElement());
            assertEquals(tab.length,method.getParameterNames().length);
            for (int i= 0;i<tab.length;i++){
                assertEquals(tab[i],method.getParameterNames()[i]);
            }
        } catch (Fault e) {
            fail("Should not throw a Fault");
        }
    }
}
