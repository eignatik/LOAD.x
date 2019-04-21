package org.loadx.application.IT;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import org.loadx.application.IT.testServer.TestServerSupport;
import org.loadx.application.http.HttpClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HttpClientIT extends TestServerSupport {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientIT.class);

    @Autowired
    private HttpClientManager httpClientManager;

    @Test
    public void testVertxClient() throws InterruptedException {
        WebClient client = httpClientManager.createClient(1);

        HttpRequest<Buffer> httpRequest = client.get(port, "localhost", "/test");

        httpRequest.send(res -> {
            LOG.info("The request succeeded: res.succeeded={}", res.succeeded());
            Assert.assertTrue(res.succeeded(), "Request should be completed successfully.");
        });

        Thread.sleep(1000);

    }

}
