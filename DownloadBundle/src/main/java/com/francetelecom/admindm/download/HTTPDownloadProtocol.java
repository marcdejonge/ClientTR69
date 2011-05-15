/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DownloadBundle
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
 * Author : Orange Labs R&D O.Beyler
 */

package com.francetelecom.admindm.download;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.download.api.Download;
import com.francetelecom.admindm.download.api.IDownloadProtocol;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class HTTPDownloadProtocol.
 */
public final class HTTPDownloadProtocol implements IDownloadProtocol {
    /**
     * Checks if is prefix compatible.
     * @param url the url
     * @return true, if checks if is prefix compatible
     */
    public boolean isPrefixCompatible(final String url) {
        return (url != null && (url.startsWith("http://") || url
                .startsWith("HTTP://")));
    }
    /** The Constant MAX_LENGTH_LINE. */
    private static final int MAX_LENGTH_LINE = 60;
    /** The Constant BUFFER_SIZE. */
    private static final int BUFFER_SIZE = 4096;
    /**
     * On download.
     * @param d the d
     * @throws Fault the fault
     */
    public void onDownload(final Download d) throws Fault {
        OutputStream os = null;
        File dest = null;
        InputStream input = null;
        BufferedInputStream inputs = null;
        StringBuffer error = null;
        try {
            URL url = new URL(d.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setAllowUserInteraction(false);
            con.setRequestMethod("GET");
            final String name = d.getUsername();
            final String password = d.getPassword();
            // -----------------------
            String userPassword = name + ":" + password;
            String encoding = Base64Encoding.encode(userPassword);
            con.setRequestProperty("Authorization", "Basic " + encoding);
            // -----------------------
            int code = con.getResponseCode();
            StringBuffer dotLine = new StringBuffer(".");
            if (code == HttpURLConnection.HTTP_OK) {
                input = con.getInputStream();
                inputs = new BufferedInputStream(input);
                dest = createFile(extractFilename(d));
                os = new FileOutputStream(dest);
                Log.info("start download");
                int count = 0;
                byte[] abyte0;
                int j = 0;
                abyte0 = new byte[BUFFER_SIZE];
                while (((j = inputs.read(abyte0)) != -1)) {
                    if ((count++ % MAX_LENGTH_LINE) == 0) {
                        Log.debug(dotLine.toString());
                        dotLine = new StringBuffer("");
                    }
                    dotLine.append(".");
                    os.write(abyte0, 0, j);
                }
                Log.debug(dotLine.toString());
                Log.info("end download");
            }
        } catch (IOException e) {
            error = new StringBuffer(FaultUtil.STR_FAULT_9017);
            error.append(": ");
            error.append(e.getLocalizedMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                Log.error("Exception on close os");
            }
            try {
                if (inputs != null) {
                    inputs.close();
                }
            } catch (IOException e) {
                Log.error("Exception on close inputs");
            }
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                Log.error("Exception on close intput");
            }
        }
        if (error != null) {
            Log.error(error.toString());
            throw new Fault(FaultUtil.FAULT_9017, error.toString());
        }
    }
    /**
     * Extract filename from Download information.
     * @param download the download
     * @return the string
     */
    protected static String extractFilename(final Download download) {
        String result = download.getTargetFileName();
        if ("".equals(download.getTargetFileName())) {
            String urlName = download.getUrl();
            int index = urlName.lastIndexOf('/') + 1;
            if (index != -1) {
                result = urlName.substring(index, urlName.length());
            } else {
                result = urlName;
            }
        }
        return result;
    }
    
    /**
     * Creates the file based on the path parameter. This method creates any needed intermediate directory.
     *
     * @param path the path
     * @return the file
     */
    private static File createFile(final String path) {
    	System.out.println("createfile - path = " + path);
    	int index = path.lastIndexOf(File.separator);
    	System.out.println("index = " + index);
    	System.out.println("");
    	if (index > 0) {
    			String subDirectoryPath = path.substring(0, index);
    			System.out.println("subDirectoryPath = " + subDirectoryPath);
    			if (subDirectoryPath.length() != 0) {
    				System.out.println("subDirectoryPath - create mkdirs");
    				File subDirectoryFile = new File(subDirectoryPath);
    				subDirectoryFile.mkdirs();
    			}
    	}
    	File file = new File(path);
    	return file;
    }
}
