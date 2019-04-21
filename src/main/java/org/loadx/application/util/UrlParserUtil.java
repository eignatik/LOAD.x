package org.loadx.application.util;

import com.google.common.base.Strings;

import java.util.regex.Pattern;

public final class UrlParserUtil {

    private static final Pattern URL_WITH_PORT_PATTERN = Pattern.compile("\\D+\\d+/?");

    private UrlParserUtil() {
        // hidden constructor for util class
    }

    public static int getPort(String url) {
        if (Strings.isNullOrEmpty(url) || !url.contains(":")) {
            throwException(url);
        }

        String[] split = url.split(":");
        if (split.length < 2) {
            throwException(url);
        }

        String port = split[split.length - 1];
        port = port.replaceAll("\\D+", "");

        return Integer.parseInt(port);
    }

    public static boolean hasPort(String url) {
        return URL_WITH_PORT_PATTERN.matcher(url).matches();
    }

    private static void throwException(String url) {
        String message = String.format("Specified url isn't eligible to parse: url=%s", url);
        throw new IllegalArgumentException(message);
    }
}
