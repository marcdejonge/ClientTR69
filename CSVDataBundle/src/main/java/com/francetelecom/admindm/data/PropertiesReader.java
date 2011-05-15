package com.francetelecom.admindm.data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class PropertiesReader.
 */
public final class PropertiesReader {
    /** The parameter data. */
    private final IParameterData parameterData;
    /**
     * Instantiates a new properties reader.
     * @param parameterData the parameter data
     */
    protected PropertiesReader(IParameterData pParameterData) {
        parameterData = pParameterData;
    }
    /**
     * Read.
     * @param filename the filename
     */
    public void read(final File file) {
        FileReader fr = null;
        BufferedReader buff = null;
        if (file == null) {
            Log.error("Unable to find file");
            return;
        }
        try {
            fr = new FileReader(file);
            buff = new BufferedReader(fr);
            String line;
            Parameter param;
            while ((line = buff.readLine()) != null) {
                String[] token = parseLine(line);
                String key;
                String strValue;
                Object value;
                if (token != null) {
                    key = parameterData.getRoot() + token[0];
                    strValue = token[1];
                    Log.info("set " + key + " to " + strValue);
                    param = parameterData.getParameter(key);
                    if (param == null) {
                        StringBuffer error;
                        error = new StringBuffer(FaultUtil.STR_FAULT_9002);
                        error.append(": unknown parameter's type for ");
                        error.append(key);
                        Log.error(error.toString());
                    } else {
                        try {
                            value = Parameter.getValue(key, strValue, param
                                    .getType());
                            param.setValueWithout(value);
                        } catch (Fault e) {
                            Log.error("Pb while reading default parameter "
                                    + e.getFaultstring());
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.error("unable to read config");
        } finally {
            if (buff != null) {
                try {
                    buff.close();
                } catch (IOException e) {
                    Log.error("unable to close bufferReader");
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    Log.error("unable to close fileReader");
                }
            }
        }
    }
    /**
     * Parse a string to extract the properties and there value (the string is
     * ignored if it starts with a # symbol).
     * @param chaine the chaine
     * @return String array
     */
    static String[] parseLine(final String chaine) {
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
