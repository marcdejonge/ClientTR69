package com.francetelecom.admindm.data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.acse.modus.util.StringUtil;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.CheckMaximum;
import com.francetelecom.admindm.model.CheckMinimum;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class CSVReader.
 */
public final class CSVReader {
    /** The Constant CSV_FIELD_POSITION_OF_NAME. */
    private static final int CSV_FIELD_POS_OF_NAME = 0;
    /** The Constant CSV_FIELD_POSITION_OF_TYPE_AND_MIN_MAX. */
    private static final int CSV_FIELD_POS_OF_TYPE_AND_MIN_MAX = 1;
    /** The Constant CSV_FIELD_POSITION_OF_STORAGE_MODE. */
    private static final int CSV_FIELD_POS_OF_STORAGE_MODE = 2;
    /** The Constant CSV_FIELD_POSITION_OF_WRITABLE. */
    private static final int CSV_FIELD_POS_OF_WRITABLE = 3;
    /** The Constant CSV_FIELD_POSITION_OF_IMMEDIATE_CHANGE. */
    private static final int CSV_FIELD_POS_OF_IMMEDIATE_CHANGE = 4;
    /** The Constant CSV_FIELD_POSITION_OF_NOTIFICATION_MODE. */
    private static final int CSV_FIELD_POS_OF_NOTIF_MODE = 5;
    /** The Constant CSV_FIELD_POSITION_OF_IS_MANDATORY. */
    private static final int CSV_FIELD_POS_OF_IS_MANDATORY = 6;
    /** The Constant CSV_FIELD_POSITION_OF_IS_ACTIVE_NOTIFICATION_DENIED. */
    private static final int CSV_FIELD_POS_OF_IS_ACTIVE_NOTIF_DENIED = 7;
    /** The Constant CSV_FIELD_POSITION_OF_ACCESS_LIST. */
    private static final int CSV_FIELD_POS_OF_ACCESS_LIST = 8;
    /** The Constant CSV_FIELD_POSITION_OF_VALUE. */
    private static final int CSV_FIELD_POS_OF_VALUE = 9;
    /** The Constant CSV_FIELD_POSITION_OF_UPDATE_MODE. */
    private static final int CSV_FIELD_POS_OF_UPDATE_MODE = 10;
    /** The Constant DEFAULT_ACCESS_LIST. */
    private static final String[] DEFAULT_ACCESS_LIST = { "Subscriber" };
    /** The parameter data. */
    private IParameterData parameterData;
    /**
     * Hide default constructor.
     * @param pParameterData the parameter data
     */
    protected CSVReader(final IParameterData pParameterData) {
        parameterData = pParameterData;
    }
    
    /**
     * open the file.
     * @param file the file
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws Fault the fault
     */
    
