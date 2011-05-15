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
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import com.francetelecom.admindm.api.FileUtil;
import com.francetelecom.admindm.download.api.IUploadProtocol;
import com.francetelecom.admindm.download.api.Upload;

/**
 * The Class FTPUploadProtocol.
 */
public class FTPUploadProtocol implements IUploadProtocol {
    
    /**
     * Checks if is prefix compatible.
     * @param url the url
     * @return true, if checks if is prefix compatible
     */
    public boolean isPrefixCompatible(String url) {
        return url.startsWith("ftp://");
    }
    
    /**
     * On upload.
     * @param d the d
     * @throws Exception the exception
     */
    public void onUpload(Upload d) throws Exception {
        URL url = null;
        if ("".equals(d.getUsername())) {
            d.setUsername("anonymous");
        }
        String host = d.getUrl().substring("ftp://".length());
        File file = FileUtil.getFileFromShortName(d.getFileType());
        url = new URL("ftp://" + d.getUsername() + ":" + d.getPassword() + "@"
                + host + "/" + d.getFilename() + ";type=i");
        URLConnection urlc = url.openConnection();
        OutputStream os = urlc.getOutputStream();
        FileInputStream is = new FileInputStream(file);
        byte[] buf = new byte[16384];
        int c;
        while (true) {
            c = is.read(buf);
            if (c <= 0)
                break;
            os.write(buf, 0, c);
        }
        os.close();
        is.close();
    }
}
