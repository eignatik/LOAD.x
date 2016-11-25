package ru.loadtest.app.LoadTest.AppCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionAPI extends Thread {
    public static final Logger logger = LogManager.getLogger(ConnectionAPI.class.getName());

    private Random random = new Random();
    private HTTPConnection connection;
    private static Map<String, Page> sitePages = new ConcurrentHashMap<>();
    private String baseURL;
    private String URL;
    private long requestTime;

    private static long timeout = 300000;
    private static int topRange = 10000;

    public ConnectionAPI(String baseURL, String startURL) {
        this.baseURL = baseURL;
        this.URL = startURL;
        Util.setWorkURL(this.baseURL);
    }

    @Override
    public void run() {
        startExplore();
    }

    private void startExplore() {
        connection = new HTTPConnection(this.baseURL);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        while (currentTime <= timeout) {
            currentTime = System.currentTimeMillis() - startTime;
            sleepByCondition();
            explore();
        }
        logger.info("Timeout. (" + currentTime + ")");
    }

    private void sleepByCondition() {
        int interval = random.nextInt(topRange);
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void explore() {
        logger.info(this.URL);
        requestTime = System.currentTimeMillis();
        String htmlPage = connection.getHTMLPageByURL(this.URL);
        requestTime = System.currentTimeMillis() - requestTime;
        setNextLink(htmlPage);
    }

    private void setNextLink(String htmlPage) {
        if (sitePages.containsKey(this.URL)) {
            sitePages.get(this.URL).addRequest(requestTime);
            this.URL = sitePages.get(this.URL).getRandomLink();
        } else {
            this.URL = getNextParsedLink(htmlPage);
        }
    }

    private String getNextParsedLink(String htmlPage) {
        List<Link> links = Parser.getLinksFromHTML(htmlPage);
        String url;
        sitePages.put(this.URL, new Page(this.URL, links));
        sitePages.get(this.URL).addRequest(requestTime);
        if (links.isEmpty()) {
            url = "";
        } else {
//            int index = getRandomValue(links.size());
            url =  sitePages.get(this.URL).getRandomLink(); //links.get(index).getURL();
        }
        return url;
    }

    public static Map<String, Page> getSitePages() {
        return sitePages;
    }

    private int getRandomValue(int size) {
        return random.nextInt(size);
    }

    /**
     * Set timeout in seconds
     * @param timeout values in seconds
     */
    public static void setTimeout(long timeout) {
        ConnectionAPI.timeout = timeout*1000;
    }

    public static long getTimeout() {
        return timeout;
    }

    public static void setRequestIntervals(int topLimit) {
        topRange = topLimit;
    }
}
