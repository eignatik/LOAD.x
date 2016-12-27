package ru.loadtest.app.LoadTest.AppCore;

import java.util.List;

/**
 * Util class provide several static methods for operating with links, parsed data, and so on.
 */
public class Util {
    private static String workURL;

    public static boolean isLinkInDomain(String URL) {
        return URL.contains(workURL) || URL.charAt(0) == '/' || URL.matches("^(?!http://|https://|www\u002e|#).*");
    }

    public static boolean isLinkContainProtocols(String address) {
        return address.contains("https://") || address.contains("http://");
    }

    public static boolean isShortLink(String address) {
        return !address.contains("http") && !address.contains("mailto");
    }

    public static boolean isShortLinkWithoutSlash(String address) {
        return !address.isEmpty() && address.charAt(0) != '/' && !isLinkContainProtocols(address);
    }

    public static void setWorkURL(String URL) {
        workURL = removeProtocols(URL);
    }

    public static String removeProtocols(String URL) {
        return URL.replaceAll("http://|https://|www.", "");
    }

    public static void setSleeping(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
