package ru.loadtest.app.LoadTest.HTTPConnection;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HTTPConnection {
    public static final Logger logger = LogManager.getLogger(HTTPConnection.class.getName());
    private static String baseAddress;

    String getHTMLPageByURL(String address) {
        return getHTTPEntityContent(appendFullPath(address));
    }

    private String appendFullPath(String address) {
        StringBuilder path = new StringBuilder();
        if (!address.contains("http") && !address.contains("mailto")) {
            path.append(baseAddress);
        }
        if(!address.isEmpty()){
            String str = address.substring(0, 4);
            if(address.charAt(0) != '/' && !str.equals("http")){
                path.append("/");
            }
        }
        path.append(address);
        return path.toString();
    }

    private String getHTTPEntityContent(String address) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(address);
        String entityContent = "";
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            entityContent = getEntityContentFromResponse(response);
            logger.info(address);
        } catch (IOException e) {
            logger.error("\nCan't get content from " + address + "\n");
        }
        return entityContent;
    }

    private String getEntityContentFromResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    static void setBaseAddress(String address) {
        baseAddress = address;
    }

    static String getBaseAddress() {
        return baseAddress;
    }
}
