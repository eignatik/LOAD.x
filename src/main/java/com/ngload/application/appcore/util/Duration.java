package com.ngload.application.appcore.util;

/**
 * Provides a few methods to operate with test durations
 */
public class Duration {
    private int duration;

    /**
     * Constructor for duration from formatted String
     * @param duration must be in HH:MM:SS format
     */
    public Duration(String duration) {

    }

    private int parseDurationToInt(String duration) {
        if (duration.matches("\\d{2}\\:\\d{2}\\:\\d[0-5]{1}\\d[0-9]{1}")) {

        }
        return 0;
    }

    int getDuration() {
        return duration;
    }
}
