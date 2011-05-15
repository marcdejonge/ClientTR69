package com.francetelecom.admindm.data;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

public class DebugLog implements LogService {
    public void log(int arg0, String arg1) {
        System.out.println(arg1);
    }
    public void log(int arg0, String arg1, Throwable arg2) {
        System.out.println(arg1);
        arg2.printStackTrace();
    }
    public void log(ServiceReference arg0, int arg1, String arg2) {
    }
    public void log(ServiceReference arg0, int arg1, String arg2,
            Throwable arg3) {
    }
}
