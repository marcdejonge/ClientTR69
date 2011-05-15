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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.download.api.Download;
import com.francetelecom.admindm.download.api.IUploadProtocol;
import com.francetelecom.admindm.download.api.Upload;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;

/**
 * The Class HTTPDownloadProtocol.
 */
public final class HTTPUploadProtocol implements IUploadProtocol {
    
    /**
     * Checks if is prefix compatible.
     * @param url the url
     * @return true, if checks if is prefix compatible
     */
    public boolean isPrefixCompatible(final String url) {
        return (url != null && (url.startsWith("http://") || url
                .startsWith("HTTP://")));
    }
    
    /**
     * On download.
     * @param d the d
     * @throws Fault the fault
     */
    public void onUpload(final Upload d) throws Fault {
        OutputStream os = null;
        InputStream input = null;
        StringBuffer error = null;
        try {
            URL url = new URL(d.getUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setAllowUserInteraction(false);
            con.setRequestMethod("PUT");
            final String name = d.getUsername();
            final String password = d.getPassword();
            // -----------------------
            String userPassword = name + ":" + password;
            String encoding = Base64Encoding.encode(userPassword);
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", ""
                    + getFileLength(d.getFilename()));
            con.setRequestProperty("Authorization", "Basic " + encoding);
            // -----------------------
            os = con.getOutputStream();
            input = con.getInputStream();
            writeFile(os, d.getFilename());
            int code = con.getResponseCode();
            StringBuffer dotLine = new StringBuffer(".");
            if (code == HttpURLConnection.HTTP_OK) {
                Log.info("end upload");
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
     * Write file.
     * @param os the os
     * @param filename the filename
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void writeFile(OutputStream os, String filename) 
        throws IOException  {
        File file = new File (filename);        
        FileInputStream is = new FileInputStream(file);
        byte [] buffer = new byte[1024];
        int c;
        while((c = is.read(buffer)) > -1) {
            os.write(buffer, 0, c);
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
     * Gets the file length.
     * @param filename the filename
     * @return the file length
     */
    protected long getFileLength(String filename) {
        long result = 0;
        File file = new File (filename);        
        try{
        FileInputStream is = new FileInputStream(file);
        byte [] buffer = new byte[1024];
        int c;
        while((c = is.read(buffer)) > -1) {
            result+=c;
        }
        } catch (Exception e) {
            Log.error("unable to get file length",e);
            result= -1;
        }
        return result;
    }
}
