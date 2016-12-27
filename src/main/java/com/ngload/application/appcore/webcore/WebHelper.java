package com.ngload.application.appcore.webcore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebHelper {
    public  static  final Logger logger = LogManager.getLogger(WebHelper.class.getName());
    private static String workURL;

    public static String getWorkURL() {
        return workURL;
    }

    public static void setWorkURL(String workURL) {
        WebHelper.workURL = workURL;
    }
}
