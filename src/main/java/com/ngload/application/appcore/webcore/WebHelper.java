package com.ngload.application.appcore.webcore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class WebHelper {
    public  static  final Logger logger = LogManager.getLogger(WebHelper.class.getName());
    private static String workURL;
    private static Random random = new Random();


    public static String getWorkURL() {
        return workURL;
    }

    public static void setWorkURL(String URL) {
        WebHelper.workURL = addEndSlash(URL);
    }

    public static Random getRandom() {
        return random;
    }

    private static String addEndSlash(String URL) {
        String url = URL.replaceAll("\\b\\/*", "");
        url += "/";
        return url;
    }
}
