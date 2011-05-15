/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : IFilePersitBundle
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
package com.francetelecom.admindm.persist.file;
import java.io.File;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.api.FileUtil;
/**
 * The Class FilePersistTest.
 */
public class FilePersistTest extends TestCase {
    
    public void testConstructorWithUnknowsFile() throws Exception {
        TestUtil.TRACE(this, "Must have 2 stack trace ");
        try {
            FileUtil.CONFIG_FILE = "./src/test/resources/unknow";
            FilePersist fp = new FilePersist();
            fp.initializeData();
            fail("Should throw an exception");
        } catch (Exception e) {      
            e.printStackTrace();           
            System.out.println("OK");
        }
        try {
            FileUtil.CONFIG_FILE = "./src/test/resources/csv_unknow.txt";
            FilePersist fp = new FilePersist();
            fp.initializeData();
            fail("Should throw an exception");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OK");
        }
        
    }
    /**
     * Test initialize data.
     * @throws Exception the exception
     */
    public void testInitializeData() throws Exception {
        TestUtil.COMMENT("First test use existing f1, f2 file");
        TestUtil.COMMENT("First test use not existing f1, f2 file");
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE = "./src/test/resources/csv.txt";
        File f1 = FileUtil.getFileFromShortName(FileUtil.SAVE);
        FilePersist fp = new FilePersist();
        fp.initializeData();
        assertTrue(f1.exists());
        f1.delete();
        fp.initializeData();
        assertTrue(f1.exists());
        f1.delete();
    }
    /**
     * Test persist parameter value long.
     * @throws Exception the exception
     */
    public void testPersistParameterValueLong() throws Exception {
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE = "./src/test/resources/csv.txt";
        FilePersist fp = new FilePersist();
        fp.initializeData();
        Long value = new Long(458963133L);
        fp.persist("test", null, 0, value, FilePersist.LONG);
        FilePersist fp2 = new FilePersist();
        fp2.initializeData();
        assertEquals(value, fp2
                .restoreParameterValue("test", FilePersist.LONG));
        fp.persist("test3", null, 0, value, FilePersist.UINT);
        fp2 = new FilePersist();
        fp2.initializeData();
        assertEquals(value, fp2.restoreParameterValue("test3",
                FilePersist.UINT));
        fp.persist("test4", null, 0, value, FilePersist.DATE);
        fp2 = new FilePersist();
        fp2.initializeData();
        assertEquals(value, fp2.restoreParameterValue("test4",
                FilePersist.DATE));
    }
    /**
     * Test persist parameter value int.
     * @throws Exception the exception
     */
    public void testPersistParameterValueInt() throws Exception {
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE = "./src/test/resources/csv.txt";
        FilePersist fp = new FilePersist();
        fp.initializeData();
        Integer value = new Integer(45896);
        fp.persist("test2", null, 0, value, FilePersist.INT);
        FilePersist fp2 = new FilePersist();
        fp2.initializeData();
        // fp2.values.list(System.out);
        assertEquals(value, fp2
                .restoreParameterValue("test2", FilePersist.INT));
    }
    /**
     * Test persist parameter0 subscriber.
     * @throws Exception the exception
     */
    public void testPersistParameter0Subscriber() throws Exception {
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE = "./src/test/resources/csv.txt";
        FilePersist fp = new FilePersist();
        fp.initializeData();
        String[] values = {};
        fp.persist("Device.Manufacturer.Name", values, 2, Boolean.TRUE,
                FilePersist.BOOLEAN);
        FilePersist fp2 = new FilePersist();
        fp2.initializeData();
        assertArrayEquals(values, fp2
                .restoreParameterSubscriber("Device.Manufacturer.Name"));
        // fp2.values.list(System.out);
    }
    /**
     * Test persist parameter subscriber.
     * @throws Exception the exception
     */
    public void testPersistParameterSubscriber() throws Exception {
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE = "./src/test/resources/csv.txt";
        FilePersist fp = new FilePersist();
        fp.initializeData();
        String[] values = { "test1" };
        fp.persist("Device.Manufacturer.Name", values, 2, Boolean.TRUE,
                FilePersist.BOOLEAN);
        FilePersist fp2 = new FilePersist();
        fp2.initializeData();
        assertArrayEquals(values, fp2
                .restoreParameterSubscriber("Device.Manufacturer.Name"));
        // fp2.values.list(System.out);
    }
    /**
     * Test persist parameter2 subscriber.
     * @throws Exception the exception
     */
    public void testPersistParameter2Subscriber() throws Exception {
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE = "./src/test/resources/csv.txt";
        FilePersist fp = new FilePersist();
        fp.initializeData();
        String[] values = { "test1", "test2" };
        fp.persist("Device.Manufacturer.Name", values, 2, Boolean.TRUE,
                FilePersist.BOOLEAN);
        FilePersist fp2 = new FilePersist();
        fp2.initializeData();
        assertArrayEquals(values, fp2
                .restoreParameterSubscriber("Device.Manufacturer.Name"));
    }
    /**
     * Test persist parameter notification.
     * @throws Exception the exception
     */
    public void testPersistParameterNotification() throws Exception {
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE = "./src/test/resources/csv.txt";
        FilePersist fp = new FilePersist();
        fp.initializeData();
        String[] values = { "test1", "test2" };
        fp.persist("Device.Manufacturer.Name", values, 2, Boolean.TRUE,
                FilePersist.BOOLEAN);
        FilePersist fp2 = new FilePersist();
        fp2.initializeData();
        assertTrue(2 == fp2
                .restoreParameterNotification("Device.Manufacturer.Name"));
    }
    /**
     * Test persist parameter notification.
     * @throws Exception the exception
     */
    public void testPersistParameterNotification0() throws Exception {
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE = "./src/test/resources/csv.txt";
        FilePersist fp = new FilePersist();
        fp.initializeData();
        String[] values = { "test1", "test2" };
        fp.persist("Device.Manufacturer.Name", values, 0, Boolean.TRUE,
                FilePersist.BOOLEAN);
        FilePersist fp2 = new FilePersist();
        fp2.initializeData();
        assertTrue(0 == fp2
                .restoreParameterNotification("Device.Manufacturer.Name"));
    }
    /**
     * Test restore not exist parameter.
     * @throws Exception the exception
     */
    public void testRestoreNotExistParameter() throws Exception {
        TestUtil.TRACE(this);
        FileUtil.CONFIG_FILE = "./src/test/resources/csv.txt";
        FilePersist fp2 = new FilePersist();
        fp2.initializeData();
        try {
            fp2.restoreParameterNotification("Unknown");
            fp2.restoreParameterSubscriber("Unknown");
            fp2.restoreParameterValue("Unknown", FilePersist.ANY);
            fp2.restoreParameterValue("Unknown", FilePersist.BASE64);
            fp2.restoreParameterValue("Unknown", FilePersist.BOOLEAN);
            fp2.restoreParameterValue("Unknown", FilePersist.DATE);
            fp2.restoreParameterValue("Unknown", FilePersist.INT);
            fp2.restoreParameterValue("Unknown", FilePersist.UINT);
            fp2.restoreParameterValue("Unknown", FilePersist.LONG);
            fp2.restoreParameterValue("Unknown", FilePersist.STRING);
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception should not occured");
        }
    }
    /**
     * Assert array equals.
     * @param tab1 the tab1
     * @param tab2 the tab2
     */
    public void assertArrayEquals(Object[] tab1, Object[] tab2) {
        if (tab1 == tab2)
            return;
        if (tab1.length == tab2.length) {
            for (int i = 0; i < tab1.length; i++) {
                assertEquals(tab1[i], tab2[i]);
            }
        } else {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < tab1.length; i++) {
                buffer.append("tab1 :");
                buffer.append(i).append(")").append(tab1[i].toString());
                buffer.append(System.getProperty("line.separator"));
            }
            System.out.println(buffer.toString());
            buffer = new StringBuffer();
            for (int i = 0; i < tab2.length; i++) {
                buffer.append("tab2 :");
                buffer.append(i).append(")").append(tab2[i].toString());
                buffer.append(System.getProperty("line.separator"));
            }
            System.out.println(buffer.toString());
            fail("tab1 != tab2");
        }
    }
}
