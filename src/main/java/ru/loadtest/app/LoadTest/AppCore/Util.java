package ru.loadtest.app.LoadTest.AppCore;

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

    public static void setWorkURL(String URL) {
        workURL = removeProtocols(URL);
    }

    public static String removeProtocols(String URL) {
        return URL.replaceAll("http://|https://|www.", "");
    }
}
