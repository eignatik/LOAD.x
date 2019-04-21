package org.loadx.application.http;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.loadx.application.config.VertxProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Manager of http clients. Keeps http options for the client and provides with the possibility to create http clients
 * from given properties.
 */
public class HttpClientManager {

    private VertxProperties properties;

    @Autowired
    public HttpClientManager(VertxProperties properties) {
        this.properties = properties;
    }

    /**
     * Creates new http client with given pool size.
     *
     * @param maxPoolSize maximal connection in the pool.
     * @return new configured instance of http client.
     */
    public WebClient createClient(int maxPoolSize) {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(properties.getWorkerPoolSize()));
        WebClientOptions options = new WebClientOptions()
                .setConnectTimeout(properties.getConnectTimeout())
                .setIdleTimeout(properties.getIdleTimeout())
                .setMaxWaitQueueSize(properties.getMaxWaitQueue())
                .setMaxPoolSize(maxPoolSize);
        return WebClient.create(vertx, options);
    }

    public VertxProperties getProperties() {
        return properties;
    }

}
