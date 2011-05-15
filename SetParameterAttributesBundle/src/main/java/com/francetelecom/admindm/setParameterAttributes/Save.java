package com.francetelecom.admindm.setParameterAttributes;
import java.io.Serializable;
import com.francetelecom.admindm.model.Parameter;
/**
 * The Class Save aim is to keep the information about the parameter.
 */
public class Save implements Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /**
     * Instantiates a new save.
     * @param pParam the param
     */
    public Save(final Parameter pParam) {
        super();
        this.param = pParam;
        this.notification = param.getNotification();
        this.accessList = param.getAccessList();
    }
    /**
     * Restore the parameter attribute.
     */
    public void restore() {
        param.setNotification(notification);
        param.setAccessList(accessList);
    }
    /** The param. */
    private final Parameter param;
    /** The notification. */
    private final int notification;
    /** The access list. */
    private final String[] accessList;
}
