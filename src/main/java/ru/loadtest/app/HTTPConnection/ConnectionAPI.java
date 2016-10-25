package ru.loadtest.app.HTTPConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.Pasrser.Parser;

import java.util.List;
import java.util.Random;

public class ConnectionAPI {
    public static final Logger logger = LogManager.getLogger(ConnectionAPI.class.getName());

    private String pattern = "\\u002c|\\u0022|\\u0020";
    private List<String> links;
    private Random random;
    private Parser parser = new Parser();
    HTTPConnection connection = new HTTPConnection();
    private int i = 0;

    public void exploreLinks(String address){

        i++;
        random = new Random();
        links = getLinksFromURL(address);
        String randomLink = getRandomLink(random.nextInt(links.size()));
        parser.parseLinks(connection.getHTMLPageByURL(randomLink));
        if(i>10){
            return;
        }
        exploreLinks(randomLink);
    }

    public void setBaseURL(String url){
        HTTPConnection.setBaseAddress(url);
    }

    private List<String> getLinksFromURL(String url){
        parser.parseLinks(connection.getHTMLPageByURL(url));
        return parser.getListOfLinks();
    }

    private String getRandomLink(int number){
        String[] str = links.get(number).split(":", 2);
        return deleteUnnecessaryChars(str[1]);
    }

    private String deleteUnnecessaryChars(String source){
        return source.replaceAll(pattern, "");
    }

    private void printLinks(){
        links.forEach(System.out::println);
    }
}
