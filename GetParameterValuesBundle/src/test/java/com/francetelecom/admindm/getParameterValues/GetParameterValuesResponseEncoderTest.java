package com.francetelecom.admindm.getParameterValues;
import java.io.IOException;
import java.io.OutputStreamWriter;
import junit.framework.TestCase;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import org.xmlpull.v1.XmlSerializer;
import com.francetelecom.admindm.model.ParameterValueStruct;
import com.francetelecom.admindm.soap.Soap;

/**
 * The Class GetParameterValuesResponseEncoderTest.
 */
public class GetParameterValuesResponseEncoderTest extends TestCase {
    
    /**
     * Test encode.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void testEncodeWithNoParameterValues() throws IOException {
        TestUtil.TRACE(this);
        GetParameterValuesResponseEncoder encoder;
        GetParameterValuesResponse response;
        response = new GetParameterValuesResponse();
        encoder = new GetParameterValuesResponseEncoder();
        Document doc = Soap.getDocument(encoder.encode(response), "test");
        XmlSerializer serial = new KXmlSerializer();
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        serial.setOutput(writer);
        serial.setFeature(
                "http://xmlpull.org/v1/doc/features.html#indent-output", true);
        doc.write(serial);
        System.out.println("");
    }
    /**
     * Test encode.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void testEncodeWithParameterValues() throws IOException {
        TestUtil.TRACE(this);
        GetParameterValuesResponseEncoder encoder;
        GetParameterValuesResponse response;
        response = new GetParameterValuesResponse();
        ParameterValueStruct[] pList = new ParameterValueStruct[1];
        pList[0]= new ParameterValueStruct("test","valeur");
        response.setParameterList(pList);
        encoder = new GetParameterValuesResponseEncoder();
        Document doc = Soap.getDocument(encoder.encode(response), "test");
        XmlSerializer serial = new KXmlSerializer();
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        serial.setOutput(writer);
        serial.setFeature(
                "http://xmlpull.org/v1/doc/features.html#indent-output", true);
        doc.write(serial);
        System.out.println("");
    }
}
