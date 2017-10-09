package com.ngload.application.loadtest;

import com.ngload.application.appcore.util.Duration;

import java.net.URL;

/**
 * Base load test class to provide common method and tools to operate with concrete classes of load testing
 * Any concrete LoadTest classes have to extend this abstract class
 * All methods here provide builder concepts by returning current instance (like tail functions)
 */
public class BaseLoadTest {
    protected URL url;
    protected Duration duration;
    protected int timeout;
    protected double expectedQualityPercentage;
    //TODO: add a one more fields to tune load test

    /**
     * Set URL to operate with
     * @param url jave.net.URL object
     * @return load test instance
     */
    protected BaseLoadTest withUrl(URL url) {
        this.url = url;
        return this;
    }

    protected BaseLoadTest withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    protected BaseLoadTest withExpectedQualityPercentage(double expectedQualityPercentage) {
        this.expectedQualityPercentage = expectedQualityPercentage;
        return this;
    }

}
