package org.loadx.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "vertx")
public class VertxProperties {
    private int connectTimeout;
    private int workerPoolSize;
    private int maxWaitQueue;

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getWorkerPoolSize() {
        return workerPoolSize;
    }

    public void setWorkerPoolSize(int workerPoolSize) {
        this.workerPoolSize = workerPoolSize;
    }

    public int getMaxWaitQueue() {
        return maxWaitQueue;
    }

    public void setMaxWaitQueue(int maxWaitQueue) {
        this.maxWaitQueue = maxWaitQueue;
    }

}
