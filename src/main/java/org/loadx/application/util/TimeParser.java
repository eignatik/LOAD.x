package org.loadx.application.util;

import java.util.regex.Pattern;

/**
 * Util class to parsing loading times.
 */
public final class TimeParser {

    private static final int MAX_DAYS = 2;
    private static final Pattern DAYS = Pattern.compile("\\d+d");
    private static final Pattern HOURS = Pattern.compile("\\d+h");
    private static final Pattern MINS = Pattern.compile("\\d+m");

    private TimeParser() {
        // hidden constructor for util class
    }

    /**
     * Parses time passed in the format dd hh mm in milliseconds.
     * <p>
     * E.g. given 2h 10m will be 7800000 ms.
     *
     * @param time string complied to the format dd hh mm.
     * @return calculated milliseconds in int.
     */
    public static int parseTime(String time) {
        if (time == null) {
            throw new IllegalArgumentException();
        }
        String[] ranges = time.split("\\s");

        int result = 0;
        for (int i = 0; i < ranges.length; i++) {
            String span = ranges[i];
            if (DAYS.matcher(span).matches()) {
                result += getMillis(span, Type.DAY);
                continue;
            }
            if (HOURS.matcher(span).matches()) {
                result += getMillis(span, Type.HOUR);
                continue;
            }
            if (MINS.matcher(span).matches()) {
                result += getMillis(span, Type.MIN);
                continue;
            }
            throw new IllegalArgumentException();
        }
        return result;
    }

    private static int getMillis(String span, Type type) {
        int modifier = 0;
        int number = Integer.parseInt(span.replaceAll("\\D+", ""));
        switch (type) {
            case DAY:
                if (number > MAX_DAYS) {
                    throw new IllegalArgumentException();
                }
                modifier = 1000 * 60 * 60 * 24;
                break;
            case HOUR:
                modifier = 1000 * 60 * 60;
                break;
            case MIN:
                modifier = 1000 * 60;
                break;
        }

        return number * modifier;
    }

    private enum Type {
        MIN, HOUR, DAY
    }

}
