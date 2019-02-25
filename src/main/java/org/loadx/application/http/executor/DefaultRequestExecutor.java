package org.loadx.application.http.executor;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DefaultRequestExecutor implements RequestExecutor {


    @Override
    public <T, R> CloseableHttpResponse execute(HttpGet getRequest, Function<T, R> requestCallback) {
        return null;
    }
}
