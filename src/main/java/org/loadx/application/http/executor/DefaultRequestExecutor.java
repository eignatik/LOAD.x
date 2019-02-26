package org.loadx.application.http.executor;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.loadx.application.http.WebsitesHttpConnector;

import java.util.function.Function;

public class DefaultRequestExecutor implements RequestExecutor {

    private final WebsitesHttpConnector connector;

    public DefaultRequestExecutor(WebsitesHttpConnector connector) {
        this.connector = connector;
    }

    @Override
    public <T, R> CloseableHttpResponse execute(HttpGet getRequest, Function<T, R> requestCallback) {
        return null;
    }
}
