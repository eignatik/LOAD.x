package ru.loadtest.app.HTTPConnection;

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

    public String getHTMLPageByURL(String address){
        address = appendFullPath(address);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(address);
        String entityContent = new String("");
        try (CloseableHttpResponse response = httpClient.execute(request)){
            //commented lines is testing lines to output results
//            logger.info(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            entityContent = EntityUtils.toString(entity);
            logger.info(address);
//            logger.info(entityContent);
        } catch (IOException e){
            logger.error("\nCan't get response from " + address + "\n");
        } finally {
            return entityContent;
        }
    }

    private String appendFullPath(String address){
        StringBuilder path = new StringBuilder();
        if(!address.contains("http") && !address.contains("mailto")){
            path.append(baseAddress);
        }
        path.append(address);
        return path.toString();
    }

    public static void setBaseAddress(String address){
        baseAddress = address;
    }
}
