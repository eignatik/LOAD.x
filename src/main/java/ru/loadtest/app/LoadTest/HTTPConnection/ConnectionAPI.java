package ru.loadtest.app.LoadTest.HTTPConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.Pasrser.Parser;

import java.util.List;
import java.util.Random;

public class ConnectionAPI {
    public static final Logger logger = LogManager.getLogger(ConnectionAPI.class.getName());

    private List<String> links;
    private Parser parser = new Parser();
    HTTPConnection connection = new HTTPConnection();
    private int i = 0;

    public void exploreLinks(String address) {

        i++;
        Random random = new Random();
        links = getLinksFromURL(address);
        String randomLink;
        while (true) {
            randomLink = getRandomLink(random.nextInt(links.size()));
            if (isCorrectLink(randomLink)) {
                break;
            }
        }

        parser.parseLinks(connection.getHTMLPageByURL(randomLink));
        if (i > 1000) {
            return;
        }
        exploreLinks(randomLink);
    }

    public void setBaseURL(String URL) {
        HTTPConnection.setBaseAddress(URL);
    }

    private boolean isCorrectLink(String URL) {
        return isLinkInBaseDomain(URL) && !isLinkMailto(URL);
    }

    private boolean isLinkInBaseDomain(String URL) {
        return URL.contains(removeTransferProtocols(HTTPConnection.getBaseAddress())) || URL.charAt(0) == '\u002f';
    }

    private String removeTransferProtocols(String URL){
        return URL.replaceAll("http://|https://|www\u002e", "");
    }

    private boolean isLinkMailto(String URL) {
        return URL.contains("mailto");
    }

    private List<String> getLinksFromURL(String URL) {
        parser.parseLinks(connection.getHTMLPageByURL(URL));
        return parser.getListOfLinks();
    }

    private String getRandomLink(int number) {
        String[] str = links.get(number).split("\":\u0020\"", 2);
        return deleteUnnecessaryChars(str[1]);
    }

    private String deleteUnnecessaryChars(String source) {
        String pattern = "\\u002c|\\u0022|\\u0020";
        return source.replaceAll(pattern, "");
    }

    private void printLinks() {
        links.forEach(System.out::println);
    }
}
