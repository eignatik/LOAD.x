package org.loadx.application.http.IT;

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
    private WebsitesHttpConnector connector;

    private static String handleResponse(HttpResponse res) {
        Assert.assertEquals(res.getStatusLine().getStatusCode(), HttpStatus.OK.value());
        return "SUCCESS";
    }

    @Test
    public void testHttpClientCanDoRequests() throws IOException {
        HttpUriRequest request = new HttpGet(String.format(URL_TEMPLATE, port));
        connector.getHttpClient().execute(request, HttpClientIT::handleResponse);
    }

}
