package com.ngload.application.appcore.webcore;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebConnector {
    private static final Logger logger = LogManager.getLogger(WebConnector.class.getName());
    private static int TIMEOUT_MS = 60000;

    private BasicCookieStore cookieStore;
    private HttpContext httpContext;
    private CloseableHttpClient httpClient;
    private RequestConfig requestConfig;

    public WebConnector(String workURL) {
        WebHelper.setWorkURL(workURL);
        cookieStore = new BasicCookieStore();
        requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .setSocketTimeout(TIMEOUT_MS)
                .setConnectTimeout(TIMEOUT_MS)
                .setConnectionRequestTimeout(TIMEOUT_MS)
                .build();
        httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .setDefaultRequestConfig(requestConfig)
                .build();
        this.httpContext = new BasicHttpContext();
        this.httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
    }

    public String getHtmlByURL(String sourceURL) {
        return getEntityContent(buildUrl(sourceURL));
    }

    private URI buildUrl(String sourceURL) {
        return buildPath(sourceURL);
    }

    private URI buildPath(String url) {
        URIBuilder uriBuilder;
        try {
            uriBuilder = (url.contains(WebHelper.getWorkURL()))? new URIBuilder(url):new URIBuilder(WebHelper.getWorkURL() + removeFirstSlashes(url));
            return new URI(uriBuilder.toString());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private String removeFirstSlashes(String URL) {
        return (URL.charAt(0) == '/')? URL.replaceFirst("/", ""):URL;
    }

    private String getEntityContent(URI url) {
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        String entityContent = "";
        try (CloseableHttpResponse response = httpClient.execute(request, httpContext)) {
            entityContent = getEntityContentFromResponse(response);
        } catch (IOException e) {
            logger.error("broken link");
        }
        return entityContent;
    }

    private String getEntityContentFromResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }
}
