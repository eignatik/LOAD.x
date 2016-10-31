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
    private HTTPConnection connection = new HTTPConnection();
    private int i = 0;

    public ConnectionAPI(String baseURL) {
        setBaseURL(baseURL);
    }

    /**
     * method that starts exploring the site from address. Reqursive method
     * @param address address that will be first link address in exploring. Empty quotes mean start from base URL
     */
    public void exploreLinks(String address) {
        i++;
        links = getLinksFromURL(address);
        String nextRandomLink = getNextRandomLink();
        parser.parseLinks(connection.getHTMLPageByURL(nextRandomLink));
        if (i > 10) {
            return;
        }
        exploreLinks(nextRandomLink);
    }

    private List<String> getLinksFromURL(String URL) {
        parser.parseLinks(connection.getHTMLPageByURL(URL));
        return parser.getListOfLinks();
    }

    private String getNextRandomLink(){
        Random random = new Random();
        String randomLink;
        while (true) {
            randomLink = getRandomLink(random.nextInt(links.size()));
            if (isCorrectLink(randomLink)) {
                break;
            }
        }
        return randomLink;
    }

    private String getRandomLink(int number) {
        String[] str = links.get(number).split("\":\u0020\"", 2);
        return deleteUnnecessaryChars(str[1]);
    }

    private String deleteUnnecessaryChars(String source) {
        String pattern = "\\u002c|\\u0022|\\u0020";
        return source.replaceAll(pattern, "");
    }

    private boolean isCorrectLink(String URL) {
        return isLinkInBaseDomain(URL) && !isLinkMailto(URL);
    }

    private boolean isLinkInBaseDomain(String URL) {
        return URL.contains(getClearBaseAddress()) || URL.charAt(0) == '\u002f' || isBaseDomainShortSlashLink(URL);
    }

    private String getClearBaseAddress(){
        return removeTransferProtocols(HTTPConnection.getBaseAddress());
    }

    private boolean isBaseDomainShortSlashLink(String URL) {
        if(!URL.contains("http://") && !URL.contains("https://") && !URL.contains("www.")){
            return isEnglishCharacter(URL);
        } else {
            return false;
        }
    }

    private boolean isEnglishCharacter(String URL){
        return URL.charAt(0) >= 'a' && URL.charAt(0) <= 'z';
    }

    private String removeTransferProtocols(String URL) {
        return URL.replaceAll("http://|https://|www\u002e", "");
    }

    private boolean isLinkMailto(String URL) {
        return URL.contains("mailto");
    }

    private void setBaseURL(String URL) {
        HTTPConnection.setBaseAddress(URL);
    }

    private void printLinks() {
        links.forEach(System.out::println);
    }
}
