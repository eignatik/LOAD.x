package org.loadx.application.http;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * The http connector that provides configured http client via flexible builder.
 *
 * The httpClient is used in RequestExecutor classes.
 * @see org.loadx.application.http.executor.RequestExecutor
 * @see CloseableHttpClient
 */
public final class WebsitesHttpConnector {

    private static final int DEFAULT_SOCKET_TIMEOUT = 60000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
    private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 60000;

    private CloseableHttpClient httpClient;
    private CookieStore cookieStore;
    private HttpContext httpContext;
    private RequestConfig requestConfig;

    private WebsitesHttpConnector() {
        // private constructor for builder
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Creates default connector with default properties for httpClient, cookieStore, configurations etc.
     *
     * @return the configured default instance of WebsitesHttpConnector.
     */
    public static WebsitesHttpConnector createDefault() {
        WebsitesHttpConnector connector = new WebsitesHttpConnector();
        connector.cookieStore = new BasicCookieStore();
        connector.requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT)
                .build();
        connector.httpClient = HttpClients.custom()
                .setDefaultCookieStore(connector.cookieStore)
                .setDefaultRequestConfig(connector.requestConfig)
                .build();
        connector.httpContext = new BasicHttpContext();
        connector.httpContext.setAttribute(HttpClientContext.COOKIE_STORE, connector.cookieStore);
        return connector;
    }
}
