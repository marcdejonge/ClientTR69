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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * The Class FileUtil.
 */
public class FileUtil {
    /** The FILEDEF. */
    public static String CONFIG_FILE ="CSV.cfg";
    public static final String CONFIG = "1 Vendor Configuration File";
    public static final String LOG = "2 Vendor Log File";
    public static final String DATAMODEL = "X ORANGE DataModel File";
    public static final String USINE = "X ORANGE Usine File";
    public static final String SAVE = "X ORANGE Data Save File";
    public static final String IPCONF = "X ORANGE IP File";
    /**
     * Gets the File by his short name.
     * @param shortName the short filename
     * @return the file if known
     */
    public static File getFileFromShortName(String shortName) {
        File result = null;
        File conf = new File(CONFIG_FILE);
        if (!conf.exists()) {
            Log.error("Base config file not exist :\"" + CONFIG_FILE + "\"");
            return null;
        }
        FileReader fr = null;
        BufferedReader buff = null;
        try {
            fr = new FileReader(conf);
            buff = new BufferedReader(fr);
            String line;
            while ((line = buff.readLine()) != null && result == null) {
                String[] tokens = parseLine(line);
                if (tokens != null && tokens.length == 2
                        && shortName.equals(tokens[0])) {
                    result = new File(tokens[1]);
                }
            }
        } catch (IOException e) {
            Log.error("failed to initialize file config", e);
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                Log.error("failed to close fr", e);
            }
            try {
                if (buff != null) {
                    buff.close();
                }
            } catch (IOException e) {
                Log.error("failed to close buff", e);
            }
        }
        return result;
    }
    /**
     * Parse a string to extract the properties and there value (the string is
     * ignored if it starts with a # symbol).
     * @param chaine the chaine
     * @return String array
     */
    public static String[] parseLine(final String chaine) {
        String[] tokens = null;
        if (chaine != null && !chaine.startsWith("#")
                && !"".equals(chaine.trim())) {
            try {
                int pos = chaine.indexOf('=');
                if (pos < 0) {
                    Log.error("bad parsing" + chaine);
                    tokens = null;
                } else {
                    tokens = new String[2];
                    tokens[0] = chaine.substring(0, pos);
                    tokens[1] = chaine.substring(pos + 1);
                }
            } catch (Exception e) {
                Log.error("bad parsing" + chaine);
                tokens = null;
            }
        }
        return tokens;
    }
}
