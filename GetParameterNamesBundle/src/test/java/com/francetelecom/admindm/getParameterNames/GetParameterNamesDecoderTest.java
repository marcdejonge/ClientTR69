package com.francetelecom.admindm.getParameterNames;

import java.io.File;
import java.io.FileReader;
import junit.framework.TestCase;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import com.francetelecom.admindm.soap.Fault;

/**
 * The Class GetParameterNamesDecoderTest.
 */
public final class GetParameterNamesDecoderTest extends TestCase {
    
    /**
     * Test decode.
     */
    public void testDecode() throws Exception {
        GetParameterNamesDecoder decoder = new GetParameterNamesDecoder();
        File file = new File("./src/test/resources/getParameterNames.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);

        try {
            GetParameterNames param = (GetParameterNames) decoder.decode(doc.getRootElement());
            assertEquals("ParameterPath", param.getParameterPath());
            assertEquals(true, param.isNextLevel());
        } catch (Fault e) {
            fail("Should not throw a Fault");
        }
    }
}
