package com.ngload.application.appcore.webcore.entities;

public class RequestStatistics {
    private long requestCount;
    private long sumOfRequestCounts;
    private long maxRequestTime;
    private long minRequestTime;
    private long avgRequestTime;

    public RequestStatistics() {

    }

    public RequestStatistics(long requestCount, long sumOfRequestCounts, long maxRequestTime, long minRequestTime, long avgRequestTime) {
        this.requestCount = requestCount;
        this.sumOfRequestCounts = sumOfRequestCounts;
        this.maxRequestTime = maxRequestTime;
        this.minRequestTime = minRequestTime;
        this.avgRequestTime = avgRequestTime;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public long getSumOfRequestCounts() {
        return sumOfRequestCounts;
    }

    public long getMaxRequestTime() {
        return maxRequestTime;
    }

    public long getMinRequestTime() {
        return minRequestTime;
    }

    public long getAvgRequestTime() {
        return avgRequestTime;
    }

    public void addRequest(long requestTime) {
        this.requestCount++;
        this.sumOfRequestCounts += requestTime;
        calculateStatistics(requestTime);
    }

    private void calculateStatistics(long requestTime) {
        calculateMaxAndMinValues(requestTime);
        calculateAverageValue(requestTime);
    }

    private void calculateMaxAndMinValues(long requestTime) {
        if (requestCount == 1) {
            maxRequestTime = minRequestTime = requestTime;
        } else {
            maxRequestTime = (requestTime >= maxRequestTime)? requestTime : maxRequestTime;
            minRequestTime = (requestTime <= minRequestTime)? requestTime : minRequestTime;
        }
    }

    private void calculateAverageValue(long avgRequestTime) {
        avgRequestTime = sumOfRequestCounts / requestCount;
    }
}
