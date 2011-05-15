package com.francetelecom.admindm.data;

import java.io.File;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.model.ParameterType;

public class PropertiesReaderTest extends TestCase {
    public void testParseLineComment() {
        TestUtil.TRACE(this);
        String [] tokens;
        String chaine ="# ceci=est un commentaire il ne doit pas etre lu";
        tokens = PropertiesReader.parseLine(chaine);
        assertNull(tokens);
    }
    public void testParseLineCommentWithoutSpace() {
        TestUtil.TRACE(this);
        String [] tokens;
        String chaine ="#ceci est un commentaire il ne doit pas etre lu";
        tokens = PropertiesReader.parseLine(chaine);
        assertNull(tokens);
    }
    public void testParseLine() {
        TestUtil.TRACE(this);
        String [] tokens;
        String chaine ="test=ceci est un champ il doit etre lu";
        tokens = PropertiesReader.parseLine(chaine);
        assertEquals(2,tokens.length);
        assertEquals("test",tokens[0]);
        assertEquals("ceci est un champ il doit etre lu",tokens[1]);        
    }
    public void testParseLineWithoutValue() {
        TestUtil.TRACE(this);
        String [] tokens;
        String chaine ="test=";
        tokens = PropertiesReader.parseLine(chaine);
        assertEquals(2,tokens.length);
        assertEquals("test",tokens[0]);
        assertEquals("",tokens[1]);        
    }
    
    public void testBug47616() throws Exception {
        TestUtil.COMMENT("Bug Codex ref: 47616");
        TestUtil.COMMENT("Lorsqu'une valeur de paramètre spécifiée dans le ");
        TestUtil.COMMENT("fichier usine.txt n'est pas valide (le paramètre ");
        TestUtil.COMMENT("n'existe pas dans le data model), les valeurs des ");
        TestUtil.COMMENT("paramètres suivants ne sont pas configurés.");
        TestUtil.TRACE(this);
        ParameterData data = new ParameterData();
        data.setRoot("root.");
        Parameter param1 = data.createOrRetrieveParameter("root.param1");
        param1.setType(ParameterType.STRING);
        Parameter param2 = data.createOrRetrieveParameter("root.param2");
        param2.setType(ParameterType.INT);
        Parameter param3 = data.createOrRetrieveParameter("root.param3");
        param3.setType(ParameterType.STRING);
        PropertiesReader reader = new PropertiesReader(data);
        File file = new File("./src/test/resources/badusine.txt");  
        reader.read(file);
        assertEquals("valeur1",param1.getValue());
        assertEquals(Integer.valueOf(56),param2.getValue());
        assertEquals("valeur3",param3.getValue());
    }

}
