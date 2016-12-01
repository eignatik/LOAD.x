package ru.loadtest.app.LoadTest.AppCore;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static ru.loadtest.app.LoadTest.AppCore.Util.*;

public class HTTPConnection {
    public static final Logger logger = LogManager.getLogger(HTTPConnection.class.getName());

    private String baseAddress;
    private BasicCookieStore cookieStore;
    private HttpContext context;
    private CloseableHttpClient httpClient;
    private RequestConfig requestConfig;
    private static int TIMEOUT_MS = 60000;

    HTTPConnection(String baseAddress) {
        this.baseAddress = baseAddress;
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
        context = new BasicHttpContext();
        context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
    }

    String getHTMLPageByURL(String address) {
        address = getEncodedAddress(removeProtocols(address));
        return getHTTPEntityContent(appendFullPath(address));
    }

    private String getEncodedAddress(String address) {
        try {
            address = URLEncoder.encode(address, "UTF-8");
        } catch(UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
        return address;
    }

    private String appendFullPath(String address) {
        StringBuilder path = new StringBuilder();
        if (!address.contains("http") && !address.contains("mailto")) {
            path.append(baseAddress);
        }
        if (!address.isEmpty()) {
            if (address.charAt(0) != '/' && !isLinkContainProtocols(address)) {
                path.append("/");
            }
        }
        path.append(address);
        return path.toString();
    }



    private String getHTTPEntityContent(String address) {
        HttpGet request = new HttpGet(address);
        request.setConfig(requestConfig);
        String entityContent = "";
        try (CloseableHttpResponse response = httpClient.execute(request, context)) {
            entityContent = getEntityContentFromResponse(response);
        } catch (IOException e) {
            logger.error("\nCan't get content from " + address + "\n");
        }
        return entityContent;
    }

    private String getEntityContentFromResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }


    String getBaseAddress() {
        return baseAddress;
    }
}