    public void foundTheRoot(final File file) throws IOException, Fault {
        FileReader fr = null;
        BufferedReader buff = null;
        List lsParam = new ArrayList();
        Parameter param = null;
        Parameter deviceSummary = null;
        fr = new FileReader(file);
        buff = new BufferedReader(fr);
        String line;
        while ((line = buff.readLine()) != null) {
            try {
                param = parseLine(line);                
                if (param != null) {
                    Log.debug("read " + param.getName());
                    if (param.getName().startsWith("DeviceSummary")) {
                        deviceSummary = param;
                    }
                    lsParam.add(param);
                }
            } catch (Fault e) {
                Log.error(e.getFaultstring(), e);
            }
        }
        if (buff != null) {
            try {
                buff.close();
            } catch (IOException e) {
                Log.error("unable to close bufferReader", e);
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    Log.error("unable to close fileReader", e);
                }
            }
        }        
        if (deviceSummary == null) { 
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": Unable to found DeviceSummary in csv file");
            throw new Fault(FaultUtil.FAULT_9002,error.toString());
        }
        String root = extractRootFromDeviceSummary(deviceSummary) + ".";
        parameterData.setRoot(root);
        while (!lsParam.isEmpty()) {
            try {
                param = (Parameter) lsParam.remove(0);
                param.setName(root + param.getName());
                parameterData.addOrUpdateParameter(param, "CPE");
            } catch (Fault e) {
                Log.error("unable to read config", e);
            }
        }
    }
    public void completeDataModel(final File file) throws IOException {
        FileReader fr = null;
        BufferedReader buff = null;
        List lsParam = new ArrayList();
        Parameter param = null;
        fr = new FileReader(file);
        buff = new BufferedReader(fr);
        String line;
        while ((line = buff.readLine()) != null) {
            try {
                param = parseLine(line);                
                if (param != null) {
                    Log.debug("read " + param.getName());
                    lsParam.add(param);
                }
            } catch (Fault e) {
                Log.error(e.getFaultstring(), e);
            }
        }
        if (buff != null) {
            try {
                buff.close();
            } catch (IOException e) {
                Log.error("unable to close bufferReader", e);
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    Log.error("unable to close fileReader", e);
                }
            }
        }        
        while (!lsParam.isEmpty()) {
            try {
                param = (Parameter) lsParam.remove(0);
                param.setName(parameterData.getRoot() + param.getName());
                parameterData.addOrUpdateParameter(param, "CPE");
            } catch (Fault e) {
                Log.error("unable to read config", e);
            }
        }
    }
    private String extractRootFromDeviceSummary(Parameter deviceSummary) {
        DeviceSummary summary;        
        summary = new DeviceSummary(deviceSummary.getTextValue(""));
        return summary.getRootObject().getObjectName();
    }
    /**
     * Parses the line.
     * @param chaine the chaine
     * @return the parameter
     * @throws Fault the fault
     */
    protected static Parameter parseLine(final String chaine) throws Fault {
        Parameter param = null;
        if (chaine == null || chaine.startsWith("#")) {
            return null;
        }
        String[] tokens;
        int intValue;
        try {
            try {
                tokens = StringUtil.simpleSplit(chaine, ";");
            } catch (Exception e) {
                StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
                error.append(": ");
                error.append(e.getMessage());
                throw new Fault(FaultUtil.FAULT_9002, error.toString(), e);
            }
            if (tokens.length == 12) {
                param = new Parameter();
                param.setName(tokens[CSV_FIELD_POS_OF_NAME]);
                intValue = getType(tokens[CSV_FIELD_POS_OF_TYPE_AND_MIN_MAX]);
                param.setType(intValue);
                param.addCheck(new CheckMinimum(getMin(
                        tokens[CSV_FIELD_POS_OF_TYPE_AND_MIN_MAX], param
                                .getType())));
                param.addCheck(new CheckMaximum(getMax(
                        tokens[CSV_FIELD_POS_OF_TYPE_AND_MIN_MAX], param
                                .getType())));
                intValue = getStorageMode(tokens[CSV_FIELD_POS_OF_STORAGE_MODE]);
                param.setStorageMode(intValue);
                param
                        .setWritable(isWritable(tokens[CSV_FIELD_POS_OF_WRITABLE]));
                param
                        .setImmediateChanges(isImmediateChanges(tokens[CSV_FIELD_POS_OF_IMMEDIATE_CHANGE]));
                param
                        .setNotification(getNotificationMode(tokens[CSV_FIELD_POS_OF_NOTIF_MODE]));
                param
                        .setMandatoryNotification(isMandatory(tokens[CSV_FIELD_POS_OF_IS_MANDATORY]));
                param
                        .setActiveNotificationDenied(isActiveNotificationDenied(tokens[CSV_FIELD_POS_OF_IS_ACTIVE_NOTIF_DENIED]));
                param
                        .setAccessList(getAccessList(tokens[CSV_FIELD_POS_OF_ACCESS_LIST]));
                if (!"".equals(tokens[CSV_FIELD_POS_OF_VALUE].trim())) {
                    // n'ecrase pas les valeurs deja possitionnees
                    param.setValue(Parameter.getValue(param.getName(),
                            tokens[CSV_FIELD_POS_OF_VALUE], param.getType()));
                }
                param
                        .setUpdateMode(getUpdateMode(tokens[CSV_FIELD_POS_OF_UPDATE_MODE]));
                // TODO gestion des deux derniers parametres non encore fait
            } else {
                Log.warn("CSVReader failed to parse:" + chaine);
            }
        } catch (RuntimeException exp) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": ");
            error.append(exp.getMessage());
            throw new Fault(FaultUtil.FAULT_9002, error.toString(), exp);
        }
        return param;
    }
    /**
     * accessList List of entities separated by commas. Only the entities
     * specified, plus the ACS, can access the parameters in writing (cf.
     * TR-069). In the current state, this field is left empty or set to the
     * default value "Subscriber".
     * @param value the value
     * @return the access list
     */
    private static String[] getAccessList(final String value) {
        String[] result = {};
        try {
            if (value == null || "".equals(value.trim())) {
                result = DEFAULT_ACCESS_LIST;
            } else {
                result = StringUtil.simpleSplit(value, ",");
            }
        } catch (Exception e) {
            Log.error("Internal Error:" + e.getLocalizedMessage(), e);
        }
        return result;
    }
    /**
     * activeNotificationDenied Identify the parameters for which the active
     * notification mode is denied by the CPE. "1" or "Y" : Yes "0" or "N" : No
     * @param value the value
     * @return true, if checks if is active notification denied
     */
    private static boolean isActiveNotificationDenied(final String value) {
        return "1".equals(value) || "Y".equals(value);
    }
    /**
     * Identify the "mandatory" parameters (which are systematically notified at
     * each Inform message). "1" or "Y" : Yes "0" or "N" : No
     * @param value the value
     * @return true, if checks if is mandatory
     */
    private static boolean isMandatory(final String value) {
        return "1".equals(value) || "Y".equals(value);
    }
    /**
     * Indicate if the SetParameterValues are immediately applied or not: "1" or
     * "Y": Yes "0" or "N": No (only after a Reboot, for example) This
     * information is used to determine the status to send back following a call
     * SetParameterValues.
     * @param value the value
     * @return true, if checks if is immediate changes
     */
    private static boolean isImmediateChanges(final String value) {
        return "1".equals(value) || "Y".equals(value);
    }
    /**
     * parse the Mode of storage : 0 (DM_ONLY): parameters managed and whose
     * value is stored uniquely at the DM Agent level. See Annex for the
     * detailed list of parameters managed by the TR069 Generic Agent. 1
     * (SYSTEM_ONLY): system parameters whose value is only temporarily stored
     * at the DM Agent level (typically, a value obtained from the system during
     * a session with the ACS is conserved until the end of the session but
     * forgotten straightaway). 2 (MIXED): parameters whose value obtained from
     * the system is conserved in the base by the DM Agent. To ensure the
     * validity of this value, the system must notify the DM Agent in case of
     * the value changing. 3 (COMPUTED): parameters whose value is computed from
     * others parameters. For example : equal parameters, number of object
     * instances, concatenation of strings. See below the definition of computed
     * parameters. writable
     * @param value the value
     * @return the storage mode
     */
    private static int getStorageMode(final String value) {
        int result = -1;
        if ("0".equals(value) || "DM_ONLY".equals(value)) {
            result = StorageMode.DM_ONLY;
        } else if ("1".equals(value) || "SYSTEM_ONLY".equals(value)) {
            result = StorageMode.SYSTEM_ONLY;
        } else if ("2".equals(value) || "MIXED".equals(value)) {
            result = StorageMode.MIXED;
        } else if ("3".equals(value) || "COMPUTED".equals(value)) {
            result = StorageMode.COMPUTED;
        }
        if (result == -1) {
            Log.error("unknown Storage Mode : " + value);
        }
        return result;
    }
    /**
     * 2 possible ways of writing, number or string with the following
     * correspondence: "0" or "INT", "1" or "UINT", "2" or "LONG", "3" or
     * "BOOLEAN", "4" or "DATE", "5" or "STRING", "6" or "ANY" (for the
     * objects). For the "INT" and "UINT" types, it is possible to specify the
     * range of the value as follows: <br/>
     * <li>INT:&lt;min&gt;:&lt;max&gt;</li> <li>INT:&lt;min&gt;</li> <li>
     * UINT:&lt;min&gt;:&lt;max&gt;</li> <li>UINT:&lt;min&gt;</li><br/>
     * When the min or max values are not specified the default values are used
     * : INT_MIN (= –2147483648) and INT_MAX (= 2147483647) for INT, 0 and
     * UINT_MAX (= 4294967295) for UINT. Example : "UINT::25" indicates the [0,
     * 25] range.
     * @param value the value
     * @return the type
     */
    private static int getType(final String value) {
        int result = ParameterType.UNDEFINED;
        if ("0".equals(value) || value.startsWith("INT")) {
            result = ParameterType.INT;
        } else if ("1".equals(value) || value.startsWith("UINT")) {
            result = ParameterType.UINT;
        } else if ("2".equals(value) || value.startsWith("LONG")) {
            result = ParameterType.LONG;
        } else if ("3".equals(value) || "BOOLEAN".equals(value)) {
            result = ParameterType.BOOLEAN;
        } else if ("4".equals(value) || "DATE".equals(value)) {
            result = ParameterType.DATE;
        } else if ("5".equals(value) || "STRING".equals(value)) {
            result = ParameterType.STRING;
        } else if ("6".equals(value) || "ANY".equals(value)) {
            result = ParameterType.ANY;
        }
        return result;
    }
    /**
     * Gets the min.
     * @param value the value
     * @param type the type
     * @return the min
     */
    private static long getMin(final String value, final int type) {
        long result = -1;
        String[] tokens;
        try {
            tokens = StringUtil.simpleSplit(value, ":");
            switch (type) {
            case ParameterType.INT:
                result = Integer.MIN_VALUE;
                if (tokens.length > 1 && !"".equals(tokens[1])) {
                    result = Integer.parseInt(tokens[1]);
                }
                break;
            case ParameterType.UINT:
                result = 0;
                if (tokens.length > 1 && !"".equals(tokens[1])) {
                    result = Integer.parseInt(tokens[1]);
                }
                break;
            default:
                result = -1;
                break;
            }
        } catch (Exception e) {
            Log.error("Internal Error:" + e.getLocalizedMessage(), e);
        }
        return result;
    }
    /**
     * Gets the max.
     * @param value the value
     * @param type the type
     * @return the max
     */
    private static long getMax(final String value, final int type) {
        long result = -1;
        String[] tokens;
        try {
            tokens = StringUtil.simpleSplit(value, ":");
            switch (type) {
            case ParameterType.INT:
                result = 2147483648L;
                if (tokens.length == 3 && !"".equals(tokens[2]))
                    result = Integer.parseInt(tokens[2]);
                break;
            case ParameterType.UINT:
                result = 4294967295L;
                if (tokens.length == 3 && !"".equals(tokens[2]))
                    result = Integer.parseInt(tokens[2]);
                break;
            default:
                result = -1;
                break;
            }
        } catch (Exception e) {
            Log.error("Internal Error:" + e.getLocalizedMessage(), e);
        }
        return result;
    }
    /**
     * Type of access allowed on the parameter: "1" or "W": read-write access
     * "0" or other: read only access.
     * @param value the value
     * @return true, if checks if is writable
     */
    private static boolean isWritable(final String value) {
        return "1".equals(value) || "W".equals(value);
    }
    /**
     * 0 : no notification 1 : passive notification (will be sent with the next
     * Inform message) 2 : active notification (an Inform message is specially
     * sent).
     * @param value the value
     * @return the notification mode
     */
    private static int getNotificationMode(final String value) {
        int result = -1;
        if ("0".equals(value)) {
            result = 0;
        }
        if ("1".equals(value)) {
            result = 1;
        }
        if ("2".equals(value)) {
            result = 2;
        }
        if (result == -1) {
            throw new RuntimeException("unknown Notification mode : " + value);
        }
        return result;
    }
    /**
     * For the SYSTEM_ONLY and MIXED parameters, indicate a mode of update for
     * the value. 0: the parameter can be obtained from the system individually
     * by an unitary getValue via the DeviceAdapter. 1: the parameter belongs to
     * a group (a sub-arborescence) of interdependent parameters which must be
     * collectively updated. Man must therefore climb up in the arborescence up
     * to the highest object of which the updateMode is at 1 and carry out a
     * getObject on this object via the Device Adapter. 2: as 1 but only applies
     * to nodes and indicates that the sub-arborescence is scalable because it
     * contains instanciable parameters. To know the update list of parameters,
     * a getNames could be addressed to the Device Adapter.
     * @param value the value
     * @return the update mode
     */
    private static int getUpdateMode(final String value) {
        int result = 0;
        // TOBE Completed
        return result;
    }
}
