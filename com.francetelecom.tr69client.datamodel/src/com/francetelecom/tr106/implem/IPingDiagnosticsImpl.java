/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DataModelBundle
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
package com.francetelecom.tr106.implem;
import java.util.Observable;
import java.util.Observer;
import com.francetelecom.admindm.api.GetterSystem;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr106.gen.IPPingDiagnostics;
/**
 * The Class IPingDiagnosticsImpl.
 */
public final class IPingDiagnosticsImpl extends IPPingDiagnostics implements
        Observer {
    /**
     * Instantiates a new i ping diagnostics impl.
     * @param data the data
     * @param basePath the base path
     */
    public IPingDiagnosticsImpl(final IParameterData data,
            final String basePath) {
        super(data, basePath);
    }
    /**
     * @see com.francetelecom.tr106.gen.IPPingDiagnostics#initialize()
     */
    public void initialize() throws Fault {
        super.initialize();
        getter = new GetterSystem("ping", "", ParameterType.STRING);
        getParamDiagnosticsState().addObserver(this);
    }
    /** The result. */
    private String result;
    /** The getter. */
    private GetterSystem getter;
    /**
     * Creates the cmd.
     * @param sessionId the session id
     * @return the string
     */
    String createCmd(String sessionId) {
        StringBuffer cmd = new StringBuffer("ping -c ");
        cmd.append(getParamNumberOfRepetitions().getTextValue(sessionId));
        cmd.append(" -w ");
        cmd.append(getParamTimeout().getTextValue(sessionId));
        cmd.append(" -s ");
        cmd.append(getParamDataBlockSize().getTextValue(sessionId));
        cmd.append(" ");
        cmd.append(getParamHost().getTextValue(sessionId));
        return cmd.toString();
    }
    /**
     * Parses the result.
     */
    protected void parseResult() {
        if (result != null) {
            String[] tokens = result.split(System
                    .getProperty("line.separator"));
            int nbtokens = tokens.length;
            String resume = (tokens[nbtokens - 2]);
            System.out.println(resume);
            System.out.println(tokens[nbtokens - 1]);
            // TODO full parsingof result
            getParamFailureCount().setDirectValue(new Integer(0));
            getParamMaximumResponseTime().setDirectValue(new Integer(0));
            getParamMinimumResponseTime().setDirectValue(new Integer(0));
            getParamSuccessCount().setDirectValue(new Integer(0));
            getParamAverageResponseTime().setDirectValue(new Integer(0));
            getParamAverageResponseTime().setDirectValue(new Integer(0));
        }
    }
    /**
     * Update.
     * @param arg0 the arg0
     * @param arg1 the arg1
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable arg0, Object arg1) {
        if (arg0 == getParamDiagnosticsState()
                && "Requested".equals(getParamDiagnosticsState().getTextValue(
                        ""))) {
            getter.setCmd(createCmd(""));
            result = (String) getter.get("");
            parseResult();
            getParamDiagnosticsState().setDirectValue("Complete");
        }
    }
}
