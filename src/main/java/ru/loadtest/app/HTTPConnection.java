package ru.loadtest.app;

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

    private static HTTPConnection instance;

    public static synchronized HTTPConnection getInstance(){
        if(instance == null){
            return new HTTPConnection();
        }
        return instance;
    }

    public void testConnection(String address){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(address);

        try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)){
            logger.info(httpResponse.getStatusLine());
            HttpEntity httpEntity = httpResponse.getEntity();
            EntityUtils.consume(httpEntity);
            logger.info(address + " make query");
        } catch (IOException e){
            logger.error("Can't get querry from " + address);
        }
    }

    private HTTPConnection(){

    }
}
