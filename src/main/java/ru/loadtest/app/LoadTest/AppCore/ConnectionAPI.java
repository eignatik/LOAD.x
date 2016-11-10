package ru.loadtest.app.LoadTest.AppCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ConnectionAPI {
    public static final Logger logger = LogManager.getLogger(ConnectionAPI.class.getName());
    private Random random = new Random();
    private HTTPConnection connection;
    private List<Page> visitedPages = new LinkedList<>();

    /**
     * method that starts exploring the site from address.
     *
     * @param address address that will be first link address in exploring. Empty quotes mean start from base URL
     */
    public void exploreLinks(String address, String baseURL) {
        connection = new HTTPConnection(baseURL);
        Util.setWorkURL(baseURL);
        startExplore(address);
    }

    private void startExplore(String address) {
        while(true) {
            String htmlPage = connection.getHTMLPageByURL(address);
            visitedPages.add(new Page(address));
            List<String> links = Parser.getLinksFromHTML(htmlPage);
            if(links.isEmpty()) {
                address = "";
            } else {
                int index = getRandomValue(links.size());
                address = links.get(index);
            }
        }
    }

    private int getRandomValue(int size) {
        return random.nextInt(size);
    }

    public List<Page> getVisitedPages() {
        return visitedPages;
    }
}
