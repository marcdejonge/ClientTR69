package com.francetelecom.admindm.data;
import java.io.File;
import java.io.IOException;
import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.FileUtil;
import com.francetelecom.admindm.api.ICSV;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class Start. The Start class do :
 * <ul>
 * <li>read the file to know where find the others configuration file</li>
 * <li>read the structure file of data model</li>
 * <li>add the data model complement</li>
 * <li>if the data file is not present, usine file is use to read default
 * parameters and add a specific event <code>0 BOOTSTRAP</code></li>
 * <li>set the data model flag to loaded</li>
 * </ul>
 */
public class Start implements ICSV {
    /**
     * Instantiates a new start.
     */
    public Start() {
    }
    
    /**
     * Discover model root.
     * @throws Fault the fault
     */
    public void discoverModelRoot() throws Fault {
        try {
            CSVReader reader = new CSVReader(data);
            File file = FileUtil.getFileFromShortName(FileUtil.DATAMODEL);
            reader.foundTheRoot(file);
        } catch (IOException e) {
            Log.error("Pb while reading root", e);
        }
    }
    /**
     * Put default parameter.
     * @see com.francetelecom.admindm.api.ICSV#putDefaultParameter()
     */
    public void putDefaultParameter() {
        File dataSave = FileUtil.getFileFromShortName(FileUtil.SAVE);
        if (dataSave == null) {
            StringBuffer error = new StringBuffer(FileUtil.SAVE);
            error.append(" is not defined : no persistance will be present.");
            Log.error(error.toString());
        }
        if (dataSave == null || !dataSave.exists()) {
            data.addEvent(new EventStruct(EventCode.BOOTSTRAP, ""));
        }
        data.addEvent(new EventStruct(EventCode.BOOT, ""));
        PropertiesReader reader;
        reader = new PropertiesReader(data);
        reader.read(FileUtil.getFileFromShortName(FileUtil.USINE));
    }
    /**
     * Read struct complement.
     * @see com.francetelecom.admindm.api.ICSV#readStructComplement()
     */
    public void readStructComplement() {
        try {
            CSVReader reader = new CSVReader(data);
            File file = FileUtil.getFileFromShortName(FileUtil.DATAMODEL);
            if (file==null){
                Log.error("");
            }
            reader.completeDataModel(file);
        } catch (IOException e) {
            Log.error("Pb while reading data model complement", e);
        }
    }
    /** The data. */
    private IParameterData data;
    /**
     * Sets the data.
     * @param pData the data
     * @see com.francetelecom.admindm.api.ICSV#setData(com.francetelecom.admindm.model.IParameterData)
     */
    public void setData(IParameterData pData) {
        this.data = pData;
    }
}
