package ru.loadtest.app.LoadTest.AppCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ConnectionAPI extends Thread {
    public static final Logger logger = LogManager.getLogger(ConnectionAPI.class.getName());

    private Random random = new Random();
    private HTTPConnection connection;
    private Map<String, Page> sitePages = new HashMap<>();
    private String baseURL;
    private String URL;

    private static long period = 300000;
    private int topRange = 10000;

    public ConnectionAPI(String baseURL, String startURL) {
        this.baseURL = baseURL;
        this.URL = startURL;
    }

    @Override
    public void run() {
        startExplore();
    }

    private void startExplore() {
        connection = new HTTPConnection(this.baseURL);
        Util.setWorkURL(this.baseURL);
        logger.info("Current work URL is " + this.baseURL + " Exploring starts from " + this.URL + "/");
        logger.info("Timeout in " + (this.period/1000)/60 + " min. (" + this.period/1000 + " sec.)");
        long startTime = System.currentTimeMillis();
        long currentTime;
        while (true) {
            currentTime = System.currentTimeMillis() - startTime;
            if (currentTime > this.period) {
                logger.info("Timeout. (" + currentTime + ")");
                break;
            }
            sleepByCondition();
            explore();
        }
    }

    private void sleepByCondition() {
        int interval = random.nextInt(topRange);
        try {
            Thread.sleep(interval);
            logger.info(interval + " sleep interval");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void explore() {
        String htmlPage = connection.getHTMLPageByURL(this.URL);
        if (sitePages.containsKey(this.URL)) {
            this.URL = sitePages.get(this.URL).getRandomLink();
        } else {
            this.URL = getNextParsedLink(htmlPage);
        }
    }

    private String getNextParsedLink(String htmlPage) {
        List<String> links = Parser.getLinksFromHTML(htmlPage);
        String url;
        sitePages.put(this.URL, new Page(this.URL, links));
        if (links.isEmpty()) {
            url = "";
        } else {
            int index = getRandomValue(links.size());
            url = links.get(index);
        }
        return url;
    }

    private int getRandomValue(int size) {
        return random.nextInt(size);
    }

    public static void setTimeout(long timeout) {
        period = timeout;
    }

    public void setRequestIntervals(int topRange) {
        this.topRange = topRange;
    }
}
