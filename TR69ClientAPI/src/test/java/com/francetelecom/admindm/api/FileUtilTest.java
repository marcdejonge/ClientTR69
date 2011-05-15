/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : TR69ClientAPI
 *
 * Copyright © 2011 France Telecom
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */ 
package com.francetelecom.admindm.api;

import org.ow2.odis.test.TestUtil;
import junit.framework.TestCase;

public class FileUtilTest extends TestCase {
    public void testGetUnknownFile() {
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE ="./src/test/resources/CSV.txt";
        assertNull(FileUtil.getFileFromShortName("UNKNOWN"));
    }
    public void testGetFile() {
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE ="./src/test/resources/CSV.txt";
        assertNotNull(FileUtil.getFileFromShortName(FileUtil.LOG));
    }
    
    public void testParseLineComment() {
        TestUtil.TRACE(this);
        String [] tokens;
        String chaine ="# ceci=est un commentaire il ne doit pas etre lu";
        tokens = FileUtil.parseLine(chaine);
        assertNull(tokens);
    }
    public void testParseLineCommentWithoutSpace() {
        TestUtil.TRACE(this);
        String [] tokens;
        String chaine ="#ceci est un commentaire il ne doit pas etre lu";
        tokens = FileUtil.parseLine(chaine);
        assertNull(tokens);
    }
    public void testParseLine() {
        TestUtil.TRACE(this);
        String [] tokens;
        String chaine ="test=ceci est un champ il doit etre lu";
        tokens = FileUtil.parseLine(chaine);
        assertEquals(2,tokens.length);
        assertEquals("test",tokens[0]);
        assertEquals("ceci est un champ il doit etre lu",tokens[1]);        
    }
    public void testParseLineWithoutValue() {
        TestUtil.TRACE(this);
        String [] tokens;
        String chaine ="test=";
        tokens = FileUtil.parseLine(chaine);
        assertEquals(2,tokens.length);
        assertEquals("test",tokens[0]);
        assertEquals("",tokens[1]);        
    }

}
