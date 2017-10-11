package com.ngload.application.appcore.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides a few methods to operate with test durations
 */
public class Duration {
    private static final Logger logger = LogManager.getLogger(Duration.class);
    private int duration;

    /**
     * Constructor for duration from formatted String
     * @param duration must be in HH:MM:SS format
     */
    public Duration(String duration) {
        this.duration = parseDurationToInt(duration);
    }

    private int parseDurationToInt(String duration) {
        if (duration.matches("\\d{2}\\:\\d{2}\\:\\d[0-5]{1}\\d[0-9]{1}")) {
            String message = "Bad duration has been set" + duration;
            logger.error(message);
            throw new RuntimeException(message);
        }
        return 0;
    }

    int getDuration() {
        return duration;
    }
}
