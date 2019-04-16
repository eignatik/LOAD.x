package org.loadx.application.http.IT;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.loadx.application.http.IT.testServer.TestServerApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestServerApplication.class)
public class HttpClientIT {

    private static final String URL_TEMPLATE = "http://localhost:%d/test";

    @LocalServerPort
    private int port;

    @Test
    public void testVertxClient() throws InterruptedException {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(100));
        WebClientOptions options = new WebClientOptions()
                .setConnectTimeout(15000)
                .setMaxPoolSize(5)
                .setMaxWaitQueueSize(1000);
        WebClient client = WebClient.create(vertx, options);

        HttpRequest<Buffer> httpRequest = client.get(port, "localhost", "/test");

        httpRequest.putHeader("x-response-time", "");
        long startTime = System.currentTimeMillis();
        httpRequest.send(res -> {
            if (res.failed()) {
                Throwable cause = res.cause(); // exception of Queue as well might be here
            }
            long endTime = System.currentTimeMillis() - startTime;
        });

        Thread.sleep(10000);
    }

}
