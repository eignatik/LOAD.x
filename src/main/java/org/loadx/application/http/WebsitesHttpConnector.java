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
import org.loadx.application.config.HttpProperties;

/**
 * The http connector that provides configured http client via flexible builder.
 *
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
        return create(new HttpProperties());
    }

    /**
     * Creates connector with custom http client properties.
     *
     * @param properties properties containing http config.
     * @return configured instance of WebSiteHttpConnector.
     */
    public static WebsitesHttpConnector createWithProperties(HttpProperties properties) {
        return create(properties);
    }

    private static WebsitesHttpConnector create(HttpProperties properties) {
        WebsitesHttpConnector connector = new WebsitesHttpConnector();
        connector.cookieStore = new BasicCookieStore();
        connector.requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .setSocketTimeout(
                        properties.getSocketTimeout() == 0 ?
                                DEFAULT_SOCKET_TIMEOUT : properties.getSocketTimeout())
                .setConnectTimeout(
                        properties.getConnectTimeout() == 0 ?
                                DEFAULT_CONNECT_TIMEOUT : properties.getConnectTimeout())
                .setConnectionRequestTimeout(
                        properties.getConnectionRequestTimeout() == 0 ?
                                DEFAULT_CONNECTION_REQUEST_TIMEOUT : properties.getConnectionRequestTimeout())
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
