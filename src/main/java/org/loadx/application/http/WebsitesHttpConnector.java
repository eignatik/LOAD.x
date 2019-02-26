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
import org.springframework.util.Assert;

import java.util.regex.Pattern;

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
    private static final Pattern URL_PATTERN = Pattern.compile("(^(www|http:\\/\\/|https:\\/\\/).+\\.\\w+)$");

    private String baseUrl;
    private CloseableHttpClient httpClient;
    private CookieStore cookieStore;
    private HttpContext httpContext;
    private RequestConfig requestConfig;

    private WebsitesHttpConnector() {
        // private constructor for builder
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Creates default connector with default properties for httpClient, cookieStore, configurations etc.
     *
     * @return the configured default instance of WebsitesHttpConnector.
     */
    private static WebsitesHttpConnector createDefault() {
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

    private static void validate(WebsitesHttpConnector connector) {
        String baseUrl = connector.getBaseUrl();
        Assert.notNull(baseUrl, "The base URL shouldn't be null");
        Assert.isTrue(URL_PATTERN.matcher(baseUrl).matches(), "The base URL doesn't match the website pattern");
    }

    public static class ConnectorBuilder {
        private WebsitesHttpConnector connector;

        private ConnectorBuilder(WebsitesHttpConnector connector) {
            this.connector = connector;
        }

        public static ConnectorBuilder createDefault() {
            return new ConnectorBuilder(WebsitesHttpConnector.createDefault());
        }

        public static ConnectorBuilder createCustom() {
            return new ConnectorBuilder(new WebsitesHttpConnector());
        }

        public ConnectorBuilder withCookieStore(CookieStore cookieStore) {
            this.connector.cookieStore = cookieStore;
            return this;
        }

        public ConnectorBuilder withHttpContext(HttpContext context) {
            this.connector.httpContext = context;
            return this;
        }

        public ConnectorBuilder withHttpClient(CloseableHttpClient httpClient) {
            this.connector.httpClient = httpClient;
            return this;
        }

        public ConnectorBuilder withBaseUrl(String baseUrl) {
            connector.baseUrl = baseUrl;
            return this;
        }

        public WebsitesHttpConnector build() {
            validate(connector);
            return connector;
        }
    }
}
