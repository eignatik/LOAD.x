package com.ngload.application.loadtest;

import com.ngload.application.appcore.entities.statistic.IStatistic;
import com.ngload.application.appcore.util.Duration;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Base load test class to provide common method and tools to operate with concrete classes of load testing
 * Any concrete LoadTest classes have to extend this abstract class
 * All methods here provide builder concepts by returning current instance (like tail functions)
 */
public abstract class BaseLoadTest {
    protected URL url;
    protected Duration duration;
    protected int timeout;
    protected double expectedQualityPercentage;
    protected List<IStatistic> statistics;
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

    /**
     * Set custom maximal timeout between requests during all load testing
     * Maximal request timeout is needed to implement random transitions during time interval
     * @param timeout timeout in ms
     * @return
     */
    protected BaseLoadTest withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Set expected quality percentage for whole testing process.
     * TODO: implement quality counting
     * @param expectedQualityPercentage double percentage (e.g 0.45, 0.5, etc)
     * @return
     */
    protected BaseLoadTest withExpectedQualityPercentage(double expectedQualityPercentage) {
        this.expectedQualityPercentage = expectedQualityPercentage;
        return this;
    }

    /**
     * Set concrete statistic implementation. Multiple args are supported
     * @param statisticImpls concrete implementations of IStatistic
     * @return
     */
    protected BaseLoadTest withStatistic(IStatistic...statisticImpls) {
        statistics.addAll(Arrays.asList(statisticImpls));
        return this;
    }

    /**
     * Set custom load test duration from Duration.class
     * TODO: implement Duration parser/analyzer inside executors or smth. like that
     * @param duration
     * @return
     */
    protected BaseLoadTest withDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Have to be overrided to perform load tests
     * @return
     */
    abstract public BaseLoadTest executeLoad();

}
