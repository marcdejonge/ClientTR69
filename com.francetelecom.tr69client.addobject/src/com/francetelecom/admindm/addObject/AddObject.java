/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : AddObjectBundle
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
package com.francetelecom.admindm.addObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import com.francetelecom.admindm.api.Factory;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class AddObject.
 */
public final class AddObject implements RPCMethod {
    /** The Constant NAME. */
    private static final String NAME = "AddObject";
    /** The sequence id file. */
    private final transient File sequenceIdFile;
    /**
     * Instantiates a new adds the object.
     * @param pSequenceIdFile the sequence id file
     * @param pObjectName the object name
     * @param pParameterKey the parameter key
     */
    public AddObject(final File pSequenceIdFile, final String pObjectName,
            final String pParameterKey) {
        super();
        this.sequenceIdFile = pSequenceIdFile;
        this.objectName = pObjectName;
        this.parameterKey = pParameterKey;
    }
    /** The map name id. */
    private Map<String, Long> mapNameId = new HashMap<String, Long>();
    /** The object name. */
    private final String objectName;
    /**
     * Gets the object name.
     * @return the object name
     */
    public String getObjectName() {
        return objectName;
    }
    /** The parameter key. */
    private final String parameterKey;
    /**
     * Gets the name.
     * @return the name
     * @see com.francetelecom.admindm.api.RPCMethod#getName()
     */
    public String getName() {
        return NAME;
    }
    /**
     * Perform.
     * @param session the session
     * @throws Fault the exception
     */
    public void perform(final Session session) throws Fault {
        if (!getObjectName().endsWith(".")) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": ObjectName value must end with a dot.");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        IParameterData data = session.getParameterData();
        Parameter param = data.getParameter(objectName);
        if (param == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9005);
            error.append(": ");
            error.append(objectName);
            error.append(" does'nt belong to the implemented data model.");
            throw new Fault(FaultUtil.FAULT_9005, error.toString());
        }
        Factory factory = param.getFactory();
        if (factory == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": Doesn't find any factory for ");
            error.append(" this kind of object.");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        long id = generateObjectId(objectName, data);
        int status = factory.createObjectInstance(id, data);
        AddObjectResponse aor = new AddObjectResponse();
        aor.setInstanceNumber(id);
        aor.setStatus(status);
        session.doASoapResponse(aor);
    }
    /**
     * Generate object id.
     * @param pObjectName the object name
     * @param data the data
     * @return the long
     */
    protected long generateObjectId(final String pObjectName,
            final IParameterData data) {
        if (sequenceIdFile.exists()) {
            restoreMapNameId();
        }
        Long id = (Long) mapNameId.get(pObjectName);
        if (id == null) {
            id = new Long(1);
        }
        // verify if this number is free or not.
        // if not the id is changed to the next free.
        while (data.getParameter(pObjectName + id + '.') != null) {
            id = new Long(id.longValue() + 1);
        }
        mapNameId.put(pObjectName, new Long(id.longValue() + 1));
        saveMapNameId();
        return id.longValue();
    }
    /**
     * Save map name id into the file.
     */
    protected void saveMapNameId() {
        FileOutputStream ostream = null;
        ObjectOutputStream oos = null;
        try {
            ostream = new FileOutputStream(sequenceIdFile);
            oos = new ObjectOutputStream(ostream);
            oos.writeObject(mapNameId);
        } catch (IOException e) {
            Log.error("Unable to write map sequence", e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    Log.error(e.getLocalizedMessage(), e);
                }
            }
            if (ostream != null) {
                try {
                    ostream.close();
                } catch (IOException e) {
                    Log.error(e.getLocalizedMessage(), e);
                }
            }
        }
    }
    /**
     * Restore map name id from file.
     */
    @SuppressWarnings("unchecked")
	protected void restoreMapNameId() {
        FileInputStream inputStream = null;
        ObjectInputStream ois = null;
        try {
            inputStream = new FileInputStream(sequenceIdFile);
            ois = new ObjectInputStream(inputStream);
            mapNameId = (HashMap<String, Long>) ois.readObject();
        } catch (IOException e) {
            Log.error("Unable to write map sequence", e);
        } catch (ClassNotFoundException e) {
            Log.error("Unable to read map sequence", e);
            mapNameId = new HashMap<String, Long>();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    Log.error(e.getLocalizedMessage(), e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.error(e.getLocalizedMessage(), e);
                }
            }
        }
    }
    /**
     * Gets the parameter key.
     * @return the parameter key
     */
    public String getParameterKey() {
        return parameterKey;
    }
    /**
     * id of the RPCMethod Request by ACS.
     */
    private String id= null;
    /**
     * Gets the id.
     * @return the Id.
     */
	public String getId() { 
		return id;
	}
	/**
	 * Setter the Id.
	 */
	public void setId(String id) {
		this.id=id;		
	}
}
