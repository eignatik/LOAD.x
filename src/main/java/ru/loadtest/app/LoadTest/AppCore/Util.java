package ru.loadtest.app.LoadTest.AppCore;

/**
 * Util class provide several static methods for operating with links, parsed data, and so on.
 */
public class Util {
    private static String workURL;

    private static boolean isInDomain(String URL){
        boolean result = URL.contains(workURL) || URL.charAt(0) == '/' || URL.matches("^(?!http|https|www.).*");
        return result;
    }

    static void setWorkURL(String URL){
        workURL = removeProtocols(URL);
    }

    private static  String removeProtocols(String URL){
        return URL.replaceAll("http://|https://|www.", "");
    }
}
