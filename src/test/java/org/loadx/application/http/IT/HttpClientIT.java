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
import org.loadx.application.http.WebsitesHttpConnector;
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

    @Autowired
    private WebsitesHttpConnector httpConnector;

    private static String handleResponse(HttpResponse res) {
        Assert.assertEquals(res.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        return "SUCCESS";
    }

    @Test
    public void testHttpClientCanDoRequests() throws IOException {
        HttpUriRequest request = new HttpGet(String.format(URL_TEMPLATE, port));
        httpConnector.getHttpClient().execute(request, HttpClientIT::handleResponse);
    }

    @Test
    public void testVertxClient() throws InterruptedException {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(100));
        WebClientOptions options = new WebClientOptions()
                .setConnectTimeout(15000)
                .setMaxPoolSize(5)
                .setMaxWaitQueueSize(1000);
        WebClient client = WebClient.create(vertx, options);

        HttpRequest<Buffer> httpRequest = client.get("eignatik.space", "/");

        httpRequest.putHeader("x-response-time", "");
        httpRequest.send(res -> {
            if (res.failed()) {
                Throwable cause = res.cause(); // exception of Queue as well might be here
            }
            String header = res.result().getHeader("x-response-time");
        });

        Thread.sleep(10000);
    }

}
