package org.loadx.application.http.executor;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.util.function.Function;

/**
 * The interface for request executors which are used during loading tasks executions.
 */
public interface RequestExecutor {
    <T, R> CloseableHttpResponse execute(HttpGet getRequest, Function<T, R> requestCallback);
}
