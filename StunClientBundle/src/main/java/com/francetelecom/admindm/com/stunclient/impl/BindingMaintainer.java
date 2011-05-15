/** */
package com.francetelecom.admindm.com.stunclient.impl;
import java.util.Observable;
import java.util.Observer;
import com.francetelecom.acse.modus.util.StringUtil;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
/**
 * <p>
 * The purpose of this class is to maintain the binding between the CPE and
 * the ACS through the NAT.
 * </p>
 * @author mpcy8647
 */
public class BindingMaintainer implements Observer {
    /** No policy in use. */
    private static final int NONE = 0;
    /**
     * <p>
     * basic binding policy
     * <p>
     * <p>
     * This politic is in use when the STUNMinimumKeepAlivePeriod parameter
     * value is equal to the the STUNMaximumKeepAlivePeriod parameter value.
     * </p>
     * <p>
     * The STUN binding request message are sent as frequently as is specified
     * by the ManagementServer.STUNMaximunKeepAlivePeriod paramerter value.
     * </p>
     */
    private static final int BASIC_POLICY = 1;
    /**
     * <p>
     * This maintain binding policy requests that the CPE determines the
     * binding timeout. This policy is called ADVANCED polocy
     * </p>
     * <p>
     * This politic is in use when the STUNMinimumKeepAlivePeriod parameter
     * value is not equal to the STUNMaximumKeepAlivePeriod parameter value.
     * </p>
     * <p>
     * <p>
     * The CPE has to send STUN Binding Request message using another source
     * port (called secondary source) and use the RESPONSE-ADDRESS attribute
     * on the Binding Request to indicate that the STUN Binding Response be
     * sent to the primary source port (the port on which the CPE is listening
     * for UDPConnectionRequest message).
     * </p>
     */
    private static final int ADVANCED_POLICY = 2;
    /** STUNMinimumKeepAlivePeriod parameter name. */
    private static final String STUN_MINIMUM_KEEP_ALIVE_PERIOD =
            "ManagementServer.STUNMinimumKeepAlivePeriod";
    /** STUNMaximumKeepAlivePeriod parameter name. */
    private static final String STUN_MAXIMUM_KEEP_ALIVE_PERIOD =
            "ManagementServer.STUNMaximumKeepAlivePeriod";
    /** URL parameter name. */
    private static final String URL = "ManagementServer.URL";
    /** StunServerAddress parameter name. */
    private static final String STUN_SERVER_ADDRESS =
            "ManagementServer.STUNServerAddress";
    /** StunServerPort parameter name. */
    private static final String STUN_SERVER_PORT =
            "ManagementServer.STUNServerPort";
    /**
     * <p>
     * default stun keep alive period (for mini/maxi).
     * </p>
     * <p>
     * WARNING : this is an arbitrary value choosen by the author of the this
     * class
     * </p>
     */
    private static final int DEFAULT_STUN_KEEP_ALIVE_PERIOD = 30;
    /** policy state. */
    private int policy = NONE;
    /** parameter data. */
    private final IParameterData parameterData;
    /** stun client. */
    private final STUNClient stunClient;
    /** STUNMaximumKeepAlivePeriod parameter. */
    private Parameter stunMaximumKeepAlivePeriodParameter = null;
    /** STUNMinimumKeepAlivePeriod parameter. */
    private Parameter stunMinimumKeepAlivePeriodParameter = null;
    /** STUNServerAddress parameter. */
    private Parameter stunServerAddressParameter = null;
    /** STUNServerPort parameter. */
    private Parameter stunServerPortParameter = null;
    /** The url parameter. */
    private Parameter urlParameter = null;
    /** message sender. */
    private BasicBindingPolicy messageSender = null;
    /** advanced binding policy. */
    private AdvancedBindingPolicy advancedBindingPolicy = null;
    /**
     * <p>
     * Initializes the Binding Maintainer.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>gets the STUNMinimumKeepAlivePeriod parameter and adds an
     * observer.</li>
     * <li>gets the STUNMaximumKeepAlivePeriod parameter and adds an
     * observer.</li>
     * <li>gets the STUNServerAddress parameter and adds an observer.</li>
     * <li>gets the STUNServerPort parameter and adds an observer.</li>
     * </ul>
     * @param iParameterData the parameter data
     * @param stunClient the stun client
     */
    public BindingMaintainer(final IParameterData iParameterData,
            final STUNClient stunClient) {
        this.parameterData = iParameterData;
        this.stunClient = stunClient;
        // get the parameters and add Observers
        try {
            stunMinimumKeepAlivePeriodParameter =
                    parameterData
                            .createOrRetrieveParameter(parameterData.getRoot()
                                                       + STUN_MINIMUM_KEEP_ALIVE_PERIOD);
            stunMinimumKeepAlivePeriodParameter.addObserver(this);
            stunMaximumKeepAlivePeriodParameter =
                    parameterData
                            .createOrRetrieveParameter(parameterData.getRoot()
                                                       + STUN_MAXIMUM_KEEP_ALIVE_PERIOD);
            stunMaximumKeepAlivePeriodParameter.addObserver(this);
            stunServerAddressParameter =
                    parameterData
                            .createOrRetrieveParameter(parameterData.getRoot()
                                                       + STUN_SERVER_ADDRESS);
            stunServerAddressParameter.addObserver(this);
            stunServerPortParameter =
                    parameterData
                            .createOrRetrieveParameter(parameterData.getRoot()
                                                       + STUN_SERVER_PORT);
            stunServerPortParameter.addObserver(this);
            
            urlParameter =
                    parameterData.createOrRetrieveParameter(parameterData
                            .getRoot()
                                                            + URL);
        } catch (Fault e) {
            Log.error("should not occured", e);
        }
    }
    /**
     * <p>
     * Start the binding maintainer.
     * </p>
     * <p>
     * Calls the {@link #updateMaintainer()} method.
     * </p>
     */
    public final void start() {
        Log.info("start binding maintainer");
        updateMaintainer();
    }
    /**
     * <p>
     * Stop the binding maintainer in use.
     * </p>
     */
    public final void stop() {
        Log.info("stop binding maintainer");
        stopPolicyInUse();
    }
    /**
     * <p>
     * Observer method.
     * </p>
     * <p>
     * An observer has been added for the following parameters:
     * <ul>
     * <li>ManagementServer.STUNServerAddress.</li>
     * <li>ManagementServer.STUNServerPort.</li>
     * <li>ManagementServer.STUNMaximumKeepAlivePeriod.</li>
     * <li>ManagementServer.STUNMinimumKeepAlivePeriod.</li>
     * </ul>
     * For all the these parameters, the {@link #updateMaintainer()} method is
     * called.
     * <p>
     * 
     * @param arg0 the arg0
     * @param arg1 the arg1
     */
    public final void update(final Observable arg0, final Object arg1) {
        updateMaintainer();
    }
    /**
     * <p>
     * Create or update the Maintainer.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>stop the current policy in use.</li>
     * <li>get the value of the STUNMaximumKeepAlivePeriod parameter and the
     * STUNMinimumKeepAlivePeriod parameter.</li>
     * <li>compare those two values:
     * <ul>
     * <li>if they are equal, the BASIC binding maintainer policy is used.</li>
     * <li>else, the ADVANCED binding maintainer policy is used.</li>
     * </ul>
     * </li>
     * </ul>
     * </p>
     */
    private void updateMaintainer() {
        stopPolicyInUse();
        if (getSTUNMaximumKeepAlivePeriodValue() != getSTUNMinimumKeepAlivePeriodValue()) {
            // ADVANCED policy
            Log.info("Binding maintainer uses the ADVANCED policy");
            this.policy = ADVANCED_POLICY;
            advancedBindingPolicy =
                    new AdvancedBindingPolicy(stunClient,
                            getSTUNServerAddress(), getSTUNServerPortValue(),
                            getSTUNMinimumKeepAlivePeriodValue(),
                            getSTUNMaximumKeepAlivePeriodValue());
            advancedBindingPolicy.start();
        } else {
            // BASIC policy
            this.policy = BASIC_POLICY;
            Log.info("Binding maintainer uses the BASIC policy");
            messageSender =
                    new BasicBindingPolicy(stunClient,
                            getSTUNMinimumKeepAlivePeriodValue(),
                            getSTUNServerAddress(), getSTUNServerPortValue(),
                            "");
            messageSender.start();
        }
    }
    /**
     * <p>
     * Stop the current binding maintainer in use.
     * </p>
     */
    private void stopPolicyInUse() {
        if (this.policy == BASIC_POLICY) {
            messageSender.stop();
            messageSender = null;
        } else if (this.policy == ADVANCED_POLICY) {
            advancedBindingPolicy.stop();
            advancedBindingPolicy = null;
        }
        // nothing to do for the NONE policy
    }
    /**
     * <p>
     * Get the value of the ManagementServer.STUNMinimumKeepAlivePeriod
     * parameter.
     * </p>
     * 
     * @return the value or the parameter or the DEFAULT value if any error
     *         occurs.
     */
    private int getSTUNMinimumKeepAlivePeriodValue() {
        if (stunMinimumKeepAlivePeriodParameter != null) {
            try {
                int value =
                        Integer.parseInt(stunMinimumKeepAlivePeriodParameter
                                .getTextValue(null));
                return value;
            } catch (NumberFormatException nfe) {
                Log
                        .error("unable to get the int value of the STUNMinimumKeepAlivePeriod parameter");
            }
        }
        return DEFAULT_STUN_KEEP_ALIVE_PERIOD;
    }
    /**
     * <p>
     * Get the value of the ManagementServer.STUNMaximumKeepAlivePeriod
     * parameter.
     * </p>
     * 
     * @return the value or the parameter or the DEFAULT value if any error
     *         occurs.
     */
    private int getSTUNMaximumKeepAlivePeriodValue() {
        if (stunMaximumKeepAlivePeriodParameter != null) {
            try {
                int value =
                        Integer.parseInt(stunMaximumKeepAlivePeriodParameter
                                .getTextValue(null));
                return value;
            } catch (NumberFormatException nfe) {
                Log
                        .error("unable to get the int value of the STUNMaximumKeepAlivePeriod parameter");
            }
        }
        return DEFAULT_STUN_KEEP_ALIVE_PERIOD;
    }
    /**
     * <p>
     * Get the STUN port.
     * </p>
     * <p>
     * By default return 3478.
     * </p>
     * @return the port
     */
    private int getSTUNServerPortValue() {
        try {
            return Integer
                    .parseInt(stunServerPortParameter.getTextValue(null));
        } catch (NumberFormatException nfe) {
            Log.error("unable to get the int value of the port");
        }
        return 3478;
    }
    /**
     * <p>
     * Get the STUN server address.
     * </p>
     * <p>
     * If the IGD.ManagementServer.StunServerAddress is not set, the
     * IGD.ManagementServer.URL parameter is used (only the host portion).
     * </p>
     * 
     * @return the server address
     */
    private String getSTUNServerAddress() {
        String stunServer = null;
        stunServer = stunServerAddressParameter.getTextValue(null);
        if ((stunServer == null) || ("".equals(stunServer))) {
            // get the value of the url parameter
            String url = urlParameter.getTextValue("");
            // extract the host portion
            try {
                String[] tokens = StringUtil.simpleSplit(url, "http://");
                if (tokens.length == 2) {

                    String address = tokens[1];
                    Log.debug("url sans http = " + address);
                    int index = address.indexOf(":");
                    if (index != -1) {
                        // host:port case
                        Log.debug("cas host:port");
                        stunServer = address.substring(0, index);
                    } else {
                        // host case (no port)
                        // look for "/" character
                        Log.debug("cas host");
                        index = address.indexOf("/");
                        if (index != -1) {
                            stunServer = address.substring(0, index);
                        } else {
                            Log.error("unable to extract stun server address for the URL : " + url + " !");
                        }
                    }
                }
            } catch (Exception e1) {
                Log.error("unable to split url = " + url + " with :", e1);
            }
        }
        Log.debug("stun server address = " + stunServer);
        return stunServer;
    }
}
