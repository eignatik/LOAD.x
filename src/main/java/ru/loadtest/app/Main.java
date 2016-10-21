package ru.loadtest.app;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.Pasrser.Parser;

import java.io.IOException;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class.getName());

    private static String html = "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "<title>JSoup Example</title>"
            + "</head>"
            + "<body>"
            + "<a href=\"link\">Text</a>"
            + "<a href=\"link1\">Text1</a>"
            + "<a href=\"link2\">Text2</a>"
            + "<a href=\"link3\">Text3</a>"
            + "</table>"
            + "</body>"
            + "</html>";

    public static void main(String[] args){
        HTTPConnection connection = HTTPConnection.getInstance();
        connection.testConnection("http://google.com/");
        Parser parser = new Parser();
        parser.parseLinks(html);
    }
}
