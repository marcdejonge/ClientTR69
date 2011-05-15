package com.francetelecom.admindm.osgi.apply;
import com.francetelecom.admindm.download.api.IApplyAction;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class AbstractApply.
 */
public abstract class AbstractApply implements IApplyAction {
    /**
     * Reject bad file.
     * @param location the location
     * @return the fault
     */
    protected final Fault rejectBadFile(final String location) {
        return new Fault(FaultUtil.FAULT_9018, faultMsg(location,null));
    }

    /**
     * Reject bad file.
     * @param location the location
     * @param e the exception
     * @return the fault
     */
    protected final Fault rejectBadFile(final String location,
            final Exception e) {
        return new Fault(FaultUtil.FAULT_9018, faultMsg(location,e), e);
    }
    /**
     * Fault msg.
     * @param name the name
     * @return the string
     */
    private String faultMsg(final String name,Exception e) {
        StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9018);
        error.append(" unable to apply '");
        error.append(name);
        if (e!=null) {
            error.append("' cause :");
            error.append(e.getMessage());
        }
        return error.toString();
    }
}
